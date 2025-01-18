package com.stephen.autoscripts.service

import android.accessibilityservice.AccessibilityService
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import com.stephen.autoscripts.R
import com.stephen.autoscripts.base.appContext
import com.stephen.autoscripts.data.DINGDING
import com.stephen.autoscripts.data.ScriptType
import com.stephen.autoscripts.ui.MainActivity
import com.stephen.autoscripts.utils.ActivateManager.getActivateStatus
import com.stephen.autoscripts.utils.atTheCurrentTime
import com.stephen.autoscripts.utils.showToast
import com.stephen.commonhelper.utils.errorLog
import com.stephen.commonhelper.utils.infoLog
import com.stephen.commonhelper.utils.jumpToAnotherApp
import com.stephen.commonhelper.utils.performClickByCoordinate
import com.stephen.commonhelper.utils.scanAndClickByText
import com.stephen.commonhelper.utils.warningLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 钉钉打卡
 */
class DingdingClockInService : AccessibilityService() {

    private val clockCoroutine = CoroutineScope(Dispatchers.Main)

    companion object {
        const val CHANNEL_ID = "samples.notification.devdeeds.com.CHANNEL_ID"
        const val CHANNEL_NAME = "Sample Notification"
    }

    override fun onCreate() {
        super.onCreate()
        infoLog("===>onCreate<=== Device Name: ${Build.BRAND} / ${Build.MODEL}")
        startForegRroundService()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (atTheCurrentTime(17, 25, 17, 30) && atTheCurrentTime(8, 25, 8, 30)) {
            if (getActivateStatus(ScriptType.DINGDING_CLOCK_IN.key)) {
                clockCoroutine.launch {
                    beginAutoClockIn()
                }
            } else {
                MainScope().launch {
                    showToast("请先激活后再重启服务")
                    delay(2000L)

                    stopSelf()
                }
            }
        }
    }

    private suspend fun beginAutoClockIn() =
        withContext(Dispatchers.Main) {
            warningLog("Clock Service triggered")
            //首先立即回到桌面
            delay(4000L)
            showToast("打开钉钉")
            jumpToAnotherApp(appContext, DINGDING)
            delay(4000L)
            showToast("模拟点击打卡")
            scanAndClickByText("打卡")
            // 预留6S定位
            delay(6000L)
            // 打卡
            showToast("模拟点击上下班打卡按钮")
            performClickByCoordinate(533f, 1257f)
            delay(4000L)
            showToast("打开完成，准备回到桌面")
            performGlobalAction(GLOBAL_ACTION_HOME)
            delay(1500L)
            delay(100L)
            performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
        }

    override fun onInterrupt() {
        errorLog("onInterrupt")
    }


    private lateinit var mNotification: Notification

    private fun startForegRroundService() {
        infoLog("startForegRroundService")

        createChannel(appContext)

        val notifyIntent = Intent(appContext, MainActivity::class.java)

        val title = "钉钉ClockIn"
        val message = "Android辅助服务"

        notifyIntent.putExtra("title", title)
        notifyIntent.putExtra("message", message)
        notifyIntent.putExtra("notification", true)

        notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        val pendingIntent =
            PendingIntent.getActivity(appContext, 0, notifyIntent, PendingIntent.FLAG_MUTABLE)

        mNotification = Notification.Builder(appContext, CHANNEL_ID)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setStyle(
                Notification.BigTextStyle()
                    .bigText(message)
            )
            .setContentText(message).build()

        startForeground(888, mNotification)
    }

    private fun createChannel(context: Context) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        notificationChannel.enableVibration(true)
        notificationChannel.setShowBadge(true)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.parseColor("#e8334a")
        notificationChannel.description = "notification channel description"
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        notificationManager.createNotificationChannel(notificationChannel)
    }

    override fun onDestroy() {
        super.onDestroy()
        infoLog("==========>onDestroy<==========")
        stopForeground(true)
    }
}