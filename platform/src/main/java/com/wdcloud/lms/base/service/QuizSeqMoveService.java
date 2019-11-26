package com.wdcloud.lms.base.service;

import com.wdcloud.lms.business.quiz.question.SeqConstants;
import com.wdcloud.lms.core.base.model.QuizItem;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：实现问题或问题组移动后，序号重新计算功能
 *
 * @author 黄建林
 */
@Service
public class QuizSeqMoveService extends SeqMoveServiceAbstract<QuizItem>{

    /**
     * @param operate,1、top:移动到顶部； 2、end,移动到底部；
     *                             3、delete 删除
     *                             4、before 在前
     * @param type,1.      问题 2. 问题组
     * @param moveId,需要移动的ID
     * @param beforeId,移动到指定ID前
     */
    public List<QuizItem> seqMove(int operate, List<QuizItem> items , int type, Long moveId, Long beforeId) {

        int len = items.size();

        switch (operate) {
            case SeqConstants.MOVE_TOP://移动到顶部;
                for (int i = 0; i < len; i++) {
                    int tmpseq = items.get(i).getSeq() + 1;
                    items.get(i).setSeq(tmpseq);
                    if (moveId.equals(items.get(i).getTargetId())  && type == items.get(i).getType())//找到需要移动对象位置
                    {
                        items.get(i).setSeq(1);
                    }
                }
                break;
            case SeqConstants.MOVE_END:
                //移动到底部;
                boolean ismove = false;
                for (int i = 0; i < len; i++) {
                    if (ismove) {
                        int tmpseq = items.get(i).getSeq() - 1;
                        items.get(i).setSeq(tmpseq);
                    }
                    if (moveId.equals(items.get(i).getTargetId())  && type == items.get(i).getType())//找到需要移动对象位置
                    {
                        items.get(i).setSeq(len);
                        ismove = true;
                    }
                }
                break;
            case SeqConstants.DELETE:
                //delete 删除
                ismove = false;
                for (int i = 0; i < len; i++) {
                    if (ismove) {
                        int tmpseq = items.get(i).getSeq() - 1;
                        items.get(i).setSeq(tmpseq);
                    }
                    if (moveId.equals(items.get(i).getTargetId()) && type == items.get(i).getType())//找到需要移动对象位置
                    {

                        ismove = true;
                    }
                }
                break;
            case SeqConstants.MOVE_BEFORE:
                //before 在前
                ismove = false;
                int j = 0;
                int changeseq = 0;
                for (int i = 0; i < len; i++) {

                    if (beforeId.equals(items.get(i).getTargetId())  && type == items.get(i).getType())//找到需要移动对象位置
                    {
                        changeseq = items.get(i).getSeq();
                        ismove = true;
                        j = i;
                    }
                    if (moveId.equals(items.get(i).getTargetId()) && type == items.get(i).getType())//找到需要移动对象位置
                    {
                        if (i + 1 < len - 1) {
                            if (items.get(i + 1).getTargetId().equals(beforeId) )//需要移动的已经在移动到的id前面
                            {
                                return null;
                            }
                        }
                        items.get(i).setSeq(changeseq);
                        ismove = false;
                    }
                    if (ismove) {
                        int tmpseq = items.get(i).getSeq() + 1;
                        items.get(i).setSeq(tmpseq);
                    }
                }
                break;
            default:
                //;
                items = null;
                break;
        }


        return items;
    }
}
