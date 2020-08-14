package utils;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class Screenshot {
    WebDriver driver;
    ExtentTest test;

    public Screenshot(WebDriver driver, ExtentTest test){
        this.driver = driver;
        this.test = test;
    }

    public String takeScreenshotAndLog() {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(scrFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String Base64StringofScreenshot = "data:image/png;base64,"+ Base64.getEncoder().encodeToString(fileContent);
        test.log(LogStatus.INFO, "Captured screenshot: " + test.addBase64ScreenShot(Base64StringofScreenshot));
        return Base64StringofScreenshot;
    }
}
