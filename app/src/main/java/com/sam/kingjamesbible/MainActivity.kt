package com.sam.kingjamesbible

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sam.kingjamesbible.feature_bible.core.CHAPTER_SCREEN
import com.sam.kingjamesbible.feature_bible.core.BOOK_SCREEN
import com.sam.kingjamesbible.feature_bible.core.HOME_SCREEN
import com.sam.kingjamesbible.feature_bible.core.VERSE_SCREEN
import com.sam.kingjamesbible.feature_bible.presentation.chapter_screen.ChapterScreen
import com.sam.kingjamesbible.feature_bible.presentation.chapter_screen.ChapterViewModel
import com.sam.kingjamesbible.feature_bible.presentation.book_screen.BookScreen
import com.sam.kingjamesbible.feature_bible.presentation.book_screen.BookScreenViewModel
import com.sam.kingjamesbible.feature_bible.presentation.home_screen.HomeScreen
import com.sam.kingjamesbible.feature_bible.presentation.verse_screen.VerseScreen
import com.sam.kingjamesbible.ui.theme.KingJamesBibleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KingJamesBibleTheme {
                MyApp(navController = rememberNavController())
            }
        }
    }
}


@Composable
fun MyApp(navController: NavHostController) {
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
            val testament = it.arguments?.getString("testament")!!
            BookScreen(
                modifier = Modifier
                    .semantics { contentDescription = "Home Screen" },
                viewModel = viewModel,
                testament = testament,
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