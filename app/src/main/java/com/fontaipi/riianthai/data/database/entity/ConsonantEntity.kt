package com.fontaipi.riianthai.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.model.ConsonantClass

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
    @ColumnInfo(name = "associated_word")
    val associatedWord: String,
    val meaning: String,
    val count: Int,
)

data class ConsonantCount(
    val id: Long,
    val count: Int,
)

fun ConsonantEntity.asExternalModel(): Consonant =
    Consonant(
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