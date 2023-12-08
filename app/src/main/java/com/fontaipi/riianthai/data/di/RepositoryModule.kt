package com.fontaipi.riianthai.data.di

import com.fontaipi.riianthai.data.repository.ConsonantRepository
import com.fontaipi.riianthai.data.repository.ConsonantRepositoryImpl
import com.fontaipi.riianthai.data.repository.WordRepository
import com.fontaipi.riianthai.data.repository.WordRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsConsonantRepository(
        consonantRepository: ConsonantRepositoryImpl,
    ): ConsonantRepository

    @Binds
    fun bindsWordRepository(
        wordRepository: WordRepositoryImpl,
    ): WordRepository
}