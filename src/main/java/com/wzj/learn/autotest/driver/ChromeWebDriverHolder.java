package com.wzj.learn.autotest.driver;

import com.wzj.learn.autotest.constants.DriverType;
import com.wzj.learn.autotest.utils.PropertiesUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

/**
 * Created by wangzhenjiang on 2017/7/19.
 */
public enum ChromeWebDriverHolder {
    INSTANCE {
        private WebDriver singletonChromeWebDriver = getSingletonChromeWebDriver();
        private ChromeDriverService singletonChromeDriverService;

        @Override
        public WebDriver getNewChromeWebDriver() {
            return getChromeWebDriver(false);
        }

        @Override
        public WebDriver getSingletonChromeWebDriver() {
            if (singletonChromeWebDriver == null) {
                singletonChromeWebDriver = getChromeWebDriver(true);
            }
            return singletonChromeWebDriver;
        }

        private WebDriver getChromeWebDriver(boolean isSingleton) {
            ChromeDriverService service = isSingleton ? getSingletonChromeDriverService() : getNewChromeDriverService();
            try {
                service.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
        }

        @Override
        public ChromeDriverService getSingletonChromeDriverService() {
            if (singletonChromeDriverService == null) {
                singletonChromeDriverService = getNewChromeDriverService();
            }
            return singletonChromeDriverService;
        }

        @Override
        public ChromeDriverService getNewChromeDriverService() {
            String driverPath = PropertiesUtil.getDriverPath(DriverType.CHROME);
            return new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File(driverPath))
                    .usingAnyFreePort()
                    .build();
        }
    };

    public abstract WebDriver getNewChromeWebDriver();

    public abstract WebDriver getSingletonChromeWebDriver();

    public abstract ChromeDriverService getNewChromeDriverService();

    public abstract ChromeDriverService getSingletonChromeDriverService();

}
