package io.github.dziugasj.selenide;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

final class ImdbTest {
    private final String IMDB_URL = "https://imdb.com";

    @Test
    @DisplayName("Custom test name containing spaces")
    void userCanLoginByUsername() {
        open(IMDB_URL);
    }


}
