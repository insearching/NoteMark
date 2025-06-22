package com.insearching.notemark.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.insearching.notemark.data.local.converters.ZonedTimeConverter
import com.insearching.notemark.data.local.dao.NoteDao
import com.insearching.notemark.data.local.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
@TypeConverters(ZonedTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        const val DB_NAME = "notes.db"
    }
}