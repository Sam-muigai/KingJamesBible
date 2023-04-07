package com.sam.kingjamesbible.feature_bible.domain.repository

import com.sam.kingjamesbible.feature_bible.core.DataState
import com.sam.kingjamesbible.feature_bible.data.local.books.BookData
import com.sam.kingjamesbible.feature_bible.data.local.chapters.ChapterDataLocal
import com.sam.kingjamesbible.feature_bible.data.local.verse.VersesLocal
import kotlinx.coroutines.flow.Flow

interface BibleRepository{

    fun getAllBooks():Flow<DataState<List<BookData>>>

    fun getChapter(bookId:String):Flow<DataState<List<ChapterDataLocal>>>

    fun getVerses(chapterId:String):Flow<DataState<VersesLocal>>

}