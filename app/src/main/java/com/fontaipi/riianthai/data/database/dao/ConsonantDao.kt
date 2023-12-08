package com.fontaipi.riianthai.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.fontaipi.riianthai.data.database.entity.ConsonantEntity
import com.fontaipi.riianthai.data.database.entity.ConsonantWordsCrossRef
import com.fontaipi.riianthai.data.database.entity.PopulatedConsonant
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsonantDao {
    @Transaction
    @Query("SELECT * FROM consonant")
    fun getConsonants(): Flow<List<PopulatedConsonant>>

    @Transaction
    @Query("SELECT * FROM consonant WHERE consonant_class = :filter")
    fun getConsonants(filter: String): Flow<List<PopulatedConsonant>>

    @Query("SELECT * FROM consonant WHERE id = :id")
    fun getConsonantById(id: Long): Flow<PopulatedConsonant>

    @Query("SELECT COUNT(*) FROM consonant")
    suspend fun countConsonants(): Int

    @Upsert
    suspend fun upsertConsonants(consonants: List<ConsonantEntity>)

    @Query("UPDATE consonant SET count = count + 1 WHERE id = :id")
    suspend fun incrementCount(id: Long)

    @Query("UPDATE consonant SET count = 0")
    suspend fun resetAllCounts()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreWordCrossRefEntities(
        consonantWordsCrossReferences: List<ConsonantWordsCrossRef>,
    )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreWordCrossRefEntity(
        consonantWordsCrossReference: ConsonantWordsCrossRef,
    )

    @Query("DELETE FROM consonant_words WHERE word_id = :wordId")
    suspend fun deleteWordCrossRefEntitiesByWordId(wordId: Long)
}