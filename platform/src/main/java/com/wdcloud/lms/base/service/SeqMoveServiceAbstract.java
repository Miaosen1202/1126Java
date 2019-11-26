package com.wdcloud.lms.base.service;

import com.wdcloud.server.frame.exception.ResourceOpeartorUnsupportedException;

import java.util.List;

/**
 * 功能：实现项目移动后，序号重新计算功能
 *
 * @author 黄建林
 */
public abstract class SeqMoveServiceAbstract<BeanType>  {

    /**
     * @param operate,1、top:移动到顶部； 2、end,移动到底部；
     *                             3、delete 删除
     *                             4、before 在前
     * @param type,类型
     * @param moveId,需要移动的ID
     * @param beforeId,移动到指定ID前
     */
    public List<BeanType> seqMove(int operate, List<BeanType> items , int type, Long moveId, Long beforeId) {
        throw new ResourceOpeartorUnsupportedException();
    }

    /**
     * 改变顺序
     * 删除或移动会触发排序的变化
     * @param operate 3、delete 删除 5、move 移动
     * @param type 类型
     * @param moveId 需要移动的ID
     * @param seq 移动到指定ID前
     */
    public List<BeanType> seqChange(int operate, List<BeanType> items , int type, Long moveId, Integer seq) {
        throw new ResourceOpeartorUnsupportedException();
    }

}
