package com.sam.kingjamesbible.feature_bible.presentation.home_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sam.kingjamesbible.feature_bible.core.DataState
import com.sam.kingjamesbible.feature_bible.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val useCases: UseCases
):ViewModel(){

    private var _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        getDailyVerse()
    }

    var visible by mutableStateOf(false)
    fun onVisibilityChange(){
        visible = !visible
    }
    private fun getDailyVerse() {
        viewModelScope.launch {
            useCases.getDailyVerse().collect{dataState ->
                when(dataState){
                    is DataState.Loading ->{
                        if (dataState.data != null){
                            _state.value = _state.value.copy(
                                verse = dataState.data.toDailyVerse()
                            )
                        }else{
                            _state.value = _state.value.copy(
                                loading = true
                            )
                        }
                    }
                    is DataState.Success ->{
                        _state.value = _state.value.copy(
                            loading = false,
                            verse = dataState.data?.toDailyVerse()!!
                        )
                    }
                    is DataState.Error ->{
                        _state.value = _state.value.copy(
                            loading = false,
                            verse = dataState.data?.toDailyVerse()!!
                        )
                    }
                }
            }
        }
    }
}