package com.sam.kingjamesbible.feature_bible.presentation.verse_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.kingjamesbible.feature_bible.core.DataState
import com.sam.kingjamesbible.feature_bible.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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

    private var content by mutableStateOf("")
    private var chapter by mutableStateOf("")
    private var previousChapter by mutableStateOf("")
    private var nextChapter by mutableStateOf("")

    var isPreviousActive by mutableStateOf(true)
    var isNextActive by mutableStateOf(true)


    fun getPrevious() {
       getVerse(state.value.previousChapter)
    }

    fun getNextChapter() {
        getVerse(state.value.nextChapter)
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
                        _state.value = _state.value.copy(
                            loading = false,
                            content = it.data?.content!!,
                            previousChapter = it.data.previousId,
                            nextChapter = it.data.nextId,
                            bookChapter = it.data.reference
                        )
                    }
                    is DataState.Error ->{
                        _state.value = _state.value.copy(
                            loading = false,
                            content = it.data?.content!!,
                            previousChapter = it.data.previousId,
                            nextChapter = it.data.nextId,
                            bookChapter = it.data.reference
                        )
                    }
                }
            }
        }
    }

 /**   private fun getVerse(chapterId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                loading = true
            )
            try {
                useCases.getVerse(chapterId).collect {
                    content = it.content
                    chapter = it.reference
                    previousChapter = it.previous.id
                    nextChapter = it.next.id
                    verseCount = it.verseCount
                }
                _state.value = state.value.copy(
                    loading = false,
                    content = content,
                    bookChapter = chapter,
                    previousChapter = previousChapter,
                    nextChapter = nextChapter,
                    verseCount = verseCount
                )
                isPreviousActive = _state.value.bookChapter.split(" ").last() != "1"
                isNextActive = _state.value.bookChapter.split(" ").last() != chapterCount
            } catch (e: IOException) {
                _state.value = state.value.copy(
                    loading = false,
                    error = "Error occurred.Please check your internet connection"
                )
            } catch (e: HttpException) {
                _state.value = state.value.copy(
                    loading = false,
                    error = "Server error occurred"
                )
            } catch (e: Exception) {
                _state.value = state.value.copy(
                    loading = false,
                    error = "Unknown error occurred"
                )
            }
        }
    }**/
}