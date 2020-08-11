package io.github.dziugasj.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.junit5.ScreenShooterExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static io.github.dziugasj.selenide.ImdbTest.SearchCategory.VIDEO_GAME;

final class ImdbTest {
    private final static String IMDB_URL = "https://imdb.com";
    private final static String SEARCH_KEY = "games of thrones";

    @RegisterExtension
    static ScreenShooterExtension screenShooterExtension = new ScreenShooterExtension(true);

    @BeforeAll
    static void setup() {
        closeWebDriver();
        Configuration.baseUrl = IMDB_URL;
    }

    @BeforeEach
    void beforeEach() {
        openMainPage();
    }

    @Test
    @DisplayName("Given main page When search by key and filter by category performed Then proper header of result page shown")
    void searchByKeyAndFilterByCategory() {
        searchByKey(SEARCH_KEY);
        filterByCategory(VIDEO_GAME);

        shouldHaveSearchByKeyHeader(SEARCH_KEY);
        shouldHaveFilterByCategoryHeader(VIDEO_GAME);
    }

    private void openMainPage() {
        open("/");
    }

    private void searchByKey(String searchKey) {
        $(byId("suggestion-search"))
                .shouldBe(visible)
                .setValue(searchKey)
                .submit();
    }

    private void filterByCategory(SearchCategory searchCategory) {
        $(byClassName("findFilterList"))
                .shouldBe(visible)
                .find(byLinkText(searchCategory.getValue()))
                .click();
    }

    private void shouldHaveSearchByKeyHeader(String searchKey) {
        SelenideElement header = $(byClassName("findHeader"));

        header.shouldHave(matchText("Displaying . results for"));
        header.find(byClassName("findSearchTerm")).shouldHave(text(searchKey));
    }

    private void shouldHaveFilterByCategoryHeader(SearchCategory searchCategory) {
        $(byId("findSubHeader"))
                .shouldHave(text(searchCategory.getValue()));
    }

    enum SearchCategory {
        VIDEO_GAME("Video Game");

        private final String value;

        SearchCategory(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
