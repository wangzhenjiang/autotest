package com.wzj.learn.autotest.driver;

import com.wzj.learn.autotest.constants.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DefaultOperation implements Operation {

    private WebDriver webDriver;

    public DefaultOperation(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public boolean isWebElementExist(By selector) {
        try {
            webDriver.findElement(selector);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public String getWebText(By selector) {
        return webDriver.findElement(selector).getText();
    }

    @Override
    public void clickElementContainingText(By selector, String text) {
        List<WebElement> elementList = webDriver.findElements(selector);
        for (WebElement e : elementList) {
            if (e.getText().contains(text)) {
                e.click();
                break;
            }
        }
    }

    @Override
    public String getLinkUrlContainingText(By selector, String text) {
        List<WebElement> subscribeButton = webDriver.findElements(selector);
        String url = null;
        for (WebElement e : subscribeButton) {
            if (e.getText().contains(text)) {
                url = e.getAttribute("href");
                break;
            }
        }
        return url;
    }

    @Override
    public void click(By selector) {
        webDriver.findElement(selector).click();
        webDriver.manage().timeouts().implicitlyWait(Constants.WAIT_ELEMENT_TO_LOAD, TimeUnit.SECONDS);
    }

    @Override
    public String getLinkUrl(By selector) {
        return webDriver.findElement(selector).getAttribute("href");
    }

    @Override
    public void sendKeys(By selector, String value) {
        webDriver.findElement(selector).sendKeys(value);
    }
}
