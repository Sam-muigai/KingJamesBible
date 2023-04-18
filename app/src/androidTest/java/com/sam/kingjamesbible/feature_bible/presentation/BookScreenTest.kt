package com.sam.kingjamesbible.feature_bible.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.sam.kingjamesbible.feature_bible.domain.model.books.Data
import com.sam.kingjamesbible.feature_bible.presentation.book_screen.BookScreen
import com.sam.kingjamesbible.feature_bible.presentation.book_screen.BookScreenState
import com.sam.kingjamesbible.ui.theme.KingJamesBibleTheme
import org.junit.Rule
import org.junit.Test

class BookScreenTest {

    private val data = Data(abbreviation = "Gen",
        bibleId = "de4e12af7f28f599-01",
        id = "GEN",
        name = "Genesis",
        nameLong = "The First Book of Moses, called Genesis"
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun when_app_launches_title_of_screen_is_displayed() {
        composeTestRule.setContent {
            KingJamesBibleTheme {
                BookScreen(
                    state = BookScreenState(),
                    onBookClick = { _, _ -> }
                )
            }
        }
        composeTestRule
            .onNodeWithText("Select A book")
            .assertIsDisplayed()
    }

    @Test
    fun when_state_is_loading_loading_animation_is_showing() {
        composeTestRule.setContent {
            KingJamesBibleTheme {
                BookScreen(
                    state = BookScreenState(loading = true),
                    onBookClick = { _, _ -> }
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Loading animation")
            .assertIsDisplayed()
    }

    @Test
    fun when_data_is_available_it_is_displayed() {
        composeTestRule.setContent {
            KingJamesBibleTheme {
                BookScreen(
                    state = BookScreenState(books = listOf(data)),
                    onBookClick = { _, _ -> }
                )
            }
        }

        composeTestRule
            .onNodeWithText("Genesis")
            .assertIsDisplayed()
    }

}