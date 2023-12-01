package com.fontaipi.riianthai.model

import com.fontaipi.riianthai.data.database.entity.ConsonantEntity
import kotlinx.serialization.Serializable

enum class ConsonantClass {
    Low,
    Mid,
    High,
    All
}

@Serializable
data class Consonant(
    val id: Long = 0,
    val thai: String,
    val phonetic: String,
    val audio: String,
    val picture: String = "",
    val consonantClass: ConsonantClass,
    val associatedWord: String,
    val meaning: String,
    val count: Int = 0,
)

fun Consonant.asEntity(): ConsonantEntity =
    ConsonantEntity(
        id = id,
        thai = thai,
        phonetic = phonetic,
        audio = audio,
        picture = picture,
        consonantClass = consonantClass,
        associatedWord = associatedWord,
        meaning = meaning,
        count = count
    )


