package com.wzj.learn.autotest;

import com.wzj.learn.autotest.constants.DriverType;
import com.wzj.learn.autotest.driver.ChromeWebDriverHolder;
import com.wzj.learn.autotest.driver.DefaultOperation;
import com.wzj.learn.autotest.driver.FirefoxWebDriverHolder;
import com.wzj.learn.autotest.driver.Operation;
import com.wzj.learn.autotest.utils.PropertiesUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.service.DriverService;
import org.testng.annotations.*;

public abstract class AbstractCustomTestCase {

    protected WebDriver webDriver;
    protected static DriverService driverService;

    protected Operation operation = new DefaultOperation(webDriver);

    @BeforeClass
    public static void initService() {
        DriverType type = PropertiesUtil.getDriverType();
        switch (type) {
            case CHROME:
                driverService = ChromeWebDriverHolder.INSTANCE.getSingletonChromeDriverService();
                break;
            default:
                System.out.println("没有需要初始化的DriverService, DriverType:" + type);
        }
    }

    @BeforeSuite
    public void initWebDriver() {
        DriverType type = PropertiesUtil.getDriverType();
        switch (type) {
            case CHROME:
                webDriver = ChromeWebDriverHolder.INSTANCE.getSingletonChromeWebDriver();
                break;
            case FIREFOX:
                webDriver = FirefoxWebDriverHolder.INSTANCE.getSingletonFirefoxWebDriver();
                break;
            default:
                System.out.println("初始化WebDriver错误,不存在的枚举类型。");
        }
    }

    //    @AfterSuite
    protected void quitWebDriver() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    //    @AfterClass
    protected static void stopDriverService() {
        if (driverService != null) {
            driverService.stop();
        }

    }
}
