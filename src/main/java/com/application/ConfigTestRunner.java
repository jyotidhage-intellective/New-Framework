package com.application;

import com.Utility.ElementUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.config.Configuration;
import com.pageObjectModel.AddContactClass;
import com.pageObjectModel.LoginAction;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class ConfigTestRunner {

    private String destFile;
    private ConfigTestRunner configTestRunner = null;
    private com.aventstack.extentreports.ExtentTest parentTest;
    private com.aventstack.extentreports.ExtentTest childTest;
    private ExtentReports extent;
    private BaseAction baseAction;
    private LoginAction loginAction;
    private Configuration config;
    public ElementUtil elementUtil;
    public AddContactClass addContactClass;
    public WebDriver driver=DriverFactory.getDriver();
    public String TestCase_Id;
    public Map<String, String> testCase = new ConcurrentHashMap<>();
    public Map<String, String> testData = new ConcurrentHashMap<>();

    public ConfigTestRunner(ExtentReports extent) {
        setExtent(extent);
        //setDriver(driver);
    }

    public void run(String Tcnumber) {
        baseAction = new BaseAction();
        baseAction.ReadTestData(Tcnumber);
        if (baseAction.testCase.get("Execute").equalsIgnoreCase("YES")) {
            parentTest = extent.createTest(baseAction.testCase.get("TC_ID") + " " + baseAction.testCase.get("Description"));
            // driver = baseAction.initializeBrowser(browser);
            intActions();
            TestCase_Id = baseAction.testCase.get("TC_ID");
            //driver.get(Constants.URL);
            int rowNo = baseAction.RowNo("TestData", Tcnumber, "TC_ID");
            baseAction.TestDataDic(rowNo, "TestData");
            loginAction.loginToApplication(configTestRunner);
            SCExecutor(Tcnumber);
            loginAction.LogOutFromApplication(configTestRunner);
            driver.close();
        } else
            parentTest.log(Status.INFO, "No Test Case is considered for execution");

    }

    public void SCExecutor(String tcNumber) {
        if (tcNumber.equalsIgnoreCase("TC001")) {
            addContactClass.fnAddNewContact(configTestRunner);
        }else if (tcNumber.equalsIgnoreCase("TC002")) {
            addContactClass.fnAddNewContact(configTestRunner);
        }else{
                parentTest.log(Status.INFO,"No Test Case is considered for execution");
        }
    }


    public void intActions(){
        elementUtil= new ElementUtil(driver);
        config = new Configuration();
        loginAction = new LoginAction(driver);
        addContactClass = new AddContactClass(driver);

    }
    public String screenShotName(String screenShotName){

        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");
        String fileName = screenShotName+"_"+dateFormat.format(new Date())+".png";
        return  capture(fileName);
    }

    public String capture(String screenShotName)  {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        System.out.println(getDestFile());
        String dest =getDestFile()+"\\"+screenShotName;
        File destination = new File(dest);
        try {
            FileUtils.copyFile(source, destination);
            byte[] bytes = new byte[(int) source.length()];
            String base64 = new String(Base64.encodeBase64(bytes), "UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return dest;
    }

    public Map<String, String> getTestData() {
        return testData;
    }

    public void setTestData(Map<String, String> testData) {
        this.testData = testData;
    }

    public ExtentReports getExtent() {
        return extent;
    }

    public void setExtent(ExtentReports extent) {
        this.extent = extent;
    }

    public void setConfigTestRunner(ConfigTestRunner configTestRunner) {
        this.configTestRunner= configTestRunner;
    }
    public ConfigTestRunner getConfigTestRunner() {
        return configTestRunner;
    }

    public String getDestFile() {
        return destFile;
    }

    public void setDestFile(String destFile) {
        this.destFile = destFile;
    }

    public ExtentTest getParentTest() {
        return parentTest;
    }

    public void setParentTest(ExtentTest parentTest) {
        this.parentTest = parentTest;
    }

    public ExtentTest getChildTest() {
        return childTest;
    }

    public void setChildTest(ExtentTest childTest) {
        this.childTest = childTest;
    }


    public Map<String, String> getTestCase() {
        return testCase;
    }

    public BaseAction getBaseAction() {
        return baseAction;
    }

    public void setBaseAction(BaseAction baseAction) {
        this.baseAction = baseAction;
    }

    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }
}


