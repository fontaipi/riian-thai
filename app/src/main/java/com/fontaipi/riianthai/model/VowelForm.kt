package com.fontaipi.riianthai.model

import com.fontaipi.riianthai.data.database.entity.VowelFormEntity
import kotlinx.serialization.Serializable

@Serializable
data class VowelForm(
    val id: Long = 0,
    val format: String,
    val accentIndicator: String,
    val exampleWord: Word,
    val note: String
)

fun VowelForm.asEntity(vowelId: Long): VowelFormEntity =
    VowelFormEntity(
        id = id,
        format = format,
        accentIndicator = accentIndicator,
        vowelId = vowelId,
        wordId = exampleWord.id,
        note = note
    )