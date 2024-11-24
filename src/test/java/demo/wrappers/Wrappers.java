package demo.wrappers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    public static void click(WebElement elementToClick, ChromeDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(elementToClick));
    
       
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", elementToClick);
    
          
            try {
                elementToClick.click();
            } catch (Exception e) {
                jsExecutor.executeScript("arguments[0].click();", elementToClick);
            }
        } catch (Exception e) {
            System.err.println("Error clicking element: " + e.getMessage());
            throw e;
        }
    }
    

    public static void sendKeys(WebElement element, String textToEnter, ChromeDriver driver) {
        if (driver != null) {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOf(element));
            if (element.isDisplayed()) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
                element.clear();
                element.sendKeys(textToEnter);
            }
        }
    }
}
