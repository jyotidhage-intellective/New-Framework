package com.Utility;


import com.application.BaseAction;
import com.application.ConfigTestRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Methods extends BaseAction {

    public  void setImplicitlyWait(int seconds,WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }
    public boolean fnWaitForVisibility(WebElement element , int waitFor,WebDriver driver){
        boolean visible = false;
        WebDriverWait wait = new WebDriverWait(driver,waitFor);
        wait.until(ExpectedConditions.visibilityOf(element));
        if(element.isDisplayed())
            return true;
        else
            return false;
    }
    public void setFocusClick(WebElement element,WebDriver driver) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.click();
        action.build().perform();
    }

    public void fnMoveToElement(WebElement element, WebDriver driver){
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.build().perform();
    }

    public WebElement listValue(ConfigTestRunner configTestRunner, String text){
        return configTestRunner.driver.findElement(By.xpath("//li[text()='"+text+"']"));
    }
    public WebElement teamsiteExpander(ConfigTestRunner configTestRunner, int i){
        return  configTestRunner.driver.findElement(By.xpath("//ul[@role='tree']//li["+i+"]//div[@class='MuiTreeItem-iconContainer']"));
    }

}
