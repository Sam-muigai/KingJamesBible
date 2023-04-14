package com.sam.kingjamesbible.feature_bible.data.remote

import com.sam.kingjamesbible.feature_bible.data.remote.model.daily_verse.DailyVerseDto
import retrofit2.http.GET


interface DailyVerseApi {

    @GET("/api/v1/get?format=json&order=daily")
    suspend fun getDailyVerse(): DailyVerseDto


    companion object {
        const val BASE_URL = "https://beta.ourmanna.com"
    }

}