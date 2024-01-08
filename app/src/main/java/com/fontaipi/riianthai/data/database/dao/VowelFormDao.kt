package com.fontaipi.riianthai.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.fontaipi.riianthai.data.database.entity.VowelFormEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VowelFormDao {
    @Upsert
    suspend fun upsertVowelForm(vowelForm: VowelFormEntity): Long

    @Upsert
    suspend fun upsertVowelForms(vowelForms: List<VowelFormEntity>)

    @Query("UPDATE vowel_form SET count = count + 1 WHERE id = :id")
    suspend fun incrementCount(id: Long)

    @Query("SELECT SUM(CASE WHEN count >= 5 THEN 1.0 ELSE count / 5.0 END) / COUNT(*) FROM vowel_form")
    fun getFlashcardProgress(): Flow<Float>
}