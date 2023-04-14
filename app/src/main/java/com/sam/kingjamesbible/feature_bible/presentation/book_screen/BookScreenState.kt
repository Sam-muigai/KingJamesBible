package com.sam.kingjamesbible.feature_bible.presentation.book_screen

import com.sam.kingjamesbible.feature_bible.domain.model.books.Data

data class BookScreenState(
    val loading:Boolean = false,
    val books:List<Data> = emptyList(),
)
