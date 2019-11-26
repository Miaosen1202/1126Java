package com.wdcloud.lms.business.announce.dto;

import com.wdcloud.lms.core.base.vo.AnnounceReplyVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class AnnounceReplyTree {

    public static List<AnnounceReplyVO> getList(List<AnnounceReplyVO> datas) {
        List<AnnounceReplyVO> reslut = new ArrayList<>();
        Map<Long, AnnounceReplyVO> map = datas.stream().collect(Collectors.toMap(AnnounceReplyVO::getId, a -> a));
        for (AnnounceReplyVO e : datas) {
            AnnounceReplyVO e_p = map.get(e.getAnnounceReplyId());
            if (e_p != null) {
                List<AnnounceReplyVO> child = e_p.getReplies();
                if (child == null) {
                    child = new ArrayList<>();
                    e_p.setReplies(child);
                }
                child.add(e);
            } else {
                reslut.add(e);
            }
        }
        return reslut;
    }
}
