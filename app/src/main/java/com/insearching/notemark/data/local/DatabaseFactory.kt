package com.insearching.notemark.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

class DatabaseFactory(
    private val context: Context
) {
    fun create(): RoomDatabase.Builder<AppDatabase> {
        return Room.databaseBuilder(
            context = context.applicationContext,
            name = AppDatabase.DB_NAME,
            klass = AppDatabase::class.java
        ).fallbackToDestructiveMigration(false)
    }
}