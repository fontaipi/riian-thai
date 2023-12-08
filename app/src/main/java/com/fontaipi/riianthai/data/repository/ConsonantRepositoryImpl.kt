package com.fontaipi.riianthai.data.repository

import com.fontaipi.riianthai.data.database.dao.ConsonantDao
import com.fontaipi.riianthai.data.database.entity.asExternalModel
import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.model.ConsonantClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ConsonantRepositoryImpl @Inject constructor(
    private val consonantDao: ConsonantDao
) : ConsonantRepository {
    override fun getConsonants(filter: ConsonantClass): Flow<List<Consonant>> {
        return if (filter == ConsonantClass.All) {
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

    override suspend fun incrementCount(id: Long) = withContext(Dispatchers.IO) {
        consonantDao.incrementCount(id)
    }

    override suspend fun resetAllCounts() = withContext(Dispatchers.IO) {
        consonantDao.resetAllCounts()
    }
}