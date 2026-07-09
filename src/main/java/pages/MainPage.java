package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

public class MainPage {

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public static final String MAIN_PAGE_URL = "https://qa-stellarburgers.education-services.ru/";

    public By buttonLogin = By.xpath(".//button[text()='Войти в аккаунт']");
    public By buttonPersonalAccount = By.xpath(".//p[text()='Личный Кабинет']");
    public By burgerConstructor = By.xpath(".//section[contains(@class,'BurgerIngredients')]");
    public By buttonBun = By.xpath(".//div[child::span[text()='Булки']]");
    public By buttonSauce = By.xpath(".//div[child::span[text()='Соусы']]");
    public By buttonIngredients = By.xpath(".//div[child::span[text()='Начинки']]");

    public void clickButtonLogin() {
        driver.findElement(buttonLogin).click();
    }

    public void clickButtonPersonalAccount() {
        driver.findElement(buttonPersonalAccount).click();
    }

    public void clickButtonBun() {
        driver.findElement(buttonBun).click();
    }

    public void clickButtonSauce() {
        driver.findElement(buttonSauce).click();
    }

    public void clickButtonIngredients() {
        driver.findElement(buttonIngredients).click();
    }

    public boolean isBunButtonActive() {
        return Objects.requireNonNull(driver
                        .findElement(buttonBun)
                        .getDomAttribute("class"))
                .contains("tab_tab_type_current");
    }

    public boolean isSauceButtonActive() {
        return Objects.requireNonNull(driver
                        .findElement(buttonSauce)
                        .getDomAttribute("class"))
                .contains("tab_tab_type_current");
    }

    public boolean isIngredientsButtonActive() {
        return Objects.requireNonNull(driver
                        .findElement(buttonIngredients)
                        .getDomAttribute("class"))
                .contains("tab_tab_type_current");
    }
}
