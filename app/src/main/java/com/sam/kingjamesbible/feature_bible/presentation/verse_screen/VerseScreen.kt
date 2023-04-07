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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.sam.kingjamesbible.feature_bible.core.components.TopBar
import com.sam.kingjamesbible.feature_bible.presentation.verse_screen.components.HeaderSection
import com.sam.kingjamesbible.R
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun VerseScreen(
    viewModel: VerseViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle().value
    VerseScreen(
        state = uiState,
        onBackPressed = onBackPressed,
        showPreviousIcon = viewModel.isPreviousActive,
        showNextIcon = viewModel.isNextActive,
        onPreviousClicked = { viewModel.getPrevious() },
        onNextClicked = { viewModel.getNextChapter() }
    )
}

@Composable
private fun VerseScreen(
    modifier: Modifier = Modifier,
    state: VerseState,
    onBackPressed: () -> Unit,
    showPreviousIcon: Boolean,
    showNextIcon: Boolean,
    onPreviousClicked: () -> Unit,
    onNextClicked: () -> Unit
) {
    val context = LocalContext.current
    val typeface = ResourcesCompat.getFont(context, R.font.raleway_medium)
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                title = state.bookName,
                onIconClick = onBackPressed
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            HeaderSection(
                title = state.bookChapter,
                onNextClick = onNextClicked,
                showPreviousIcon = showPreviousIcon,
                showNextIcon = showNextIcon,
                onPreviousClick = onPreviousClicked
            )
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
                        textView.setTextColor(Color.BLACK)
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