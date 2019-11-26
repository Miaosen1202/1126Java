package com.wdcloud.lms.business.term.vo;//

import com.wdcloud.lms.core.base.model.Term;
import com.wdcloud.lms.core.base.model.TermConfig;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class TermVO extends Term {
    private List<TermConfig> termTimes;
    private Long coursesCount;
}
