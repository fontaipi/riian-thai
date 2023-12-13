package com.fontaipi.riianthai.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.fontaipi.riianthai.model.VowelForm

@Entity(
    tableName = "vowel_form",
    foreignKeys = [
        ForeignKey(
            entity = VowelEntity::class,
            parentColumns = ["id"],
            childColumns = ["vowel_id"],
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
    val note: String
)

fun VowelFormEntity.asExternalModel(): VowelForm =
    VowelForm(
        id = id,
        format = format,
        accentIndicator = accentIndicator,
        note = note
    )