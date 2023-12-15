package com.fontaipi.riianthai.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "vowel_form",
    foreignKeys = [
        ForeignKey(
            entity = VowelEntity::class,
            parentColumns = ["id"],
            childColumns = ["vowel_id"],
        ),
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["id"],
            childColumns = ["word_id"],
        )
    ]
)
data class VowelFormEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val format: String,
    @ColumnInfo(name = "accent_indicator")
    val accentIndicator: String,
    @ColumnInfo(name = "vowel_id")
    val vowelId: Long,
    @ColumnInfo(name = "word_id")
    val wordId: Long,
    val note: String
)