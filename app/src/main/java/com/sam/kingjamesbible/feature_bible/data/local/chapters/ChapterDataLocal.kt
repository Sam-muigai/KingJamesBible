package com.sam.kingjamesbible.feature_bible.data.local.chapters

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sam.kingjamesbible.feature_bible.domain.model.chapters.ChapterData

@Entity
data class ChapterDataLocal(
    val bibleId: String,
    val bookId: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val number: String,
    val reference: String
){
    fun toChapterData():ChapterData{
        return ChapterData(
            bibleId = bibleId,
            bookId = bookId,
            id = id,
            number = number,
            reference = reference
        )
    }
}