package com.wdcloud.lms.task;

import com.wdcloud.lms.base.service.GenseeLiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GenseeTask {

    @Autowired
    private GenseeLiveService genseeLiveService;

    /**
     * 同步点播
     * 每天凌晨00:00:00
     * @throws Exception
     */
    @Scheduled(cron = "0 0 0 * * ?")
    //@Scheduled(cron = "0 * * * * ?")
    public void vodTask() throws Exception {
        log.info("【同步点播定时任务】开始时间:{}",System.currentTimeMillis());
        genseeLiveService.syncVod();
        log.info("【同步点播定时任务】结束时间:{}",System.currentTimeMillis());
    }

    /**
     * 同步直播访问记录
     * 每天凌晨2:00:00
     * @throws Exception
     */
    @Scheduled(cron = "0 0 2 * * ?")
    //@Scheduled(cron = "0 * * * * ?")
    public void liveHistoryTask() throws Exception {
        log.info("【同步直播访问记录定时任务】开始时间:{}",System.currentTimeMillis());
        genseeLiveService.syncLiveHistory();
        log.info("【同步直播访问记录定时任务】结束时间:{}",System.currentTimeMillis());
    }

    /**
     * 同步点播访问记录
     * 每天凌晨4:00:00
     * @throws Exception
     */
    @Scheduled(cron = "0 0 4 * * ?")
//    @Scheduled(cron = "0 * * * * ?")
    public void vodHistoryTask() throws Exception {
        log.info("【同步点播访问记录定时任务】开始时间:{}",System.currentTimeMillis());
        genseeLiveService.syncVodHistory();
        log.info("【同步点播访问记录定时任务】结束时间:{}",System.currentTimeMillis());

    }
}