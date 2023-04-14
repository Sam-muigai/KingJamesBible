package com.sam.kingjamesbible.feature_bible.presentation.home_screen

import com.sam.kingjamesbible.feature_bible.domain.model.daily_verse.DailyVerse

data class HomeScreenState(
    val verse:DailyVerse = DailyVerse(),
    val loading:Boolean = false
)