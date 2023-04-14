package com.sam.kingjamesbible

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.sam.kingjamesbible.feature_bible.domain.model.books.Books
import com.sam.kingjamesbible.feature_bible.domain.model.books.Data
import com.sam.kingjamesbible.feature_bible.presentation.book_screen.BookScreen
import com.sam.kingjamesbible.feature_bible.presentation.book_screen.BookScreenState
import com.sam.kingjamesbible.ui.theme.KingJamesBibleTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    private val data = Data(abbreviation = "Gen",
        bibleId = "de4e12af7f28f599-01",
        id = "GEN",
        name = "Genesis",
        nameLong = "The First Book of Moses, called Genesis"
    )
    val books = Books(
        listOf(data)
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun when_app_launches_top_bar_is_displayed() {
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
    fun when_loading_animation_is_showing() {
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