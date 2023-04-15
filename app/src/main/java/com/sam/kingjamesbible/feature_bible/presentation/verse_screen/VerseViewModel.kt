package com.sam.kingjamesbible.feature_bible.presentation.verse_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.kingjamesbible.feature_bible.core.DataState
import com.sam.kingjamesbible.feature_bible.core.UiEvents
import com.sam.kingjamesbible.feature_bible.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class VerseViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(VerseState())
    val state = _state.asStateFlow()

    private val _uiEvents = MutableSharedFlow<UiEvents>()
    val uiEvents = _uiEvents.asSharedFlow()


    var chapterCount by mutableStateOf("")
    init {
        val bookName = savedStateHandle.get<String>("bookName")!!
        val chapterId = savedStateHandle.get<String>("chapterId")!!
        chapterCount = savedStateHandle.get<String>("chapterCount")!!
        _state.value = _state.value.copy(
            bookName = bookName
        )
        getVerse(chapterId)
    }

    var isPreviousActive by mutableStateOf(true)
    var isNextActive by mutableStateOf(true)


    fun getPrevious() {
       getVerse(state.value.previousChapter)
    }

    fun getNextChapter() {
        getVerse(state.value.nextChapter)
    }

    fun onBackPressed(){
        emitUiEvents(UiEvents.PopBackStack)
    }
    private fun getVerse(chapterId:String){
        viewModelScope.launch {
            useCases.getVerse(chapterId).collect{
                when(it){
                    is DataState.Loading ->{
                            _state.value = _state.value.copy(
                                loading = true
                            )
                    }
                    is DataState.Success ->{
                        it.data?.let {verses ->
                            _state.value = _state.value.copy(
                                loading = false,
                                content = verses.content,
                                previousChapter = verses.previousId,
                                nextChapter = verses.nextId,
                                bookChapter = verses.reference
                            )
                        }
                        isPreviousActive = _state.value.bookChapter.split(" ").last() != "1"
                        isNextActive = _state.value.bookChapter.split(" ").last() != chapterCount
                    }
                    is DataState.Error ->{
                        it.data?.let {verses ->
                            _state.value = _state.value.copy(
                                loading = false,
                                content = verses.content,
                                previousChapter = verses.previousId,
                                nextChapter = verses.nextId,
                                bookChapter = verses.reference
                            )
                        }
                        emitUiEvents(UiEvents.ShowSnackBar(it.message!!))
                        isPreviousActive = _state.value.bookChapter.split(" ").last() != "1"
                        isNextActive = _state.value.bookChapter.split(" ").last() != chapterCount
                    }
                }
            }
        }
    }

    private fun emitUiEvents(event: UiEvents){
        viewModelScope.launch {
            _uiEvents.emit(event)
        }
    }
}