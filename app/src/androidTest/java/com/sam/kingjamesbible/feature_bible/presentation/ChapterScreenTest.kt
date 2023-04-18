package com.sam.kingjamesbible.feature_bible.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.sam.kingjamesbible.feature_bible.domain.model.chapters.ChapterData
import com.sam.kingjamesbible.feature_bible.presentation.chapter_screen.ChapterScreen
import com.sam.kingjamesbible.feature_bible.presentation.chapter_screen.ChapterState
import com.sam.kingjamesbible.feature_bible.presentation.chapter_screen.fakeChapter
import com.sam.kingjamesbible.ui.theme.KingJamesBibleTheme
import org.junit.Rule
import org.junit.Test

class ChapterScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun when_screen_launches_the_title_is_displayed_correctly() {
        composeTestRule.setContent {
            KingJamesBibleTheme {
                ChapterScreen(
                    state = ChapterState(bookName = "Genesis"),
                    onChapterClicked = {},
                    onBackPress = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Genesis")
            .assertIsDisplayed()
    }


    @Test
    fun when_loading_and_chapter_list_is_empty_animation_is_displayed() {
        composeTestRule.setContent {
            KingJamesBibleTheme {
                ChapterScreen(
                    state = ChapterState(loading = true),
                    onChapterClicked = {},
                    onBackPress = {}
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("Loading animation")
            .assertIsDisplayed()
    }

    @Test
    fun when_list_is_not_empty_loading_animation_is_not_shown() {
        composeTestRule.setContent {
            KingJamesBibleTheme {
                ChapterScreen(
                    state = ChapterState(
                        chapters = listOf(
                            ChapterData("", "", "", "", "")
                        )
                    ),
                    onChapterClicked = {},
                    onBackPress = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Loading animation")
            .assertDoesNotExist()
    }


    @Test
    fun when_data_is_available_it_is_displayed_correctly() {
        composeTestRule.setContent {
            KingJamesBibleTheme {
                ChapterScreen(
                    state = ChapterState(chapters = fakeChapter),
                    onChapterClicked = {},
                    onBackPress = {}
                )
            }
        }

        composeTestRule.onNodeWithText("3")
            .assertIsDisplayed()
    }
}