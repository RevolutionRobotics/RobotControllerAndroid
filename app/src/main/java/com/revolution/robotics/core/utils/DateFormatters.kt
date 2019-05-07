package com.revolution.robotics.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateFormatters private constructor() {

    companion object {
        private const val YEAR_MONTH_DAY = "yyyy/MM/dd"

        fun yearMonthDay(date: Date?, locale: Locale = Locale.getDefault()): String =
            SimpleDateFormat(YEAR_MONTH_DAY, locale).format(date)
    }
}
