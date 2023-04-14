package com.sam.kingjamesbible.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.sam.kingjamesbible.feature_bible.core.GsonParser
import com.sam.kingjamesbible.feature_bible.data.local.BibleDatabase
import com.sam.kingjamesbible.feature_bible.data.local.Converters
import com.sam.kingjamesbible.feature_bible.data.remote.BibleApi
import com.sam.kingjamesbible.feature_bible.data.remote.DailyVerseApi
import com.sam.kingjamesbible.feature_bible.data.repository.BibleRepositoryImpl
import com.sam.kingjamesbible.feature_bible.domain.repository.BibleRepository
import com.sam.kingjamesbible.feature_bible.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private fun okHttpClientFactory(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor { chain ->
                chain.request().newBuilder()
                    .addHeader("api-key", "e84c248f4da0154438aac812b2ea0688")
                    .build()
                    .let(chain::proceed)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideBibleApi(): BibleApi {
        return Retrofit
            .Builder()
            .baseUrl(BibleApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientFactory())
            .build()
            .create(BibleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDailyVerseApi(): DailyVerseApi {
        return Retrofit
            .Builder()
            .baseUrl(DailyVerseApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(DailyVerseApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBibleDatabase(@ApplicationContext context: Context): BibleDatabase =
        Room.databaseBuilder(
            context,
            BibleDatabase::class.java,
            "bible_db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideBibleRepository(
        api: BibleApi,
        dailyVerseApi: DailyVerseApi,
        db: BibleDatabase
    ): BibleRepository {
        return BibleRepositoryImpl(
            api,
            dailyVerseApi,
            db.dao
        )
    }

    @Provides
    @Singleton
    fun provideUseCase(
        repository: BibleRepository
    ): UseCases {
        return UseCases(
            getAllBooks = GetAllBooks(repository),
            getChapters = GetChapters(repository),
            getVerse = GetVerse(repository),
            getDailyVerse = GetDailyVerse(repository)
        )
    }

}