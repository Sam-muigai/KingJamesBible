package com.sam.kingjamesbible.feature_bible.presentation.home_screen

import com.sam.kingjamesbible.feature_bible.domain.model.books.Data

data class HomeScreenState(
    val loading:Boolean = false,
    val books:List<Data> = emptyList(),
)
