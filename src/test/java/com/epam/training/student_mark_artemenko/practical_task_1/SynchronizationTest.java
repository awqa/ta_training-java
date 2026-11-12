package com.epam.training.student_mark_artemenko.practical_task_1;

import com.epam.training.student_mark_artemenko.util.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

import static com.epam.training.student_mark_artemenko.data.Constants.HELLO_WORLD_TEXT;
import static com.epam.training.student_mark_artemenko.data.Constants.HEROKUAPP_DYNAMIC_LOADING_URL;

public class SynchronizationTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser) {
        driver = DriverFactory.createDriver(browser);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
        if(driver != null){
            driver.quit();
        }
    }

    @Test
    public void helloWorldTextIsDisplayedAfterLoadingFinishes() {
        driver.get(HEROKUAPP_DYNAMIC_LOADING_URL);

        By startButton = By.cssSelector("#start > button");
        wait.until(ExpectedConditions.elementToBeClickable(startButton)).click();

        By loadingIndicator = By.xpath("//div[@id='loading' and contains(., 'Loading...')]");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIndicator));

        WebElement helloWorldText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#finish > h4")));
        Assert.assertEquals(helloWorldText.getText(),  HELLO_WORLD_TEXT, "Unexpected text after dynamic loading finished");
    }
}
