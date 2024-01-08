package com.fontaipi.riianthai.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.fontaipi.riianthai.data.database.entity.PopulatedVowel
import com.fontaipi.riianthai.data.database.entity.VowelEntity
import com.fontaipi.riianthai.data.database.entity.VowelFormEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VowelDao {
    @Transaction
    @Query("SELECT * FROM vowel")
    fun getVowels(): Flow<List<PopulatedVowel>>

    @Transaction
    @Query("SELECT * FROM vowel WHERE id = :id")
    fun getVowel(id: Long): Flow<PopulatedVowel>

    @Transaction
    @Query("SELECT * FROM vowel ORDER BY RANDOM() LIMIT :limit")
    fun getNotLearnedVowel(limit: Int): Flow<List<PopulatedVowel>>

    @Upsert
    suspend fun upsertVowel(vowel: VowelEntity): Long

    @Upsert
    suspend fun upsertVowels(vowels: List<VowelEntity>)

    @Query("SELECT COUNT(*) FROM vowel")
    suspend fun countVowels(): Int
}