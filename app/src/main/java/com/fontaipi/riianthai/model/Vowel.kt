package com.fontaipi.riianthai.model

import kotlinx.serialization.Serializable

enum class VowelClass {
    Short,
    Long,
    Any
}

@Serializable
data class Vowel(
    val id: Long = 0,
    val thai: String,
    val count: Int = 0,
)


