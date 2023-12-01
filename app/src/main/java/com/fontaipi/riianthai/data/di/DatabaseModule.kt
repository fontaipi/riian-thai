package com.fontaipi.riianthai.data.di

import android.content.Context
import androidx.room.Room
import com.fontaipi.riianthai.data.database.RiianThaiDatabase
import com.fontaipi.riianthai.data.database.dao.ConsonantDao
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
    ).build()

    @Provides
    fun providesConsonantDao(
        database: RiianThaiDatabase,
    ): ConsonantDao = database.consonantDao()
}