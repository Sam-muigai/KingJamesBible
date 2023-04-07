package com.sam.kingjamesbible.feature_bible.domain.use_cases

import com.sam.kingjamesbible.feature_bible.core.DataState
import com.sam.kingjamesbible.feature_bible.data.local.books.BookData
import com.sam.kingjamesbible.feature_bible.domain.model.books.Books
import com.sam.kingjamesbible.feature_bible.domain.model.books.Data
import com.sam.kingjamesbible.feature_bible.domain.repository.BibleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBooks @Inject constructor(
    private val repository: BibleRepository
){
    operator fun invoke():Flow<DataState<List<BookData>>>{
        return repository.getAllBooks()
    }
}