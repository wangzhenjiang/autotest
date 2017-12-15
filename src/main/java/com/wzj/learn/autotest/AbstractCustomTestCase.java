package com.wzj.learn.autotest;

import com.wzj.learn.autotest.constants.DriverType;
import com.wzj.learn.autotest.driver.ChromeWebDriverHolder;
import com.wzj.learn.autotest.driver.FirefoxWebDriverHolder;
import com.wzj.learn.autotest.utils.PropertiesUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

public abstract class AbstractCustomTestCase {

    protected WebDriver webDriver;

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

    @AfterSuite
    protected void quitWebDriver() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    protected WebElement findElement(WebElement ele, By by) {
        try {
            return ele.findElement(by);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    protected WebElement findElement(By by) {
        try {
            return webDriver.findElement(by);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
