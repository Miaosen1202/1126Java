package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.Term;
import com.wdcloud.lms.core.base.vo.TermAndConfigVo;
import com.wdcloud.lms.core.base.vo.TermListVO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface TermExtMapper {

    List<TermAndConfigVo> findTermAndConfigs(List<Long> termIds);

    List<TermListVO> termList(Long orgId);

    List<Term> findUserOrgTerms(@Param("rootOrgTreeId") String rootOrgTreeId, @Param("sisIds") Collection<String> sisIds);

    List<TermListVO> termList(String rootTreeId);
}
