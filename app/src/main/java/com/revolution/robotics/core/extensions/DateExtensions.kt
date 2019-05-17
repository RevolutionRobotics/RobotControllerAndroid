package com.revolution.robotics.core.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatYearMonthDaySlashed(locale: Locale = Locale.getDefault()): String =
    SimpleDateFormat("yyyy/MM/dd", locale).format(this)

fun Date.formatYearMonthDayDotted(locale: Locale = Locale.getDefault()): String =
    SimpleDateFormat("yyyy.MM.dd.", locale).format(this)

fun Long.formatYearMonthDay(locale: Locale = Locale.getDefault()): String =
    Date(this).formatYearMonthDayDotted(locale)
