package ru.netology.web.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataGenerator;
import ru.netology.web.page.TransferPage;
import ru.netology.web.page.LoginPage;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class MoneyTransferTest {

    @Test
    void shouldTestTransferBetweenTwoOwnCards() {
        var authInfo = DataGenerator.getAuthInfo();
        var verificationCode = DataGenerator.getVerificationCodeFor(authInfo);
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var verificationPage = loginPage.validLogin(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        //сохранить начальные балансы на картах
        int startBalFirst = dashboardPage.getCardBalance(DataGenerator.getFirstCard().getId());
        int startBalSecond = dashboardPage.getCardBalance(DataGenerator.getSecondCard().getId());
        //выбрать карту для пополнения (1 или 2)
        TransferPage transferPage = dashboardPage.moneyTransferTo(DataGenerator.getSecondCard());
        int amount = startBalFirst / 2;
        //перевести на вторую карту с первой
        transferPage.transferBetweenCards(DataGenerator.getFirstCard(), amount);
        int finalBalanceFirst = dashboardPage.getCardBalance(DataGenerator.getFirstCard().getId());
        int finalBalanceSecond = dashboardPage.getCardBalance(DataGenerator.getSecondCard().getId());
        Assertions.assertEquals(startBalFirst - amount, finalBalanceFirst);
        Assertions.assertEquals(startBalSecond + amount, finalBalanceSecond);
    }

    @Test
    void shouldTestLoginWithWrongPassword() {
        var authInfo = DataGenerator.getWrongAuthInfo();
        var loginPage = open("http://localhost:9999", LoginPage.class);
        loginPage.validLogin(authInfo);
        loginPage.notificationVisibility();
    }

    @Test
    public void shouldTestCancellationOfTransfer() {
        var authInfo = DataGenerator.getAuthInfo();
        var verificationCode = DataGenerator.getVerificationCodeFor(authInfo);
        var dashboardPage = open("http://localhost:9999", LoginPage.class).validLogin(authInfo).validVerify(verificationCode);
        //сохранить начальные балансы на картах
        int startBalFirst = dashboardPage.getCardBalance(DataGenerator.getFirstCard().getId());
        int startBalSecond = dashboardPage.getCardBalance(DataGenerator.getSecondCard().getId());
        TransferPage transferPage = dashboardPage.moneyTransferTo(DataGenerator.getSecondCard());
        transferPage.transferCancellation();
        Assertions.assertEquals(startBalFirst, dashboardPage.getCardBalance(DataGenerator.getFirstCard().getId()));
        Assertions.assertEquals(startBalSecond, dashboardPage.getCardBalance(DataGenerator.getSecondCard().getId()));
    }

    @Test
    public void shouldTestTransferZeroAmount() {
        var authInfo = DataGenerator.getAuthInfo();
        var verificationCode = DataGenerator.getVerificationCodeFor(authInfo);
        var dashboardPage = open("http://localhost:9999", LoginPage.class).validLogin(authInfo).validVerify(verificationCode);
        int startBalFirst = dashboardPage.getCardBalance(DataGenerator.getFirstCard().getId());
        int startBalSecond = dashboardPage.getCardBalance(DataGenerator.getSecondCard().getId());
        TransferPage transferPage = dashboardPage.moneyTransferTo(DataGenerator.getFirstCard());
        transferPage.transferBetweenCards(DataGenerator.getFirstCard(), 0);
        Assertions.assertEquals(startBalFirst, dashboardPage.getCardBalance(DataGenerator.getFirstCard().getId()));
        Assertions.assertEquals(startBalSecond, dashboardPage.getCardBalance(DataGenerator.getSecondCard().getId()));
    }

    @Test
    void shouldTestTransferWithEmptyFields() {
        var authInfo = DataGenerator.getAuthInfo();
        var verificationCode = DataGenerator.getVerificationCodeFor(authInfo);
        var dashboardPage = open("http://localhost:9999", LoginPage.class).validLogin(authInfo).validVerify(verificationCode);
        TransferPage transferPage = dashboardPage.moneyTransferTo(DataGenerator.getSecondCard());
        transferPage.notificationVisibility();
    }

    @Test
    void shouldTestLoginWithWrongVerificationCode() {
        var authInfo = DataGenerator.getAuthInfo();
        var verificationCode = DataGenerator.getOtherVerificationCode(authInfo);
        var verificationPage = open("http://localhost:9999", LoginPage.class).validLogin(authInfo);
        verificationPage.validVerify(verificationCode);
        verificationPage.notificationVisibility();
    }

}
