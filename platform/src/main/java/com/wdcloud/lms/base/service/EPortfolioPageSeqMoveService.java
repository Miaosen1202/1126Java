package com.wdcloud.lms.base.service;

import com.wdcloud.lms.business.ePortfolio.transfer.UserEPortfolioItemMoveTransfer;
import com.wdcloud.lms.business.quiz.question.SeqConstants;
import com.wdcloud.lms.core.base.model.UserEPortfolioPage;
import com.wdcloud.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：实现电子学档页面删除、移动、复制后，序号重新计算功能
 *
 * @author 黄建林
 */
@Service
public class EPortfolioPageSeqMoveService extends SeqMoveServiceAbstract<UserEPortfolioPage>{

    @Autowired
    private EPortfolioSeqMoveBaseService ePortfolioSeqMoveBaseService;

    /**
     * @param operate 1 top移动到顶部； 2 end,移动到底部； 3 delete 删除；4 before 在前
     * @param type  3 电子学档页面集 ；4 电子学档页面
     * @param moveId 需要移动的ID
     * @param seq 移动到的位置
     */
    public List<UserEPortfolioPage> seqChange(int operate, List<UserEPortfolioPage> items , int type, Long moveId, Integer seq) {
        List<UserEPortfolioItemMoveTransfer> moveItems = BeanUtil.beanCopyPropertiesForList(items,
                UserEPortfolioItemMoveTransfer.class);

        moveItems = ePortfolioSeqMoveBaseService.seqChange(operate, moveItems, moveId, seq);

        items = BeanUtil.beanCopyPropertiesForList(moveItems, UserEPortfolioPage.class);

        return items;
    }
}
