package com.wdcloud.lms.base.service;

import com.wdcloud.lms.business.ePortfolio.transfer.UserEPortfolioItemMoveTransfer;
import com.wdcloud.lms.business.quiz.question.SeqConstants;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EPortfolioSeqMoveBaseService {

    public List<UserEPortfolioItemMoveTransfer> seqChange(int operate, List<UserEPortfolioItemMoveTransfer> items,
                                                        Long moveId, Integer seq){
        int len = items.size();
        boolean isMove = false;

        switch (operate) {
            case SeqConstants.DELETE:
                //delete 删除
                for (int i = 0; i < len; i++) {
                    if (isMove){
                        int tmpSeq = items.get(i).getSeq() - 1;
                        items.get(i).setSeq(tmpSeq);
                    }
                    //找到需要移动对象位置
                    if (moveId.equals(items.get(i).getId())) {
                        isMove = true;
                    }
                }
                break;
            case SeqConstants.MOVE:
                //before 在前
                boolean hasMeetData = false;
                boolean hasMeetSeq = false;
                int moveItem = 0;
                int tmpSeq = 0;
                for (int i = 0; i < len; i++) {
                    //找到需要移动对象位置
                    if (seq.equals(items.get(i).getSeq())) {
                        isMove = !isMove;
                        hasMeetSeq = true;

                        if(hasMeetData){ //处理移动位置处的元素
                            tmpSeq = items.get(i).getSeq() - 1;
                            items.get(i).setSeq(tmpSeq);
                        }
                    }
                    //找到需要移动对象位置
                    if (moveId.equals(items.get(i).getId()) ){
                        if (i < len) {
                            if (items.get(i).getSeq().equals(seq)) {//需要移动的已经在移动到的id前面
                                return items;
                            }
                        }
                        moveItem = i;
                        isMove = !isMove;
                        hasMeetData = true;
                        continue;
                    }

                    if (isMove && hasMeetSeq) {
                        tmpSeq = items.get(i).getSeq() + 1;
                        items.get(i).setSeq(tmpSeq);
                    }else if(isMove && hasMeetData){
                        tmpSeq = items.get(i).getSeq() - 1;
                        items.get(i).setSeq(tmpSeq);
                    }
                }

                items.get(moveItem).setSeq(seq);
                break;
            default:
                items = null;
                break;
        }
        return items;
    }
}
