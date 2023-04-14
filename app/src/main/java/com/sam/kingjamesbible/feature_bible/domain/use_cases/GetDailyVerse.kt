package com.sam.kingjamesbible.feature_bible.domain.use_cases

import com.sam.kingjamesbible.feature_bible.core.DataState
import com.sam.kingjamesbible.feature_bible.data.local.daily_verse.DailyVerseLocal
import com.sam.kingjamesbible.feature_bible.domain.repository.BibleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDailyVerse @Inject constructor(
    private val repository: BibleRepository
) {
    operator fun invoke():Flow<DataState<DailyVerseLocal>>{
        return repository.getDailyVerse()
    }
}