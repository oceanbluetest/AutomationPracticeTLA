package tests;

import base.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SynchronizationPage;
import utils.RetryAnalyzer;

import java.lang.reflect.Method;

public class SynchronizationPageTest extends BaseTest {
    HomePage homePage;
    SynchronizationPage synchronizationPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, ITestResult result){
        super.setUp(method, result);
        homePage = new HomePage(driver());
        synchronizationPage = new SynchronizationPage(driver());
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void verifyAlert(){
        homePage.click(homePage.navBtn_synchronization);
        synchronizationPage.click(synchronizationPage.alertBtn);
        synchronizationPage.waitForAlert();
        Alert alert = driver().switchTo().alert();
        Assert.assertTrue(alert.getText().contains("This alert was displayed after"));
    }

}
