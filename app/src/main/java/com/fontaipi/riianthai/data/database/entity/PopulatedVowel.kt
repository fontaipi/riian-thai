package com.fontaipi.riianthai.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.fontaipi.riianthai.model.Vowel

data class PopulatedVowel(
    @Embedded
    val entity: VowelEntity,

    @Relation(
        entity = VowelFormEntity::class,
        parentColumn = "id",
        entityColumn = "vowel_id"
    )
    val forms: List<VowelFormEntity>,
)

fun PopulatedVowel.asExternalModel() = Vowel(
    id = entity.id,
    thaiScript = entity.thaiScript,
    vowelClass = entity.vowelClass,
    audioFile = entity.audioFile,
    soundFile = entity.soundFile,
    writingForms = forms.map(VowelFormEntity::asExternalModel),
    note = entity.note
)