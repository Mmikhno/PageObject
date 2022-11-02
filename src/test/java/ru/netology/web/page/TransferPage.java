package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataGenerator;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement actionButton = $("[data-test-id='action-transfer']");
    private SelenideElement actionCancel = $("[data-test-id='action-cancel']");
    private SelenideElement notification = $("[data-test-id='error-notification']");
    private SelenideElement notificationContent = notification.$(".notification__content");

    public DashboardPage TransferBetweenCards(DataGenerator.Card card, int amount) {
        amountField.setValue(String.valueOf(amount));
        fromField.setValue(card.getCardNum());
        actionButton.click();
        return new DashboardPage();
    }

    public DashboardPage TransferCancellation() {
        actionCancel.click();
        return new DashboardPage();
    }


    public SelenideElement getNotification() {
        return notification;
    }

    public SelenideElement getActionButton() {
        return actionButton;
    }

    public SelenideElement getNotificationContent() {
        return notificationContent;
    }
}
