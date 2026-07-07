package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {

    private WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public static final String REGISTRATION_PAGE_URL = "https://qa-stellarburgers.education-services.ru/register";

    private By nameInput = By.xpath(".//label[text()='Имя']/following-sibling::input");
    private By emailInput = By.xpath(".//label[text()='Email']/following-sibling::input");
    private By passwordInput = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    private By buttonRegister = By.xpath(".//button[text()='Зарегистрироваться']");
    public By incorrectPassword = By.xpath(".//p[text()='Некорректный пароль']");

    public By buttonLogin = By.xpath(".//a[text()='Войти']");

    public void setName(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }

    public void setEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void setPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickButtonRegister() {
        driver.findElement(buttonRegister).click();
    }

    public boolean passwordErrorIsVisible() {
        return driver.findElement(incorrectPassword).isDisplayed();
    }

    public void clickButtonLogin() {
        driver.findElement(buttonLogin).click();
    }

}
