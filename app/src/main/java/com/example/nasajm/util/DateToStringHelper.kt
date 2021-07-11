package com.example.nasajm.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun setDateInString(minusDaysFromToday: Long, pattern: String): String{
    return LocalDateTime.now().minusDays(minusDaysFromToday)
        .format(DateTimeFormatter.ofPattern(pattern))
}