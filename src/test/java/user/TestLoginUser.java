package user;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestLoginUser {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void loginWithCorrectParameters() {
        User user = UserGenerator.userDefault();
        Response response = sendPostRequestUser(user);
        response.then().statusCode(200);
    }

    @Test
    public void loginWithUncorrectedParameters() {
        User user = UserGenerator.userDefaultWithUncorrectedParameters();
        Response response = sendPostRequestUser(user);
        response.then().assertThat().body("message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(401);
    }

    @Step("Send POST request to /api/auth/login")
    public Response sendPostRequestUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/auth/login");
    }
}
