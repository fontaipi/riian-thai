package com.fontaipi.riianthai.data.repository

import android.app.Application
import com.fontaipi.riianthai.data.database.dao.ConsonantDao
import com.fontaipi.riianthai.data.database.dao.WordDao
import com.fontaipi.riianthai.data.database.entity.ConsonantWordsCrossRef
import com.fontaipi.riianthai.data.database.entity.asExternalModel
import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.model.asEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ConsonantRepositoryImpl @Inject constructor(
    private val application: Application,
    private val consonantDao: ConsonantDao,
    private val wordDao: WordDao
) : ConsonantRepository {
    override fun getConsonants(filter: ConsonantClass?): Flow<List<Consonant>> {
        return if (filter == null) {
            consonantDao.getConsonants()
                .map { it.map { consonant -> consonant.asExternalModel() } }
        } else {
            consonantDao.getConsonants(filter.name)
                .map { it.map { consonant -> consonant.asExternalModel() } }
        }
    }

    override fun getConsonantById(id: Long): Flow<Consonant> {
        return consonantDao.getConsonantById(id)
            .map { it.asExternalModel() }
    }

    override fun getNotLearnedConsonants(limit: Int): Flow<List<Consonant>> {
        return consonantDao.getNotLearnedConsonant(limit)
            .map { it.map { consonant -> consonant.asExternalModel() } }
    }

    override fun getFlashcardProgress(): Flow<Float> {
        return consonantDao.getFlashcardProgress()
    }

    override suspend fun incrementCount(id: Long) = withContext(Dispatchers.IO) {
        consonantDao.incrementCount(id)
    }

    override suspend fun resetAllCounts() = withContext(Dispatchers.IO) {
        consonantDao.resetAllCounts()
    }

    override suspend fun loadJsonData() = withContext(Dispatchers.IO) {
        val consonantJsonString = application.applicationContext.assets.open("consonants.json").readBytes().decodeToString()
        val consonants = Json.decodeFromString<List<Consonant>>(consonantJsonString)

        if (consonantDao.countConsonants() == 0) {
            consonants.forEach { consonant ->
                val consonantId = consonantDao.upsertConsonant(consonant.asEntity())
                consonant.exampleWords.forEach {
                    val wordId = wordDao.upsertWord(it.asEntity())
                    consonantDao.insertOrIgnoreWordCrossRefEntity(
                        ConsonantWordsCrossRef(
                            consonantId = consonantId,
                            wordId = wordId
                        )
                    )
                }
            }
        }
    }
}