import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ForgotPasswordPage;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;
import service.Browser;
import service.RestAssuredTests;
import service.TestDataHandler;

import java.time.Duration;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pages.ForgotPasswordPage.FORGOT_PASSWORD_PAGE_URL;
import static pages.MainPage.MAIN_PAGE_URL;
import static pages.RegistrationPage.REGISTRATION_PAGE_URL;

public class LoginTests extends RestAssuredTests {

    private WebDriver driver;
    private MainPage mainPage;
    private RegistrationPage registrationPage;
    private LoginPage loginPage;
    private ForgotPasswordPage forgotPasswordPage;
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

        registrationPage = new RegistrationPage(driver);
        loginPage = new LoginPage(driver);
        mainPage = new MainPage(driver);
        forgotPasswordPage = new ForgotPasswordPage(driver);

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
    @DisplayName("Проверяю вход по кнопке «Войти в аккаунт» на главной странице")
    void checkLoginInMainPage() {
        driver.get(MAIN_PAGE_URL);
        mainPage.clickButtonLogin();
        loginUser(email, password);
        verifySuccessfulLoginUser();
    }

    @Test
    @DisplayName("Проверяю вход через кнопку «Личный кабинет»")
    void checkLoginFromButtonPersonalAccount() {
        driver.get(MAIN_PAGE_URL);
        mainPage.clickButtonPersonalAccount();
        loginUser(email, password);
        verifySuccessfulLoginUser();
    }

    @Test
    @DisplayName("Проверяю вход через кнопку в форме регистрации")
    void checkLoginInRegisterPage() {
        driver.get(REGISTRATION_PAGE_URL);
        registrationPage.clickButtonLogin();
        loginUser(email, password);
        verifySuccessfulLoginUser();
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    void checkLoginInForgotPasswordPage() {
        driver.get(FORGOT_PASSWORD_PAGE_URL);
        forgotPasswordPage.clickButtonLogin();
        loginUser(email, password);
        verifySuccessfulLoginUser();
    }

    @Step("Выполняю вход")
    public void loginUser(String email, String password) {
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickButtonLogin();
    }

    @Step("Проверяю успешный вход")
    public void verifySuccessfulLoginUser() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(mainPage.burgerConstructor));
        assertEquals(MAIN_PAGE_URL, driver.getCurrentUrl());
    }

}
