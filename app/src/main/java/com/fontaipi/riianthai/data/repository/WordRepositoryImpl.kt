package com.fontaipi.riianthai.data.repository

import com.fontaipi.riianthai.data.database.dao.ConsonantDao
import com.fontaipi.riianthai.data.database.dao.WordDao
import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.model.Word
import com.fontaipi.riianthai.model.asEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordDao: WordDao,
    private val consonantDao: ConsonantDao
) : WordRepository {
    override suspend fun upsertWord(word: Word) = withContext(Dispatchers.IO) {
        wordDao.upsertWord(word.asEntity())
    }

    override suspend fun deleteWord(word: Word) = withContext(Dispatchers.IO) {
        consonantDao.deleteWordCrossRefEntitiesByWordId(word.id)
        wordDao.deleteWord(word.asEntity())
    }
}