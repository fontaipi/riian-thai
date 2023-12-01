package com.fontaipi.riianthai.model

import com.fontaipi.riianthai.data.database.entity.ConsonantEntity
import kotlinx.serialization.Serializable

enum class VowelClass {
    Short,
    Long,
    All
}

@Serializable
data class Vowel(
    val id: Long = 0,
    val thai: String,
    val count: Int = 0,
)


