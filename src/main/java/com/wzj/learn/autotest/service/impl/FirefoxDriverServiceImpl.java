package com.wzj.learn.autotest.service.impl;

import com.wzj.learn.autotest.driver.FirefoxWebDriverHolder;
import com.wzj.learn.autotest.service.DriverService;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

/**
 * 请填写类的描述
 *
 * @author wangzhenjiang
 * @date 2017-11-27 21:30
 */
@Service("firefoxDriverService")
public class FirefoxDriverServiceImpl implements DriverService {
    @Override
    public WebDriver getWebDriver() {
        return FirefoxWebDriverHolder.INSTANCE.getNewFirefoxWebDriver();
    }

    @Override
    public Boolean quit() {
        return true;
    }
}
