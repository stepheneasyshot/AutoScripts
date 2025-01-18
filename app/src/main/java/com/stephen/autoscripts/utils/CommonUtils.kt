package com.stephen.autoscripts.utils

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.widget.Toast
import com.stephen.autoscripts.base.appContext
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


fun showToast(text: String) {
    MainScope().launch {
        Toast.makeText(appContext, text, Toast.LENGTH_LONG).show()
    }
}

/**
 * 创建滑动手势
 */
fun AccessibilityService.createSwipeGesture(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    duration: Long = 500L
): GestureDescription {
    val path = Path()
    path.moveTo(startX, startY)
    path.lineTo(endX, endY)
    val builder = GestureDescription.Builder()
    // 立即开始
    val startTime = 0L
    // 滑动持续时间（单位：毫秒）
    val duration = duration
    val stroke = GestureDescription.StrokeDescription(path, startTime, duration)
    builder.addStroke(stroke)
    return builder.build()
}