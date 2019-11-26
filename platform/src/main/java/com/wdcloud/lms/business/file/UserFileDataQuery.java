package com.wdcloud.lms.business.file;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.RoleService;
import com.wdcloud.lms.base.service.UserFileService;
import com.wdcloud.lms.business.file.vo.UserFileVo;
import com.wdcloud.lms.core.base.dao.CourseDao;
import com.wdcloud.lms.core.base.dao.StudyGroupDao;
import com.wdcloud.lms.core.base.dao.UserDao;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.enums.FileAccessStrategyEnum;
import com.wdcloud.lms.core.base.enums.FileSpaceTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.enums.UserRegistryStatusEnum;
import com.wdcloud.lms.core.base.model.StudyGroup;
import com.wdcloud.lms.core.base.model.User;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.core.base.vo.CourseJoinedVo;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;
import java.util.*;
import java.util.stream.Collectors;

@ResourceInfo(name = Constants.RESOURCE_TYPE_USER_FILE, description = "文件查询")
public class UserFileDataQuery implements IDataQueryComponent<UserFile> {

    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserFileService userFileService;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private StudyGroupDao studyGroupDao;
    @Autowired
    private UserDao userDao;

    /**
     * @api {get} /userFile/list 文件夹内容列表
     * @apiDescription 指定parentId时，返回parentId目录的子文件及目录，未指定parentId，返回用户文件列表（包括个人目录、课程目录、学习小组目录）
     * 如果parentId, spaceType均未指定，则返回用户可以访问的所有根目录
     * @apiName UserFileList
     * @apiGroup Common
     *
     * @apiParam {Number} [parentId] 父目录ID, 返回指定目录的下级文件列表（此参数优先级最高）
     * @apiParam {Number=1,2,3} [spaceType] 空间, 1: 课程 2: 小组 3: 个人（当 parentId 为空时参数有效），为3时返回当前用户个人空间根目录
     * @apiParam {Number} [courseId] 课程ID, spaceType=1时必传，返回课程根目录
     * @apiParam {Number} [studyGroupId] 小组ID, spaceType=2时必传，返回小组根目录
     *
     *
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} entity 文件列表
     * @apiSuccess {Number} entity.id 文件ID
     * @apiSuccess {Number} entity.parentId 父目录ID
     * @apiSuccess {String} entity.fileName 文件名称
     * @apiSuccess {Number} [entity.fileSize] 文件大小
     * @apiSuccess {String} [entity.fileType] 文件类型
     * @apiSuccess {Number=0,1} entity.isDirectory 是否为目录
     * @apiSuccess {Number=0,1} entity.isSystemLevel 是否系统创建目录
     * @apiSuccess {Number=1,2,3} entity.spaceType 所属空间，1：课程 2：学习小组 3：个人
     * @apiSuccess {Number} [entity.userId] 所属用户ID
     * @apiSuccess {Number} [entity.courseId] 所属课程ID
     * @apiSuccess {Number} [entity.studyGroupId] 所属学习小组ID
     * @apiSuccess {Number=1,2,3} entity.status 发布状态 1:发布,2:未发布,3:限制访问
     * @apiSuccess {String} entity.treeId TreeId
     * @apiSuccess {Number} entity.createTime 创建时间
     * @apiSuccess {Number} entity.updateTime 最近修改时间
     * @apiSuccess {Number} entity.createUserId 创建用户ID
     * @apiSuccess {Number} entity.updateUserId 修改用户ID
     * @apiSuccess {String} [entity.createUserName] 创建用户名称
     * @apiSuccess {String} [entity.updateUserName] 修改用户名称
     *
     * @apiSuccessExample {json} 响应示例：
     * {
     *     "code": 200,
     *     "message": "success",
     *     "entity": [{
     *         id: 1,
     *         courseId: 1,
     *         fileName: "abc.txt",
     *         fileType: "word",
     *         isDirectory: 0,
     *         ...
     *     }]
     * }
     *
     */
    @Override
    public List<? extends UserFile> list(Map<String, String> param) {

        List<UserFile> userFiles = new ArrayList<>();

        Long parentId = null;
        FileSpaceTypeEnum spaceTypeEnum = null;
        if (StringUtil.isNotEmpty(param.get(Constants.PARAM_SPACE_TYPE))) {
            spaceTypeEnum = FileSpaceTypeEnum.typeOf(Integer.parseInt(param.get(Constants.PARAM_SPACE_TYPE)));
        }
        if (StringUtil.isNotEmpty(param.get(Constants.PARAM_PARENT_ID))) {
            parentId = Long.parseLong(param.get(Constants.PARAM_PARENT_ID));
        }

        if (parentId != null) {
            UserFile parentDir = userFileDao.get(parentId);
            if (parentDir != null && Objects.equals(parentDir.getIsDirectory(), Status.YES.getStatus())) {
                userFileService.validateFileAccessPermission(parentDir);
                String accessPublishedSQL = userFileService.accessPublishedSQL(parentDir);
                String accessUnpublishedSQL = userFileService.accessUnpublishedSQL(parentDir);
                String accessRestrictedSQL = userFileService.accessRestrictedSQL(parentDir);

                Example example = userFileDao.getExample();
                /**
                 * 文件排序方式，按文件类型和更新时间排序。第一次创建时，创时等更时。
                 * 时间排序主要用于后台自动为文件重命名（文件重名）后，希望显示在最前面。
                 */
                example.orderBy(UserFile.IS_DIRECTORY).desc()
                        .orderBy(UserFile.UPDATE_TIME).desc()
                        .orderBy(UserFile.FILE_NAME);
                Example.Criteria criteria = example.createCriteria()
                        .andEqualTo(UserFile.PARENT_ID, parentId)
                        .andCondition("(" + accessPublishedSQL + " or (" + accessUnpublishedSQL + ") or ("
                                + accessRestrictedSQL + "))");

                userFiles.addAll(userFileDao.find(example));
            }
        } else if (spaceTypeEnum == FileSpaceTypeEnum.COURSE) {
            if (!param.containsKey(Constants.PARAM_COURSE_ID)) {
                throw new ParamErrorException();
            }

            UserFile courseRootDirectory = userFileService.getCourseRootDirectory(Long.parseLong(param.get(Constants.PARAM_COURSE_ID)));
            if (courseRootDirectory != null) {
                userFiles.add(courseRootDirectory);
            }
        } else if (spaceTypeEnum == FileSpaceTypeEnum.STUDY_GROUP) {
            if (!param.containsKey(Constants.PARAM_STUDY_GROUP_ID)) {
                throw new ParamErrorException();
            }

            List<UserFile> groupRootDirectories = userFileService.findStudyGroupRootDirectories(List.of(Long.parseLong(param.get("studyGroupId"))));
            if (!groupRootDirectories.isEmpty()) {
                userFiles.addAll(groupRootDirectories);
            }
        } else if (spaceTypeEnum == FileSpaceTypeEnum.PERSONAL) {
            UserFile myFileDir = userFileService.getUserRootDirectory(WebContext.getUserId());
            if (myFileDir != null) {
                userFiles.add(myFileDir);
            }
        } else {
            UserFile myFileDir = userFileService.getUserRootDirectory(WebContext.getUserId());
            userFiles.add(myFileDir);

            HashMap<String, Object> joinedCourseParam = new HashMap<>();
            joinedCourseParam.putAll(Map.of(Constants.PARAM_USER_ID, WebContext.getUserId(),
                    Constants.PARAM_ROLE_ID, WebContext.getRoleId(),
                    Constants.PARAM_RESOURCE_STATUS, UserRegistryStatusEnum.JOINED.getStatus()));
            joinedCourseParam.put(Constants.PARAM_STATUS, roleService.hasStudentRole() ? Status.YES.getStatus() : null);
            List<CourseJoinedVo> courseJoined = courseDao.findCourseJoined(joinedCourseParam);
            List<UserFile> courseRootDirs
                    = userFileService.findCourseRootDirectories(courseJoined.stream().map(CourseJoinedVo::getId).collect(Collectors.toList()));
            userFiles.addAll(courseRootDirs);

            // 只有学生时才返回小组空间文件夹
            if (roleService.hasStudentRole()) {
                List<StudyGroup> studyGroupJoined = studyGroupDao.findJoined(WebContext.getUserId(), null);
                List<UserFile> studyGroupRootDirs
                            = userFileService.findStudyGroupRootDirectories(studyGroupJoined.stream().map(StudyGroup::getId).collect(Collectors.toList()));
                userFiles.addAll(studyGroupRootDirs);
            }
        }

        List<UserFileVo> result = BeanUtil.beanCopyPropertiesForList(userFiles, UserFileVo.class);
        if (!result.isEmpty()) {
            Set<Long> createUserIds = result.stream()
                    .map(UserFile::getCreateUserId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            Set<Long> updateUserIds = result.stream()
                    .map(UserFile::getUpdateUserId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            Set<Long> userIds = new HashSet<>();
            userIds.addAll(createUserIds);
            userIds.addAll(updateUserIds);

            Map<Long, String> userIdNameMap = userDao.gets(userIds)
                    .stream()
                    .collect(Collectors.toMap(User::getId, User::getUsername));
            for (UserFileVo userFileVo : result) {
                userFileVo.setCreateUserName(userIdNameMap.get(userFileVo.getCreateUserId()));
                userFileVo.setUpdateUserName(userIdNameMap.get(userFileVo.getUpdateUserId()));
            }
        }
        return result;
    }

    /**
     * @api {get} /userFile/get 获取文件信息
     * @apiDescription 获取文件信息
     * @apiName UserFileGet
     * @apiGroup Common
     * @apiParam {Number} id
     * @apiSuccess {String} code 业务码
     * @apiSuccess {String} message code描述
     * @apiSuccess {Object[]} entity 文件列表
     * @apiSuccessExample {json} 响应示例：
     * {
     * "code": 200,
     * "message": "success",
     * "entity": {
     * id: 1,
     * courseId: 1,
     * fileName: "abc.txt",
     * fileType: "word",
     * isDirectory: 0,
     * ...
     * }
     * }
     */
    @Override
    public UserFile find(String id) {
        return userFileDao.get(Long.valueOf(id));
    }
}
