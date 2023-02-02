package user;

import com.github.javafaker.Faker;

public class UserGenerator {

    public static User userDefaultWithName() {
        return new User("test-data@yandex.ru", "password", "Username");
    }

    public static User userDefault() {
        return new User("test-data@yandex.ru", "password");
    }

    public static User userDefaultWithUncorrectedParameters() {
        return new User("test-data@yan.ru", "paswor");
    }

    public static User userRandom() {
        Faker faker = new Faker();
        return new User(faker.internet().emailAddress(), faker.internet().password(), faker.name().toString());
    }
}
