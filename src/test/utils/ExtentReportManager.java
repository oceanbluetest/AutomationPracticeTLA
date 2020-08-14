package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;

import java.io.File;

public class ExtentReportManager {

    public static ExtentReports loadConfig(){
        ExtentReports extentReports = new ExtentReports("target//reports/extentReports.html", true);
        extentReports.loadConfig(new File("src/main/resources/reports/extent-config.xml"));
        return extentReports;
    }

    public static void logExtent(ExtentReports extentReports, ExtentTest extentTest, ITestResult iTestResult){
        if(iTestResult.getStatus() == ITestResult.FAILURE){
            extentTest.log(LogStatus.FAIL, iTestResult.getName(), "Test was unsuccessful");
        }else if (iTestResult.getStatus() == ITestResult.SUCCESS){
            extentTest.log(LogStatus.PASS, iTestResult.getName(), "Test was successful");
        }else {
            extentTest.log(LogStatus.SKIP, iTestResult.getName(), "Test was skipped");
            extentTest.log(LogStatus.SKIP, iTestResult.getThrowable());
        }

        extentReports.endTest(extentTest);
    }

}
