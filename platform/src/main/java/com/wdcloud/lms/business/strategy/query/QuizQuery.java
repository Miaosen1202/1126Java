package com.wdcloud.lms.business.strategy.query;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.business.resources.dto.QuizResourceShareDTO;
import com.wdcloud.lms.business.strategy.AbstractQuizStrategy;
import com.wdcloud.lms.core.base.model.ModuleItem;
import com.wdcloud.lms.core.base.model.Quiz;
import com.wdcloud.lms.core.base.vo.QuizItemVO;
import com.wdcloud.utils.BeanUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class QuizQuery extends AbstractQuizStrategy implements QueryStrategy {
    @SuppressWarnings("Duplicates")
    @Override
    public OriginData query(Long id) {
        Quiz quiz = quizDao.get(id);
        if (quiz != null) {
            return OriginData.builder()
                    .status(quiz.getStatus())
                    .originId(quiz.getId())
                    .score(quiz.getTotalScore())
                    .originType(support().getType())
                    .title(quiz.getTitle())
                    .build();
        }
        return null;
    }

    @Override
    public Object queryDetail(Long moduleItemId) {
        ModuleItem moduleItem = moduleItemDao.get(moduleItemId);
        return moduleItem;
    }

    @Override
    public String queryResourceShareInfo(Long id, Long resourceId, String resourceName) {
        Quiz quiz = quizDao.get(id);
        QuizResourceShareDTO dto = BeanUtil.copyProperties(quiz, QuizResourceShareDTO.class);
        List<QuizItemVO> quizItemVOs = quizItemDao.getQuerstionAllInfors(id);
        dto.setQuizItemVOs(quizItemVOs);
        dto.setTitle(resourceName);
        return JSON.toJSONString(dto);
    }

    @Override
    public Object convertResourceObject(String beanJson) {
        return JSON.parseObject(beanJson, QuizResourceShareDTO.class);
    }
}
