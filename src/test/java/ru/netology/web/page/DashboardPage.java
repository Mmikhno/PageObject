package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private String balanceStart = "баланс: ";
    private String balanceFinal = " р.";
    private SelenideElement reloadButton = $("[data-test-id='action-reload']");

    public int getCardBalance(String id) {
        val text = cards.find(attribute("data-test-id", id)).text();
        return extractBalance(text);
    }

    public int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinal);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage moneyTransferTo(DataGenerator.Card card) {
        String id = card.getId();
        SelenideElement nCard = cards.find(attribute("data-test-id", id));
        nCard.$(".button[data-test-id='action-deposit']").click();
        return new TransferPage();
    }

}









