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
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class DriverClass {
    public String destFile;
    public ExtentTest test;
    public ExtentHtmlReporter htmlReports;   //to generate an html file
    private ExtentReports extent;
    private ConfigTestRunner configTestRunnerLocal;

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
        htmlReports.config().setReportName("Free CRM Application");
        htmlReports.config().setTheme(Theme.STANDARD);
        htmlReports.config().setDocumentTitle("FreeCrm Test Result");
    }
    @BeforeMethod
    public void initializeBrowser(){
        DriverFactory driverFactory = new DriverFactory();
        WebDriver driver =driverFactory.startBrowser("chrome");
        driver.get(Constants.URL);
    }
    @Test
   // @Parameters("browser")
    public void SanityTest001(){
        ConfigTestRunner configTestRunner = new ConfigTestRunner(extent);
        configTestRunner.setConfigTestRunner(configTestRunner);
        setConfigTestRunnerLocal(configTestRunner);
        configTestRunner.setDestFile(destFile);
        configTestRunner.run("TC001");
    }
    @Test
    // @Parameters("browser")
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
                getConfigTestRunnerLocal().getChildTest().log(Status.FAIL, "Test is failed" + getConfigTestRunnerLocal().getChildTest().addScreenCaptureFromPath(getConfigTestRunnerLocal().capture(name)));
            }catch (Exception exc){
                exc.printStackTrace();
            }
        }  else if(testResult.getStatus() == ITestResult.SKIP){
            test.log(Status.SKIP,testResult.getThrowable());
        }
        LoginAction loginAction = new LoginAction(DriverFactory.getDriver());
        try {
            loginAction.LogOutFromApplication(getConfigTestRunnerLocal());
        }catch (Exception e){

        }
    }
    @AfterSuite
    public void End_SetUp(){
        getConfigTestRunnerLocal().getExtent().flush();
        Set<String> windowHandle = getConfigTestRunnerLocal().driver.getWindowHandles();
        for(String s:windowHandle) {
            if (getConfigTestRunnerLocal().driver != null)
                getConfigTestRunnerLocal().driver.quit();
        }
    }


    public ConfigTestRunner getConfigTestRunnerLocal() {
        return configTestRunnerLocal;
    }

    public void setConfigTestRunnerLocal(ConfigTestRunner configTestRunnerLocal) {
        this.configTestRunnerLocal = configTestRunnerLocal;
    }

}
