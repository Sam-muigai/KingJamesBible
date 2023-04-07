package com.sam.kingjamesbible.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.sam.kingjamesbible.feature_bible.core.GsonParser
import com.sam.kingjamesbible.feature_bible.data.local.BibleDatabase
import com.sam.kingjamesbible.feature_bible.data.local.Converters
import com.sam.kingjamesbible.feature_bible.data.remote.BibleApi
import com.sam.kingjamesbible.feature_bible.data.repository.BibleRepositoryImpl
import com.sam.kingjamesbible.feature_bible.domain.repository.BibleRepository
import com.sam.kingjamesbible.feature_bible.domain.use_cases.GetAllBooks
import com.sam.kingjamesbible.feature_bible.domain.use_cases.GetChapters
import com.sam.kingjamesbible.feature_bible.domain.use_cases.GetVerse
import com.sam.kingjamesbible.feature_bible.domain.use_cases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideBibleDatabase(@ApplicationContext context: Context): BibleDatabase =
        Room.databaseBuilder(context, BibleDatabase::class.java, "bible_db")
            .fallbackToDestructiveMigration()
            //.addTypeConverter(Converters(GsonParser(Gson())))
            .build()

    @Provides
    @Singleton
    fun provideBibleRepository(
        api: BibleApi,
        db: BibleDatabase
    ): BibleRepository {
        return BibleRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideUseCase(
        repository: BibleRepository
    ): UseCases {
        return UseCases(
            getAllBooks = GetAllBooks(repository),
            getChapters = GetChapters(repository),
            getVerse = GetVerse(repository)
        )
    }

}