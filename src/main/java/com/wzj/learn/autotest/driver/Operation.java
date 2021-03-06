package com.wzj.learn.autotest.driver;

import org.openqa.selenium.By;

public interface Operation {

    boolean isWebElementExist(By selector);

    String getWebText(By selector);

    void clickElementContainingText(By selector, String text);

    String getLinkUrlContainingText(By selector, String text);

    void click(By selector);

    String getLinkUrl(By selector);

    void sendKeys(By selector, String value);
}
