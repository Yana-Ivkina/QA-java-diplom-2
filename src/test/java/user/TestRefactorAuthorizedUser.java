package user;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import order.TokenUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

import com.github.javafaker.Faker;

public class TestRefactorAuthorizedUser extends TokenUser {
    private static String tokenUser;
    private static User user;
    Faker faker = new Faker();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        user = UserGenerator.userRandom();
        tokenUser = getUserTokenRegistration(user);
    }

    @Test
    public void editEmailAuthorizedUser() {
        user.setEmail(faker.internet().emailAddress());
        Response response = sendPatchRequestUser(user);
        response.then().statusCode(200);
    }

    @Test
    public void editPasswordAuthorizedUser() {
        user.setPassword(faker.internet().password());
        Response response = sendPatchRequestUser(user);
        response.then().statusCode(200);
    }

    @Test
    public void editNameAuthorizedUser() {
        user.setName(faker.name().toString());
        Response response = sendPatchRequestUser(user);
        response.then().statusCode(200);
    }

    @Step("Send PATCH request to /api/auth/user")
    public Response sendPatchRequestUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(tokenUser)
                .and()
                .body(user)
                .patch("/api/auth/user");
    }

    @After
    public void deleteUserAfterTest() {
        given()
                .auth().oauth2(tokenUser)
                .delete("api/auth/user");
    }
}
