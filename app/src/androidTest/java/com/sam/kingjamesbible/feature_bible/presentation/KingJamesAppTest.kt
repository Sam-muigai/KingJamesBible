package com.sam.kingjamesbible.feature_bible.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sam.kingjamesbible.MainActivity
import com.sam.kingjamesbible.di.AppModule
import com.sam.kingjamesbible.feature_bible.core.BOOK_SCREEN
import com.sam.kingjamesbible.feature_bible.core.CHAPTER_SCREEN
import com.sam.kingjamesbible.feature_bible.core.HOME_SCREEN
import com.sam.kingjamesbible.feature_bible.core.VERSE_SCREEN
import com.sam.kingjamesbible.feature_bible.presentation.book_screen.BookScreen
import com.sam.kingjamesbible.feature_bible.presentation.book_screen.BookScreenViewModel
import com.sam.kingjamesbible.feature_bible.presentation.chapter_screen.ChapterScreen
import com.sam.kingjamesbible.feature_bible.presentation.chapter_screen.ChapterViewModel
import com.sam.kingjamesbible.feature_bible.presentation.home_screen.HomeScreen
import com.sam.kingjamesbible.feature_bible.presentation.verse_screen.VerseScreen
import com.sam.kingjamesbible.ui.theme.KingJamesBibleTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class)
class KingJamesAppTest{

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.activity.setContent {
            KingJamesBibleTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = HOME_SCREEN
                ) {
                    composable(
                        route = HOME_SCREEN
                    ) {
                        HomeScreen(
                            onTestamentClick = {
                                navController.navigate(it)
                            }
                        )
                    }
                    composable(
                        route = "$BOOK_SCREEN?testament={testament}",
                        arguments = listOf(
                            navArgument("testament"){
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val viewModel: BookScreenViewModel = hiltViewModel()
                        BookScreen(
                            modifier = Modifier
                                .semantics { contentDescription = "Home Screen" },
                            viewModel = viewModel,
                            onBackClicked = {
                                navController.popBackStack()
                            }
                        ) { route ->
                            navController.navigate(route)
                        }
                    }
                    composable(
                        route = "$CHAPTER_SCREEN?bookId={bookId}?bookName={bookName}",
                        arguments = listOf(
                            navArgument("bookId") {
                                type = NavType.StringType
                            },
                            navArgument("bookName") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        val bookName = it.arguments?.getString("bookName")!!
                        val viewModel: ChapterViewModel = hiltViewModel()
                        ChapterScreen(
                            viewModel = viewModel,
                            onChapterClicked = { route ->
                                navController.navigate("$route?bookName=$bookName")
                            }
                        ) {
                            navController.popBackStack()
                        }
                    }
                    composable(
                        route = "$VERSE_SCREEN?chapterId={chapterId}?chapterCount={chapterCount}?bookName={bookName}",
                        arguments = listOf(
                            navArgument("chapterCount") {
                                type = NavType.StringType
                            },
                            navArgument("bookName") {
                                type = NavType.StringType
                            },
                            navArgument("chapterId") {
                                type = NavType.StringType
                            }
                        )
                    ) {
                        VerseScreen {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }

    @Test
    fun on_new_testament_click_navigates_to_book_screen_with_new_testament_books(){
        runBlocking {
            composeRule.onNodeWithTag("Select Bible")
                .performClick()
            composeRule.onNodeWithText("NEW TESTAMENT")
                .performClick()

            delay(5000)
            composeRule.onAllNodesWithTag("book")[0]
                .assertTextEquals("Matthew")
        }
    }


}