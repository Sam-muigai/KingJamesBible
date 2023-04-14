package com.sam.kingjamesbible.feature_bible.presentation.book_screen

import com.sam.kingjamesbible.feature_bible.domain.model.books.Data

data class BookScreenState(
    val loading:Boolean = false,
    val books:List<Data> = emptyList(),
)
val catholicsBooks =
    listOf("1 Maccabees","2 Maccabees","Susanna",
        "Bel and the Dragon","Song of the Three",
        "Baruch","Wisdom","Judith","Tobit",
        "1 Esdras","2 Esdras","Manasseh","Esther (Greek)",
        "Ecclesiasticus")