package com.wzj.learn.autotest.service;

import org.openqa.selenium.WebDriver;

public interface DriverService {

    /**
     * 获取WebDriver
     *
     * @return WebDriver
     */
    WebDriver getWebDriver();

    /**
     * 退出
     *
     * @return
     */
    Boolean quit();
}
