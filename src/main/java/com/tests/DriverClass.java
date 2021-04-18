package com.tests;

import com.Utility.Constants;
import com.application.ConfigTestRunner;
import com.application.DriverFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.pageObjectModel.LoginAction;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class DriverClass {
    public String destFile;
    public ExtentTest test;
    public ExtentHtmlReporter htmlReports;   //to generate an html file
    private static ExtentReports extent;
    private ConfigTestRunner configTestRunnerLocal;
    private WebDriver driver;

    @BeforeSuite
    public void extentReportConfig(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");
        destFile = System.getProperty("user.dir")+ Constants.reportsFilePath+"SeleniumTraining_"+dateFormat.format(new Date());
        File newFolder = new File(destFile);
        boolean created =  newFolder.mkdir();  //mkdir will create folder
        if(created)
            System.out.println("Folder is created !");
        else
            System.out.println("Unable to create folder");

        String destDir = "FreeCrem_Report"+dateFormat.format(new Date()) + ".html";
        htmlReports = new ExtentHtmlReporter(destFile + "\\" +destDir);  //to generate an html file
        extent = new ExtentReports();
        extent.attachReporter(htmlReports);
        setExtent(extent);
        htmlReports.config().setReportName("Free CRM Application");
        htmlReports.config().setTheme(Theme.STANDARD);
        htmlReports.config().setDocumentTitle("FreeCrm Test Result");
    }
    @BeforeMethod
    @Parameters("browser")
    public void initializeBrowser(String browser){
        DriverFactory driverFactory = new DriverFactory();
        driver =driverFactory.startBrowser(browser);
        driver.get(Constants.URL);
    }
    @Test
    public void SanityTest001(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC001");
    }
    @Test
    public void SanityTest002(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC002");
    }
    @AfterMethod
    public void fnCloseBrowser(ITestResult testResult){
        if(testResult.getStatus()== ITestResult.FAILURE){
            String name ="Test_Fail_ScreenShot";
            try {
                getConfigTestRunnerLocal().getChildTest().log(Status.FAIL, "Test is failed" + getConfigTestRunnerLocal().getChildTest().addScreenCaptureFromPath(getConfigTestRunnerLocal().screenShotName(name)));
            }catch (Exception exc){
                exc.printStackTrace();
            }
            getConfigTestRunnerLocal().getBaseAction().setCellData("Fail",getConfigTestRunnerLocal().getBaseAction().rowValue(getConfigTestRunnerLocal().getBaseAction().getTestCase().get("TC_ID"), getConfigTestRunnerLocal().getBaseAction().ColumnValue("TC_ID","TestCase")),getConfigTestRunnerLocal().getBaseAction().ColumnValue("Status","TestCase"));
        }  else if(testResult.getStatus() == ITestResult.SKIP){
            test.log(Status.SKIP,testResult.getThrowable());
            getConfigTestRunnerLocal().getBaseAction().setCellData("Skip",getConfigTestRunnerLocal().getBaseAction().rowValue(getConfigTestRunnerLocal().getBaseAction().getTestCase().get("TC_ID"), getConfigTestRunnerLocal().getBaseAction().ColumnValue("TC_ID","TestCase")),getConfigTestRunnerLocal().getBaseAction().ColumnValue("Status","TestCase"));
        }
        getConfigTestRunnerLocal().getBaseAction().setCellData("Pass",getConfigTestRunnerLocal().getBaseAction().rowValue(getConfigTestRunnerLocal().getBaseAction().getTestCase().get("TC_ID"), getConfigTestRunnerLocal().getBaseAction().ColumnValue("TC_ID","TestCase")),getConfigTestRunnerLocal().getBaseAction().ColumnValue("Status","TestCase"));
    }
    @AfterSuite
    public void End_SetUp(){
        getConfigTestRunnerLocal().getExtent().flush();
        if(getConfigTestRunnerLocal().driver!=null)
        getConfigTestRunnerLocal().driver.quit();
    }

    public ConfigTestRunner getConfigTestRunnerLocal() {
        return configTestRunnerLocal;
    }

    public void setConfigTestRunnerLocal(ConfigTestRunner configTestRunnerLocal) {
        this.configTestRunnerLocal = configTestRunnerLocal;
    }

    public ExtentReports getExtent() {
        return extent;
    }

    public void setExtent(ExtentReports extent) {
        this.extent = extent;
    }
}
