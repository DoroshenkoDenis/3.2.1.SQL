package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class LoginPage {
    SelenideElement inputLoginError = $("[data-test-id=login] .input__sub");
    SelenideElement inputPasswordError = $("[data-test-id=password] .input__sub");
    SelenideElement errorNotification = $("[data-test-id=error-notification] .notification__content");
    SelenideElement loginField = $("[data-test-id=login] .input__control");
    SelenideElement passwordField = $("[data-test-id=password] .input__control");
    SelenideElement loginButton = $("[data-test-id=action-login] .button__text");

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        sleep(500);
        loginField.setValue(authInfo.getLogin());
        sleep(500);
        passwordField.setValue(authInfo.getPassword());
        sleep(500);
        loginButton.click();
        sleep(500);
        return new VerificationPage();
    }

    public LoginPage badPasswordLogin(DataHelper.AuthInfo info, DataHelper.BadPassword badPassword) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(badPassword.getBadPassword());
        loginButton.click();
        return new LoginPage();
    }

    public LoginPage clearFields() {
        loginField.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        passwordField.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        return new LoginPage();
    }
}


