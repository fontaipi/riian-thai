package com.fontaipi.riianthai.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fontaipi.riianthai.model.VowelClass

@Entity(tableName = "vowel")
data class VowelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val vowelClass: VowelClass,
    val thaiScript: String,
    val audioFile: String,
    val soundFile: String,
    val note: String
)