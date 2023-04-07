package com.sam.kingjamesbible.feature_bible.presentation.verse_screen

data class VerseState(
    val bookName:String = "",
    val bookChapter:String = "",
    val content:String = "",
    val loading:Boolean = false,
    val error:String? = null,
    val previousChapter:String = "",
    val nextChapter:String = "",
    val verseCount:Int = 0
)
