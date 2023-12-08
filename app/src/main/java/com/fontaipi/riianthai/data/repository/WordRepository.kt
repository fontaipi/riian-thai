package com.fontaipi.riianthai.data.repository

import com.fontaipi.riianthai.model.Word

interface WordRepository {
    suspend fun insertWordForConsonant(word: Word, consonantId: Long)
    suspend fun updateWord(word: Word)
    suspend fun deleteWord(word: Word)
}