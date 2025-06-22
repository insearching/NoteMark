package com.insearching.notemark.data.local.converters

import androidx.room.TypeConverter
import java.time.ZonedDateTime


class ZonedTimeConverter {

    @TypeConverter
    fun fromZonedDateTime(value: ZonedDateTime): String {
        return value.toString()
    }

    @TypeConverter
    fun toZonedDateTime(value: String): ZonedDateTime {
        return ZonedDateTime.parse(value)
    }
}