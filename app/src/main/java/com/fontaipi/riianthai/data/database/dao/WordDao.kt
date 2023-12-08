package com.fontaipi.riianthai.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.fontaipi.riianthai.data.database.entity.ConsonantEntity
import com.fontaipi.riianthai.data.database.entity.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT COUNT(*) FROM word")
    suspend fun countWords(): Int

    @Upsert
    suspend fun upsertWords(words: List<WordEntity>)
}