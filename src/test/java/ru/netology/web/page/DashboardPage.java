package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id=dashboard]");

    public void shouldBeVisible(String text) {
        heading.shouldBe(Condition.visible, Condition.text(text));
    }

}