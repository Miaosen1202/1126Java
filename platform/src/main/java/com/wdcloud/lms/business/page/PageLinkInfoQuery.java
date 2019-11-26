package com.wdcloud.lms.business.page;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.business.vo.ContentLinkInfoVo;
import com.wdcloud.server.frame.interfaces.ISelfDefinedSearch;
import com.wdcloud.server.frame.interfaces.SelfDefinedFunction;

@SelfDefinedFunction(
        resourceName = Constants.RESOURCE_TYPE_PAGE,
        functionName = Constants.FUNCTION_TYPE_LINK_INFO
)
public class PageLinkInfoQuery implements ISelfDefinedSearch<ContentLinkInfoVo> {

    // todo
}
