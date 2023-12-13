package com.fontaipi.riianthai.data.database.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.fontaipi.riianthai.data.database.entity.VowelFormEntity

@Dao
interface VowelFormDao {

    @Upsert
    suspend fun upsertVowelForms(vowelForms: List<VowelFormEntity>)
}