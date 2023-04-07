package com.sam.kingjamesbible.feature_bible.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.sam.kingjamesbible.feature_bible.core.JsonParser
import com.sam.kingjamesbible.feature_bible.data.local.chapters.ChapterDataLocal

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromJson(json: String): List<ChapterDataLocal> {
        return jsonParser.fromJson<ArrayList<ChapterDataLocal>>(
            json,
            object : TypeToken<ArrayList<ChapterDataLocal>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toJson(chapterDataLocal: List<ChapterDataLocal>): String {
        return jsonParser.toJson(
            chapterDataLocal,
            object : TypeToken<ArrayList<ChapterDataLocal>>() {}.type
        ) ?: "[]"
    }
}