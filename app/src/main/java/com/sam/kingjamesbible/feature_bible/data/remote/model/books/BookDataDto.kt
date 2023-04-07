package com.sam.kingjamesbible.feature_bible.data.remote.model.books

import com.sam.kingjamesbible.feature_bible.data.local.books.BookData

data class BookDataDto(
    val abbreviation: String,
    val bibleId: String,
    val id: String,
    val name: String,
    val nameLong: String
){
    fun toBookData():BookData{
        return BookData(
            abbreviation = abbreviation,
            bibleId = bibleId,
            id = id,
            name = name,
            nameLong = nameLong
        )
    }
}