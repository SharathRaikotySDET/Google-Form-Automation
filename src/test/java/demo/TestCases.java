package demo;

import demo.wrappers.Wrappers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

public class TestCases {
    ChromeDriver driver;

    @Test
    public void testCase01() throws InterruptedException {
        driver.get("https://forms.gle/wjPkzeSEk1CM7KgGA");
        String title = driver.getTitle();
        System.out.println(title);
        long epoch = System.currentTimeMillis() / 1000;
        System.out.println(epoch);

        WebElement name = driver.findElement(By.xpath("(//input[@type='text'])[1]"));
        Wrappers.sendKeys(name, "Crio Learner", driver);

        WebElement answer = driver.findElement(By.xpath("//textarea[@aria-label='Your answer']"));
        Wrappers.sendKeys(answer, "I want to be the best QA Engineer!" + epoch, driver);

        WebElement option2 = driver.findElement(By.xpath("(//div[@class='AB7Lab Id5V1'])[2]"));
        Wrappers.click(option2, driver);

        Wrappers.click(driver.findElement(By.xpath("(//div[@class='uHMk6b fsHoPb'])[1]")), driver);
        Wrappers.click(driver.findElement(By.xpath("(//div[@class='uHMk6b fsHoPb'])[2]")), driver);
        Wrappers.click(driver.findElement(By.xpath("(//div[@class='uHMk6b fsHoPb'])[4]")), driver);
        WebElement dropdownTrigger = driver
                .findElement(By.xpath("//div[@class='MocG8c HZ3kWc mhLiyf LMgvRb KKjvXb DEh1R']"));
        Wrappers.click(dropdownTrigger, driver);

        Thread.sleep(1000);

        WebElement mrOption = driver.findElement(By.xpath("//div[@role='option' and .//span[text()='Mr']]"));
       //javascript code click on dropdown element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", mrOption);

        js.executeScript("arguments[0].click();", mrOption);
        //to get current date and format it
        LocalDate curenDate = LocalDate.now();
        LocalDate requiredDate = curenDate.minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = requiredDate.format(formatter);

        WebElement dateInput = driver.findElement(By.xpath("//input[@type='date']"));
        Wrappers.sendKeys(dateInput, formattedDate, driver);

        Wrappers.sendKeys(driver.findElement(By.xpath("//input[@aria-label='Hour']")), "07", driver);
        Wrappers.sendKeys(driver.findElement(By.xpath("//input[@aria-label='Minute']")), "30", driver);
       //click on submit button
        WebElement submitButton = driver.findElement(By.xpath("//span[text()='Submit']"));
        Wrappers.click(submitButton, driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successMessage=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Thanks for your response, Automation Wizard!']")));
       //print success message
        System.out.println("Success Message is" +successMessage.getText());
    }

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();
    }
}
