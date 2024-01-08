package com.fontaipi.riianthai.model

import com.fontaipi.riianthai.data.database.entity.VowelEntity
import kotlinx.serialization.Serializable

enum class VowelClass {
    Short,
    Long,
    Special
}

enum class SoundType {
    Monophthong,
    Diphthong,
}

@Serializable
data class Vowel(
    val id: Long = 0,
    val vowelClass: VowelClass,
    val soundType: SoundType = SoundType.Monophthong,
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

val sampleVowel = Vowel(
    thaiScript = "อา",
    vowelClass = VowelClass.Long,
    soundType = SoundType.Monophthong,
    audioFile = "",
    soundFile = "",
    writingForms = listOf(
        VowelForm(
            format = "เIา",
            accentIndicator = "   ",
            note = "This is a note, with some content",
            exampleWord = Word(
                thai = "เรา",
                meaning = "We",
                audioFile = "audio/word/we.mp3"
            )
        )
    ),
)