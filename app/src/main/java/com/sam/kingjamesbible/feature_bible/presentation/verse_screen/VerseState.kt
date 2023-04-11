package com.sam.kingjamesbible.feature_bible.presentation.verse_screen

data class VerseState(
    val bookName:String = "",
    val bookChapter:String = "",
    val content:String = "",
    val loading:Boolean = false,
    val previousChapter:String = "",
    val nextChapter:String = "",
)

val fakeVerseState = VerseState(
    bookName = "Genesis",
    bookChapter = "Genesis 3",
    content = "Contents of Genesis chapter 3",
    loading = false,
    previousChapter = "Genesis 2",
    nextChapter = "Genesis 4"
)