package com.fontaipi.riianthai.data.database.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.model.Word

data class PopulatedConsonant(
    @Embedded
    val entity: ConsonantEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = ConsonantWordsCrossRef::class,
            parentColumn = "consonant_id",
            entityColumn = "word_id",
        ),
    )
    val words: List<WordEntity>,
)

fun PopulatedConsonant.asExternalModel() = Consonant(
    id = entity.id,
    thai = entity.thai,
    phonetic = entity.phonetic,
    audio = entity.audio,
    picture = entity.picture,
    consonantClass = entity.consonantClass,
    endingSound = entity.endingSound,
    associatedWord = entity.associatedWord,
    meaning = entity.meaning,
    exampleWords = words.map(WordEntity::asExternalModel),
    count = entity.count,
)