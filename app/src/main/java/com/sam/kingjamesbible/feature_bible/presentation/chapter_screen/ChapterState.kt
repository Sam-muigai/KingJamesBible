package com.sam.kingjamesbible.feature_bible.presentation.chapter_screen

import com.sam.kingjamesbible.feature_bible.domain.model.chapters.ChapterData

data class ChapterState(
    val chapters:List<ChapterData> = emptyList(),
    val bookName:String = "",
    val loading:Boolean = false
)

