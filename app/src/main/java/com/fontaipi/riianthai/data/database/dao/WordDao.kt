package com.fontaipi.riianthai.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.fontaipi.riianthai.data.database.entity.WordEntity

@Dao
interface WordDao {
    @Query("SELECT COUNT(*) FROM word")
    suspend fun countWords(): Int

    @Insert
    suspend fun insertWord(word: WordEntity): Long

    @Update
    suspend fun updateWord(word: WordEntity)

    @Upsert
    suspend fun upsertWord(word: WordEntity): Long

    @Upsert
    suspend fun upsertWords(words: List<WordEntity>): List<Long>

    @Delete
    suspend fun deleteWord(word: WordEntity)
}