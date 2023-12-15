package com.fontaipi.riianthai.model

import com.fontaipi.riianthai.data.database.entity.WordEntity
import kotlinx.serialization.Serializable

@Serializable
data class Word(
    val id: Long = 0,
    val thai: String,
    val meaning: String,
    val audioFile: String = "",
)

fun Word.asEntity(): WordEntity =
    WordEntity(
        id = id,
        thai = thai,
        meaning = meaning,
        audioFile = audioFile
    )