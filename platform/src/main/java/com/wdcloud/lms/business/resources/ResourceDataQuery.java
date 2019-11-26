package com.wdcloud.lms.business.resources;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.base.service.ResourceCommonService;
import com.wdcloud.lms.business.strategy.StrategyFactory;
import com.wdcloud.lms.business.strategy.query.QueryStrategy;
import com.wdcloud.lms.config.OssClient;
import com.wdcloud.lms.core.base.dao.*;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.ResourceFile;
import com.wdcloud.lms.core.base.model.ResourceUpdate;
import com.wdcloud.lms.core.base.model.ResourceVersion;
import com.wdcloud.lms.core.base.vo.resource.ResourceVO;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.genid.GenId;

import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@ResourceInfo(name = Constants.RESOURCE_TYPE_RES_SEARCH, description = "资源查询")
public class ResourceDataQuery implements IDataQueryComponent<ResourceVO> {

    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private ResourceAdminOperationLogDao resourceAdminOperationLogDao;

    @Autowired
    private StrategyFactory strategyFactory;

    @Autowired
    private ResourceCommonService resourceCommonService;


    /**
     * @api {get} /resSearch/get 资源详情
     * @apiDescription 资源详情
     * @apiName resSearchGet
     * @apiGroup resource
     *
     * @apiParam {String} data 资源ID
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} [message] 响应描述
     * @apiSuccess {Object} [entity] 资源信息
     * @apiSuccess {Number} entity.id 资源ID
     * @apiSuccess {Number=0,1} entity.hasNewNote 是否有新的版本信息 0; 没有，1：有
     * @apiSuccess {Number=0,1} entity.hasCheck 是否有未查看的日志 0：没有，1：有
     * @apiSuccess {Object} entity.resource 资源对象，根据类型不同而不同
     * @apiSuccess {Number=1,2,3,15} entity.originType 类型，1：作业，2：讨论，3：测验，15：课程
     * @apiSuccess {Number} entity.importCount 导入数量
     * @apiSuccess {Number} entity.favoriteCount 喜爱数量
     * @apiSuccess {String} entity.licence 版权1：Public Domain，2：CC-Attribution，3：CC-Attribution ShareAlike，
     * 4：CC-Attribution NoDerivs，5：CC-Attribution NonCommercial，6：CC-Attribution NonCommercial ShareAlike，
     * 7：CC-Attribution NonCommercial NoDerivs
     * @apiSuccess {Number=0,1} entity.isFavorite 是否是我喜爱的资源，0：不是，1：是
     * @apiSuccess {String} entity.thumbnailUrl 缩略图url
     * @apiSuccess {Number=1,2} entity.operation 可做的操作，1：import，2：import/update
     * @apiSuccess {String} entity.author 上传者名字
     * @apiSuccess {String} entity.authorId 上传者id
     * @apiSuccess {String} entity.institution 机构名字
     * @apiSuccess {Number} entity.gradeStart 年级开始
     * @apiSuccess {Number} entity.gradeEnd 年级结束
     * @apiSuccess {String} entity.description 简介
     * @apiSuccess {String[]} entity.tags 标签
     * @apiSuccess {String} entity.shareRange 分享范围
     * @apiSuccessExample {json} 响应示例:
     * {
     *     "code": 200,
     *     "entity": {
     *         "author": "admin admin",
     *         "description": "是分享作业的介绍呀",
     *         "favoriteCount": 0,
     *         "gradeEnd": 4,
     *         "gradeStart": 1,
     *         "hasCheck": 0,
     *         "id": 6,
     *         "importCount": 0,
     *         "institution": "Root",
     *         "isFavorite": 0,
     *         "licence": 6,
     *         "name": "是分享作业呀",
     *         "operation": 1,
     *         "originType": 1,
     *         "thumbnailUrl":"group1/M00/00/32/wKgFFF01asSAVVpXAAA74ROzjQo909.png",
     *         "resource": {
     *             "allowFile": 0,
     *             "allowMedia": 0,
     *             "allowText": 0,
     *             "allowUrl": 0,
     *             "description": "<p>555555555555</p>",
     *             "id": 89,
     *             "isIncludeGrade": 1,
     *             "score": 100,
     *             "showScoreType": 2,
     *             "submissionType": 4,
     *             "title": "勿删3"
     *         },
     *         "shareRange": 3,
     *         "tags": []
     *     },
     *     "message": "success"
     * }
     *
     * {
     *     "code": 200,
     *     "entity": {
     *         "author": "admin admin",
     *         "authorId": 2,
     *         "description": "是测验的介绍呀",
     *         "grade": 132,
     *         "gradeEnd": 7,
     *         "gradeStart": 2,
     *         "hasCheck": 0,
     *         "hasNewNote": 0,
     *         "id": 79,
     *         "importCount": 0,
     *         "institution": "Root",
     *         "isFavorite": 0,
     *         "licence": 6,
     *         "name": "是1623测验呀",
     *         "operation": 1,
     *         "originType": 2,
     *         "resource": {
     *             "attachmentFileId": 1086,
     *             "content": "<p>discussiondiscussiondiscussiondiscussion</p>",
     *             "id": 492,
     *             "isGrade": 0,
     *             "title": "discussion"
     *         },
     *         "shareRange": 3,
     *         "tags": ["测验",分享"],
     *         "thumbnailUrl": "group1/M00/00/33/wKgFFF04GeqARLziAABzb_BceR4896.png",
     *         "version": 1564006666000
     *     },
     *     "message": "success"
     * }
     *
     * {
     *     "code": 200,
     *     "entity": {
     *         "author": "admin admin",
     *         "authorId": 2,
     *         "description": "是测验的介绍呀",
     *         "grade": 132,
     *         "gradeEnd": 7,
     *         "gradeStart": 2,
     *         "hasCheck": 0,
     *         "hasNewNote": 0,
     *         "id": 78,
     *         "importCount": 0,
     *         "institution": "Root",
     *         "isFavorite": 0,
     *         "licence": 6,
     *         "name": "是1623测验呀",
     *         "operation": 1,
     *         "originType": 3,
     *         "resource": {
     *             "allowMultiAttempt": 1,
     *             "description": "<p>youko quizyouko quizyouko quizyouko quiz</p>",
     *             "id": 1623,
     *             "isNeedAccessCode": 1,
     *             "accessCode":"12334",
     *             "isShuffleAnswer": 1,
     *             "quizItemVOs": [
     *                 {
     *                     "createTime": 1563984728000,
     *                     "createUserId": 2,
     *                     "id": 802,
     *                     "question": {
     *                         "content": "<p>多选多选多选多选多选多选多选</p>",
     *                         "courseId": 203,
     *                         "createTime": 1563984728000,
     *                         "createUserId": 2,
     *                         "generalComment": "若喏的",
     *                         "groupId": 2,
     *                         "id": 859,
     *                         "isTemplate": 0,
     *                         "matchoptions": [],
     *                         "options": [
     *                             {
     *                                 "code": "A",
     *                                 "content": "1",
     *                                 "createTime": 1563984728000,
     *                                 "createUserId": 2,
     *                                 "id": 1631,
     *                                 "isCorrect": 0,
     *                                 "questionId": 859,
     *                                 "seq": 1,
     *                                 "updateTime": 1563984728000,
     *                                 "updateUserId": 2
     *                             },
     *                             {
     *                                 "code": "B",
     *                                 "content": "2",
     *                                 "createTime": 1563984728000,
     *                                 "createUserId": 2,
     *                                 "id": 1632,
     *                                 "isCorrect": 1,
     *                                 "questionId": 859,
     *                                 "seq": 2,
     *                                 "updateTime": 1563984728000,
     *                                 "updateUserId": 2
     *                             },
     *                             {
     *                                 "code": "C",
     *                                 "content": "3",
     *                                 "createTime": 1563984728000,
     *                                 "createUserId": 2,
     *                                 "id": 1633,
     *                                 "isCorrect": 0,
     *                                 "questionId": 859,
     *                                 "seq": 3,
     *                                 "updateTime": 1563984728000,
     *                                 "updateUserId": 2
     *                             }
     *                         ],
     *                         "questionBankId": 0,
     *                         "questionTemplateId": 0,
     *                         "score": 3000,
     *                         "seq": 0,
     *                         "title": "Question",
     *                         "type": 1,
     *                         "updateTime": 1563984728000,
     *                         "updateUserId": 2
     *                     },
     *                     "quizId": 1623,
     *                     "seq": 1,
     *                     "targetId": 859,
     *                     "type": 1,
     *                     "updateTime": 1563984728000,
     *                     "updateUserId": 2
     *                 },
     *                 {
     *                     "createTime": 1563984789000,
     *                     "createUserId": 2,
     *                     "id": 803,
     *                     "question": {
     *                         "content": "<p>多个答案答案多个答案答案多个答案答案多个答案答案多个答案答案</p>",
     *                         "courseId": 203,
     *                         "createTime": 1563984789000,
     *                         "createUserId": 2,
     *                         "generalComment": "法二测的",
     *                         "groupId": 2,
     *                         "id": 860,
     *                         "isTemplate": 0,
     *                         "matchoptions": [],
     *                         "options": [
     *                             {
     *                                 "code": "A",
     *                                 "content": "1",
     *                                 "createTime": 1563984789000,
     *                                 "createUserId": 2,
     *                                 "id": 1634,
     *                                 "isCorrect": 1,
     *                                 "questionId": 860,
     *                                 "seq": 1,
     *                                 "updateTime": 1563984789000,
     *                                 "updateUserId": 2
     *                             },
     *                             {
     *                                 "code": "B",
     *                                 "content": "2",
     *                                 "createTime": 1563984789000,
     *                                 "createUserId": 2,
     *                                 "id": 1635,
     *                                 "isCorrect": 1,
     *                                 "questionId": 860,
     *                                 "seq": 2,
     *                                 "updateTime": 1563984789000,
     *                                 "updateUserId": 2
     *                             },
     *                             {
     *                                 "code": "C",
     *                                 "content": "3",
     *                                 "createTime": 1563984789000,
     *                                 "createUserId": 2,
     *                                 "id": 1636,
     *                                 "isCorrect": 0,
     *                                 "questionId": 860,
     *                                 "seq": 3,
     *                                 "updateTime": 1563984789000,
     *                                 "updateUserId": 2
     *                             }
     *                         ],
     *                         "questionBankId": 0,
     *                         "questionTemplateId": 0,
     *                         "score": 3000,
     *                         "seq": 0,
     *                         "title": "Question",
     *                         "type": 2,
     *                         "updateTime": 1563984789000,
     *                         "updateUserId": 2
     *                     },
     *                     "quizId": 1623,
     *                     "seq": 2,
     *                     "targetId": 860,
     *                     "type": 1,
     *                     "updateTime": 1563984789000,
     *                     "updateUserId": 2
     *                 },
     *                 {
     *                     "createTime": 1563984820000,
     *                     "createUserId": 2,
     *                     "id": 804,
     *                     "question": {
     *                         "content": "<p>对或错对或错对或错对或错对或错对或错对或错对或错</p>",
     *                         "courseId": 203,
     *                         "createTime": 1563984820000,
     *                         "createUserId": 2,
     *                         "generalComment": "对或错对或错对或错",
     *                         "groupId": 2,
     *                         "id": 861,
     *                         "isTemplate": 0,
     *                         "matchoptions": [],
     *                         "options": [
     *                             {
     *                                 "code": "A",
     *                                 "content": "True",
     *                                 "createTime": 1563984820000,
     *                                 "createUserId": 2,
     *                                 "id": 1637,
     *                                 "isCorrect": 1,
     *                                 "questionId": 861,
     *                                 "seq": 1,
     *                                 "updateTime": 1563984820000,
     *                                 "updateUserId": 2
     *                             },
     *                             {
     *                                 "code": "B",
     *                                 "content": "False",
     *                                 "createTime": 1563984820000,
     *                                 "createUserId": 2,
     *                                 "id": 1638,
     *                                 "isCorrect": 0,
     *                                 "questionId": 861,
     *                                 "seq": 2,
     *                                 "updateTime": 1563984820000,
     *                                 "updateUserId": 2
     *                             }
     *                         ],
     *                         "questionBankId": 0,
     *                         "questionTemplateId": 0,
     *                         "score": 3000,
     *                         "seq": 0,
     *                         "title": "Question",
     *                         "type": 3,
     *                         "updateTime": 1563984820000,
     *                         "updateUserId": 2
     *                     },
     *                     "quizId": 1623,
     *                     "seq": 3,
     *                     "targetId": 861,
     *                     "type": 1,
     *                     "updateTime": 1563984820000,
     *                     "updateUserId": 2
     *                 },
     *                 {
     *                     "createTime": 1563984841000,
     *                     "createUserId": 2,
     *                     "id": 805,
     *                     "question": {
     *                         "content": "<p>文章问题文章问题文章问题文章问题文章问题文章问题文章问题</p>",
     *                         "courseId": 203,
     *                         "createTime": 1563984841000,
     *                         "createUserId": 2,
     *                         "generalComment": "文章问题文章问题文章问题",
     *                         "groupId": 2,
     *                         "id": 862,
     *                         "isTemplate": 0,
     *                         "matchoptions": [],
     *                         "options": [],
     *                         "questionBankId": 0,
     *                         "questionTemplateId": 0,
     *                         "score": 3000,
     *                         "seq": 0,
     *                         "title": "Question",
     *                         "type": 6,
     *                         "updateTime": 1563984841000,
     *                         "updateUserId": 2
     *                     },
     *                     "quizId": 1623,
     *                     "seq": 4,
     *                     "targetId": 862,
     *                     "type": 1,
     *                     "updateTime": 1563984841000,
     *                     "updateUserId": 2
     *                 }
     *             ],
     *             "showReplyStrategy": 1,
     *             "timeLimit": 30,
     *             "title": "youko quiz",
     *             "type": 1
     *         },
     *         "shareRange": 3,
     *         "tags": ["测验","分享"],
     *         "thumbnailUrl": "group1/M00/00/33/wKgFFF03yyOAWhmbAABzb_BceR4245.png",
     *         "version": 1563986497000
     *     },
     *     "message": "success"
     * }
     * 查看课程资源时，作业、讨论、测验中每次最多显示一中列表
     * {
     *     "code": 200,
     *     "entity": {
     *         "author": "admin admin",
     *         "authorId": 2,
     *         "description": "是课程的介绍呀",
     *         "grade": 132,
     *         "gradeEnd": 7,
     *         "gradeStart": 2,
     *         "hasCheck": 0,
     *         "hasNewNote": 0,
     *         "id": 93,
     *         "importCount": 0,
     *         "institution": "Root",
     *         "isFavorite": 0,
     *         "licence": 6,
     *         "name": "是203课程呀",
     *         "operation": 1,
     *         "originType": 15,
     *         "resource": {
     *             "assignments": {
     *                 "count": 1,
     *                 "items": [
     *                     {
     *                         "id":2,
     *                         "title":"作业",
     *                         "score":23
     *                     }
     *                 ]
     *             },
     *             "discussions": {
     *                 "count": 1,
     *                 "items": [
     *                     {
     *                         "id": 492,
     *                         "title": "discussion"
     *                     }
     *                 ]
     *             },
     *             "quizes": {
     *                 "count": 2,
     *                 "items":[
     *                     {
     *                         "id":2,
     *                         "title":"测验",
     *                         "score":120,
     *                         "questionCount":2
     *                     }
     *                 ]
     *             }
     *         },
     *         "shareRange": 3,
     *         "tags": ["课程","分享"],
     *         "thumbnailUrl": "group1/M00/00/33/wKgFFF04KkGANyrUAABzb_BceR4137.png",
     *         "version": 1564010848000
     *     },
     *     "message": "success"
     * }
     */
    @Override
    public ResourceVO find(String id) {
        Long resourceId = Long.valueOf(id);
        Long userId = WebContext.getUserId();

        ResourceVO resourceVO = resourceDao.getByIdAndImportUserId(resourceId, userId);
        if(Objects.isNull(resourceVO)){
            throw new ParamErrorException();
        }
        if(Objects.nonNull(resourceVO.getGrade())){
            resourceVO.setGradeStart(resourceCommonService.getGradeStart(resourceVO.getGrade()));
            resourceVO.setGradeEnd(resourceCommonService.getGradeEnd(resourceVO.getGrade()));
        }

        int hasCheck = resourceAdminOperationLogDao.countByIsSeeAndResourceAuthorId(Status.NO.getStatus(), userId) > 0 ? 1 : 0;
        resourceVO.setHasCheck(hasCheck);
        String beanJson = resourceCommonService.getResourceData(resourceId);
        QueryStrategy queryStrategy = strategyFactory.getQueryStrategy(OriginTypeEnum.typeOf(resourceVO.getOriginType()));
        Object object = queryStrategy.convertResourceObject(beanJson);
        resourceVO.setResource(object);
        return resourceVO;
    }

