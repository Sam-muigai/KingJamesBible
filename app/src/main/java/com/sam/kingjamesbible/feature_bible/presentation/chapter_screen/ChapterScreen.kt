package com.sam.kingjamesbible.feature_bible.presentation.chapter_screen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sam.kingjamesbible.feature_bible.core.components.TopBar
import com.sam.kingjamesbible.feature_bible.presentation.chapter_screen.components.ChapterButton
import com.sam.kingjamesbible.R
import com.sam.kingjamesbible.feature_bible.core.UiEvents
import com.sam.kingjamesbible.feature_bible.core.components.LoadingScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChapterScreen(
    viewModel: ChapterViewModel,
    onChapterClicked: (String) -> Unit,
    onBackPress: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val uiState = viewModel.state.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collectLatest {
            when (it) {
                is UiEvents.PopBackStack -> {
                    onBackPress()
                }
                is UiEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.message,
                        duration = SnackbarDuration.Long
                    )
                }
                is UiEvents.Navigate -> {
                    onChapterClicked(it.route)
                }
            }
        }
    }
    ChapterScreen(
        state = uiState,
        scaffoldState = scaffoldState,
        onBackPress = viewModel::onBackPressed,
        onChapterClicked = viewModel::onChapterClicked
    )
}

@Composable
fun ChapterScreen(
    modifier: Modifier = Modifier,
    state: ChapterState,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onChapterClicked: (String) -> Unit,
    onBackPress: () -> Unit
) {
    val chapters = state.chapters.filter {
        it.number != "intro"
    }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(title = stringResource(id = R.string.select_chapter)) {
                onBackPress()
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(4.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = state.bookName,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 21.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyVerticalGrid(columns = GridCells.Fixed(5)) {
                items(chapters) { chapterData ->
                    ChapterButton(
                        modifier = Modifier.padding(1.dp),
                        text = chapterData.number) {
                        onChapterClicked(chapterData.id)
                    }
                }
            }
            if (state.loading) {
                LoadingScreen(
                    modifier = Modifier.semantics {
                        contentDescription = "Loading animation"
                    }
                )
            }
        }
    }
}