package com.wdcloud.lms.base.service;

import com.wdcloud.lms.Constants;
import com.wdcloud.lms.base.dto.PageDefault;
import com.wdcloud.utils.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PageDefaultConvertService {

    public final static PageDefault defaultConvert(String pageIndex, String pageSize){
        Integer pageIndexInt = StringUtil.isEmpty(pageIndex) ? Constants.PAGE_INDEX_FIRST : Integer.valueOf(pageIndex);
        Integer pageSizeInt = Objects.isNull(pageSize) ? Constants.PAGE_SIZE_TWENTY : Integer.valueOf(pageSize);

        return new PageDefault(pageIndexInt, pageSizeInt);
    }

    public final static PageDefault sizeSixteenConvert(String pageIndex, String pageSize){
        Integer pageIndexInt = StringUtil.isEmpty(pageIndex) ? Constants.PAGE_INDEX_FIRST : Integer.valueOf(pageIndex);
        Integer pageSizeInt = Objects.isNull(pageSize) ? Constants.PAGE_SIZE_SIXTEEN : Integer.valueOf(pageSize);

        return new PageDefault(pageIndexInt, pageSizeInt);
    }
}
