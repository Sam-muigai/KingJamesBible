package com.sam.kingjamesbible.feature_bible.data.remote.model.chapters

import com.sam.kingjamesbible.feature_bible.data.local.chapters.ChapterDataLocal

data class ChapterDataDto(
    val bibleId: String,
    val bookId: String,
    val id: String,
    val number: String,
    val reference: String
) {
    fun toChapterDataLocal(): ChapterDataLocal {
        return ChapterDataLocal(
            bibleId = bibleId,
            bookId = bookId,
            id = id,
            number = number,
            reference = reference
        )
    }
}