package com.wdcloud.lms.business.certification;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.WebContext;
import com.wdcloud.lms.core.base.dao.CertificationDao;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.core.base.vo.certification.LearnerCertificationVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_CERTIFICATION_LEARNER)
public class LearnerCertificationDataQuery implements IDataQueryComponent<LearnerCertificationVO> {

    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private CertificationDao certificationDao;


    /**
     * @api {get} /certificationLearner/pageList 学习者认证列表
     * @apiName certificationLearnerPageList
     * @apiGroup certificationLearner
     *
     * @apiParam {Number} orderBy 排序 0：name升序 1:name降序 2: DateUpdated降序
     * @apiParam {Number} pageIndex 页码
     * @apiParam {Number} pageSize 页大小
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.total 总结果数量
     * @apiSuccess {Number} entity.pageIndex 页码
     * @apiSuccess {Number} entity.pageSize 页大小
     * @apiSuccess {Object[]} entity.list 列表
     * @apiSuccess {Number} entity.list.certificationId 认证ID
     * @apiSuccess {Number} entity.list.userId 用户ID
     * @apiSuccess {Number} entity.list.status 认证状态 0:Assigned 1:PendingApproval 2:Certified 3:Expired 4:Unenrolled
     * @apiSuccess {String} entity.list.name 认证名称
     * @apiSuccess {Number} entity.list.issuer 发行机构 0：Internal 1：External
     * @apiSuccess {Number} entity.list.dueTime 操作截止日期
     * @apiSuccess {String} entity.list.fileUrl 封面图Url
     * @apiSuccess {String} entity.list.certStatus 发认证状态 0:草稿 1：已发布 2：已注销',
     */
    @Override
    public PageQueryResult<? extends LearnerCertificationVO> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        Map<String, Object> params = new HashMap();
        params.putAll(param);

        PageHelper.startPage(pageIndex,pageSize);
        Page<LearnerCertificationVO> page= (Page<LearnerCertificationVO>) certificationDao.findCertificationListByLearner(params);

        return new PageQueryResult(page.getTotal(),page.getResult(),pageSize,pageIndex);
    }


    /**
     * @api {get} /certificationLearner/get 学习者认证详情
     * @apiName certificationLearnerGet
     * @apiGroup certificationLearner
     *
     * @apiParam {Number} data 认证ID
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.certificationId 认证ID
     * @apiSuccess {Number} entity.userId 用户ID
     * @apiSuccess {String} entity.name 认证名称
     * @apiSuccess {String} entity.memo 认证描述
     * @apiSuccess {Number} entity.opDay 可操作性天数
     * @apiSuccess {Number=0,1} entity.type 认证类型 0：永久认证 1：周期认证
     * @apiSuccess {Number} entity.validity 证件有效月份（单位月）
     * @apiSuccess {Number} entity.issuer 发行机构 0：Internal 1：External'
     * @apiSuccess {Number} entity.status 认证状态 0:Assigned 1:PendingApproval 2:Certified 3:Expired 4:Unenrolled
     * @apiSuccess {Number} [entity.coverImgId] 封面图ID
     * @apiSuccess {Object} [entity.userFile] 封面图对象
     * @apiSuccess {Number} [entity.userFile.id] 封面图 ID
     * @apiSuccess {Number} [entity.userFile.fileUrl] 封面图 URL
     * @apiSuccess {Object} [entity.proofFile] 证书附件
     * @apiSuccess {Number} [entity.proofFile.id] 证书附件 ID
     * @apiSuccess {Number} [entity.proofFile.fileUrl] 证书附件 URL
     * @apiSuccess {Number} [entity.proofFile.fileName] 证书附件名称
     *
     */
    @Override
    public LearnerCertificationVO find(String id) {
        Long certificationId = Long.valueOf(id);
        LearnerCertificationVO vo=certificationDao.findOneCertificationListByLearner(certificationId, WebContext.getUserId());
        if (vo.getCoverImgId() != null) {
           UserFile userFile= userFileDao.get(vo.getCoverImgId());
           vo.setUserFile(userFile);
        }
        if (vo.getProofFileId() != null) {
            UserFile userFile= userFileDao.get(vo.getProofFileId());
            vo.setProofFile(userFile);
        }
        return vo;
    }
}
