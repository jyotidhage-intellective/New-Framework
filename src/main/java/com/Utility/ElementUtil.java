package com.Utility;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ElementUtil {
    private final WebDriver driver;

    public  ElementUtil(WebDriver driver){
        this.driver=driver;
    }

    public void waitAndClick(final WebElement element, int waitfor) {
        WebDriverWait wait = new WebDriverWait(driver, waitfor);
        wait.until(new ExpectedCondition<WebElement>() {
            public final ExpectedCondition<WebElement> visibilityOfElement = ExpectedConditions.visibilityOf(element);
            @Override
            public WebElement apply(WebDriver driver) {
                try {
                    WebElement elementx = this.visibilityOfElement.apply(driver);
                    if (elementx == null) {
                        return null;
                    }
                    if (elementx.isDisplayed() && elementx.isEnabled())  {
                        elementx.click();
                        return elementx;
                    } else {
                        return null;
                    }
                } catch (WebDriverException e) {
                    return null;
                }
            }
        });
    }

    public boolean fnWaitForVisibility(WebElement element , int waitFor){
        WebDriverWait wait = new WebDriverWait(driver,waitFor);
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.isDisplayed();
    }

    public WebElement getRowDoc(int id){
        return driver.findElement(By.xpath("//table//tbody//tr["+id+"]"));
    }

    public WebElement getSpanText(String text){
        return driver.findElement(By.xpath("//span[text()='"+text+"']"));
    }
    public WebElement getSpanContainsText(String text){
        return driver.findElement(By.xpath("//span[contains(text(),'"+text+"')]"));
    }
    public WebElement expandPart(String text){
        return driver.findElement(By.xpath("//button[contains(@name,'"+text+"')]"));
    }
    public WebElement getHtag(String text){
        return driver.findElement(By.xpath("//h6[text()='"+text+"']"));
    }
    public WebElement getH1tag(String text){
        return driver.findElement(By.xpath("//h1[text()='"+text+"']"));
    }


    public WebElement selectdoticontoOpencase(String text){
        return driver.findElement(By.xpath("//span[text()='"+text+"']/following::button[1]"));
    }

    public WebElement checkStatusOfCase(String caseId , String status){
        return driver.findElement(By.xpath("//span[text()='"+caseId+"']/following::span[text()='"+status+"']"));
    }
    public WebElement getButtonText(String text){
        return driver.findElement(By.xpath("//button[text()='"+text+"']"));
    }
    public WebElement getText(String text){
        return driver.findElement(By.xpath("//*[text()='"+text+"']"));
    }
    public void setFocusEnter(WebElement element, String value) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.sendKeys(value);
        action.sendKeys(Keys.DOWN);
        action.sendKeys(Keys.ENTER);
        action.build().perform();
    }
    public void moveToElement(WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.build().perform();
    }
    public void setFocus(WebElement element, String value) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.click();
        action.sendKeys(value);
        action.build().perform();
    }

    public void sendKeysEnter(WebElement element){
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.click();
        action.sendKeys(Keys.ENTER);
        action.build().perform();
    }

    public void setFocusdblClick(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.doubleClick();
        action.build().perform();
    }
    public void setFocusClick(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.click();
        action.build().perform();
    }
    public void sendKeysDownClick(WebElement element){
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.sendKeys(Keys.DOWN);
        action.build().perform();
    }
    public void sendKeysDownClickEnter(WebElement element){
        Actions action = new Actions(driver);
        action.moveToElement(element);
        action.sendKeys(Keys.DOWN);
        action.sendKeys(Keys.ENTER);
        action.build().perform();
    }
    public void emptyTextData(WebElement element){
        element.sendKeys(Keys.CONTROL+"a");
        element.sendKeys(Keys.DELETE);
    }
    public void executeExtJsClick(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

}

