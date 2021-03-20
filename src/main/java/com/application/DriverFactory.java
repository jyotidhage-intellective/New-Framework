package com.application;

import com.Utility.Constants;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;

import java.util.concurrent.TimeUnit;

public class DriverFactory {

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public String driverpath;

    /*******
     * This method is used to initialized the browser
     *
     */

    public WebDriver startBrowser(String browser) {
        if(browser.trim().equalsIgnoreCase("Chrome")){
            ChromeOptions options = new ChromeOptions();
            options.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
            options.addArguments("--start-maximized");
            options.addArguments("--disable-default-apps");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable.dev.shm.usage");
            options.setExperimentalOption("excludeSwithes", new String[] {"enable-automation"});
            options.setExperimentalOption("useAutomationExtension",false);
            driverpath = Constants.driverPath + "/chromedriver.exe";
            System.setProperty("webdriver.chrome.driver", driverpath);
            tlDriver.set(new ChromeDriver());
            setImplicitlyWait(Constants.Implicit_Wait);

        }else if(browser.trim().equalsIgnoreCase("IE")) {
            InternetExplorerOptions ieOption = new InternetExplorerOptions();
            ieOption.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
            ieOption.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            ieOption.setCapability("ignoreProtectedModeSettings", true);
            driverpath = Constants.driverPath + "/IEDriverServer.exe";
            System.setProperty("webdriver.ie.driver", driverpath);
            tlDriver.set(new InternetExplorerDriver(ieOption));
            setImplicitlyWait(100);
        }else{
            System.out.println("No Browser is selected for opening");
        }
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        return getDriver();
    }
    /*
    This method is used to apply the Implicit wait on the browser
     */
    public  void setImplicitlyWait(int seconds) {
        getDriver().manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }
    /*
     *This method is to return the driver instance
     */
    public static synchronized WebDriver getDriver(){
        return tlDriver.get();
    }
}
