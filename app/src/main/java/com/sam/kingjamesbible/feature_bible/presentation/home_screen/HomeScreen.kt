package com.sam.kingjamesbible.feature_bible.presentation.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sam.kingjamesbible.R
import com.sam.kingjamesbible.feature_bible.core.UiEvents
import com.sam.kingjamesbible.feature_bible.core.components.TopBar
import com.sam.kingjamesbible.feature_bible.presentation.chapter_screen.components.ChapterButton
import com.sam.kingjamesbible.feature_bible.presentation.home_screen.components.DailyVerseCard
import com.sam.kingjamesbible.feature_bible.presentation.home_screen.components.SelectBibleButton
import kotlinx.coroutines.flow.collectLatest


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onTestamentClick: (String) -> Unit
) {
    val uiState = viewModel.state.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = true){
        viewModel.uiEvents.collectLatest {
            when(it){
                is UiEvents.Navigate ->{
                    onTestamentClick(it.route)
                }
                is UiEvents.PopBackStack ->Unit
                is UiEvents.ShowSnackBar ->Unit
            }
        }
    }

    HomeScreen(
        state = uiState,
        visible = viewModel.visible,
        onVisibleChange = viewModel::onVisibilityChange,
        onTestamentClick = viewModel::navigate
    )
}


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    visible:Boolean,
    onVisibleChange:()->Unit,
    onTestamentClick: (String) -> Unit
) {
    Scaffold(
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    modifier = Modifier.size(250.dp),
                    painter = painterResource(id = R.drawable.bible_image),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h6.copy(
                        color = MaterialTheme.colors.primary,
                        fontSize = 29.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                if (state.loading) {
                    Text(text = "Loading")
                } else {
                    DailyVerseCard(
                        verse = state.verse.text,
                        book = state.verse.reference,
                        bibleVersion = state.verse.version
                    )
                }
                SelectBibleButton(
                    visible = visible,
                    onButtonClick = onVisibleChange,
                    onTestamentClick = onTestamentClick
                )
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}
