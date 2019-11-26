package com.wdcloud.lms.business.discussion.dto;

import com.wdcloud.lms.core.base.vo.DiscussionReplyVO;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class DiscussionReplyTree {

	public static List<DiscussionReplyVO> getList(List<DiscussionReplyVO> datas ) {
		List<DiscussionReplyVO> reslut = new ArrayList();
		Map<Long, DiscussionReplyVO> map =datas.stream().collect(Collectors.toMap(DiscussionReplyVO::getId, Function.identity(), (o,n)->o));
		for (DiscussionReplyVO e : datas) {
			DiscussionReplyVO e_p = map.get(e.getDiscussionReplyId());
			if (e_p != null) {
				List<DiscussionReplyVO> child = e_p.getReplies();
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
