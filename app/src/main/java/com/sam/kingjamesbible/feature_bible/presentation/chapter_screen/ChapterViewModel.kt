package com.sam.kingjamesbible.feature_bible.presentation.chapter_screen


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.kingjamesbible.feature_bible.core.DataState
import com.sam.kingjamesbible.feature_bible.core.UiEvents
import com.sam.kingjamesbible.feature_bible.core.VERSE_SCREEN
import com.sam.kingjamesbible.feature_bible.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChapterViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _state = MutableStateFlow(ChapterState())
    val state = _state.asStateFlow()

    private var _uiEvents = MutableSharedFlow<UiEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    private var chapterCount by mutableStateOf("")

    init {
        val bookId = savedStateHandle.get<String>("bookId")!!
        val bookName = savedStateHandle.get<String>("bookName")!!
        _state.value = _state.value.copy(
            bookName = bookName
        )
        getChapters(bookId)
    }

    fun onChapterClicked(chapterId: String) {
        emitUiEvents(UiEvents.Navigate("$VERSE_SCREEN?chapterId=$chapterId?chapterCount=$chapterCount"))
    }

    fun onBackPressed() {
        emitUiEvents(UiEvents.PopBackStack)
    }


    private fun getChapters(bookId: String) {
        viewModelScope.launch {
            useCases.getChapters(bookId).collect { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                            _state.value = _state.value.copy(
                                chapters = dataState.data?.map { it.toChapterData() } ?: emptyList(),
                                loading = true
                            )
                    }
                    is DataState.Success -> {
                        _state.value = _state.value.copy(
                            chapters = dataState.data?.map { it.toChapterData() } ?: emptyList(),
                            loading = false
                        )
                        chapterCount = (_state.value.chapters.size - 1).toString()
                    }

                    is DataState.Error -> {
                        _state.value = _state.value.copy(
                            chapters = dataState.data?.map { it.toChapterData() } ?: emptyList(),
                            loading = false
                        )
                        dataState.message?.let {
                            emitUiEvents(UiEvents.ShowSnackBar(it))
                        }
                    }
                }
            }
        }
    }

    private fun emitUiEvents(events: UiEvents) {
        viewModelScope.launch {
            _uiEvents.emit(events)
        }
    }

}