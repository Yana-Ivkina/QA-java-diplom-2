package user;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestCreateUser {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void createUniqueUser() {
        User user = UserGenerator.userRandom();
        Response response = sendPostRequestUser(user);
        response.then().statusCode(200);
    }

    @Test
    public void createExistingUser() {
        User user = UserGenerator.userDefaultWithName();
        Response response = sendPostRequestUser(user);
        response.then().assertThat().body("message", equalTo("User already exists"))
                .and()
                .statusCode(403);
    }

    @Test
    public void createUserWithoutFilledField() {
        User user = new User("email@email.com", "password", null);
        Response response = sendPostRequestUser(user);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }

    @Step("Send POST request to /api/auth/register")
    public Response sendPostRequestUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post("/api/auth/register");
    }
}
