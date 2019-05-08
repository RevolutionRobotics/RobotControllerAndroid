package com.revolution.robotics.core.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatYearMonthDay(locale: Locale = Locale.getDefault()): String =
    SimpleDateFormat("yyyy/MM/dd", locale).format(this)
