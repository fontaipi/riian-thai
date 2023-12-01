package com.fontaipi.riianthai.data.repository

import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.model.ConsonantClass
import kotlinx.coroutines.flow.Flow

interface ConsonantRepository {
    fun getConsonants(filter: ConsonantClass): Flow<List<Consonant>>
    suspend fun incrementCount(id: Long)
    suspend fun resetAllCounts()
}