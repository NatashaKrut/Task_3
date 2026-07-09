import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.config.RestAssuredConfig;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import service.Browser;
import service.TestDataHandler;

import java.time.Duration;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pages.LoginPage.LOGIN_PAGE_URL;
import static pages.MainPage.MAIN_PAGE_URL;

public class PersonalAccountTests extends RestAssuredConfig {

    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private String name;
    private String email;
    private String password;
    private static String userToken;

    Faker faker = new Faker(new Locale("ru"));
    static TestDataHandler tdh = new TestDataHandler();

    @BeforeEach
    public void prepareTestData() {

        try {
            driver = new Browser().getWebDriver(System.getProperty("browser"));
        } catch (NullPointerException e) {
            //Для запуска через Junit
            driver = new ChromeDriver();
        }

        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);

        name = faker.name().firstName();
        email = faker.internet().emailAddress();
        password = faker.internet().password();

        tdh.createTestUser(email, password, name);

    }

    @AfterEach
    public void tearDown() {
        userToken = tdh.authorizeTestUser(email, password);
        tdh.deleteUser(userToken);
        driver.quit();
    }

    @Test
    @DisplayName("Проверяю переход по клику на «Личный кабинет»")
    void checkNavigationToPersonalAccount() {
        clickButtonPersonalAccount();
        verifySuccessfulNavigationToPersonalAccount();
    }

    @Step("Нажимаю кнопку «Личный кабинет» на главной странице")
    public void clickButtonPersonalAccount() {
        driver.get(MAIN_PAGE_URL);
        mainPage.clickButtonPersonalAccount();
    }

    @Step("Проверяю успешный переход в личный кабинет")
    public void verifySuccessfulNavigationToPersonalAccount() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(loginPage.buttonLogin));
        assertEquals(LOGIN_PAGE_URL, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Проверяю переход из личного кабинета по клику на «Конструктор»")
    void checkNavigationFromPersonalAccountToConstructor() {
        clickButtonConstructor();
        verifySuccessfulNavigationToMainPage();
    }

    @Step("Нажимаю кнопку «Конструктор» в личном кабинете")
    public void clickButtonConstructor() {
        driver.get(LOGIN_PAGE_URL);
        loginPage.clickButtonConstructor();
    }

    @Step("Проверяю успешный переход на главную страницу")
    public void verifySuccessfulNavigationToMainPage() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(mainPage.burgerConstructor));
        assertEquals(MAIN_PAGE_URL, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Проверяю выход по кнопке «Выйти» в личном кабинете")
    void checkLogout() {
        loginUser(email, password);
        goToPersonalAccount();
        clickLogout();
        verifyLogout();
    }

    @Step("Выполняю вход")
    public void loginUser(String email, String password) {
        driver.get(LOGIN_PAGE_URL);
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickButtonLogin();
    }

    @Step("Перехожу в личный кабинет")
    public void goToPersonalAccount() {
        mainPage.clickButtonPersonalAccount();
    }

    @Step("Нажимаю на кнопку «Выход»")
    public void clickLogout() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(loginPage.buttonExit));
        loginPage.clickButtonExit();
    }

    @Step("Проверяю выход из аккаунта")
    public void verifyLogout() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(loginPage.buttonLogin));
        assertEquals(LOGIN_PAGE_URL, driver.getCurrentUrl());
    }

}
