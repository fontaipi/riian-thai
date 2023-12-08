package com.fontaipi.riianthai.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fontaipi.riianthai.model.Word

@Entity(tableName = "word")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val thai: String,
    val meaning: String
)

fun WordEntity.asExternalModel(): Word =
    Word(
        id = id,
        thai = thai,
        meaning = meaning
    )