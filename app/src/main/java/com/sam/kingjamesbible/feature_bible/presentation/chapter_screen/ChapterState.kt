package com.sam.kingjamesbible.feature_bible.presentation.chapter_screen

import com.sam.kingjamesbible.feature_bible.domain.model.chapters.ChapterData

data class ChapterState(
    val chapters:List<ChapterData> = emptyList(),
    val bookName:String = "",
    val loading:Boolean = false
)

val fakeChapter = listOf(
    ChapterData(
        bibleId = "de4e12af7f28f599-01" ,
        bookId = "GEN",
        id = "GEN.3",
        number = "3",
        reference = "Genesis 3"
    )
)