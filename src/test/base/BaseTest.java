package base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ConfigReader;
import utils.ExtentReportManager;
import utils.Screenshot;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    private final String propertyPath = "src/main/resources/config/configuration.properties";
    private static final ThreadLocal<WebDriver> drivers = new ThreadLocal<>();

    ExtentReports extentReports;
    protected ExtentTest extentTest;

    protected Screenshot screenshot;

    String suiteName;

    @BeforeSuite(alwaysRun = true)
    public void startReport(ITestContext iTestContext){
        extentReports = ExtentReportManager.loadConfig();
        suiteName = iTestContext.getCurrentXmlTest().getSuite().getName();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, ITestResult result){
        initializeDriver(ConfigReader.readProperty(propertyPath, "browser"));

        extentTest = extentReports.startTest((this.getClass().getSimpleName() + " : " + method.getName()), method.getName());
        extentTest.assignCategory(suiteName);
        extentTest.assignAuthor("TLA Instructor");
        extentTest.log(LogStatus.INFO, result.getMethod().getDescription());

        screenshot = new Screenshot(driver(), extentTest);

        driver().get(ConfigReader.readProperty(propertyPath, "url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result){
        ExtentReportManager.logExtent(extentReports, extentTest, result);
        driver().quit();
    }

    @AfterSuite
    public void closeReports(){
        extentReports.flush();
        extentReports.close();
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
