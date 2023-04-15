package com.sam.kingjamesbible.feature_bible.presentation.verse_screen

import android.text.Html
import android.widget.TextView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import android.graphics.Color
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.sam.kingjamesbible.feature_bible.core.components.TopBar
import com.sam.kingjamesbible.feature_bible.presentation.verse_screen.components.HeaderSection
import com.sam.kingjamesbible.R
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sam.kingjamesbible.feature_bible.core.UiEvents
import com.sam.kingjamesbible.feature_bible.core.components.LoadingScreen
import kotlinx.coroutines.flow.collectLatest
import java.util.*


@Composable
fun VerseScreen(
    viewModel: VerseViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle().value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collectLatest { events ->
            when (events) {
                is UiEvents.PopBackStack -> {
                    onBackPressed()
                }
                is UiEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = events.message
                    )
                }

                is UiEvents.Navigate -> Unit
            }
        }
    }
    VerseScreen(
        state = uiState,
        onBackPressed = viewModel::onBackPressed,
        showPreviousIcon = viewModel.isPreviousActive,
        scaffoldState = scaffoldState,
        showNextIcon = viewModel.isNextActive,
        onPreviousClicked = { viewModel.getPrevious() },
        onNextClicked = { viewModel.getNextChapter() }
    )
}

@Composable
fun VerseScreen(
    modifier: Modifier = Modifier,
    state: VerseState,
    onBackPressed: () -> Unit,
    showPreviousIcon: Boolean,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    showNextIcon: Boolean,
    onPreviousClicked: () -> Unit,
    onNextClicked: () -> Unit
) {
    val context = LocalContext.current
    val typeface = ResourcesCompat.getFont(context, R.font.raleway_medium)
    val textColor = if (isSystemInDarkTheme()) Color.WHITE else Color.BLACK
    Scaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                title = state.bookName,
                onIconClick = onBackPressed
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            HeaderSection(
                title = state.bookChapter,
                onNextClick = onNextClicked,
                showPreviousIcon = showPreviousIcon,
                showNextIcon = showNextIcon,
                onPreviousClick = onPreviousClicked
            )
            if (state.loading) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.semantics {
                                contentDescription = "Loading animation"
                            },
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }else{
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        AndroidView(
                            factory = { context ->
                                TextView(context)
                            },
                            update = { textView ->
                                textView.typeface = typeface
                                textView.setTextColor(textColor)
                                textView.text = Html.fromHtml(
                                    state.content,
                                    Html.FROM_HTML_MODE_LEGACY
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}