package tests;

import base.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SynchronizationPageTest extends BaseTest {




    @Test
    public void verifyAlert(){
        //click alert nav button
        //assertion
        driver().getTitle();
    }

}
