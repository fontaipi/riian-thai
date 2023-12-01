package com.fontaipi.riianthai.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.fontaipi.riianthai.data.database.entity.ConsonantCount
import com.fontaipi.riianthai.data.database.entity.ConsonantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsonantDao {
    @Query("SELECT * FROM consonant")
    fun getConsonants(): Flow<List<ConsonantEntity>>

    @Query("SELECT * FROM consonant WHERE consonant_class = :filter")
    fun getConsonants(filter: String): Flow<List<ConsonantEntity>>

    @Query("SELECT COUNT(*) FROM consonant")
    suspend fun countConsonants(): Int

    @Upsert
    suspend fun upsertConsonants(consonants: List<ConsonantEntity>)

    @Query("UPDATE consonant SET count = count + 1 WHERE id = :id")
    suspend fun incrementCount(id: Long)

    @Query("UPDATE consonant SET count = 0")
    suspend fun resetAllCounts()
}