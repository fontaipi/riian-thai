package com.fontaipi.riianthai.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fontaipi.riianthai.data.database.dao.ConsonantDao
import com.fontaipi.riianthai.data.database.entity.ConsonantEntity

@Database(
    entities = [
        ConsonantEntity::class
    ],
    version = 1,
    exportSchema = false,
)
abstract class RiianThaiDatabase : RoomDatabase() {
    abstract fun consonantDao(): ConsonantDao
}