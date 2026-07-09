import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.RegistrationPage;
import service.Browser;
import service.RestAssuredTests;
import service.TestDataHandler;

import java.time.Duration;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pages.LoginPage.LOGIN_PAGE_URL;
import static pages.RegistrationPage.REGISTRATION_PAGE_URL;

public class RegistrationTests extends RestAssuredTests {

    private WebDriver driver;
    private RegistrationPage registrationPage;
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
        driver.get(REGISTRATION_PAGE_URL);
        registrationPage = new RegistrationPage(driver);
        loginPage = new LoginPage(driver);

        name = faker.name().firstName();
        email = faker.internet().emailAddress();
        password = faker.internet().password();

    }

    @AfterEach
    public void tearDown() {
        userToken = tdh.authorizeTestUser(email, password);
        tdh.deleteUser(userToken);
        driver.quit();
    }

    @Test
    @DisplayName("Проверяю успешную регистрацию пользователя")
    void checkUserCanBeRegistered() {
        registerUser(name, email, password);
        verifyUserRegistered();
    }

    @Test
    @DisplayName("Проверяю ошибку для некорректного пароля (минимальный пароль — шесть символов).")
    void checkErrorForIncorrectPassword() {
        registerUser(name, email, faker.number().digits(5));
        verifyError();
    }

    @Step("Выполняю регисторацию пользователя")
    public void registerUser(String name, String email, String password) {
        registrationPage.setName(name);
        registrationPage.setEmail(email);
        registrationPage.setPassword(password);
        registrationPage.clickButtonRegister();
    }

    @Step("Проверяю, что  пользователь успешно зарегистрирован")
    public void verifyUserRegistered() {
        new WebDriverWait(driver, Duration.ofSeconds(8))
                .until(ExpectedConditions.visibilityOfElementLocated(loginPage.buttonLogin));
        assertEquals(LOGIN_PAGE_URL, driver.getCurrentUrl());
    }

    @Step("Проверяю, что  попоявилась ошибка для некорректного пароля")
    public void verifyError() {
        assertTrue(registrationPage.passwordErrorIsVisible());
    }

}
