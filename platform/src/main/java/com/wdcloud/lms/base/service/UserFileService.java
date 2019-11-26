package com.wdcloud.lms.base.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wdcloud.base.ResponseDTO;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.dto.FileInfo;
import com.wdcloud.lms.config.OssClient;
import com.wdcloud.lms.core.base.dao.CourseUserDao;
import com.wdcloud.lms.core.base.dao.StudyGroupUserDao;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.enums.*;
import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import com.wdcloud.utils.StringUtil;
import com.wdcloud.utils.TreeIdUtils;
import com.wdcloud.utils.file.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserFileService {

    private static final String FILE_NAME_APPEND = "-Copy";

    @Autowired
    private OssClient ossClient;
    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StudyGroupUserDao studyGroupUserDao;
    @Autowired
    private CourseUserDao courseUserDao;


    /**
     * 保存用户个人配置附件（如头像文件）
     * @param fileInfo
     * @return
     */
    public UserFile saveAvatar(FileInfo fileInfo) {
        return saveUserPersonalFile(fileInfo, FileUsageEnum.PROFILE);
    }

    /**
     * 个人回复附件保存（包括讨论作业附件,回复附件、公告回复附件、评分评论附件、问题回答附件）
     * @param fileInfo　附件信息
     * @return　新增附件文件
     */
    public UserFile saveReplyAttachment(FileInfo fileInfo) {
        return saveUserPersonalFile(fileInfo, FileUsageEnum.UNFILED);
    }

    public UserFile saveSubmission(FileInfo fileInfo, Course course) {
        List<UserFile> userFiles = saveSubmission(List.of(fileInfo), course);
        return userFiles.isEmpty() ? null : userFiles.get(0);
    }

    public List<UserFile> saveSubmission(List<FileInfo> fileInfos, Course course) {
        UserFile parent = userFileDao.findOne(UserFile.builder()
                .spaceType(FileSpaceTypeEnum.PERSONAL.getType())
                .dirUsage(FileUsageEnum.SUBMISSION.getUsage())
                .userId(WebContext.getUserId())
                .build());
        Integer isDirectory = Status.NO.getStatus();

        UserFile courseSubmissionDir = userFileDao.findOne(UserFile.builder().parentId(parent.getId()).courseId(course.getId()).build());
        if (courseSubmissionDir == null) {
            courseSubmissionDir = UserFile.builder()
                    .parentId(parent.getId())
                    .treeId(getNextTreeId(parent))
                    .spaceType(FileSpaceTypeEnum.PERSONAL.getType())
                    .userId(WebContext.getUserId())
                    .courseId(course.getId())
                    .isDirectory(Status.YES.getStatus())
                    .isSystemLevel(Status.YES.getStatus())
                    .allowUpload(Status.NO.getStatus())
                    .fileName(course.getName())
                    .accessStrategy(FileAccessStrategyEnum.NONE.getStrategy())
                    .status(FilePermissionStatusEnum.PUBLIC.getStatus())
                    .build();
            userFileDao.save(courseSubmissionDir);
        }

        Long parentId = courseSubmissionDir.getId();
        List<UserFile> result = new ArrayList<>();
        for (FileInfo fileInfo : fileInfos) {
            //判断文件名，若已存在自动追加-Copy
            String fileName = getNewFileName(fileInfo.getOriginName(), fileInfo.getId(), parentId, isDirectory);

            UserFile submissionFile = UserFile.builder()
                    .parentId(courseSubmissionDir.getId())
                    .treeId(getNextTreeId(courseSubmissionDir))
                    .spaceType(FileSpaceTypeEnum.PERSONAL.getType())
                    .courseId(course.getId())
                    .userId(WebContext.getUserId())
                    .isDirectory(Status.NO.getStatus())
                    .isSystemLevel(Status.YES.getStatus())
                    .fileName(fileName)
                    .fileUrl(fileInfo.getFileId())
                    .fileSize(fileInfo.getFileSize())
                    .fileType(fileInfo.getFileType())
                    .accessStrategy(FileAccessStrategyEnum.NONE.getStrategy())
                    .status(FilePermissionStatusEnum.PUBLIC.getStatus())
                    .build();
            userFileDao.save(submissionFile);
            result.add(submissionFile);
        }

        return result;
    }

    /**
     * 保存电子学档中上传的文件（图片、视频、文件）
     * @param fileInfo
     * @return
     */
    public UserFile saveUserEPortfolioFile(FileInfo fileInfo){
        return saveUserPersonalFile(fileInfo, FileUsageEnum.UNFILED);
    }

    private UserFile saveUserPersonalFile(FileInfo fileInfo, FileUsageEnum usageEnum) {
        if (!BeanUtil.checkObject(fileInfo, "originName,fileType,fileSize,fileId")) {
            throw new RuntimeException();
        }
        UserFile parent = userFileDao.findOne(UserFile.builder()
                .spaceType(FileSpaceTypeEnum.PERSONAL.getType())
                .dirUsage(usageEnum.getUsage())
                .userId(WebContext.getUserId())
                .build());

        //判断文件名，若已存在自动追加-Copy
        String fileName = getNewFileName(fileInfo.getOriginName(), fileInfo.getId(), parent.getId(), Status.NO.getStatus());

        UserFile userFile = UserFile.builder()
                .spaceType(FileSpaceTypeEnum.PERSONAL.getType())
                .userId(WebContext.getUserId())
                .isDirectory(Status.NO.getStatus())
                .isSystemLevel(Status.YES.getStatus())
                .fileName(fileName)
                .fileType(fileInfo.getFileType())
                .fileSize(fileInfo.getFileSize())
                .fileUrl(fileInfo.getFileId())
                .treeId(getNextTreeId(parent))
                .parentId(parent==null?0:parent.getId())
                .accessStrategy(FileAccessStrategyEnum.NONE.getStrategy())
                .status(FilePermissionStatusEnum.PUBLIC.getStatus())
                .build();
        userFileDao.save(userFile);
        return userFile;
    }

    /**
     * 课程内容附件保存（包括课程讨论附件、公告附件等课程内容关联附件）
     * @param fileInfo　附件信息
     * @param courseId　课程ID
     * @return 新增附件文件
     */
    public UserFile saveCourseContentAttachment(FileInfo fileInfo, Long courseId) {
        if (courseId == null || !BeanUtil.checkObject(fileInfo, "originName,fileType,fileSize,fileId")) {
            throw new RuntimeException();
        }
        return saveContentAttachment(fileInfo, courseId, null);
    }

    /**
     * 课程小组内容附件添加
     * @param fileInfo　附件信息
     * @param courseId　课程ID
     * @param studyGroupId　小组ID
     * @return 新增附件文件
     */
    public UserFile saveStudyGroupContentAttachment(FileInfo fileInfo, Long courseId, Long studyGroupId) {
        if (courseId == null || studyGroupId == null || !BeanUtil.checkObject(fileInfo, "originName,fileType,fileSize,fileId")) {
            throw new RuntimeException();
        }
        return saveContentAttachment(fileInfo, courseId, studyGroupId);
    }

    /**
     * 内容附件添加（studyGroupId为空时保存到课程空间，否则保存到小组空间）
     * @param fileInfo　附件信息
     * @param courseId　课程ID
     * @param studyGroupId　小组ID
     * @return 新增附件文件
     */
    private UserFile saveContentAttachment(FileInfo fileInfo, Long courseId, Long studyGroupId) {
        if (!BeanUtil.checkObject(fileInfo, "originName,fileType,fileSize,fileId")) {
            throw new RuntimeException();
        }

        Integer spaceType = studyGroupId == null ? FileSpaceTypeEnum.COURSE.getType() : FileSpaceTypeEnum.STUDY_GROUP.getType();
        UserFile parent = null;
        if (studyGroupId == null) {
            parent = userFileDao.findOne(UserFile.builder()
                    .spaceType(FileSpaceTypeEnum.COURSE.getType())
                    .courseId(courseId)
                    .dirUsage(FileUsageEnum.UNFILED.getUsage())
                    .build());
        } else {
            parent = userFileDao.findOne(UserFile.builder()
                    .spaceType(FileSpaceTypeEnum.STUDY_GROUP.getType())
                    .studyGroupId(studyGroupId)
                    .dirUsage(FileUsageEnum.UNFILED.getUsage())
                    .build());
        }
        Long parentId = parent.getId();
        Integer isDirectory = Status.NO.getStatus();
        //判断文件名，若已存在自动追加-Copy
        String fileName = getNewFileName(fileInfo.getOriginName(), fileInfo.getId(), parentId, isDirectory);

        UserFile userFile = UserFile.builder()
                .spaceType(spaceType)
                .courseId(courseId)
                .studyGroupId(studyGroupId)
                .isDirectory(Status.NO.getStatus())
                .isSystemLevel(Status.YES.getStatus())
                .fileName(fileName)
                .fileType(fileInfo.getFileType())
                .fileSize(fileInfo.getFileSize())
                .fileUrl(fileInfo.getFileId())
                .treeId(getNextTreeId(parent))
                .parentId(parentId)
                .accessStrategy(FileAccessStrategyEnum.NONE.getStrategy())
                .status(FilePermissionStatusEnum.PUBLIC.getStatus())
                .build();
        userFileDao.save(userFile);
        return userFile;
    }



    public UserFile initStudyGroupDir(long courseId, long studyGroupId, String name) {
        // 学习小组默认目录
        UserFile studyGroupDir = UserFile.builder()
                .spaceType(FileSpaceTypeEnum.STUDY_GROUP.getType())
                .courseId(courseId)
                .studyGroupId(studyGroupId)
                .isDirectory(Status.YES.getStatus())
                .allowUpload(Status.YES.getStatus())
                .fileName(name)
                .treeId(getNextTreeId(null))
                .parentId(Constants.TREE_ROOT_PARENT_ID)
                .accessStrategy(FileAccessStrategyEnum.NONE.getStrategy())
                .status(FilePermissionStatusEnum.PUBLIC.getStatus())
                .isSystemLevel(Status.YES.getStatus())
                .build();
        userFileDao.save(studyGroupDir);

        UserFile unfiled = UserFile.builder()
                .spaceType(FileSpaceTypeEnum.STUDY_GROUP.getType())
                .courseId(courseId)
                .studyGroupId(studyGroupId)
                .dirUsage(FileUsageEnum.UNFILED.getUsage())
                .fileName(FileUsageEnum.UNFILED.getDefName())
                .isDirectory(Status.YES.getStatus())
                .isSystemLevel(Status.YES.getStatus())
                .allowUpload(Status.YES.getStatus())
                .treeId(getNextTreeId(studyGroupDir))
                .parentId(studyGroupDir.getId())
                .accessStrategy(FileAccessStrategyEnum.NONE.getStrategy())
                .status(FilePermissionStatusEnum.PUBLIC.getStatus())
                .build();
        userFileDao.save(unfiled);

        log.info("[UserFileService] init study group directory, created root and unfiled dir, groupId={}, rootDirId={}",
                studyGroupId, studyGroupDir.getId());
        return studyGroupDir;
    }

    public UserFile initCourseSpaceDir(long courseId, String name) {
        // 课程根目录
        UserFile courseDir = UserFile.builder()
                .spaceType(FileSpaceTypeEnum.COURSE.getType())
                .courseId(courseId)
                .isDirectory(Status.YES.getStatus())
                .allowUpload(Status.YES.getStatus())
                .fileName(name)
                .treeId(getNextTreeId(null))
                .parentId(Constants.TREE_ROOT_PARENT_ID)
                .accessStrategy(FileAccessStrategyEnum.NONE.getStrategy())
                .status(FilePermissionStatusEnum.PUBLIC.getStatus())
                .isSystemLevel(Status.YES.getStatus())
                .build();
        userFileDao.save(courseDir);

        // 课程默认unfiled目录
        UserFile unfiled = UserFile.builder()
                .spaceType(FileSpaceTypeEnum.COURSE.getType())
                .courseId(courseId)
                .dirUsage(FileUsageEnum.UNFILED.getUsage())
                .fileName(FileUsageEnum.UNFILED.getDefName())
                .isDirectory(Status.YES.getStatus())
                .isSystemLevel(Status.YES.getStatus())
                .allowUpload(Status.YES.getStatus())
                .treeId(getNextTreeId(courseDir))
                .parentId(courseDir.getId())
                .accessStrategy(FileAccessStrategyEnum.NONE.getStrategy())
                .status(FilePermissionStatusEnum.PUBLIC.getStatus())
                .build();
        userFileDao.save(unfiled);
        log.info("[UserFileService] init course directory, created root and unfiled dir, courseId={}, rootDirId={}",
                courseId, courseDir.getId());

        return courseDir;
    }

    /**
     * 初始化个人空间目录
     */
    public UserFile initPersonalSpaceDir(long userId) {
        // 个人目录根目录
        UserFile myFileDir = UserFile.builder()
                .spaceType(FileSpaceTypeEnum.PERSONAL.getType())
                .userId(userId)
                .isDirectory(Status.YES.getStatus())
                .allowUpload(Status.YES.getStatus())
                .fileName(Constants.PERSONAL_ROOT_DIR_NAME)
                .treeId(getNextTreeId( null))
                .parentId(Constants.TREE_ROOT_PARENT_ID)
                .accessStrategy(FileAccessStrategyEnum.NONE.getStrategy())
                .status(FilePermissionStatusEnum.PUBLIC.getStatus())
                .isSystemLevel(Status.YES.getStatus())
                .build();
        userFileDao.save(myFileDir);

        // 个人资料相关文件：头像
        UserFile profilePictureDir = UserFile.builder()
                .spaceType(FileSpaceTypeEnum.PERSONAL.getType())
                .userId(userId)
                .dirUsage(FileUsageEnum.PROFILE.getUsage())
                .fileName(FileUsageEnum.PROFILE.getDefName())
                .isDirectory(Status.YES.getStatus())
                .isSystemLevel(Status.YES.getStatus())
                .allowUpload(Status.YES.getStatus())
                .treeId(getNextTreeId(myFileDir))
                .parentId(myFileDir.getId())
                .accessStrategy(FileAccessStrategyEnum.NONE.getStrategy())
                .status(FilePermissionStatusEnum.PUBLIC.getStatus())
                .build();
        userFileDao.save(profilePictureDir);

        // 其他文件目录：在课程内（非小组内）讨论、公告回复附件
        UserFile unfiledDir = UserFile.builder()
                .spaceType(FileSpaceTypeEnum.PERSONAL.getType())
                .userId(userId)
                .dirUsage(FileUsageEnum.UNFILED.getUsage())
                .fileName(FileUsageEnum.UNFILED.getDefName())
                .isDirectory(Status.YES.getStatus())
                .isSystemLevel(Status.YES.getStatus())
                .allowUpload(Status.YES.getStatus())
                .treeId(getNextTreeId(myFileDir))
                .parentId(myFileDir.getId())
                .accessStrategy(FileAccessStrategyEnum.NONE.getStrategy())
                .status(FilePermissionStatusEnum.PUBLIC.getStatus())
                .build();
        userFileDao.save(unfiledDir);

        UserFile submission = UserFile.builder()
                .spaceType(FileSpaceTypeEnum.PERSONAL.getType())
                .userId(userId)
                .dirUsage(FileUsageEnum.SUBMISSION.getUsage())
                .fileName(FileUsageEnum.SUBMISSION.getDefName())
                .isDirectory(Status.YES.getStatus())
                .isSystemLevel(Status.YES.getStatus())
                .allowUpload(Status.NO.getStatus())
                .treeId(getNextTreeId(myFileDir))
                .parentId(myFileDir.getId())
                .accessStrategy(FileAccessStrategyEnum.NONE.getStrategy())
                .status(FilePermissionStatusEnum.PUBLIC.getStatus())
                .build();
        userFileDao.save(submission);

        log.info("[UserFileService] init user directory, created root, unfiled and profile dir, userId={}, rootDirId={}",
                userId, myFileDir.getId());

        return myFileDir;
    }

    public void removePersonalSpaceDirs(Collection<Long> userIds) {
        Example example = userFileDao.getExample();
        example.createCriteria()
                .andIn(UserFile.USER_ID, userIds)
                .andEqualTo(UserFile.SPACE_TYPE, FileSpaceTypeEnum.PERSONAL.getType());
        userFileDao.delete(example);
    }

    public UserFile getFileRootDirectory(String treeId) {
        if (StringUtil.isEmpty(treeId) || treeId.length() < Constants.TREE_ROOT_TREE_ID_LENGTH) {
            return null;
        }

        String rootTreeId = treeId.substring(0, Constants.TREE_ROOT_TREE_ID_LENGTH);
        return userFileDao.findOne(UserFile.builder().treeId(rootTreeId).build());
    }

    public UserFile getCourseRootDirectory(Long courseId) {
        if (courseId == null) {
            return null;
        }
        UserFile record = UserFile.builder()
                .courseId(courseId)
                .parentId(Constants.TREE_ROOT_PARENT_ID)
                .spaceType(FileSpaceTypeEnum.COURSE.getType())
                .build();
        return userFileDao.findOne(record);
    }

    public List<UserFile> findCourseRootDirectories(List<Long> courseIds) {
        if (ListUtils.isEmpty(courseIds)) {
            return new ArrayList<>();
        }
        Example example = userFileDao.getExample();
        example.createCriteria()
                .andIn(UserFile.COURSE_ID, courseIds)
                .andEqualTo(UserFile.PARENT_ID, Constants.TREE_ROOT_PARENT_ID)
                .andEqualTo(UserFile.SPACE_TYPE, FileSpaceTypeEnum.COURSE.getType());
        example.orderBy(UserFile.FILE_NAME);
        return userFileDao.find(example);
    }

    public List<UserFile> findStudyGroupRootDirectories(List<Long> studyGroupIds) {
        if (ListUtils.isEmpty(studyGroupIds)) {
            return new ArrayList<>();
        }

        Example example = userFileDao.getExample();
        example.createCriteria()
                .andIn(UserFile.STUDY_GROUP_ID, studyGroupIds)
                .andEqualTo(UserFile.PARENT_ID, Constants.TREE_ROOT_PARENT_ID)
                .andEqualTo(UserFile.SPACE_TYPE, FileSpaceTypeEnum.STUDY_GROUP.getType());
        example.orderBy(UserFile.FILE_NAME);
        return userFileDao.find(example);
    }

    public UserFile getUserRootDirectory(Long userId) {
        if (userId == null) {
            return null;
        }
        return userFileDao.findOne(UserFile.builder().userId(userId)
                .parentId(Constants.TREE_ROOT_PARENT_ID)
                .spaceType(FileSpaceTypeEnum.PERSONAL.getType()).build());
    }

    public FileInfo getFileInfo(String fileId) {
        ResponseDTO response = JSON.parseObject(ossClient.fileInfo(fileId), ResponseDTO.class);
        if (response.isSuccess()) {
            FileInfo fileInfo = JSON.parseObject(((JSONObject) response.getEntity()).toJSONString(), FileInfo.class);
            return fileInfo;
        }
        return null;
    }

    public String getNextTreeId(UserFile parent) {
        if (parent == null) {
            String lastTreeId = userFileDao.getLastTreeId(Constants.TREE_ROOT_PARENT_ID);
            if (StringUtil.isNotEmpty(lastTreeId)) {
                return TreeIdUtils.produceTreeId(lastTreeId, Constants.TREE_ID_PER_LEVEL_LENGTH);
            } else {
                return TreeIdUtils.produceTreeIdByParentId(Constants.TREE_ROOT_ID, Constants.TREE_ID_PER_LEVEL_LENGTH);
            }
        }

        String lastTreeId = userFileDao.getLastTreeId(parent.getId());
        if (StringUtil.isEmpty(lastTreeId)) {
            return TreeIdUtils.produceTreeIdByParentId(parent.getTreeId(), Constants.TREE_ID_PER_LEVEL_LENGTH);
        }
        return TreeIdUtils.produceTreeId(lastTreeId, Constants.TREE_ID_PER_LEVEL_LENGTH);
    }


    public void validateFileOperatePermission(UserFile userFile) {
        FileSpaceTypeEnum spaceTypeEnum = FileSpaceTypeEnum.typeOf(userFile.getSpaceType());

        if (spaceTypeEnum == FileSpaceTypeEnum.PERSONAL) {
            if (!Objects.equals(userFile.getUserId(), WebContext.getUserId())) {
                throw new PermissionException();
            }
        } else if (spaceTypeEnum == FileSpaceTypeEnum.COURSE) {
            if (!roleService.hasTeacherOrTutorRole()) {
                throw new PermissionException();
            }
        } else if (spaceTypeEnum == FileSpaceTypeEnum.STUDY_GROUP) {
            if (!studyGroupUserDao.hasUser(userFile.getStudyGroupId(), WebContext.getUserId())
                    && !roleService.hasTeacherOrTutorRole()) {
                throw new PermissionException();
            }
        }
    }

    public void validateFileAccessPermission(UserFile userFile) {
        FileSpaceTypeEnum spaceTypeEnum = FileSpaceTypeEnum.typeOf(userFile.getSpaceType());

        if (spaceTypeEnum == FileSpaceTypeEnum.PERSONAL) {
            if (!Objects.equals(userFile.getUserId(), WebContext.getUserId())) {
                throw new PermissionException();
            }
        } else if (spaceTypeEnum == FileSpaceTypeEnum.COURSE) {
            if (!courseUserDao.hasUser(userFile.getCourseId(), WebContext.getUserId())) {
                throw new PermissionException();
            }
        } else if (spaceTypeEnum == FileSpaceTypeEnum.STUDY_GROUP) {
            if (!studyGroupUserDao.hasUser(userFile.getStudyGroupId(), WebContext.getUserId())
                    && !roleService.hasTeacherOrTutorRole()) {
                throw new PermissionException();
            }
        }
    }

    public boolean canAccessUnpublished(UserFile root) {
        if (root == null) {
            return false;
        }

        FileSpaceTypeEnum spaceTypeEnum = FileSpaceTypeEnum.typeOf(root.getSpaceType());
        if (spaceTypeEnum == FileSpaceTypeEnum.PERSONAL) {
            if (!Objects.equals(root.getUserId(), WebContext.getUserId())) {
                return false;
            }
        } else if (spaceTypeEnum == FileSpaceTypeEnum.COURSE || spaceTypeEnum == FileSpaceTypeEnum.STUDY_GROUP) {
            if (!roleService.hasTeacherOrTutorRole()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 拼接状态为published的查询sql语句
     * @param root
     * @return
     */
    public String accessPublishedSQL(UserFile root) {

        if (root == null) {
            return "1!=1";
        }

        if(roleService.hasStudentRole() || roleService.hasTeacherOrTutorRole()){
            StringBuffer sql = new StringBuffer();
            sql.append(humpToLine(UserFile.STATUS))
                    .append("=")
                    .append(FilePermissionStatusEnum.PUBLIC.getStatus());

            return sql.toString();
        }

        return "1!=1";
    }

    /**
     * 拼接状态为unpublished的查询sql语句
     * @param root
     * @return
     */
    public String accessUnpublishedSQL(UserFile root) {
        if (root == null) {
            return "1!=1";
        }

        StringBuffer sql = new StringBuffer();
        sql.append(humpToLine(UserFile.STATUS))
                .append("=")
                .append(FilePermissionStatusEnum.UNPUBLIC.getStatus());
        FileSpaceTypeEnum spaceTypeEnum = FileSpaceTypeEnum.typeOf(root.getSpaceType());
        if (spaceTypeEnum == FileSpaceTypeEnum.PERSONAL) {
            if (Objects.equals(root.getUserId(), WebContext.getUserId())) {
                return sql.toString();
            }
        } else if (spaceTypeEnum == FileSpaceTypeEnum.COURSE || spaceTypeEnum == FileSpaceTypeEnum.STUDY_GROUP) {
            if (roleService.hasTeacherOrTutorRole()) {
                return sql.toString();
            }
        }
        return "1!=1";
    }

    /**
     * 拼接状态为restricte的查询sql语句
     * @param root
     * @return 如果可以查看
     */
    public String accessRestrictedSQL(UserFile root){

        if (root == null) {
            return "1!=1";
        }

        StringBuffer sql = new StringBuffer();
        sql.append(humpToLine(UserFile.STATUS))
                .append("=")
                .append(FilePermissionStatusEnum.RESTRICTED.getStatus());

        if(roleService.hasStudentRole()){
            Date date = new Date();
            String strDateFormat = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            String currentDate = sdf.format(date);

            String start = humpToLine(UserFile.START_TIME);
            String end = humpToLine(UserFile.END_TIME);
            String accessStrategy = humpToLine(UserFile.ACCESS_STRATEGY);

            sql.append(" and ((").append(accessStrategy).append("=")
                    .append(FileAccessStrategyEnum.SCHEDULE.getStrategy()).append(" and ")
                    .append(start).append(" < \"").append(currentDate).append("\" and (")
                    .append(end).append(" is null or ").append(end).append(" > \"").append(currentDate).append("\")) or ")
                    .append(accessStrategy).append("=").append(FileAccessStrategyEnum.NONE.getStrategy()).append(")");
            return sql.toString();
        }

        if(roleService.hasTeacherOrTutorRole()){
            return sql.toString();
        }

        return "1!=1";
    }

    /**
     * 判断文件名是否已存在，存在则在名字后追加-Copy
     * @param fileInfo
     * @return
     */
    public String getPortraitName(FileInfo fileInfo){
        UserFile folder = userFileDao.findOne(UserFile.builder()
                .spaceType(FileSpaceTypeEnum.PERSONAL.getType())
                .dirUsage(FileUsageEnum.PROFILE.getUsage())
                .userId(WebContext.getUserId())
                .build());

        if(Objects.isNull(folder)){
            throw new BaseException("prop.value.not-exists", "folder", FileUsageEnum.PROFILE.getDefName());
        }else{
            return getNewFileName(fileInfo.getOriginName(), fileInfo.getId(), folder.getId(), Status.NO.getStatus());
        }
    }

    /**
     * 判断文件名是否已存在，存在则在名字后追加-Copy
     * @param originName
     * @param id
     * @param parentId
     * @param isDirectory
     * @return
     */
    public String getNewFileName(String originName, Long id, Long parentId, Integer isDirectory){
        boolean exist = userFileDao.isNameExists(id, parentId, originName, isDirectory);
        if(exist){
            String fileName = FileUtils.getFileName(originName);
            String fileSuffix = "";
            if(isDirectory.equals(Status.NO.getStatus())){
               fileSuffix = FileUtils.getFileSuffix(originName);

               if(StringUtil.isNotEmpty(fileSuffix)){
                   fileSuffix = "." + fileSuffix;
               }
            }

            Example example = userFileDao.getExample();
            example.setOrderByClause("length(file_name) desc");
            example.createCriteria()
                    .andEqualTo(UserFile.PARENT_ID, parentId)
                    .andEqualTo(UserFile.IS_DIRECTORY, isDirectory)
                    .andCondition(" file_name REGEXP \"^(" + fileName + ")(-Copy)*(" + fileSuffix + ")$\" ");

            List<UserFile> temp = userFileDao.find(example);
            originName = temp.get(0).getFileName().replaceAll(fileSuffix + "$", FILE_NAME_APPEND) + fileSuffix;
        }

        return originName;
    }

    /**
     * 组装覆盖文件对象
     * 用于上传文件时，若文件已存在，则覆盖原文件
     * @param parentDirectoryId
     * @param filename
     * @param fileInfo
     * @return
     */
    public UserFile coveredFilePackage(Long parentDirectoryId, String filename, FileInfo fileInfo){
        UserFile userFile = userFileDao.findByParentIdAndName(parentDirectoryId, filename);
        userFile.setUserId(WebContext.getUserId());
        userFile.setFileSize(fileInfo.getFileSize());
        userFile.setFileUrl(fileInfo.getFileId());

        return userFile;
    }

    /**
     * 驼峰转下划线
     * @param para
     * @return
     */
    private String humpToLine(String para){
        StringBuilder sb = new StringBuilder(para);
        int temp=0;
        if (!para.contains("_")) {
            for(int i=0;i<para.length();i++){
                if(Character.isUpperCase(para.charAt(i))){
                    sb.insert(i+temp, "_"); temp+=1;
                }
            }
        }
        return sb.toString().toLowerCase();
    }
}
