package com.mytodo.todo.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class DateConvertors {

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.parse(it)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }

    fun dateFormatter(date: LocalDateTime?): String? {
        val createdDateFormatted: String? = date?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        return createdDateFormatted
    }

    fun timeFormatter(dateTime : LocalDateTime?) : String? {
//        val dateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val time = dateTime?.format(formatter)
        return time
    }

    fun dateDeletion(date: LocalDateTime?) {
        val todayDate = LocalDateTime.now()
        var tempDateTime = LocalDateTime.from(date)

        val years = tempDateTime.until(todayDate, ChronoUnit.YEARS)
        tempDateTime = tempDateTime.plusYears(years)

        val months = tempDateTime.until(todayDate, ChronoUnit.MONTHS)
        tempDateTime = tempDateTime.plusMonths(months)

        val days = tempDateTime.until(todayDate, ChronoUnit.DAYS)
        tempDateTime = tempDateTime.plusDays(days)

        val hours = tempDateTime.until(todayDate, ChronoUnit.HOURS)
        tempDateTime = tempDateTime.plusHours(hours)

        val minutes = tempDateTime.until(todayDate, ChronoUnit.MINUTES)
        tempDateTime = tempDateTime.plusMinutes(minutes)

        Log.d("dattimedeletionCreate", date.toString())
        Log.d("dattimedeletiontoday", todayDate.toString())
        Log.d("dattimedeletion", "${days.toString()}, ${hours.toString()}, ${minutes.toString()}")
    }

}
