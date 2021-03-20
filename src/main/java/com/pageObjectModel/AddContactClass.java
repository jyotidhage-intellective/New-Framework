package com.pageObjectModel;

import com.application.BaseAction;
import com.aventstack.extentreports.Status;
import com.application.ConfigTestRunner;
import com.Utility.Constants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddContactClass extends BaseAction {
    public static Log log = LogFactory.getLog(AddContactClass.class);

    //add locators either use Factory or By reusable method
    @FindBy(xpath = "//div[contains(@class,'left-to-right')]") WebElement leftPanelVerticalMenu;

    @FindBy(xpath = "//span[text()='Contacts']")
    WebElement contact;
    @FindBy(xpath = "//input[@name='first_name']")
    WebElement Fname;

    @FindBy(xpath = "//input[@name='last_name']")
    WebElement Lname;
    @FindBy(xpath = "//div[@name='category']")
    WebElement category;
    @FindBy(xpath = "//button[text()='New']")
    WebElement newBtn;
    @FindBy(xpath = "//th[text()='Address']")
    WebElement  addresCol;
    @FindBy(xpath = "//span[text()='Customer']")
    WebElement customer;
    public AddContactClass(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void fnAddNewContact(ConfigTestRunner configTestRunner){
        configTestRunner.setChildTest(configTestRunner.getParentTest().createNode("Add New Contact Verification"));
        configTestRunner.elementUtil.fnWaitForVisibility(leftPanelVerticalMenu, Constants.AJAX_TIMEOUT);
        //move to left hand panel
        leftPanelVerticalMenu.click();
        configTestRunner.elementUtil.moveToElement(leftPanelVerticalMenu);
        configTestRunner.getChildTest().log(Status.INFO,"Click on the left hand panel");
        log.info("Click on the left hand panel");

        //click on Contacts menu
//        fnWaitForVisibility(getspanWithText(configTestRunner,"Contacts"),Constants.AJAX_TIMEOUT,driver);
        WebElement contact1 = contact;
        configTestRunner.getChildTest().log(Status.INFO,"Click on the contact menu on left hand panel");
        log.info("Click on contact menu");
        configTestRunner.elementUtil.setFocusClick(contact1);
        addresCol.click();
        //click on new button
//        newBtn.click();
        configTestRunner.elementUtil.setFocusClick(newBtn);
        configTestRunner.getChildTest().log(Status.INFO,"Click on the new button");
        //enter firstname
        Fname.sendKeys(configTestRunner.getBaseAction().getTestData().get("Fname"));
        configTestRunner.getChildTest().log(Status.INFO,"User  name enter is "+configTestRunner.getBaseAction().getTestData().get("Fname"));
        //enter lname
        Lname.sendKeys(configTestRunner.getBaseAction().getTestData().get("Lname"));
        configTestRunner.getChildTest().log(Status.INFO,"Lase name enter is "+configTestRunner.getBaseAction().getTestData().get("Lname"));
        //select category
        category.click();
        customer.click();
    }



}
