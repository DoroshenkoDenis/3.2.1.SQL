package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

@Value
public class DataHelper {

    public DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo1() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getAuthInfo2() {
        return new AuthInfo("petya", "123qwerty");
    }

    public static AuthInfo getBadInfo(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return new AuthInfo(
                faker.name().username(),
                faker.internet().password()
        );
    }

    @Value
    public static class BadPassword {
        String badPassword;
    }

    public static BadPassword getBadPassword(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return new BadPassword(
                faker.internet().password()
        );
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static String getBadVerificationCode(String locale, int min, int max) {
        Faker faker = new Faker(new Locale(locale));
        return String.valueOf(faker.random().nextInt(min, max));
    }
}
