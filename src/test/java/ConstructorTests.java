import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.MainPage;
import service.Browser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static pages.MainPage.MAIN_PAGE_URL;

public class ConstructorTests {

    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    public void prepareTestData() {

        try {
            driver = new Browser().getWebDriver(System.getProperty("browser"));
        } catch (NullPointerException e) {
            //Для запуска через Junit
            driver = new ChromeDriver();
        }

        mainPage = new MainPage(driver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Проверяю переход к разделу «Булки»")
    void checkNavigationToBun() throws InterruptedException {
        clickButtonBun();
        verifyNavigationToBun();
    }

    @Step("Нажимаю на кнопку «Булки»")
    public void clickButtonBun() throws InterruptedException {
        driver.get(MAIN_PAGE_URL);
        mainPage.clickButtonSauce();
        mainPage.clickButtonBun();
    }

    @Step("Проверяю, что выполнен успешный переход к разделу «Булки»")
    public void verifyNavigationToBun() {
        assertTrue(mainPage.isBunButtonActive());
    }

    @Test
    @DisplayName("Проверяю переход к разделу «Соусы»")
    void checkNavigationToSouse() {
        clickButtonSouse();
        verifyNavigationToSouse();
    }

    @Step("Нажимаю на кнопку «Соусы»")
    public void clickButtonSouse() {
        driver.get(MAIN_PAGE_URL);
        mainPage.clickButtonSauce();
    }

    @Step("Проверяю, что выполнен успешный переход к разделу «Соусы»")
    public void verifyNavigationToSouse() {
        assertTrue(mainPage.isSauceButtonActive());
    }

    @Test
    @DisplayName("Проверяю переход к разделу «Начинки»")
    void checkNavigationToIngredients() {
        clickButtonIngredients();
        verifyNavigationToIngredients();
    }

    @Step("Нажимаю на кнопку «Начинки»")
    public void clickButtonIngredients() {
        driver.get(MAIN_PAGE_URL);
        mainPage.clickButtonIngredients();
    }

    @Step("Проверяю, что выполнен успешный переход к разделу «Начинки»")
    public void verifyNavigationToIngredients() {
        assertTrue(mainPage.isIngredientsButtonActive());
    }
}
