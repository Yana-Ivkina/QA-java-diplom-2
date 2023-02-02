package order;

import user.UserGenerator;
import user.UserWithTokens;
import user.User;

import static io.restassured.RestAssured.given;

public class TokenUser {
    protected static String getUserTokenAuthorization(User user) {
        UserWithTokens authorization = given()
                .header("Content-type", "application/json")
                .body(user)
                .post("/api/auth/login")
                .body().as(UserWithTokens.class);

        return authorization.getAccessToken().replace("Bearer ", "");
    }

    protected static String getUserTokenRegistration(User user) {
        UserWithTokens registration = given()
                .header("Content-type", "application/json")
                .body(user)
                .post("/api/auth/register")
                .body().as(UserWithTokens.class);
        return registration.getAccessToken().replace("Bearer ", "");
    }


}
