package com.sam.kingjamesbible.feature_bible.data.local.books

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sam.kingjamesbible.feature_bible.domain.model.books.Data

@Entity
data class BookData(
    val abbreviation: String,
    val bibleId: String,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val nameLong: String
){
    fun toData(): Data {
        return Data(
            abbreviation = abbreviation,
            bibleId = bibleId,
            id = id,
            name = name,
            nameLong = nameLong
        )
    }
}