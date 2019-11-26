package com.wdcloud.lms.business.live;

import com.wdcloud.lms.base.enums.LiveTypeEnum;
import com.wdcloud.lms.base.service.LiveService;
import com.wdcloud.lms.business.strategy.assignmentgroupcontentmove.AssignmentGroupContentMoveStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component
public class LiveFactory {

    private Map<LiveTypeEnum, LiveService> liveServiceMap = new EnumMap<>(LiveTypeEnum.class);

    @Autowired
    private void init(LiveService[] liveServices) {
        for (LiveService liveService : liveServices) {
            liveServiceMap.put(liveService.support(), liveService);
        }
    }
   public LiveService getLiveService(LiveTypeEnum liveType) {
        return liveServiceMap.get(liveType);
   }
}
