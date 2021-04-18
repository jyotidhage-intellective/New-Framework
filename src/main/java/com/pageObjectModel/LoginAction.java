package com.pageObjectModel;

import com.application.BaseAction;
import com.application.ConfigTestRunner;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.Utility.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LoginAction extends BaseAction {
    public ExtentTest test;
//@FindBy it replace driver.findElement
    @FindBy(xpath="//span[text()='Log In']") WebElement SignInButton;

    @FindBy(name="email")
    WebElement userName;

    @FindBy(name="password")
    WebElement password;


    @FindBy(xpath="//div[text()='Login']")
    WebElement loginBtn;

    @FindBy(xpath = "//div[contains(@class,'left-to-right')]")
    WebElement leftPanelVerticalMenu;

    @FindBy(xpath = "(//i[@class='settings icon'])[1]")
    WebElement settingIcon;

    @FindBy(xpath = "//span[text()='Log Out']")
    WebElement logOutBtn;


    public LoginAction(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean loginToApplication(ConfigTestRunner configTestRunner) {
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Login Window Verification"));
    	configTestRunner.getChildTest().log(Status.INFO, "URL use to login into the application is "+Constants.URL);
        boolean isPresence = false;
        //Assert.assertTrue(SignInBut ton.isDisplayed());
        WebElement element = SignInButton;
       configTestRunner.elementUtil.fnWaitForVisibility(element,Constants.AJAX_TIMEOUT);
        if(SignInButton.isDisplayed()) {
            SignInButton.click();
            userName.sendKeys(configTestRunner.getBaseAction().testCase.get("UserName"));
            configTestRunner.getChildTest().log(Status.INFO, "User Name Enter is "+configTestRunner.getBaseAction().testCase.get("UserName"));
            password.sendKeys(configTestRunner.getBaseAction().testCase.get("Password"));
            configTestRunner.getChildTest().log(Status.INFO, "User Name Enter is "+configTestRunner.getBaseAction().testCase.get("Password"));
            loginBtn.click();
            sleep(2000);
            try{
                Assert.assertTrue(leftPanelVerticalMenu.isDisplayed());
                if (configTestRunner.elementUtil.fnWaitForVisibility(leftPanelVerticalMenu, Constants.AJAX_TIMEOUT)) {
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
        configTestRunner.elementUtil.fnWaitForVisibility(settingIcon,Constants.AJAX_TIMEOUT);
        configTestRunner.elementUtil.waitAndClick(settingIcon,Constants.AJAX_TIMEOUT);
        configTestRunner.elementUtil.fnWaitForVisibility(logOutBtn,Constants.AJAX_TIMEOUT);
        configTestRunner.elementUtil.waitAndClick(logOutBtn,Constants.AJAX_TIMEOUT);
        sleep(1000);
        if(configTestRunner.elementUtil.fnWaitForVisibility(userName,Constants.AJAX_TIMEOUT))
            configTestRunner.getChildTest().log(Status.PASS, "User is able to log out from the application");
        else
            configTestRunner.getChildTest().log(Status.FAIL, "User is not able to log out from the application");
    }

    }
