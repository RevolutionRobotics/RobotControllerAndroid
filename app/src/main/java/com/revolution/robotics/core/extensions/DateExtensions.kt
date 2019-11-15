package com.revolution.robotics.core.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun Date.formatYearMonthDay(locale: Locale = Locale.getDefault()): String =
    SimpleDateFormat("yyyy-MMM-dd", locale).format(this).toUpperCase()

fun Long.formatYearMonthDay(locale: Locale = Locale.getDefault()): String =
    Date(TimeUnit.SECONDS.toMillis(this)).formatYearMonthDay(locale)
