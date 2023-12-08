package com.fontaipi.riianthai.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "consonant_words", primaryKeys = ["consonant_id", "word_id"],
    foreignKeys = [
        ForeignKey(
            entity = ConsonantEntity::class,
            parentColumns = ["id"],
            childColumns = ["consonant_id"]
        ),
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["id"],
            childColumns = ["word_id"]
        )
    ]
)
data class ConsonantWordsCrossRef(
    @ColumnInfo(name = "consonant_id")
    val consonantId: Long,
    @ColumnInfo(name = "word_id")
    val wordId: Long
)