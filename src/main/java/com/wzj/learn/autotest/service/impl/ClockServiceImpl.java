package com.wzj.learn.autotest.service.impl;

import com.wzj.learn.autotest.service.ClockService;
import com.wzj.learn.autotest.service.DriverService;
import com.wzj.learn.autotest.utils.PropertiesUtil;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Service("clockService")
public class ClockServiceImpl implements ClockService {

    @Resource(name = "chromeDriverService")
    private DriverService driverService;

    private WebDriver webDriver;

//    @PostConstruct
    public void initWebDriver() {
        webDriver = driverService.getWebDriver();
        String url = PropertiesUtil.get("erp.url");
        String username = PropertiesUtil.get("erp.username");
        String password = PropertiesUtil.get("erp.password");
        //打开Chrome，并跳转至ERP页面
        webDriver.get(url);
        //登录ERP
        loginERP(username, password);
    }

//    @PreDestroy
    public void quitWebDriver() {
        webDriver.quit();
    }


    @Override
    public boolean clockIn() {
        initWebDriver();
        doClockIn();
        quitWebDriver();
        return true;
    }

    private void doClockIn() {
        try {
            Thread.sleep(RandomUtils.nextInt(600000, 1200000));
        } catch (InterruptedException e) {//ignore
        }
        //嘿嘿
        WebElement divElement = findElement(By.id("clockLink"));
        WebElement buttonElement = divElement.findElement(By.tagName("button"));
        //执行javascript
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("arguments[0].click()", buttonElement);
    }

    @Override
    public boolean clockOut() {
        return clockIn();
    }

    private void loginERP(String erp, String erpPwd) {
        WebElement element = findElement(By.id("username"));
        element.sendKeys(erp);
        element = findElement(By.id("password"));
        element.sendKeys(erpPwd);
        element = findElement(By.className("formsubmit_btn"));
        element.submit();
    }

    protected WebElement findElement(By by) {
        try {
            return webDriver.findElement(by);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}