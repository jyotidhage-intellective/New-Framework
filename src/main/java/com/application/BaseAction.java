package com.application;

import com.Utility.Constants;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import javax.security.auth.login.Configuration;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public  class BaseAction {
    public WebDriver driver;
    protected Configuration conf;
    public String driverpath;
    public String destFile;
    public ExtentTest test;
    private ExtentReports extent;
    private static XSSFWorkbook wb;
    private static XSSFSheet sh;
    private int Rowcnt;
    File Src;
    public Map<String, String> testCase = new ConcurrentHashMap<String, String>();
    private Map<String, String> testData = new ConcurrentHashMap<String, String>();
    private boolean Exist = false;
    public com.aventstack.extentreports.ExtentTest parentTest;
    public com.aventstack.extentreports.ExtentTest childTest;


//    public BaseAction(WebDriver driver, Configuration conf) {
//            this.driver = driver;
//        this.conf = conf;
//    }

    public WebDriver getDriver() {
        return driver;
    }

    public Configuration getConf() {
        return conf;
    }
//    public abstract String getFormName()

    public void readExcelDataFile(String FilePath) {
        InputStream is;
        FileInputStream Src=null;
        if (!Constants.TestDataPath.isEmpty() ) {
            try {
//                Src = new File(System.getProperty("user.dir") + Constants.TestDataPath + FilePath);
                Src = new FileInputStream(System.getProperty("user.dir") + Constants.TestDataPath + FilePath);
            }catch (Exception e){

            }

        } else {
            is = getClass().getResourceAsStream(FilePath);
            try {
                wb = new XSSFWorkbook(is);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("Path of the File is" + is);
        }
        try {
            wb=new XSSFWorkbook(Src);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Map<String,String> ReadTestData(String TCID){
        readExcelDataFile("TestData.xlsx");
        // int RCount = RowCount("TestCase");
        // int CCount = ColCount("TestCase");
        int rowno =  RowNo("TestCase",TCID,"TC_ID");
        return TestCaseDic(rowno,"TestCase");
    }
    public int RowCount(String SheetName){
        sh = wb.getSheet(SheetName);
        return sh.getLastRowNum();
    }

    public int ColCount(String SheetName){
        sh = wb.getSheet(SheetName);
        return sh.getRow(0).getLastCellNum();

    }
    public Map<String,String>  TestCaseDic(int m, String sheetName) {
        int CCnt = ColCount(sheetName);
        for (int q = 0; q < CCnt; q++) {
            String Key = getExcelDataint(sheetName,0, q).trim();

            if (Key.equals("")) {
                testCase.put(Key, " ");
            } else {
                try {
                    String Value = getExcelDataint(sheetName,m, q).trim();
                    testCase.put(Key, Value);
                }catch (Exception e){

                }
            }
        }
        return testCase;
    }
    public void TestDataDic(int RowNo,String sheetName) {
        int Ccnt = ColCount(sheetName);
        try {
            for (int j = 0; j < Ccnt; j++) {
                String key = getExcelDataint(sheetName, 0, j).trim();
                if (key.equals("")) {
                    testData.put(key, null);
                } else {
                    try {
                        String value = getExcelDataint(sheetName, RowNo, j).trim();
                        testData.put(key, value);
                    } catch (Exception ex) {
//                        ex.printStackTrace();
                    }
                }
            }
        }catch (Exception e){

        }
    }
    String getExcelDataint(String sheetName,int RowNo, int ColNo){
        String CellValue=null;
        try {
            // int ColNo = ColumnValue(colNo);
            sh = wb.getSheet(sheetName);
            // Cell cell=sh.getRow(RowNo).getCell(ColNo);
            CellValue = sh.getRow(RowNo).getCell(ColNo).getStringCellValue();
        }catch (Exception ex){
//             ex.printStackTrace();
        }
        return CellValue;
    }
    public int RowNo(String Sheet,String RowValue,String ColValue1){
        int RCount = RowCount(Sheet);
        int Cocnt = ColumnValue(ColValue1,Sheet);
        for (int p = 1; p<=RCount; p++){
            String Value = sh.getRow(p).getCell(Cocnt).getStringCellValue().trim();
            if (RowValue.trim().equals(Value.trim())) {
                Exist = true;
                return p;
            }
        }
        if (!Exist){
            System.out.println(RowValue+" This row value is not exist.");
        }
        return -1;
    }
    public int rowValue(String RowData, int colNo){
        Rowcnt = sh.getLastRowNum();
        for (int j = 0 ; j<=Rowcnt ; j++ ) {
            try {
                String Value = sh.getRow(j).getCell(colNo).getStringCellValue().trim();
                if (RowData.trim().equals(Value.trim())) {
                    Exist = true;
                    return j;
                }
            }catch (Exception e1){

            }
        }
        if (!Exist){
            System.out.println(RowData+" This row is not exist.");
        }
        return -1;
    }
    public int ColumnValue(String ColumnName, String shName){

        sh = wb.getSheet(shName);
        int cnt = sh.getRow(0).getLastCellNum();
        for (int j = 0 ; j<=cnt ; j++ ) {

            String Value = sh.getRow(0).getCell(j).getStringCellValue().trim();
            if (ColumnName.trim().equals(Value.trim())) {
                Exist = true;
                return j;
            }
        }
        if (!Exist){
            System.out.println(ColumnName+" This column is not exist.");
        }

        return -1;
    }

    public ExtentReports getExtent() {
        return extent;
    }

    public void setExtent(ExtentReports extent) {
        this.extent = extent;
    }

    public boolean fnWaitForVisibility(WebElement element , int waitFor, WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver,waitFor);
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.isDisplayed();
    }
    public void Sleep(int wait){
        try{
            Thread.sleep(wait);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void takeScreenShot(WebDriver driver, String filename){
        //convert driver object into TakeScreenshot object
        TakesScreenshot srcShot =  (TakesScreenshot)driver;
        //getScreenshotas which actually take screen shot
        File sourceFile = srcShot.getScreenshotAs(OutputType.FILE);
        //copy this to destination
        File destinationpath = new File("C:\\Users\\jdhage\\Desktop\\swTesting\\Project\\newPro\\LatestFreeCRM\\LatestFreeCRM\\resources\\Reports\\"+filename+".png");
        try {
            FileUtils.copyFile(sourceFile, destinationpath);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void fnTakeScreenAshot(ConfigTestRunner configTestRunner, String status , String Message, String screenShotName){

        if(status.equalsIgnoreCase("Pass")){
            try {
                configTestRunner.getChildTest().log(Status.PASS, Message +" "+ configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName(screenShotName )));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(status.equalsIgnoreCase("Fail")){
            try {
                configTestRunner.getChildTest().log(Status.FAIL, Message+" " + configTestRunner.getChildTest().addScreenCaptureFromPath(configTestRunner.screenShotName(screenShotName )));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public WebElement getSpanWithText(ConfigTestRunner configTestRunner,String text) {
        List<WebElement> webElement=null;
        try{
            webElement = configTestRunner.driver.findElements(By.xpath("//span[text() = '" + text + "']"));
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL,  text+" :field is not present in the application.");
        }
        //  assert webElement != null;
        if (webElement.size() >= 1) {

            return webElement.get((webElement.size() - 1));
        } else {
            return webElement.get(0);
        }

    }
    public WebElement getThOfTableHeader(ConfigTestRunner configTestRunner, String text, WebDriver driver){
        WebElement element = driver.findElement(By.xpath("//th[//span[text()='"+ text +"']]"));
        //WebElement element = driver.findElement(By.xpath("//h6[text()='Enterprise search']/following::table[//th[//span[text()='"+text+"']]"));
        return element;
    }
    public WebElement getHeaderWithText(ConfigTestRunner configTestRunner,String text, WebDriver driver) {
        List<WebElement> webElement=null;
        try{
            webElement = driver.findElements(By.xpath("//h6[text() = '" + text + "']"));
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL,  text+" :field is not present in the application.");
        }
        //  assert webElement != null;
        assert webElement != null;
        if (webElement.size() >= 1) {

            return webElement.get((webElement.size() - 1));
        }else
            return webElement.get(0);

    }
    public WebElement getButtonWithText(ConfigTestRunner configTestRunner,String text) {
        List<WebElement> webElement=null;
        try{
            webElement = driver.findElements(By.xpath("//button[text() = '" + text + "']"));
        }catch (Exception e){
            configTestRunner.getChildTest().log(Status.FAIL,  text+" :field is not present in the application.");
        }
        assert webElement != null;
        if (webElement.size() >= 1) {

            return webElement.get((webElement.size() - 1));
        } else {
            return webElement.get(0);
        }

    }
    public Map<String, String> getTestCase() {
        return testCase;
    }

    public void setTestCase(Map<String, String> testCase) {
        this.testCase = testCase;
    }

    public Map<String, String> getTestData() {
        return testData;
    }

    public void setTestData(Map<String, String> testData) {
        this.testData = testData;
    }
    public WebElement getWebElement(String name,String formName,ConfigTestRunner configTestRunner) {
        return configTestRunner.getConfig().getWebElement(configTestRunner.driver, formName, name);
    }
    public List<WebElement>  getWebElements(ConfigTestRunner configTestRunner,String formName,String name) {
        List<WebElement> webElement= configTestRunner.getConfig().getWebElementes(configTestRunner.driver,  formName, name);
        return webElement;
    }
    public void sleep(int timer){
        try{
            Thread.sleep(timer);
        }catch (Exception e){

        }
    }
    public void waitAndClick(final WebElement element, int waitfor, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, waitfor);
        wait.until(new ExpectedCondition<WebElement>() {
            public ExpectedCondition<WebElement> visibilityOfElement = ExpectedConditions.visibilityOf(element);
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
    public void setCellData(String Data, int row , int col){

        DataFormatter formatter = new DataFormatter();
        String data1 = formatter.formatCellValue(sh.getRow(row).getCell(col));
        if (!data1.isEmpty())
            sh.getRow(row).getCell(col).setCellValue("");
        if (!(Data.isEmpty())){
            sh.getRow(row).getCell(col).setCellValue(Data);

            try{
                //FileOutputStream fileOut = new FileOutputStream(getClass().getResource("/TestData.xlsx").getFile());
                FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir")+Constants.TestDataPath+"/TestData.xlsx");
                wb.write(fileOut);
                fileOut.flush();
                fileOut.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
    public void setdataSendKeysEnter(WebElement element, String value) {
        element.sendKeys(value);
        try {
            sleep(300);
        }catch (Exception e){

        }
        //element.sendKeys(Keys.DOWN);
        element.sendKeys(Keys.ENTER);

    }
    public boolean isFileDownloaded(String downloadPath, String fileName) {
        boolean flag = false;
        File dir = new File(downloadPath);
        File[] dir_contents = dir.listFiles();

        for (int i = 0; i < dir_contents.length; i++) {
            if(fileName.contains(dir_contents[i].getName()))
                return flag=true;
            dir_contents[i].delete();
        }

        return flag;
    }

}
