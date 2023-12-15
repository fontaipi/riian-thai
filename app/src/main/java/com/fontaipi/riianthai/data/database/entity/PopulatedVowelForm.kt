package com.fontaipi.riianthai.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.fontaipi.riianthai.model.VowelForm

data class PopulatedVowelForm(
    @Embedded
    val entity: VowelFormEntity,

    @Relation(
        entity = WordEntity::class,
        parentColumn = "word_id",
        entityColumn = "id"
    )
    val exampleWord: WordEntity,
)

fun PopulatedVowelForm.asExternalModel() = VowelForm(
    id = entity.id,
    format = entity.format,
    accentIndicator = entity.accentIndicator,
    exampleWord = exampleWord.asExternalModel(),
    note = entity.note
)