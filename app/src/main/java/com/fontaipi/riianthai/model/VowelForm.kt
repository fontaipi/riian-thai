package com.fontaipi.riianthai.model

import com.fontaipi.riianthai.data.database.entity.VowelFormEntity
import kotlinx.serialization.Serializable

@Serializable
data class VowelForm(
    val id: Long,
    val format: String,
    val accentIndicator: String,
    val note: String
)

fun VowelForm.asEntity(vowelId: Long): VowelFormEntity =
    VowelFormEntity(
        id = id,
        format = format,
        accentIndicator = accentIndicator,
        vowelId = vowelId,
        note = note
    )