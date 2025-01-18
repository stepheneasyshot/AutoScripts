package com.stephen.autoscripts.utils

import android.text.format.Time

fun atTheCurrentTime(beginHour: Int, beginMin: Int, endHour: Int, endMin: Int): Boolean {
    var result = false
    val aDayInMillis = (1000 * 60 * 60 * 24).toLong()
    val currentTimeMillis = System.currentTimeMillis()
    val now = Time()
    now.set(currentTimeMillis)
    val startTime = Time()
    startTime.set(currentTimeMillis)
    startTime.hour = beginHour
    startTime.minute = beginMin
    val endTime = Time()
    endTime.set(currentTimeMillis)
    endTime.hour = endHour
    endTime.minute = endMin
    // 跨天的特殊情况(比如23:00-2:00)
    if (!startTime.before(endTime)) {
        startTime.set(startTime.toMillis(true) - aDayInMillis)
        result = !now.before(startTime) && !now.after(endTime) // startTime <= now <= endTime
        val startTimeInThisDay = Time()
        startTimeInThisDay.set(startTime.toMillis(true) + aDayInMillis)
        if (!now.before(startTimeInThisDay)) {
            result = true
        }
    } else {
        // 普通情况(比如5:00-10:00)
        result = !now.before(startTime) && !now.after(endTime) // startTime <= now <= endTime
    }
    return result
}
