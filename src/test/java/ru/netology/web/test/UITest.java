package ru.netology.web.test;

import lombok.val;
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
        new LoginPage()
                .validLogin(DataHelper.getAuthInfo2())
                .verify(SQLSetter.getVerificationCode(DataHelper.getAuthInfo2()))
                .shouldBeVisible(dashBoardHeaderText);
    }

    //    Этот тест должен упасть, оформлено issue - блокирует на 4-й раз корректный ввод SMS-кода, выдаёт уведомление "Ошибка! Превышено количество попыток ввода кода!"
    @Test
    void shouldNotBlocksMultipleLogin() {
        new LoginPage()
                .validLogin(DataHelper.getAuthInfo1())
                .verify(SQLSetter.getVerificationCode(DataHelper.getAuthInfo1()));
        back();
        back();
        new LoginPage()
                .validLogin(DataHelper.getAuthInfo1())
                .verify(SQLSetter.getVerificationCode(DataHelper.getAuthInfo1()));
        back();
        back();
        new LoginPage()
                .validLogin(DataHelper.getAuthInfo1())
                .verify(SQLSetter.getVerificationCode(DataHelper.getAuthInfo1()));
        back();
        back();
        new LoginPage()
                .validLogin(DataHelper.getAuthInfo1())
                .verify(SQLSetter.getVerificationCode(DataHelper.getAuthInfo1()))
                .shouldBeVisible(dashBoardHeaderText);
    }

    //    Этот тест должен упасть, оформлено issue - отсутствует блокировка при многократном вводе неверного пароля
    @Test
    void shouldBlocksUserIfPasswordWasWrongThreeTimes() {
        new LoginPage()
                .badPasswordLogin(DataHelper.getAuthInfo1(), badPassword)
                .clearFields()
                .badPasswordLogin(DataHelper.getAuthInfo1(), badPassword)
                .clearFields()
                .badPasswordLogin(DataHelper.getAuthInfo1(), badPassword)
                .shouldBeVisiblePasswordErrorNotification();
        assertEquals(SQLSetter.getUserStatus(DataHelper.getAuthInfo1()), status);
    }
}
