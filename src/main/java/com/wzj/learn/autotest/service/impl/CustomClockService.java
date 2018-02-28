package com.wzj.learn.autotest.service.impl;

import com.wzj.learn.autotest.constants.DriverType;
import com.wzj.learn.autotest.service.ClockService;
import com.wzj.learn.autotest.utils.PropertiesUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 请填写类的描述
 *
 * @author wangzhenjiang
 * @date 2017-12-18 20:03
 */
@Service("customClockService")
public class CustomClockService implements ClockService {

    private WebDriver webDriver;
    private ChromeDriverService chromeDriverService;

    @PostConstruct
    public void initWebDriver() {
        String driverPath = PropertiesUtil.getDriverPath(DriverType.CHROME);
        chromeDriverService = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(driverPath))
                .usingAnyFreePort()
                .build();
        try {
            chromeDriverService.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        webDriver = new RemoteWebDriver(chromeDriverService.getUrl(), DesiredCapabilities.chrome());
        System.out.println("---启动浏览器. " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public boolean clockIn() {
        try {
//            initWebDriver();
            String url = PropertiesUtil.get("erp.url");
            String username = PropertiesUtil.get("erp.username");
            String password = PropertiesUtil.get("erp.password");
            webDriver.get(url);
            //登录ERP
            loginERP(username, password);
            doClockIn();
        } catch (Exception e) {
            e.printStackTrace();
        } /*finally {
            exit();
        }*/
        return true;
    }

    @Override
    public boolean clockOut() {
        return false;
    }

    private void loginERP(String erp, String erpPwd) {
        System.out.println("---开始登陆ERP. " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        WebElement element = findElement(By.id("username"));
        if(element != null){
            element.sendKeys(erp);
            element = findElement(By.id("password"));
            element.sendKeys(erpPwd);
            element = findElement(By.className("formsubmit_btn"));
//        try {
//            //自动输入太快，休眠2秒后提交
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//        }
            element.submit();
            System.out.println("---登陆ERP完成. " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }
    }

    private void doClockIn() {
        long millis = RandomUtils.nextLong(60000, 1140000);
        System.out.println("---进入休眠，休眠时间：" + millis + "ms. " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        try {
            //随机休眠 1~19 分钟
            Thread.sleep(millis);
//            Thread.sleep(RandomUtils.nextInt(1000, 2000));
        } catch (InterruptedException e) {//ignore
        }
        System.out.println("---休眠结束，休眠时间：" + millis + "ms. " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        //嘿嘿
        WebElement divElement = findElement(By.id("clockLink"));
        WebElement buttonElement = divElement.findElement(By.tagName("button"));
        //执行javascript
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("arguments[0].click()", buttonElement);
        System.out.println("---打卡完成. " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

    private WebElement findElement(By by) {
        try {
            return webDriver.findElement(by);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public void exit() {
        if (webDriver != null ) {
            webDriver.close();
            webDriver.quit();
        }
        if (chromeDriverService != null && chromeDriverService.isRunning()) {
            chromeDriverService.stop();
        }
    }

}
