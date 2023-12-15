package com.fontaipi.riianthai.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fontaipi.riianthai.data.database.dao.ConsonantDao
import com.fontaipi.riianthai.data.database.dao.VowelDao
import com.fontaipi.riianthai.data.database.dao.VowelFormDao
import com.fontaipi.riianthai.data.database.dao.WordDao
import com.fontaipi.riianthai.data.database.entity.ConsonantEntity
import com.fontaipi.riianthai.data.database.entity.ConsonantWordsCrossRef
import com.fontaipi.riianthai.data.database.entity.VowelEntity
import com.fontaipi.riianthai.data.database.entity.VowelFormEntity
import com.fontaipi.riianthai.data.database.entity.WordEntity

@Database(
    entities = [
        ConsonantEntity::class,
        VowelEntity::class,
        VowelFormEntity::class,
        WordEntity::class,
        ConsonantWordsCrossRef::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class RiianThaiDatabase : RoomDatabase() {
    abstract fun consonantDao(): ConsonantDao
    abstract fun vowelDao(): VowelDao
    abstract fun vowelFormDao(): VowelFormDao
    abstract fun wordDao(): WordDao
}