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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sam.kingjamesbible.R
import com.sam.kingjamesbible.feature_bible.core.components.TopBar
import com.sam.kingjamesbible.feature_bible.presentation.home_screen.components.DailyVerseCard
import com.sam.kingjamesbible.feature_bible.presentation.home_screen.components.SelectBibleButton
import com.sam.kingjamesbible.ui.theme.KingJamesBibleTheme


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onNewTestamentClick: (String) -> Unit,
    onOldTestamentClick: (String) -> Unit
) {
    val uiState = viewModel.state.collectAsState().value
    HomeScreen(
        state = uiState,
        onOldTestamentClick = onOldTestamentClick,
        onNewTestamentClick = onNewTestamentClick
    )
}


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    onNewTestamentClick: (String) -> Unit,
    onOldTestamentClick: (String) -> Unit,
) {
    var visible by remember {
        mutableStateOf(false)
    }
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
                    onButtonClick = {
                        visible = !visible
                    },
                    onNewTestamentClick = onNewTestamentClick,
                    onOldTestamentClick = onOldTestamentClick
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

