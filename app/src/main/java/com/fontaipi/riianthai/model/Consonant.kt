package com.fontaipi.riianthai.model

import com.fontaipi.riianthai.data.database.entity.ConsonantEntity
import kotlinx.serialization.Serializable

enum class ConsonantClass {
    Low,
    Mid,
    High,
    All
}

enum class EndingSound(val endingClass: EndingClass, val phonetic: String) {
    N(EndingClass.Live, "-n"),
    Ng(EndingClass.Live, "-ng"),
    M(EndingClass.Live, "-m"),
    Y(EndingClass.Live, "-y"),
    W(EndingClass.Live, "-w"),
    P(EndingClass.Dead, "-p"),
    T(EndingClass.Dead, "-t"),
    K(EndingClass.Dead, "-k"),
    Impossible(EndingClass.None, ""),
}

enum class EndingClass {
    Live,
    Dead,
    None
}

@Serializable
data class Consonant(
    val id: Long = 0,
    val thai: String,
    val phonetic: String,
    val audio: String,
    val picture: String = "",
    val consonantClass: ConsonantClass,
    val endingSound: EndingSound,
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
        endingSound = endingSound,
        associatedWord = associatedWord,
        meaning = meaning,
        count = count
    )


