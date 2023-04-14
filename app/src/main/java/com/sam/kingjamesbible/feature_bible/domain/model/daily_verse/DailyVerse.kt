package com.sam.kingjamesbible.feature_bible.domain.model.daily_verse

import androidx.room.Entity
import androidx.room.PrimaryKey


data class DailyVerse(
    val reference: String = "",
    val text: String = "",
    val verseurl: String = "",
    val version: String = ""
)