package com.wzj.learn.autotest;

import com.wzj.learn.autotest.constants.DriverType;
import com.wzj.learn.autotest.driver.ChromeWebDriverHolder;
import com.wzj.learn.autotest.driver.DefaultOperation;
import com.wzj.learn.autotest.driver.Operation;
import com.wzj.learn.autotest.utils.PropertiesUtil;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.service.DriverService;

/**
 * Created by wangzhenjiang on 2017/7/19.
 */
@RunWith(JUnit4.class)
public abstract class AbstractCustomTestCase extends TestCase {

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
                System.out.println("初始化DriverService错误,不存在的枚举类型。");
        }
    }

    @Before
    public void initWebDriver() {
        DriverType type = PropertiesUtil.getDriverType();
        switch (type) {
            case CHROME:
                webDriver = ChromeWebDriverHolder.INSTANCE.getSingletonChromeWebDriver();
                break;
            default:
                System.out.println("初始化WebDriver错误,不存在的枚举类型。");
        }
    }

    @After
    public void quitWebDriver() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @AfterClass
    public static void stopDriverService() {
        if (driverService != null) {
            driverService.stop();
        }
    }
}
