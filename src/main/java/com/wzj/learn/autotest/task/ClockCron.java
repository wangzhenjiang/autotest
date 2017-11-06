package com.wzj.learn.autotest.task;

import com.wzj.learn.autotest.service.ClockService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service("clockCron")
public class ClockCron {

    @Resource(name = "clockService")
    private ClockService clockService;

    public void cron() {
        System.out.println("--------------Begin-------------------");
        clockService.clockIn();
        System.out.println("--------------End-------------------" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }
}

