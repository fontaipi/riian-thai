package com.fontaipi.riianthai.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.model.EndingSound

@Entity(tableName = "consonant")
data class ConsonantEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val thai: String,
    val phonetic: String,
    val audio: String,
    val picture: String = "",
    @ColumnInfo(name = "consonant_class")
    val consonantClass: ConsonantClass,
    @ColumnInfo(name = "ending_sound")
    val endingSound: EndingSound,
    @ColumnInfo(name = "associated_word")
    val associatedWord: String,
    val meaning: String,
    val count: Int,
)