package com.vanlee.sportify.utils

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import java.util.*

class DateUtils {

    companion object {
        private val TAG = DateUtils::class.java.simpleName

        fun convertedDateToLocalTime(dateStr: String?): Calendar? {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            sdf.timeZone = TimeZone.getTimeZone("UTC")

            sdf.parse(dateStr)
            sdf.timeZone = TimeZone.getDefault()

            return sdf.calendar
        }

        fun String.toDate(
            dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss",
            timeZone: TimeZone = TimeZone.getTimeZone("UTC")
        ): Date {
            val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
            parser.timeZone = timeZone
            return parser.parse(this)
        }

        fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
            val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
            formatter.timeZone = timeZone
            return formatter.format(this)
        }
    }
}