package com.insearching.notemark.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun ZonedDateTime.toShortDayMonth(): String {
    val now = ZonedDateTime.now()
    val isSameYear = this.year == now.year

    val pattern = if (isSameYear) "dd MMM" else "dd MMM yyyy"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH)

    return this.format(formatter).uppercase(Locale.ENGLISH)
}