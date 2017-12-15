package com.wzj.learn.autotest.service.impl;

import com.wzj.learn.autotest.driver.ChromeWebDriverHolder;
import com.wzj.learn.autotest.service.DriverService;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

@Service("chromeDriverService")
public class ChromeDriverServiceImpl implements DriverService {

    @Override
    public WebDriver getWebDriver() {
        return ChromeWebDriverHolder.INSTANCE.getNewChromeWebDriver();
//        return ChromeWebDriverHolder.INSTANCE.getSingletonChromeWebDriver();
    }

    @Override
    public Boolean quit() {
        return ChromeWebDriverHolder.INSTANCE.quit();
    }
}
