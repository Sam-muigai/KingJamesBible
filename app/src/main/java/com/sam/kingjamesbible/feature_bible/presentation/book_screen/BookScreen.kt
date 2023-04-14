package com.sam.kingjamesbible.feature_bible.presentation.home_screen

import androidx.compose.foundation.layout.*
import com.sam.kingjamesbible.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.sam.kingjamesbible.feature_bible.core.UiEvents
import com.sam.kingjamesbible.feature_bible.core.components.LoadingScreen
import com.sam.kingjamesbible.feature_bible.core.components.TopBar
import com.sam.kingjamesbible.feature_bible.presentation.home_screen.components.BookColumn
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel,
    onBookClick: (String) ->Unit
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle().value
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect{events ->
            when (events) {
                is UiEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        events.message
                    )
                }
                is UiEvents.Navigate ->{
                    onBookClick(events.route)
                }
                is UiEvents.PopBackStack ->Unit
            }
        }
    }
    HomeScreen(
        modifier = modifier,
        state = uiState,
        scaffoldState = scaffoldState,
        onBookClick = viewModel::onBookClicked
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onBookClick: (String,String) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = "Select A book",
                icon = null
            )
        },
        scaffoldState = scaffoldState
    ) {
        Surface(modifier = Modifier.padding(it)) {
            LazyColumn {
                itemsIndexed(state.books) { index, data ->
                    val backgroundColor =
                        if (index % 2 == 0)
                            MaterialTheme.colors.secondary.copy(alpha = 0.2f)
                        else
                            MaterialTheme.colors.background
                    BookColumn(
                        backgroundColor = backgroundColor,
                        bookTitle = data.name,
                        onBookClick = {
                            onBookClick(
                                data.name,
                                data.id
                            )
                        }
                    )
                }
            }
            if (state.loading) {
                LoadingScreen(
                    modifier = Modifier.semantics{
                        contentDescription = "Loading animation"
                    }
                )
            }
        }
    }
}