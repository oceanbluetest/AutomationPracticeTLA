package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    private final String propertyPath = "src/main/resources/config/configuration.properties";
    private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    public void setUp(){
        initializeDriver(ConfigReader.readProperty(propertyPath, "browser"));
        driver().get(ConfigReader.readProperty(propertyPath, "url"));
    }

    @AfterMethod
    public void tearDown(){
        driver().quit();
    }

    public WebDriver driver(){
        WebDriver driver = drivers.get();
        if (driver == null){
            throw new IllegalStateException("driver is null");
        }
        return driver;
    }

    private void initializeDriver(String browser){
        WebDriver driver = null;
        switch (browser){
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Invalid driver");
        }
        drivers.set(driver);
        driver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        //driver().manage().window().maximize();
    }
}
