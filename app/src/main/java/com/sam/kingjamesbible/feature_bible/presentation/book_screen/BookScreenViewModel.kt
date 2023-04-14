package com.sam.kingjamesbible.feature_bible.presentation.book_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.kingjamesbible.feature_bible.core.CHAPTER_SCREEN
import com.sam.kingjamesbible.feature_bible.core.DataState
import com.sam.kingjamesbible.feature_bible.core.UiEvents
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
    private val useCases: UseCases
) : ViewModel() {

    private var _state = MutableStateFlow(BookScreenState())
    val state = _state.asStateFlow()

    private var _uiEvents = MutableSharedFlow<UiEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    init {
        getAllBooks()
    }

    fun onBookClicked(bookName:String,bookId:String,) {
        emitUiEvent(UiEvents.Navigate("$CHAPTER_SCREEN?bookId=$bookId?bookName=$bookName"))
    }

    fun onBackClicked(){
        emitUiEvent(UiEvents.PopBackStack)
    }

    private fun getAllBooks() {
        viewModelScope.launch {
            useCases.getAllBooks().collect { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _state.value = _state.value.copy(
                            loading = true
                        )
                    }
                    is DataState.Success -> {
                        _state.value = _state.value.copy(
                            loading = false,
                            books = dataState.data?.map { it.toData() }!!,
                        )
                    }
                    is DataState.Error -> {
                        _state.value = _state.value.copy(
                            books = dataState.data?.map { it.toData() }!!,
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