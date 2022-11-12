package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataGenerator;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement actionButton = $("[data-test-id='action-transfer']");
    private SelenideElement actionCancel = $("[data-test-id='action-cancel']");
    private SelenideElement notification = $("[data-test-id='error-notification']");
    private SelenideElement notificationContent = notification.$(".notification__content");

    public DashboardPage transferBetweenCards(DataGenerator.Card card, int amount) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(card.getCardNum());
        actionButton.click();
        return new DashboardPage();
    }

    public DashboardPage transferCancellation() {
        actionCancel.click();
        return new DashboardPage();
    }

    public void notificationVisibility() {
        actionButton.click();
        notificationContent.shouldBe(Condition.visible).shouldHave(text("Ошибка! Произошла ошибка"));
    }


}
