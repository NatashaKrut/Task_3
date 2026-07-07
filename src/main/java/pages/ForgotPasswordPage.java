package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage {

    private WebDriver driver;

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    public static final String FORGOT_PASSWORD_PAGE_URL = "https://qa-stellarburgers.education-services.ru/forgot-password";

    public By buttonLogin = By.xpath(".//a[text()='Войти']");

    public void clickButtonLogin() {
        driver.findElement(buttonLogin).click();
    }

}
