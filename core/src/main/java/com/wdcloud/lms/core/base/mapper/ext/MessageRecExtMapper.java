package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.MessageRec;
import com.wdcloud.lms.core.base.vo.msg.MessageSubjectVO;
import com.wdcloud.lms.core.base.vo.msg.MsgItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MessageRecExtMapper {
    void batchSave(List<MessageRec> messageRecList);

    List<MessageSubjectVO> findRecSubjectList(Map<String, Object> param);

    MsgItemVO myLastestRecMsg(@Param("subjectId") Long subjectId, @Param("recId") Long recId);

    List<MsgItemVO> findRecMsgListBySubject(Map<String, String> condition);

    int getUnreadNum(@Param("courseIds")List<Long> courseIds,@Param("loginUserId") Long userId);
}
