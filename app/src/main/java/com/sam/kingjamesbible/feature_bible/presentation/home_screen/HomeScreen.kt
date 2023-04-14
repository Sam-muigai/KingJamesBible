package com.sam.kingjamesbible.feature_bible.presentation.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sam.kingjamesbible.R
import com.sam.kingjamesbible.feature_bible.core.UiEvents
import com.sam.kingjamesbible.feature_bible.presentation.home_screen.components.DailyVerseCard
import com.sam.kingjamesbible.feature_bible.presentation.home_screen.components.SelectBibleButton
import kotlinx.coroutines.flow.collectLatest


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onTestamentClick: (String) -> Unit
) {
    val uiState = viewModel.state.collectAsState().value
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
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    modifier = Modifier.size(250.dp),
                    painter = painterResource(id = R.drawable.bible_image),
                    contentDescription = null
                )
                Text(
                    text = "King James Bible",
                    style = MaterialTheme.typography.h6.copy(
                        color = MaterialTheme.colors.primary,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                if (state.loading) {
                    Text(text = "Loading")
                } else {
                    DailyVerseCard(
                        verse = state.verse.text,
                        book = state.verse.reference,
                        bibleVersion = state.verse.version
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
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

