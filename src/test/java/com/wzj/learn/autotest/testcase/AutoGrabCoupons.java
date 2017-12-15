package com.wzj.learn.autotest.testcase;

import com.wzj.learn.autotest.AbstractCustomTestCase;
import com.wzj.learn.autotest.utils.PropertiesUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

/**
 * 请填写类的描述
 *
 * @author wangzhenjiang
 * @date 2017-11-10 15:35
 */
public class AutoGrabCoupons  extends AbstractCustomTestCase {
    //备件库商品链接
    private static final String URL ="https://sale.jd.com/act/FciOehkX53fqAY.html";

    @Test
    public void autoGrabCoupons(){
        webDriver.get(URL);
        webDriver.findElement(By.className("back-shade")).click();
        toLoginJD();
    }

    private void toLoginJD() {
        String username = PropertiesUtil.get("jd.username");
        String password = PropertiesUtil.get("jd.password");
        WebElement loginDivElement = findElement(By.className("login-tab login-tab-r"));
        WebElement loginAElement = loginDivElement.findElement(By.tagName("a"));
        //执行javascript
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("arguments[0].click()", loginAElement);

        WebElement element = findElement(By.id("loginname"));
        element.sendKeys(username);
        element = findElement(By.id("nloginpwd"));
        element.sendKeys(password);
        element = findElement(By.className("login-btn"));
        WebElement submitBtn = element.findElement(By.tagName("a"));
        executor.executeScript("arguments[0].click()", submitBtn);
    }
}
