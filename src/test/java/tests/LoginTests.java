package tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static authorization.AuthApi.authCookieKey;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import static io.qameta.allure.Allure.step;

public class LoginTests extends TestBase {

    @Test
    void loginWithUITest() {
        step("Open login page", () ->
                open("/login"));

        step("Fill login form", () -> {
            $("#Email").setValue(login);
            $("#Password").setValue(password).pressEnter();
        });

        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(login)));
    }

    @Test
    void loginWithApiTest() {
        step("Get authorization cookie by api and set it to browser", () -> {
            String authCookieValue = authApi.getAuthCookie(login, password);

            open("/Themes/DefaultClean/Content/images/bullet-right.gif");
            getWebDriver().manage().addCookie(new Cookie(authCookieKey, authCookieValue));
        });

        step("Open main page", () ->
                open(""));

        step("Verify successful authorization", () ->
                $(".account").shouldHave(text(login)));
    }
}
