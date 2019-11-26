package com.wdcloud.lms.business.sis.reader;

import java.util.List;

public interface ISisCsvReader<T> {

    /**
     * 读取对象
     * @return List<T>
     */
    List<T> read();

}
