package com.wzj.learn.autotest.testcase;

import com.wzj.learn.autotest.AbstractCustomTestCase;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * chromedriver: https://chromedriver.storage.googleapis.com/index.html?path=2.30/
 */
@RunWith(JUnit4.class)
public class ChromeDriverTest extends AbstractCustomTestCase {

    @Test
    public void testGoogleSearch() {
        webDriver.get("http://www.google.com");
        WebElement element = webDriver.findElement(By.name("q"));
        System.out.println("====> " + element.getTagName());
        System.out.println("====> " + element.getAttribute("title"));
    }

    @Test
    public void testBaiduSearch() {
        webDriver.get("https://www.baidu.com");
        WebElement element = webDriver.findElement(By.name("f"));
        System.out.println("====> " + element.getTagName());
        System.out.println("====> " + element.getAttribute("title"));
        System.out.println(webDriver.getTitle());
    }

}
