package com.wzj.learn.autotest.driver;

import com.wzj.learn.autotest.constants.DriverType;
import com.wzj.learn.autotest.utils.PropertiesUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

public enum ChromeWebDriverHolder {
    INSTANCE {
        private WebDriver singletonChromeWebDriver;
        private ChromeDriverService chromeDriverService;

        @Override
        public WebDriver getNewChromeWebDriver() {
            return getChromeWebDriver();
        }

        @Override
        public WebDriver getSingletonChromeWebDriver() {
            if (singletonChromeWebDriver == null) {
                singletonChromeWebDriver = getChromeWebDriver();
            }
            return singletonChromeWebDriver;
        }

        @Override
        public Boolean quit() {
            if (singletonChromeWebDriver != null) {
                singletonChromeWebDriver.quit();
            }
            /*if (chromeDriverService != null) {
                chromeDriverService.stop();
            }*/
            return true;
        }

        private WebDriver getChromeWebDriver() {
            if (chromeDriverService == null) {
                chromeDriverService = newChromeDriverService();
            }
            try {
                chromeDriverService.start();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            return new RemoteWebDriver(chromeDriverService.getUrl(), DesiredCapabilities.chrome());
        }

        public ChromeDriverService newChromeDriverService() {
            String driverPath = PropertiesUtil.getDriverPath(DriverType.CHROME);
            return new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File(driverPath))
                    .usingAnyFreePort()
                    .build();
        }
    };

    public abstract WebDriver getNewChromeWebDriver();

    public abstract WebDriver getSingletonChromeWebDriver();

    public abstract Boolean quit();

}
