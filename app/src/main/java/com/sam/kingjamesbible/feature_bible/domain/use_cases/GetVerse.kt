package com.sam.kingjamesbible.feature_bible.domain.use_cases


import com.sam.kingjamesbible.feature_bible.core.DataState
import com.sam.kingjamesbible.feature_bible.data.local.verse.VersesLocal
import com.sam.kingjamesbible.feature_bible.domain.repository.BibleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVerse @Inject constructor(
    private val repository: BibleRepository
) {
    operator fun invoke(chapterId: String): Flow<DataState<VersesLocal>> {
        return repository.getVerses(chapterId)
    }
}