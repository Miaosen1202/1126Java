package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.GenseeLiveExtMapper;
import com.wdcloud.lms.core.base.model.GenseeLive;
import com.wdcloud.lms.core.base.model.GenseeVod;
import com.wdcloud.lms.core.base.vo.LiveListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class GenseeVodDao extends CommonDao<GenseeVod, Long> {

    @Override
    protected Class<GenseeVod> getBeanClass() {
        return GenseeVod.class;
    }
}