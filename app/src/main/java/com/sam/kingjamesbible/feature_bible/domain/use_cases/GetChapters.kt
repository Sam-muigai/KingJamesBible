package com.sam.kingjamesbible.feature_bible.domain.use_cases

import com.sam.kingjamesbible.feature_bible.core.DataState
import com.sam.kingjamesbible.feature_bible.data.local.chapters.ChapterDataLocal
import com.sam.kingjamesbible.feature_bible.domain.repository.BibleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChapters @Inject constructor(
    private val repository: BibleRepository
) {
    operator fun invoke(bookId:String):Flow<DataState<List<ChapterDataLocal>>>{
        return repository.getChapter(bookId)
    }
}