package com.sam.kingjamesbible.feature_bible.data.repository

import com.sam.kingjamesbible.feature_bible.core.DataState
import com.sam.kingjamesbible.feature_bible.data.local.BibleDao
import com.sam.kingjamesbible.feature_bible.data.local.books.BookData
import com.sam.kingjamesbible.feature_bible.data.local.chapters.ChapterDataLocal
import com.sam.kingjamesbible.feature_bible.data.local.verse.VersesLocal
import com.sam.kingjamesbible.feature_bible.data.remote.BibleApi
import com.sam.kingjamesbible.feature_bible.domain.repository.BibleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject


class BibleRepositoryImpl @Inject constructor(
    private val api: BibleApi,
    private val dao: BibleDao
) : BibleRepository {
    override fun getAllBooks(): Flow<DataState<List<BookData>>> {
        return flow {
            emit(DataState.Loading())
            try {
                val booksDto = api.getAllBooks().data
                dao.deleteBook(booksDto.map { it.toBookData() })
                dao.insertBooks(booksDto.map { it.toBookData() })
                val booksLocal = dao.getAllBooks()
                emit(DataState.Success(booksLocal))
            } catch (e: IOException) {
                val booksLocal = dao.getAllBooks()
                emit(
                    DataState.Error(
                        data = booksLocal,
                        message = "No internet.Please check your connection."
                    )
                )
            } catch (e: Exception) {
                val booksLocal = dao.getAllBooks()
                emit(
                    DataState.Error(
                        data = booksLocal,
                        message = "Unknown error occurred"
                    )
                )
            }
        }
    }

    override fun getChapter(bookId: String): Flow<DataState<List<ChapterDataLocal>>> =
        flow {
            emit(DataState.Loading())
            try {
                val chapterRemote = api.getChapters(bookId = bookId).data
                val chapterDataLocal = chapterRemote.map { it.toChapterDataLocal() }
                dao.deleteChapters(chapterDataLocal)
                dao.insertChapters(chapterDataLocal)
                val chapters = dao.getChapters(bookId)
                // TODO: Refactor:Added a delay to allow completion of insertion before emitting Success
                delay(2000)
                emit(
                    DataState.Success(
                        data = chapters
                    )
                )
            } catch (e: IOException) {
                val chapters = dao.getChapters(bookId)
                emit(
                    DataState.Error(
                        data = chapters,
                        message = "No internet.Please check your connection."
                    )
                )
            } catch (e: Exception) {
                val chapters = dao.getChapters(bookId)
                emit(
                    DataState.Error(
                        data = chapters,
                        message = "Unknown error occurred"
                    )
                )
            }
        }

    override fun getVerses(chapterId: String): Flow<DataState<VersesLocal>> {
        return flow {
            emit(DataState.Loading())
            try {
                val verses = api.getVerses(chapterId = chapterId).data
                withContext(Dispatchers.IO){
                    dao.deleteVerses(verses.toVerseLocal())
                    dao.insertVerses(verses.toVerseLocal())
                }
                val versesLocal = dao.getVerses(chapterId)
                delay(2000)
                emit(
                    DataState.Success(
                        data = versesLocal
                    )
                )
            }catch (e:Exception){
                val versesLocal = dao.getVerses(chapterId)
                emit(
                    DataState.Error(
                        data = versesLocal,
                        message = "Unknown error occurred."
                    )
                )
            }
        }
    }
}