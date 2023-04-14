package com.sam.kingjamesbible.feature_bible.data.local.daily_verse

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sam.kingjamesbible.feature_bible.domain.model.daily_verse.DailyVerse

@Entity
data class DailyVerseLocal(
    @PrimaryKey(autoGenerate = false)
    val id:Int = 1,
    val reference: String,
    val text: String,
    val verseurl: String,
    val version: String
){
    fun toDailyVerse():DailyVerse{
        return DailyVerse(
            reference = reference,
            text = text,
            verseurl = verseurl,
            version = version
        )
    }
}