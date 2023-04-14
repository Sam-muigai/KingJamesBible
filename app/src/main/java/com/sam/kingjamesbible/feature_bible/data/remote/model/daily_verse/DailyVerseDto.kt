package com.sam.kingjamesbible.feature_bible.data.remote.model.daily_verse

import com.sam.kingjamesbible.feature_bible.data.local.daily_verse.DailyVerseLocal

data class DailyVerseDto(
    val verse: Verse
){
    fun toDailyVerseLocal():DailyVerseLocal{
        return DailyVerseLocal(
            reference = verse.details.reference,
            text = verse.details.text,
            verseurl = verse.details.verseurl,
            version = verse.details.version
        )
    }
}