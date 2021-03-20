package com.application;

import com.Utility.Constants;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login_xpathproperties extends BaseAction {
    private String formName="Login";

    public boolean loginToApplication(ConfigTestRunner configTestRunner) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Login Window Verification"));
        configTestRunner.getChildTest().log(Status.INFO, "URL use to login into the application is "+ Constants.URL);
        boolean isPresence = false;
        //Assert.assertTrue(SignInBut ton.isDisplayed());
        WebElement element = getWebElement("userid",formName,configTestRunner);
        configTestRunner.elementUtil.fnWaitForVisibility(element,Constants.AJAX_TIMEOUT);
        if(getWebElement("signInBtn",formName,configTestRunner).isDisplayed()) {
            getWebElement("signInBtn",formName,configTestRunner).click();
            getWebElement("userid",formName,configTestRunner).sendKeys(configTestRunner.getBaseAction().testCase.get("UserName"));
            configTestRunner.getChildTest().log(Status.INFO, "User Name Enter is "+configTestRunner.getBaseAction().testCase.get("UserName"));
            getWebElement("password",formName,configTestRunner).sendKeys(configTestRunner.getBaseAction().testCase.get("Password"));
            configTestRunner.getChildTest().log(Status.INFO, "User Name Enter is "+configTestRunner.getBaseAction().testCase.get("Password"));
            getWebElement("loginBtn",formName,configTestRunner).click();
            sleep(2000);
            try{
                if (configTestRunner.elementUtil.fnWaitForVisibility(getWebElement("leftPanelVerticalMenu",formName,configTestRunner), Constants.AJAX_TIMEOUT)) {
                    configTestRunner.getChildTest().log(Status.PASS,"Login to the application is successful"+configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName("Login_Successfull")));
                } else
                    configTestRunner.getChildTest().log(Status.FAIL, "Login to the application is successful."+configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName("Login_Successfully")));
            }catch (Exception e){
                e.printStackTrace();
                try{
                    configTestRunner.getChildTest().log(Status.FAIL, "Login to the application is not successful."+configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.capture("Login_UnSuccessfully")));
                }catch (Exception e1){

                }
            }
        }
        return isPresence;
    }
    public synchronized void LogOutFromApplication(ConfigTestRunner configTestRunner) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("LogOut From Application Verification"));
        configTestRunner.elementUtil.fnWaitForVisibility(getWebElement("settingIcon",formName,configTestRunner),Constants.AJAX_TIMEOUT);
        configTestRunner.elementUtil.waitAndClick(getWebElement("settingIcon",formName,configTestRunner),Constants.AJAX_TIMEOUT);
        configTestRunner.elementUtil.fnWaitForVisibility(getWebElement("logOutBtn",formName,configTestRunner),Constants.AJAX_TIMEOUT);
        configTestRunner.elementUtil.waitAndClick(getWebElement("logOutBtn",formName,configTestRunner),Constants.AJAX_TIMEOUT);
        if(configTestRunner.elementUtil.fnWaitForVisibility(getWebElement("userid",formName,configTestRunner),Constants.AJAX_TIMEOUT))
            configTestRunner.getChildTest().log(Status.PASS, "User is able to log out from the application");
        else
            configTestRunner.getChildTest().log(Status.FAIL, "User is not able to log out from the application");
    }

}

