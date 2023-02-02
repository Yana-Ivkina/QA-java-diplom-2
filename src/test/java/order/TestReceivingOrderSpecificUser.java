package order;

import user.UserGenerator;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class TestReceivingOrderSpecificUser extends TokenUser {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void getListOrdersAuthorizedUser() {
        Response response = sendGetRequestOrder(getUserTokenAuthorization(UserGenerator.userDefault()));
        response.then().statusCode(200);
    }

    @Test
    public void getListOrdersUnauthorizedUser() {
        Response response = sendGetRequestOrder("");
        response.then().statusCode(401);
    }

    @Step("Send GET request to /api/auth/user")
    public Response sendGetRequestOrder(String tokenUser) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(tokenUser)
                .and()
                .get("/api/orders");
    }
}
