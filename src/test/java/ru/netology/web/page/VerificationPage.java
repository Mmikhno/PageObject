package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataGenerator;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private SelenideElement code = $("[data-test-id='code'] input");
    private SelenideElement actionButton = $("[data-test-id='action-verify']");
    private SelenideElement notification = $("[data-test-id='error-notification'] .notification__content");

    public VerificationPage() {
    }

    public DashboardPage validVerify(DataGenerator.VerificationCode verificationCode) {
        code.setValue(verificationCode.getCode());
        actionButton.click();
        return new DashboardPage();
    }

    public void notificationVisibility() {
        notification.shouldBe(Condition.visible).shouldHave(text("Ошибка! Неверно указан код! Попробуйте ещё раз."));
    }
}
