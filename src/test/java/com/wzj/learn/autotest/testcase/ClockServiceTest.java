package com.wzj.learn.autotest.testcase;

import com.wzj.learn.autotest.service.ClockService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.annotation.Resource;

@Test
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ClockServiceTest extends AbstractTestNGSpringContextTests {

    @Resource(name = "clockService")
    private ClockService clockService;

    @Test
    public void clockIn() {
        clockService.clockIn();
    }
}
