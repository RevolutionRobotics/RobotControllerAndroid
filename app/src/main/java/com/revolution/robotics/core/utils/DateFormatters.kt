package com.revolution.robotics.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateFormatters private constructor() {

    companion object {
        private val YEAR_MONTH_DAY = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

        fun yearMonthDay(date: Date?): String = YEAR_MONTH_DAY.format(date)
    }
}
