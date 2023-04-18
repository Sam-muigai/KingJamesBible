package com.sam.kingjamesbible.feature_bible.presentation.book_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.kingjamesbible.feature_bible.core.CHAPTER_SCREEN
import com.sam.kingjamesbible.feature_bible.core.DataState
import com.sam.kingjamesbible.feature_bible.core.UiEvents
import com.sam.kingjamesbible.feature_bible.data.local.books.BookData
import com.sam.kingjamesbible.feature_bible.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookScreenViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _state = MutableStateFlow(BookScreenState())
    val state = _state.asStateFlow()

    private var _uiEvents = MutableSharedFlow<UiEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    private var testament by mutableStateOf("")

    init {
        testament = savedStateHandle.get<String>("testament")!!
        getAllBooks()
    }

    fun onBookClicked(bookName: String, bookId: String) {
        emitUiEvent(UiEvents.Navigate("$CHAPTER_SCREEN?bookId=$bookId?bookName=$bookName"))
    }

    fun onBackClicked() {
        emitUiEvent(UiEvents.PopBackStack)
    }

    private fun getAllBooks() {
        viewModelScope.launch {
            useCases.getAllBooks().collect { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        dataState.data?.let { books ->
                            _state.value = _state.value.copy(
                                books = books.map { it.toData() },
                                loading = false
                            )
                        }
                        _state.value = _state.value.copy(
                            loading = true
                        )
                    }
                    is DataState.Success -> {
                        val books = dataState.data?.map { it.toData() }!!
                        val filteredBooks = if (testament == "OLD TESTAMENT"){
                            books.subList(0,53).filter {
                                !catholicsBooks.contains(it.name)
                            }
                        }else{
                            books.subList(53,books.size)
                        }
                        _state.value = _state.value.copy(
                            loading = false,
                            books = filteredBooks,
                        )
                    }
                    is DataState.Error -> {
                        val books = dataState.data?.map { it.toData() }!!
                        val filteredBooks = if (testament == "OLD TESTAMENT"){
                            books.subList(0,53).filter {
                                !catholicsBooks.contains(it.name)
                            }
                        }else{
                            books.subList(53,books.size)
                        }
                        _state.value = _state.value.copy(
                            books = filteredBooks,
                            loading = false
                        )
                        emitUiEvent(UiEvents.ShowSnackBar(dataState.message!!))
                    }
                }
            }
        }
    }

    private fun emitUiEvent(events: UiEvents) {
        viewModelScope.launch {
            _uiEvents.emit(events)
        }
    }

}