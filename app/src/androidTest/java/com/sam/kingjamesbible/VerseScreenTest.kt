package com.sam.kingjamesbible

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.sam.kingjamesbible.feature_bible.presentation.verse_screen.VerseScreen
import com.sam.kingjamesbible.feature_bible.presentation.verse_screen.VerseState
import com.sam.kingjamesbible.feature_bible.presentation.verse_screen.fakeVerseState
import com.sam.kingjamesbible.ui.theme.KingJamesBibleTheme
import org.junit.Rule
import org.junit.Test

class VerseScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loading_state_displays_animation_correctly() {
        composeTestRule.setContent {
            KingJamesBibleTheme {
                VerseScreen(
                    state = VerseState(loading = true),
                    onBackPressed = { /*TODO*/ },
                    showPreviousIcon = true,
                    showNextIcon = true,
                    onPreviousClicked = { /*TODO*/ }) {

                }
            }
        }

        composeTestRule.onNodeWithContentDescription("Loading animation")
            .assertIsDisplayed()
    }

    @Test
    fun contents_are_displayed_correctly() {
        composeTestRule.setContent {
            KingJamesBibleTheme {
                VerseScreen(
                    state = fakeVerseState,
                    onBackPressed = { },
                    showPreviousIcon = true,
                    showNextIcon = false,
                    onPreviousClicked = { }
                ) {

                }
            }
        }

        composeTestRule.onNodeWithText("Genesis")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Genesis 3")
            .assertIsDisplayed()

    }




}