package com.example.jetpacksample.ui.converters

import androidx.room.TypeConverter
import org.joda.time.LocalDate

class Converter {
    @TypeConverter
    fun fromLongToLocalDate(long: Long) : LocalDate {
        return LocalDate(long)
    }
    @TypeConverter
    fun toLongFromLocalDate(localDate: LocalDate) : Long {
        return localDate.toDate().time
    }
}