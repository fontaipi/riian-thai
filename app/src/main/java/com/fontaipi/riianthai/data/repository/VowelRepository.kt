package com.fontaipi.riianthai.data.repository

import com.fontaipi.riianthai.model.Vowel
import kotlinx.coroutines.flow.Flow

interface VowelRepository {
    fun getVowels(): Flow<List<Vowel>>
    fun getVowel(id: Long): Flow<Vowel>

}