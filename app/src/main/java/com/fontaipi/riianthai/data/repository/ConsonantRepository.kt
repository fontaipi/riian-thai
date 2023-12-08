package com.fontaipi.riianthai.data.repository

import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.model.ConsonantClass
import kotlinx.coroutines.flow.Flow

interface ConsonantRepository {
    fun getConsonants(filter: ConsonantClass? = null): Flow<List<Consonant>>
    fun getConsonantById(id: Long): Flow<Consonant>
    suspend fun incrementCount(id: Long)
    suspend fun resetAllCounts()
}