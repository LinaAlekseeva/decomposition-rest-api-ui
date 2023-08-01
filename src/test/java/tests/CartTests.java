package tests;


import models.AddToCartResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static authorization.AuthApi.authCookieKey;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;

public class CartTests extends TestBase {
    String authCookieValue;
    int numberOfItems;
    int quantity = 3;
    String data = "product_attribute_72_5_18=52" +
            "&product_attribute_72_6_19=54" +
            "&product_attribute_72_3_20=58" +
            "&addToCart_72.EnteredQuantity=" + quantity;

    @Test
    void addToCartAsAuthorizedTest() {
        step("Get authorization cookie by api and set it to browser", () ->
                authCookieValue = authApi.getAuthCookie(login, password));

        step("Get the number of items in the cart", () -> {
            String page = given()
                    .cookie(authCookieKey, authCookieValue)
                    .when()
                    .get()
                    .then()
                    .statusCode(200)
                    .extract()
                    .asString();

            Document doc = Jsoup.parse(page);
            String count = doc.select(".cart-qty").text();
            numberOfItems = Integer.parseInt(count.replaceAll("[()]", ""));
        });

        AddToCartResponse response = step("Add items to cart", () -> given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie(authCookieKey, authCookieValue)
                .body(data)
                .when()
                .post("/addProductToCart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(AddToCartResponse.class));

        step("Check adding items to cart", () -> {
            assertThat(response.getSuccess()).isEqualTo("true");
            assertThat(response.getMessage()).isEqualTo("The product has been added to your <a href=\"/cart\">shopping cart</a>");
            assertThat(response.getUpdatetopcartsectionhtml()).isEqualTo("(" + (numberOfItems + quantity) + ")");
        });
    }

    @Test
    void addToCartAsAnonymTest() {

        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .body(data)
                .when()
                .post("/addProductToCart/details/72/1")
                .then()
                .log().all()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .body("updatetopcartsectionhtml", is("(3)"));
    }
}
