
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SauceDemoTest {

    WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        if (driver != null) {
            try {
                Path folder = Paths.get("screenshots");
                Files.createDirectories(folder);

                String testName = testInfo.getTestMethod()
                        .map(method -> method.getName())
                        .orElse("unknown-test");

                File screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.FILE);

                Path destination = folder.resolve(testName + ".png");

                Files.copy(
                        screenshot.toPath(),
                        destination,
                        StandardCopyOption.REPLACE_EXISTING
                );

                System.out.println(
                        "Screenshot saved: " + destination.toAbsolutePath()
                );

            } catch (IOException | RuntimeException e) {
                System.out.println(
                        "Screenshot could not be saved: " + e.getMessage()
                );
            } finally {
                driver.quit();
            }
        }
    }

    void login(String username, String password) {
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }

    String getErrorMessage() {
        WebElement error = driver.findElement(
                By.cssSelector("[data-test='error']")
        );
        return error.getText();
    }

    @Test
    void validLogin() {
        login("standard_user", "secret_sauce");

        assertTrue(driver.getCurrentUrl().contains("inventory.html"));
        assertEquals(
                "Products",
                driver.findElement(By.className("title")).getText()
        );
    }

    @Test
    void incorrectPassword() {
        login("standard_user", "wrong_password");

        assertTrue(
                getErrorMessage().contains(
                        "Username and password do not match"
                )
        );
    }

    @Test
    void emptyCredentials() {
        driver.findElement(By.id("login-button")).click();

        assertTrue(
                getErrorMessage().contains("Username is required")
        );
    }

    @Test
    void lockedOutUser() {
        login("locked_out_user", "secret_sauce");

        assertTrue(
                getErrorMessage().contains(
                        "Sorry, this user has been locked out"
                )
        );
    }

    @Test
    void missingPassword() {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("login-button")).click();

        assertTrue(
                getErrorMessage().contains("Password is required")
        );
    }
}
