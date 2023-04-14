package com.sam.kingjamesbible.feature_bible.presentation.book_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sam.kingjamesbible.feature_bible.core.UiEvents
import com.sam.kingjamesbible.feature_bible.core.components.LoadingScreen
import com.sam.kingjamesbible.feature_bible.core.components.TopBar
import com.sam.kingjamesbible.feature_bible.presentation.book_screen.components.BookColumn

@Composable
fun BookScreen(
    modifier: Modifier = Modifier,
    viewModel: BookScreenViewModel,
    onBackClicked: () -> Unit,
    testament:String = "",
    onBookClick: (String)->Unit
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
                is UiEvents.PopBackStack -> {
                    onBackClicked()
                }
            }
        }
    }
    BookScreen(
        modifier = modifier,
        state = uiState,
        testament = testament,
        onBackClicked = viewModel::onBackClicked,
        scaffoldState = scaffoldState,
        onBookClick = viewModel::onBookClicked
    )
}

@Composable
fun BookScreen(
    modifier: Modifier = Modifier,
    state: BookScreenState,
    testament:String,
    onBackClicked:() ->Unit = {},
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onBookClick: (String,String) -> Unit
) {
    val bibleBooks = state.books
    val books = if (!state.loading){
        if (testament == "OLD TESTAMENT"){
            bibleBooks.subList(0,53).filter {
                !catholicsBooks.contains(it.name)
            }
        }else{
            bibleBooks.subList(53,bibleBooks.size)
        }
    }else{
        state.books
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopBar(
                title = "Select A book"
            ){
                onBackClicked()
            }
        },
        scaffoldState = scaffoldState
    ) {
        Surface(modifier = Modifier.padding(it)) {
            LazyColumn {
                itemsIndexed(books) { index, data ->
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