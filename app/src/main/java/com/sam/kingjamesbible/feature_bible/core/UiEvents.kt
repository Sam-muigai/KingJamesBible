package com.sam.kingjamesbible.feature_bible.core

sealed class UiEvents{
    data class ShowSnackBar(val message:String):UiEvents()
    object PopBackStack:UiEvents()
    data class Navigate(val route:String):UiEvents()
}
