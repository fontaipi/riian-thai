package com.fontaipi.riianthai.data.repository

import com.fontaipi.riianthai.data.database.dao.VowelDao
import com.fontaipi.riianthai.data.database.entity.asExternalModel
import com.fontaipi.riianthai.model.Vowel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VowelRepositoryImpl @Inject constructor(
    private val vowelDao: VowelDao
) : VowelRepository {
    override fun getVowels(): Flow<List<Vowel>> {
        return vowelDao.getVowels().map { it.map { vowel -> vowel.asExternalModel() } }
    }

    override fun getVowel(id: Long): Flow<Vowel> {
        return vowelDao.getVowel(id).map { it.asExternalModel() }
    }
}