package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public static final String LOGIN_PAGE_URL = "https://qa-stellarburgers.education-services.ru/login";

    public By buttonLogin = By.xpath(".//button[text()='Войти']");
    private By emailInput = By.xpath(".//label[text()='Email']/following-sibling::input");
    private By passwordInput = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    public By buttonConstructor = By.xpath(".//p[text()='Конструктор']");
    public By buttonExit = By.xpath(".//button[text()='Выход']");


    public void setEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void setPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void clickButtonLogin() {
        driver.findElement(buttonLogin).click();
    }

    public void clickButtonConstructor() {
        driver.findElement(buttonConstructor).click();
    }

    public void clickButtonExit() {
        driver.findElement(buttonExit).click();
    }
}
