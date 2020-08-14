package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void sleep(long milliseconds){
        try{
            Thread.sleep(milliseconds);
        }catch (InterruptedException e){
            e.printStackTrace();;
        }
    }

    public void waitForClickability(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForVisibility(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForVisibilityOfAll(List<WebElement> elementList){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfAllElements(elementList));
    }

    public void waitForPageToLoad(){
        ExpectedCondition<Boolean> pageLoadCondition = driver -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(pageLoadCondition);
    }

    public void waitForAlert(){
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.alertIsPresent());
    }

    public void click(WebElement element){
        waitForClickability(element);
        highlightElement(element);
        element.click();
    }

    public void sendKeys(WebElement element, String input){
        waitForVisibility(element);
        highlightElement(element);
        element.sendKeys(input);
    }

    public String getText(WebElement element){
        waitForVisibility(element);
        highlightElement(element);
        return element.getText();
    }

    public void moveIntoView(WebElement element){
        try{
            ((JavascriptExecutor)driver).executeScript("arguments[0].schrollIntoView(true);", element);
        }catch (Exception e){
            e.printStackTrace();
        }
        highlightElement(element);
    }

    public void highlightElement(WebElement element){
        JavascriptExecutor executor = (JavascriptExecutor)driver;

        for (int i = 0; i < 2; i++){
            try{
                if(i == 0){
                    executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "color: black; border:3px solid red; background: yellow");
                }else {
                    sleep(1000);
                    executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
