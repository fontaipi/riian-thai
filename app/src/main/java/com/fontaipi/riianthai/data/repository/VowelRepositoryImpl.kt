package com.fontaipi.riianthai.data.repository

import android.app.Application
import com.fontaipi.riianthai.data.database.dao.VowelDao
import com.fontaipi.riianthai.data.database.dao.VowelFormDao
import com.fontaipi.riianthai.data.database.dao.WordDao
import com.fontaipi.riianthai.data.database.entity.asExternalModel
import com.fontaipi.riianthai.model.Vowel
import com.fontaipi.riianthai.model.asEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class VowelRepositoryImpl @Inject constructor(
    private val application: Application,
    private val vowelDao: VowelDao,
    private val wordDao: WordDao,
    private val vowelFormDao: VowelFormDao
) : VowelRepository {
    override fun getVowels(): Flow<List<Vowel>> {
        return vowelDao.getVowels().map { it.map { vowel -> vowel.asExternalModel() } }
    }

    override fun getVowel(id: Long): Flow<Vowel> {
        return vowelDao.getVowel(id).map { it.asExternalModel() }
    }

    override fun getNotLearnedVowels(limit: Int): Flow<List<Vowel>> {
        return vowelDao.getNotLearnedVowel(limit)
            .map { it.map { vowel -> vowel.asExternalModel() } }
    }

    override fun getFlashcardProgress(): Flow<Float> {
        return vowelFormDao.getFlashcardProgress()
    }

    override suspend fun incrementVowelFormCount(id: Long) = withContext(Dispatchers.IO) {
        vowelFormDao.incrementCount(id)
    }

    override suspend fun loadJsonData() = withContext(Dispatchers.IO) {
        val vowelJsonString =
            application.applicationContext.assets.open("vowels.json").readBytes().decodeToString()
        val vowels = Json.decodeFromString<List<Vowel>>(vowelJsonString)

        if (vowelDao.countVowels() == 0) {
            vowels.forEach {
                val vowelId = vowelDao.upsertVowel(it.asEntity())
                it.writingForms.forEach { vowelForm ->
                    val wordId = wordDao.upsertWord(vowelForm.exampleWord.asEntity())
                    vowelFormDao.upsertVowelForm(
                        vowelForm.asEntity(vowelId).copy(wordId = wordId)
                    )
                }
            }
        }
    }
}