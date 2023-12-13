package com.fontaipi.riianthai.model

import com.fontaipi.riianthai.data.database.entity.VowelEntity
import kotlinx.serialization.Serializable

enum class VowelClass {
    Short,
    Long,
    Special
}

@Serializable
data class Vowel(
    val id: Long = 0,
    val vowelClass: VowelClass,
    val thaiScript: String,
    val audioFile: String,
    val soundFile: String,
    val writingForms: List<VowelForm>,
    val note: String = ""
)

fun Vowel.asEntity(): VowelEntity =
    VowelEntity(
        id = id,
        thaiScript = thaiScript,
        vowelClass = vowelClass,
        audioFile = audioFile,
        soundFile = soundFile,
        note = note
    )