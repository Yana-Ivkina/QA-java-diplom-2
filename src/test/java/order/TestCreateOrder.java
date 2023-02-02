package order;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import user.UserGenerator;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class TestCreateOrder extends TokenUser {
    private List<String> ingredients = new ArrayList<>();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

        ingredients.add("61c0c5a71d1f82001bdaaa73");
        ingredients.add("61c0c5a71d1f82001bdaaa6c");
    }

    @Test
    public void createOrderAuthorizationAndWithIngredients() {
        Order order = new Order(ingredients);
        Response response = sendPostRequestOrder(order, getUserTokenAuthorization(UserGenerator.userDefault()));
        response.then().statusCode(200);
    }

    @Test
    public void createOrderNotAuthorization() {
        Order order = new Order(ingredients);
        Response response = sendPostRequestOrder(order, "");
        response.then().statusCode(200);
    }

    @Test
    public void createOrderWithoutIngredients() {
        ingredients.clear();
        Order order = new Order(ingredients);
        Response response = sendPostRequestOrder(order, "");
        response.then().statusCode(400);
    }

    @Test
    public void createOrderWithWrongHash() {
        ingredients.add("Wrong hash");
        Order order = new Order(ingredients);
        Response response = sendPostRequestOrder(order, "");
        response.then().statusCode(500);
    }

    @Step("Send POST request to /api/auth/user")
    public Response sendPostRequestOrder(Order order, String tokenUser) {
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(tokenUser)
                .and()
                .body(order)
                .post("/api/orders");
    }
}
