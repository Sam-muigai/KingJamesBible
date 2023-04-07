package com.sam.kingjamesbible.feature_bible.data.remote

import com.sam.kingjamesbible.feature_bible.data.remote.model.books.BooksDto
import com.sam.kingjamesbible.feature_bible.data.remote.model.chapters.ChapterDto
import com.sam.kingjamesbible.feature_bible.data.remote.model.verse.VerseDataDto
import com.sam.kingjamesbible.feature_bible.data.remote.model.verse.VerseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface BibleApi {

    @GET("/v1/bibles/{bibleId}/books")
    suspend fun getAllBooks(
        @Path("bibleId") bibleId: String = KING_JAMES
    ): BooksDto

    @GET("/v1/bibles/{bibleId}/books/{bookId}/chapters")
    suspend fun getChapters(
        @Path("bibleId") bibleId: String = KING_JAMES,
        @Path("bookId") bookId:String
    ):ChapterDto

    @GET("v1/bibles/{bibleId}/chapters/{chapterId}")
    suspend fun getVerses(
        @Path("bibleId") bibleId: String = KING_JAMES,
        @Path("chapterId") chapterId: String,
    ): VerseDto

    companion object {
        const val KING_JAMES = "de4e12af7f28f599-01"
        const val BASE_URL = "https://api.scripture.api.bible"
    }

}