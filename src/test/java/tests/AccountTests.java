package tests;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static authorization.AuthApi.authCookieKey;

import static io.restassured.RestAssured.given;

public class AccountTests extends TestBase {

    @Test
    void editAddressTest() {
        Map<String, String> data = new HashMap<>();
        data.put("Address.Id", "3108172");
        data.put("Address.FirstName", "Name");
        data.put("Address.LastName", "Lastname");
        data.put("Address.Email", "myemail@gmail.com");
        data.put("Address.Company", "company");
        data.put("Address.CountryId", "2");
        data.put("Address.StateProvinceId", "63");
        data.put("Address.City", "city");
        data.put("Address.Address1", "address1");
        data.put("Address.Address2", "address2");
        data.put("Address.ZipPostalCode", "123456");
        data.put("Address.PhoneNumber", "098987876");
        data.put("Address.FaxNumber", "112233");

        String authCookieValue = authApi.getAuthCookie(login, password);

        given()
                .contentType("application/x-www-form-urlencoded")
                .cookie(authCookieKey, authCookieValue)
                .formParams(data)
                .when()
                .post("/customer/addressedit/3108172")
                .then()
                .log().all()
                .statusCode(302);
    }

    @Test
    void addNewAddressTest() {
        Map<String, String> data = new HashMap<>();
        data.put("Address.Id", "0");
        data.put("Address.FirstName", "Name");
        data.put("Address.LastName", "Lastname");
        data.put("Address.Email", "myemail@gmail.com");
        data.put("Address.Company", "company");
        data.put("Address.CountryId", "61");
        data.put("Address.StateProvinceId", "0");
        data.put("Address.City", "city");
        data.put("Address.Address1", "address1");
        data.put("Address.Address2", "address2");
        data.put("Address.ZipPostalCode", "123456");
        data.put("Address.PhoneNumber", "098987876");
        data.put("Address.FaxNumber", "112233");

        String authCookieValue = authApi.getAuthCookie(login, password);

        given()
                .contentType("application/x-www-form-urlencoded")
                .cookie(authCookieKey, authCookieValue)
                .formParams(data)
                .when()
                .post("/customer/addressadd")
                .then()
                .log().all()
                .statusCode(302);
    }
}