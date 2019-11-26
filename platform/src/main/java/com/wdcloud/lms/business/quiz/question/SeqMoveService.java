package com.wdcloud.lms.business.quiz.question;

import com.wdcloud.lms.base.service.QuizSeqMoveService;
import com.wdcloud.lms.core.base.dao.QuizItemDao;
import com.wdcloud.lms.core.base.model.QuizItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：题编号删除、移动处理
 *
 * @author 黄建林
 */
@Service
public class SeqMoveService {
    @Autowired
    private QuizItemDao quizItemDao;
    @Autowired
    private QuizSeqMoveService quizSeqMoveService;

    /**
     * @param operate,1、top:移动到顶部； 2、end,移动到底部；
     *                             3、delete 删除
     *                             4、before 在前
     * @param questionType,1.      问题 2. 问题组
     * @param moveId,需要移动的ID
     * @param beforeId,移动到指定ID前
     */
    public List<QuizItem> seqMove(int operate, Long quizId, int questionType, Long moveId, Long beforeId) {
        List<QuizItem> quizItems = quizItemDao.getAllByQuizId(quizId);
        quizItems=  quizSeqMoveService.seqMove(operate,quizItems,questionType,moveId,beforeId);
        if (quizItems != null) {
            quizItemDao.batchUpdateSeq(quizItems);
        }

        return quizItems;
    }


}
