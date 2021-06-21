package ru.netology.web.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.SQLSetter;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.VerificationPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UITest {
    LoginPage loginPage = new LoginPage();
    VerificationPage verificationPage = new VerificationPage();

    DataHelper.AuthInfo authInfo1 = DataHelper.getAuthInfo("vasya", "qwerty123");
    DataHelper.AuthInfo authInfo2 = DataHelper.getAuthInfo("petya", "123qwerty");

    DataHelper.BadPassword badPassword = DataHelper.getBadPassword("en");
    String status = "blocked";
    String dashBoardHeaderText = "  Личный кабинет";


    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void clean() {
        SQLSetter.dropDataBase();
    }

    @Test
    void shouldLogin() {
        loginPage
                .validLogin(authInfo2)
                .verify(authInfo2)
                .shouldBeVisible(dashBoardHeaderText);
    }

    //    Этот тест должен упасть, оформлено issue - блокирует на 4-й раз корректный ввод SMS-кода, выдаёт уведомление "Ошибка! Превышено количество попыток ввода кода!"
    @Test
    void shouldNotBlocksMultipleLogin() {
        loginPage
                .validLogin(authInfo1)
                .verify(authInfo1);
        back();
        back();
        loginPage
                .validLogin(authInfo1)
                .verify(authInfo1);
        back();
        back();
        loginPage
                .validLogin(authInfo1)
                .verify(authInfo1);
        back();
        back();
        loginPage
                .validLogin(authInfo1)
                .verify(authInfo1)
                .shouldBeVisible(dashBoardHeaderText);
    }

    //    Этот тест должен упасть, оформлено issue - отсутствует блокировка при многократном вводе неверного пароля
    @Test
    void shouldBlocksUserIfPasswordWasWrongThreeTimes() {
        loginPage
                .badPasswordLogin(authInfo1, badPassword)
                .clearFields()
                .badPasswordLogin(authInfo1, badPassword)
                .clearFields()
                .badPasswordLogin(authInfo1, badPassword);
        assertEquals(SQLSetter.getUserStatus(authInfo1), status);
    }
}
