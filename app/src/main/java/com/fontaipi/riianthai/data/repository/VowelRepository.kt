package com.fontaipi.riianthai.data.repository

import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.model.Vowel
import kotlinx.coroutines.flow.Flow

interface VowelRepository {
    fun getVowels(): Flow<List<Vowel>>
    fun getVowel(id: Long): Flow<Vowel>
    fun getNotLearnedVowels(limit: Int = 20): Flow<List<Vowel>>
    fun getFlashcardProgress(): Flow<Float>
    suspend fun incrementVowelFormCount(id: Long)
    suspend fun loadJsonData()
}