package com.wzj.learn.autotest.testcase;

import com.wzj.learn.autotest.AbstractCustomTestCase;
import com.wzj.learn.autotest.constants.TestConstant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class FireFoxDriverTest extends AbstractCustomTestCase {

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
        WebElement searchBox = webDriver.findElement(By.name("wd"));
        searchBox.clear();
        searchBox.sendKeys("wangzhenjiang");//搜索关键词
        WebElement element = webDriver.findElement(By.id("su"));//获取搜索一下按钮
        element.click();//触发点击事件
        //等待搜索结果
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, TestConstant.WAIT_ELEMENT_TO_LOAD);
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id='1']")));
        //遍历搜索结果
        int i = 1;
        while (i < 100) {
            WebElement searchRes = webDriver.findElement(By.id(i + ""));
            if (searchRes != null) {//查找搜索结果中 链接文本包含 “镇江人才在线” 的连接
                WebElement e = searchRes.findElement(By.xpath("//a[contains(text(),'镇江人才在线')]"));
                if (e == null)
                    continue;
                System.out.println();
                System.out.println("====> " + e.getAttribute("href"));
                break;
            }
            i++;
        }
    }
}