    /**
     * @api {get} /resSearch/list 资源列表
     * @apiDescription 资源列表
     * @apiName resSearchList
     * @apiGroup resource
     *
     * @apiParam {Number} originId 来源ID
     * @apiParam {Number=1,2,3,15} originType 类别，1：作业，2：讨论，3：测验，15：课程
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} [message] 响应描述
     * @apiSuccess {Object} [entity] 资源信息
     * @apiSuccess {Number} entity.id 资源ID
     * @apiSuccess {Number} entity.name 资源名字
     * @apiSuccess {Date} entity.version 资源版本
     * @apiExample {json} 请求示例:
     * {
     *     "code": 200,
     *     "entity": [
     *         {
     *             "id": 6,
     *             "name": "是分享作业呀",
     *             "version": 1563377544000
     *         },
     *         {
     *             "id": 7,
     *             "name": "是分享作业呀",
     *             "version": 1563551780000
     *         },
     *         {
     *             "id": 9,
     *             "name": "是分享作业呀",
     *             "version": 1563553213000
     *         }
     *     ],
     *     "message": "success"
     * }
     */
    @Override
    public List<ResourceVO> list(Map<String, String> param) {
        Long originId = Long.valueOf(param.get(Constants.PARAM_ORIGIN_ID));
        Integer originType = Integer.valueOf(param.get(Constants.PARAM_ORIGIN_TYPE));

        if(Objects.isNull(originId) || Objects.isNull(originType)){
            throw new ParamErrorException();
        }

        List<ResourceVO> resourVOS = resourceDao.getByOriginIdAndOriginType(originId, originType);
        return resourVOS;
    }
}
