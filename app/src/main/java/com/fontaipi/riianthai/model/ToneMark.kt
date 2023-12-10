package com.fontaipi.riianthai.model

import kotlinx.serialization.Serializable

@Serializable
data class ToneMark(
    val id: Long = 0,
    val thai : String,
    val phonetic: String,
    val symbol: String
)