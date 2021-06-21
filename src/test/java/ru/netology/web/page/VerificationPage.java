package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.SQLSetter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class VerificationPage {
    SelenideElement inputCodeError = $("[data-test-id=code] .input__sub");
    SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__content");
    SelenideElement codeField = $("[data-test-id=code] .input__control");
    SelenideElement verifyButton = $("[data-test-id=action-verify] .button__text");

    public DashboardPage verify(DataHelper.AuthInfo authInfo) {
        sleep(500);
        codeField.setValue(SQLSetter.getVerificationCode(authInfo));
        sleep(500);
        verifyButton.click();
        sleep(500);
        return new DashboardPage();
    }

    public void shouldBeVisibleErrorNotification() {
        errorNotification.shouldBe(Condition.visible);
    }
}
