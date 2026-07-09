package service;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class RestAssuredTests {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://qa-stellarburgers.education-services.ru/";
    }
}
