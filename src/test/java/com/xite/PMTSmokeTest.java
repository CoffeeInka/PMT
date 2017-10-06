package com.xite;

import com.codeborne.selenide.*;
import com.xite.core.Helpers;
import com.xite.testdata.TestData;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class PMTSmokeTest {

    @Before
    public void setUp() {
        Configuration.timeout = 11000;
        Configuration.holdBrowserOpen = true;
        Configuration.collectionsTimeout = 11000;
    }

    public static SelenideElement title = $$(".channel-title> .title-text").findBy(visible);
    public static SelenideElement channelName = $("#channel-name.name");
    public static SelenideElement channelLabel = $(".channel .label");
    public static SelenideElement player = $("#video");

    @Test
    public void PMTSmokeTest() {
//        open("http://js-app.xite.com//app.html#fix");
//        switchChannelTo("Party Time");
//        assertChannelName("Party Time");
//        sleep(1500);
//        like();
//        assertHeartIcon();
//        sleep(2000);
////        rememberSong();
//        skip();
////        assertSkipIcon();
////        assertNextSong();
//        assertChannelName("Party Time");

        String username = Helpers.getUniqueString("User");
        String email = String.format(username + "@xite.com");
        String date = Helpers.getDate();
        System.out.println(username + "," + email + "," + date);

//        register(username, date, email, TestData.password);
//        assertPromptingContains(username);
//        assertChannelName("NOW");
//        logout(TestData.username);
//        assertPromptingContains("Press left to login");
//        login(TestData.username, TestData.password);
//        assertPromptingContains(TestData.username);
//        switchChannelTo(Channels.MyChannel("rock, the 00s, hot, energetic"));
//        assertChannelName("My Channel",TestData.username);
//        getToPlayer();
//        switchChannelTo(Channels.Search);
//        searchQuery(TestData.query);
//        assertSearchResults(TestData.query);
//        startSongFromSearchResults(2);
//        assertChannelName("Search");
//        like();
//        assertLikeIcon();
//        skip();
//        assertSkipIcon();
//        assertNextSong();
//        assertChannelName("My Channel",TestData.username);
//        switchToGuest();
//        switchToUser(TestData.username);
    }

    private void assertPromptingContains(String username) {
        $("#channel-action-text-top").waitUntil(visible, 11000);
        $("#channel-action-text-top").shouldHave(text("User"));
        $("#channel-action-text-bottom").shouldHave(exactText(username));
    }

//    private static void rememberSong() {
//        sleep(11000);
//        channelLabel.getText();
//    }

    private static void register(String username, String date, String email, String password) {
        pressLeft();
        pressDown();
        pressOK();
        pressDown(3);
        pressOK();

        $("#register-name").setValue(username);
        pressDown();
        $("#register-dob").setValue(date);
        pressDown();
        $("#register-gender").click();
        pressRight();
        pressDown();
        $("#register-email").setValue(email);
        pressDown();
        $("#register-password").setValue(password);
        pressDown();
        $("#register-register").click();
        pressOK();

        $("#disclaimer-yes").shouldBe(visible);
        pressOK();
    }

    private static void skip() {
        pressRight();
    }

    private void assertHeartIcon() {
        $("#center-hints .block .icon .fa-heart").should(appear);
    }

    private void assertSkipIcon() {
        $("#center-hints .block .icon .fa-step-forward").should(appear);
    }

    private static void like() {
        pressUp();
    }

    private static void assertChannelName(String text) {
//        channelLabel.waitUntil(Condition.matchesText("Channel"), 11);
        channelName.shouldHave(exactText(text));
    }

    private static void switchChannelTo(String channelName) {
        sleep(1500);
        player.sendKeys(Keys.ARROW_DOWN);
        //player.sendKeys(Keys.ARROW_LEFT);
        System.out.println(title);
        while (!(title.getText().contains(channelName))) {
            player.sendKeys(Keys.ARROW_LEFT);
            sleep(2000);
        }
        player.sendKeys(Keys.ENTER);
    }

    private static void pressUp() {
        player.sendKeys(Keys.ARROW_UP);
    }

    private static void pressDown() {
        player.sendKeys(Keys.ARROW_DOWN);
    }

    private static void pressDown(int times) {
        for (int i = 0; i < times; i++) {
            player.sendKeys(Keys.ARROW_DOWN);
        }
    }

    private static void pressLeft() {
        player.sendKeys(Keys.ARROW_LEFT);
    }

    private static void pressRight() {
        player.sendKeys(Keys.ARROW_RIGHT);
    }

    private static void pressOK() {
        player.sendKeys(Keys.ENTER);
    }
}

