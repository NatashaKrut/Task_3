package service;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Browser {
    public WebDriver getWebDriver(String browserName) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless");
        switch (browserName) {
            case "chrome":
                return new ChromeDriver(options);
            case "yandex":
                System.setProperty("webdriver.chrome.driver", "drivers/yandexdriver.exe");
                return new ChromeDriver(options);
            default: {
                Assertions.fail("Указанный браузер не распознан");
                return null;
            }
        }
    }
}
