package com.wdcloud.lms.core.base.mapper.ext;

import java.util.List;
import java.util.Set;

public interface UserEPortfolioPageFileExtMapper {

    Set<Long> getByUserFileIdIn(List<Long> userFileIds);

}
