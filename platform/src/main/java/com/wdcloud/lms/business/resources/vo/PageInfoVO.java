package com.wdcloud.lms.business.resources.vo;

import com.wdcloud.server.frame.interfaces.info.PageQueryResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageInfoVO<T> extends PageQueryResult<T> {
    /**
     * 是否有管理员给动过 0：没有，1：有
     */
    private int hasCheck;

    /**
     * 是否有新的版本 0：没有，1：有
     */
    private int hasNewVersion;

    public PageInfoVO(long total, List<T> list, int pageSize, int pageIndex, int hasCheck) {
        super(total, list, pageSize, pageIndex);
        this.hasCheck = hasCheck;
    }

    public PageInfoVO(long total, List<T> list, int pageSize, int pageIndex, int hasCheck, int hasNewVersion) {
        super(total, list, pageSize, pageIndex);
        this.hasCheck = hasCheck;
        this.hasNewVersion = hasNewVersion;
    }
}
