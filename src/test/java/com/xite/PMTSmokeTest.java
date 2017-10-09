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
    public static SelenideElement hearFilter = $("#hear .slide-title");
    public static SelenideElement beFilter = $("#be .slide-title");
    public static SelenideElement seeFilter = $("#see .slide-title");
    public static SelenideElement feelFilter = $("#feel .slide-title");
    public static ElementsCollection searchResults = $$(".search-song").filterBy(visible);
    public static ElementsCollection users = $$(".user-name").filterBy(visible);

    @Test
    public void PMTSmokeTest() {
        open("http://js-app.xite.com//app.html#fix");
        like();
        assertHeartIcon();
        sleep(2000);
//        rememberSong();
        skip();
//        assertSkipIcon();
//        assertNextSong();
        switchChannelTo("Fiesta Latina");
        assertChannelName("Fiesta Latina");
        sleep(1500);

        //setting up User credentials
        String username = Helpers.getUniqueString("User");
        String email = String.format(username + "@xite.com");
        String date = Helpers.getDate();
        System.out.println(username + "," + email + "," + date);

        register(username, date, email, TestData.password);
        assertPromptingContainsUsername(username);
        assertChannelName("NOW");

        switchChannelTo("Sunday Chill");
        assertChannelName("Sunday Chill");

        like();
        assertHeartIcon();
        sleep(2000);
        skip();
//        assertSkipIcon();

        logout(username);
        assertPromptingContains("to login");
        assertChannelName("NOW");

        login(username, TestData.password);
        assertPromptingContainsUsername(username);
        assertChannelName("NOW");

        switchChannelTo("My Channel");
        setupMyChannel("rock", "the 00s", "hot", "energetic");
        assertMyChannelIndication(username);
        quitMyChannel();

        like();
        assertHeartIcon();
        sleep(2000);
        skip();
//        assertSkipIcon();

        switchChannelTo("Search");
        searchQuery(TestData.query);
        assertSearchResults(TestData.query);
        sleep(500);
        startSongFromSearchWithIndex(1);
        assertChannelName("Search");

        like();
        assertHeartIcon();
        sleep(2000);
        skip();
//        assertSkipIcon();
//        assertNextSong();
        assertMyChannelIndication(username);

        switchChannelTo("My Favourites");
        assertChannelName("My Favourites");

        switchToGuest();
        assertPromptingContains("to login");
        switchToUser(username);
        assertPromptingContainsUsername(username);

    }

    private static void switchToUser(String username) {
        pressLeft();
        for (SelenideElement user : users) {
            if (!(user.getText().contains(username))) {
                player.sendKeys(Keys.ARROW_DOWN);
            } else {
                pressOK();
            }
        }
    }

    private static void switchToGuest() {
        pressLeft();
        for (SelenideElement user : users) {
            if (!(user.getText().contains("Guest"))) {
                player.sendKeys(Keys.ARROW_DOWN);
            } else {
                pressOK();
            }
        }
    }

    private static void startSongFromSearchWithIndex(int index) {
        pressDown(index);
        pressOK();
    }

    private static void assertSearchResults(String query) {
        for (SelenideElement result : searchResults) {
            result.shouldHave(value(query));
        }
    }

    private static void searchQuery(String query) {
//        $("#search-input").click();
        $("#search-input").setValue(query).pressEnter();
        $("#limeReturn").click();
        $(".lime-container").should(disappear);
    }

    private static void quitMyChannel() {
        pressDown();
        pressBack(2);
    }

    private static void pressBack() {
        player.pressEscape();
    }

    private static void pressBack(int times) {
        for (int i = 0; i < times; i++) {
            player.pressEscape();
        }
    }

    private void assertMyChannelIndication(String username) {
        channelLabel.shouldHave(exactText("My Channel"));
        channelName.shouldHave(exactText(username));
    }

    private void setupMyChannel(String hear, String be, String see, String feel) {
        while (!(hearFilter.getText().contains(hear))) {
            player.sendKeys(Keys.ARROW_DOWN);
            sleep(1500);
        }
        pressRight();

        while (!(beFilter.getText().contains(be))) {
            player.sendKeys(Keys.ARROW_DOWN);
            sleep(500);
        }
        pressRight();

        while (!(seeFilter.getText().contains(see))) {
            player.sendKeys(Keys.ARROW_DOWN);
            sleep(500);
        }
        pressRight();

        while (!(feelFilter.getText().contains(feel))) {
            player.sendKeys(Keys.ARROW_DOWN);
            sleep(500);
        }
        pressOK();
    }

    private void login(String username, String password) {
        pressLeft();

    }

    private void logout(String username) {
        pressLeft();
        $$("#users-list .user-name").findBy(text(username)).sendKeys(Keys.ARROW_LEFT);
        pressOK();
    }

    private void assertPromptingContainsUsername(String username) {
        $("#channel-action-text-top").waitUntil(visible, 3000);
//        $("#channel-action-text-top").shouldHave(text("User"));
        $("#channel-action-text-top").waitUntil(Condition.text("User"), 45000);
        $("#channel-action-text-bottom").shouldHave(exactText(username));
    }

    private void assertPromptingContains(String text) {
        $("#channel-action-text-top").waitUntil(visible, 3000);
        $("#channel-action-text-top").waitUntil(Condition.text("Press left"), 45000);
        $("#channel-action-text-bottom").shouldHave(exactText(text));
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
        channelName.shouldHave(exactText(text));
    }

    private static void switchChannelTo(String channelName) {
        sleep(1500);
        player.sendKeys(Keys.ARROW_DOWN);
        System.out.println(title);
        while (!(title.getText().contains(channelName))) {
            player.sendKeys(Keys.ARROW_RIGHT);
            sleep(500);
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

