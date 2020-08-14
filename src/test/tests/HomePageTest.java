package tests;

import base.BaseTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;

import java.lang.reflect.Method;
import java.util.Set;

public class HomePageTest extends BaseTest {
    HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void setUp(ITestResult result, Method method){
        super.setUp(method, result);
        homePage = new HomePage(driver());
    }



    @Test(description = "Testing header of the page")
    public void verifyMainHeaderText(){
        extentTest.log(LogStatus.INFO, "Captured title of the page: " + homePage.mainHeader.getText());
        Assert.assertEquals(homePage.mainHeader.getText(), "Automation with Selenium");
        screenshot.takeScreenshotAndLog();
    }

    @Test(testName = "Verify Title", description = "This test validates title of Home page", groups = "smoke")
    public void verifyTitle(){
        Assert.assertEquals(driver().getTitle(), "TLA Automation");
    }

    @Test
    public void verifyHeader(){
        String headerActual = driver().findElement(By.id("title")).getText();
        String expected = "Automation with Selenium";
        Assert.assertEquals(headerActual, expected);
    }


    @Test(dataProvider = "dataSet", enabled = false)
    public void verifyLinks(String linkText, String pageTitle){
        String mainWindowID = driver().getWindowHandle();
        String actualTitle = "";

        WebElement element = driver().findElement(By.linkText(linkText));
        element.click();

        Set<String> windows = driver().getWindowHandles();
        for (String id: windows){
            if (!id.equals(mainWindowID))
                driver().switchTo().window(id);
                actualTitle = driver().getTitle();
        }

        Assert.assertEquals(actualTitle, pageTitle);
    }



    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver().quit();
    }

    /**
     * This method is used as a data set
     * @author Victoria
     * @return it will return two dimensional array of Strings
     */
    @DataProvider(name = "dataSet")
    public Object[][] dataSet(){
        Object[][] data = new Object[7][2];
        data[0][0] = "PHP Travels";
        data[0][1] = "Demo Script Test drive - PHPTRAVELS";
        data[1][0] = "Mercury tours";
        data[1][1] = "Unknown";
        data[2][0] = "Internet";
        data[2][1] = "The Internet";
        data[3][0] = "E-commerce";
        data[3][1] = "My Store";
        data[4][0] = "Passion Tea Company";
        data[4][1] = "Welcome";
        data[5][0] = "Saucedemo";
        data[5][1] = "Swag Labs";
        data[6][0] = "Shopping Cart";
        data[6][1] = "React Shopping Cart";

        return data;
    }


}
