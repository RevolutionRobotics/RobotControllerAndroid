package com.revolution.robotics.core.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun Date.formatYearMonthDaySlashed(locale: Locale = Locale.getDefault()): String =
    SimpleDateFormat("yyyy/MM/dd", locale).format(this)

fun Date.formatYearMonthDayDotted(locale: Locale = Locale.getDefault()): String =
    SimpleDateFormat("yyyy.MM.dd.", locale).format(this)

fun Long.formatYearMonthDaySlashed(locale: Locale = Locale.getDefault()): String =
    Date(TimeUnit.SECONDS.toMillis(this)).formatYearMonthDaySlashed(locale)

fun Long.formatYearMonthDayDotted(locale: Locale = Locale.getDefault()): String =
    Date(TimeUnit.SECONDS.toMillis(this)).formatYearMonthDayDotted(locale)
