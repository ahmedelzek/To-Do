package com.example.to_do

import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar

fun CalendarDay.timeInMillis(): Long {
    val calendar: Calendar = Calendar.getInstance()
    calendar.set(this.year, this.month - 1, this.day)
    calendar.clearTime()
    return calendar.timeInMillis
}

fun Calendar.clearTime() {
    this.clear(Calendar.MILLISECOND)
    this.clear(Calendar.SECOND)
    this.clear(Calendar.MINUTE)
    this.clear(Calendar.HOUR)
}