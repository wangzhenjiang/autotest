package com.wzj.learn.autotest.testcase;

import com.wzj.learn.autotest.AbstractCustomTestCase;
import com.wzj.learn.autotest.utils.PropertiesUtil;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

public class TestAutoShopping extends AbstractCustomTestCase {
    private static final String CODE = "PG2017112300013029";//备件库条码
    private static final boolean skipCodeCheck = true;//跳过备件库条码检查
    private static final String goodsURL =//备件库商品链接
            "http://insales.360buy.com/productdetail/show?timerand=1523357841506&wareId=1938008&cid1=&cid2=&orgId=&priceMin=&priceMax=&status=&specialRateStatus=";

    @Test
    public void autoShoppingHighLevel() {
        webDriver.get(goodsURL);
        toLogin();
        outer:
        for (; ; ) {
            //刷新当前页面
            webDriver.navigate().refresh();
            //获取tbody标签,为防止加载速度太慢，使用等待机制
            WebElement element = new WebDriverWait(webDriver, 5)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("tbody")));
            List<WebElement> trElementList = element.findElements(By.tagName("tr"));
            if (trElementList.size() < 2) {
                System.out.println("------商品被抢完了------------");
                return;
            }
            for (int i = 1, length = trElementList.size(); i < length; i++) {
                WebElement trElement = trElementList.get(i);
                List<WebElement> tdElementList = trElement.findElements(By.xpath("td"));
                //获取备件库条码
                WebElement tdElement = tdElementList.get(1);
                if (skipCodeCheck || CODE.equals(tdElement.getText())) {
                    tdElement = tdElementList.get(tdElementList.size() - 1);
                    WebElement cartElement = findElement(tdElement, By.id("addtocart"));
                    if (cartElement == null) {
                        System.out.println("---------抢购尚未开始,刷新页面------------");
                        break;
                    }
                    System.out.println("---------抢购开始------------");
                    toShopping(cartElement);
                    break outer;
                }
            }
        }
    }

    @Test
    public void shoppingRemind() {
        String remindURL = "http://insales.360buy.com/productdetail/show?timerand=1514367585678&wareId=4734534&cid1=&cid2=&orgId=&priceMin=&priceMax=&status=&specialRateStatus=";
        webDriver.get(remindURL);
        toLogin();
        for (; ; ) {
            //刷新当前页面
            webDriver.navigate().refresh();
            //打开购物车
            WebElement noGoods = findElement(By.id("no_result_info"));
            if (noGoods != null) {
                long millis = RandomUtils.nextLong(300000, 600000);
                System.out.println("---进入休眠，休眠时间：" + millis + "ms. " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                try {
                    //随机休眠 5~10 分钟
                    Thread.sleep(millis);
                } catch (InterruptedException e) {//ignore
                }
                System.out.println("---休眠结束，休眠时间：" + millis + "ms. " + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

                continue;
            } else {
                webDriver.get("http://insales.360buy.com/");
                toLogin();
                break;
            }
        }

    }

    private void toShopping(WebElement cartElement) {
        //执行javascript
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        //加入购物车
        executor.executeScript("arguments[0].click()", cartElement);
        //处理弹窗
        Alert alert = new WebDriverWait(webDriver, 1).until(ExpectedConditions.alertIsPresent());
        alert.accept();
        //打开购物车
        WebElement openCartElement = findElement(By.id("cartspan"));
        executor.executeScript("arguments[0].click()", openCartElement);
        //去结算
        WebElement toPayElement = findElement(By.id("topay"));
        executor.executeScript("arguments[0].click()", toPayElement);
        //提交订单
        WebElement submitOrderElement = findElement(By.id("sub_topay"));
        executor.executeScript("arguments[0].click()", submitOrderElement);
        System.out.println("------订单提交成功------------- ");
    }

    private void toLogin() {
        String username = PropertiesUtil.get("erp.username");
        String password = PropertiesUtil.get("erp.password");
        WebElement element = findElement(By.id("username"));
        element.sendKeys(username);
        element = findElement(By.id("password"));
        element.sendKeys(password);
        element = findElement(By.className("formsubmit_btn"));
        element.submit();
    }
}
