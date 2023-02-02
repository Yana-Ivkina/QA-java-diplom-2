package user;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import com.github.javafaker.Faker;

import static io.restassured.RestAssured.given;

public class TestRefactorUnauthorizedUser {

    Faker faker = new Faker();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void editParameterUnauthorizedUser() {
        User user = UserGenerator.userDefaultWithName();
        user.setEmail(faker.internet().emailAddress());
        Response response = sendPatchRequestUser(user);
        response.then().statusCode(401);
    }

    @Step("Send PATCH request to /api/auth/user")
    public Response sendPatchRequestUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .patch("/api/auth/user");
    }
}
