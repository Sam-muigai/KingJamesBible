package com.sam.kingjamesbible.feature_bible.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sam.kingjamesbible.feature_bible.data.local.books.BookData
import com.sam.kingjamesbible.feature_bible.data.local.chapters.ChapterDataLocal
import com.sam.kingjamesbible.feature_bible.data.local.verse.VersesLocal


@Dao
interface BibleDao {

    @Query("SELECT * FROM bookdata")
    suspend fun getAllBooks():List<BookData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(bookData: List<BookData>)

    @Delete
    suspend fun deleteBook(bookData: List<BookData>)

    @Query("SELECT * FROM chapterdatalocal WHERE bookId=:bookId")
    suspend fun getChapters(bookId:String): List<ChapterDataLocal>

    @Delete
    suspend fun deleteChapters(chapters:List<ChapterDataLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<ChapterDataLocal>)

    @Query("SELECT * FROM verseslocal WHERE id=:id")
    suspend fun getVerses(id:String):VersesLocal

    @Delete
    suspend fun deleteVerses(versesLocal: VersesLocal)

    @Insert
    suspend fun insertVerses(versesLocal: VersesLocal)

}