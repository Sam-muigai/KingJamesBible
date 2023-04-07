package com.sam.kingjamesbible.feature_bible.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sam.kingjamesbible.feature_bible.data.local.books.BookData
import com.sam.kingjamesbible.feature_bible.data.local.chapters.ChapterDataLocal
import com.sam.kingjamesbible.feature_bible.data.local.verse.VersesLocal


@Database(
    entities = [BookData::class, ChapterDataLocal::class, VersesLocal::class],
    version = 5,
    exportSchema = false
)
abstract class BibleDatabase:RoomDatabase(){
    abstract val dao:BibleDao
}