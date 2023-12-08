package com.fontaipi.riianthai.data.repository

import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    suspend fun upsertWord(word: Word)
    suspend fun deleteWord(word: Word)
}