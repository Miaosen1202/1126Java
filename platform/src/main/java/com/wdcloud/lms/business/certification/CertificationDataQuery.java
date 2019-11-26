package com.wdcloud.lms.business.certification;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wdcloud.lms.Constants;
import com.wdcloud.lms.core.base.dao.CertificationDao;
import com.wdcloud.lms.core.base.dao.UserFileDao;
import com.wdcloud.lms.core.base.model.Certification;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.core.base.vo.certification.CertificationVO;
import com.wdcloud.server.frame.interfaces.IDataQueryComponent;
import com.wdcloud.server.frame.interfaces.ResourceInfo;
import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import com.wdcloud.utils.BeanUtil;
import com.wdcloud.utils.ListUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ResourceInfo(name = Constants.RESOURCE_TYPE_CERTIFICATION)
public class CertificationDataQuery implements IDataQueryComponent<CertificationVO> {

    @Autowired
    private UserFileDao userFileDao;
    @Autowired
    private CertificationDao certificationDao;


    /**
     * @api {get} /certification/pageList 认证列表分页
     * @apiName certificationPageList
     * @apiGroup certification
     *
     * @apiParam {String} [searchKey] 认证名称 模糊搜索
     * @apiParam {Number} status 认证状态 -1:所有 0:草稿 1：已发布 2：已注销
     * @apiParam {Number} issuer 发行机构 -1:所有 0：Internal 1：External
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
     * @apiSuccess {Number} entity.list.id 认证ID
     * @apiSuccess {String} entity.list.name 认证名称
     * @apiSuccess {String} entity.list.memo 认证描述
     * @apiSuccess {Number} entity.list.opDay 可操作性天数
     * @apiSuccess {Number=0,1} entity.list.type 认证类型 0：永久认证 1：周期认证
     * @apiSuccess {Number} entity.list.validity 证件有效月份（单位月）
     * @apiSuccess {Number} entity.list.issuer 发行机构 0：Internal 1：External'
     * @apiSuccess {Number} entity.list.status 发认证状态 0:草稿 1：已发布 2：已注销',
     * @apiSuccess {Number} entity.list.publishTime 发布时间
     * @apiSuccess {Number} [entity.list.coverImgId] 封面图ID
     * @apiSuccess {Object} [entity.list.userFile] 封面图对象
     * @apiSuccess {Number} [entity.list.userFile.id] 封面图 ID
     * @apiSuccess {Number} [entity.list.userFile.fileUrl] 封面图 URL
     */
    @Override
    public PageQueryResult<? extends CertificationVO> pageList(Map<String, String> param, int pageIndex, int pageSize) {
        Map<String, Object> params = new HashMap();
        params.putAll(param);

        PageHelper.startPage(pageIndex,pageSize);
        Page<CertificationVO> page= (Page<CertificationVO>) certificationDao.findCertificationListByAdmin(params);

        if (page != null&&page.getTotal()>0) {
           List<Long> imgIds= page.getResult().stream().filter(o->o.getCoverImgId()!=null).map(CertificationVO::getCoverImgId).collect(Collectors.toList());
            //批量获取 文件实体
            if (ListUtils.isNotEmpty(imgIds)) {
                List<UserFile> userFiles=userFileDao.gets(imgIds);
                Map<Long, UserFile> userFileMap = userFiles.stream().collect(Collectors.toMap(UserFile::getId,o->o));
                page.getResult().forEach(certificationVO -> {
                    if (certificationVO.getCoverImgId() != null) {
                        certificationVO.setUserFile(userFileMap.get(certificationVO.getCoverImgId()));
                    }
                });
            }
        }
        return new PageQueryResult(page.getTotal(),page.getResult(),pageSize,pageIndex);
    }


    /**
     * @api {get} /certification/get 认证详情
     * @apiName certificationGet
     * @apiGroup certification
     *
     * @apiParam {Number} data 认证Id
     *
     * @apiSuccess {Number=200,500} code 响应码，200为处理成功，其他处理失败
     * @apiSuccess {String} message 响应描述
     * @apiSuccess {Object} [entity] 结果
     * @apiSuccess {Number} entity.id ID
     * @apiSuccess {String} entity.name 认证名称
     * @apiSuccess {String} entity.memo 认证描述
     * @apiSuccess {Number} entity.opDay 可操作性天数
     * @apiSuccess {Number=0,1} entity.type 认证类型 0：永久认证 1：周期认证
     * @apiSuccess {Number} entity.validity 证件有效月份（单位月）
     * @apiSuccess {Number} entity.issuer 发行机构 0：Internal 1：External'
     * @apiSuccess {Number} entity.status 发认证状态 0:草稿 1：已发布 2：已注销',
     * @apiSuccess {Number} entity.publishTime 发布时间
     * @apiSuccess {Number} [entity.coverImgId] 封面图ID
     * @apiSuccess {Object} [entity.userFile] 封面图对象
     * @apiSuccess {Number} [entity.userFile.id] 封面图 ID
     * @apiSuccess {Number} [entity.userFile.fileUrl] 封面图 URL
     *
     */
    @Override
    public CertificationVO find(String id) {
        Certification certification = certificationDao.findOne(Certification.builder().id(Long.valueOf(id)).isDelete(0).build());
        CertificationVO vo = BeanUtil.beanCopyProperties(certification,CertificationVO.class);
        if (vo.getCoverImgId() != null) {
           UserFile userFile= userFileDao.get(vo.getCoverImgId());
           vo.setUserFile(userFile);
        }
        return vo;
    }
}
