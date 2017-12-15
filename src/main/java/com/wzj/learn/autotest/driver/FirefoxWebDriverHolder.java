package com.wzj.learn.autotest.driver;

import com.wzj.learn.autotest.constants.DriverType;
import com.wzj.learn.autotest.utils.PropertiesUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

/**
 * Created by wangzhenjiang on 2017/7/19.
 */
public enum FirefoxWebDriverHolder {

    INSTANCE {
        private WebDriver singletonFirefoxWebDriver = getSingletonFirefoxWebDriver();

        @Override
        public WebDriver getNewFirefoxWebDriver() {
            String path = PropertiesUtil.getDriverPath(DriverType.FIREFOX);
            System.setProperty("webdriver.gecko.driver",path);
            return new FirefoxDriver();
        }

        @Override
        public WebDriver getSingletonFirefoxWebDriver() {
            if (singletonFirefoxWebDriver == null) {
                singletonFirefoxWebDriver = getNewFirefoxWebDriver();
            }
            return singletonFirefoxWebDriver;
        }
    };

    public abstract WebDriver getNewFirefoxWebDriver();

    public abstract WebDriver getSingletonFirefoxWebDriver();
}
