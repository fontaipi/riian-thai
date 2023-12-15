package com.fontaipi.riianthai.data.di

import android.content.Context
import androidx.room.Room
import com.fontaipi.riianthai.data.database.RiianThaiDatabase
import com.fontaipi.riianthai.data.database.dao.ConsonantDao
import com.fontaipi.riianthai.data.database.dao.VowelDao
import com.fontaipi.riianthai.data.database.dao.VowelFormDao
import com.fontaipi.riianthai.data.database.dao.WordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun providesExpenseTrackerDatabase(
        @ApplicationContext context: Context,
    ): RiianThaiDatabase = Room.databaseBuilder(
        context,
        RiianThaiDatabase::class.java,
        "expense-tracker-database",
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun providesConsonantDao(
        database: RiianThaiDatabase,
    ): ConsonantDao = database.consonantDao()

    @Provides
    fun providesVowelDao(
        database: RiianThaiDatabase,
    ): VowelDao = database.vowelDao()

    @Provides
    fun providesVowelFormDao(
        database: RiianThaiDatabase,
    ): VowelFormDao = database.vowelFormDao()

    @Provides
    fun providesWordDao(
        database: RiianThaiDatabase,
    ): WordDao = database.wordDao()
}