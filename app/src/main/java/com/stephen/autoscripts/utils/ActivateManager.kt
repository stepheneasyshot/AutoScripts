package com.stephen.autoscripts.utils

import android.annotation.SuppressLint
import com.stephen.autoscripts.base.appContext
import com.stephen.autoscripts.data.ScriptType
import com.stephen.commonhelper.datastore.MMKVHelper
import okio.ByteString.Companion.decodeBase64
import java.text.SimpleDateFormat
import java.util.Date

object ActivateManager {

    /**
     * 获取服务激活状态
     */
    fun getActivateStatus(serviceKey: String): Boolean {
        return MMKVHelper.getBoolean(serviceKey, false)
    }

    fun setActivateStatus(serviceKey: String, activateCode: String) {
        when (serviceKey) {
            ScriptType.DINGDING_CLOCK_IN.key -> {
                activteDingdingClockIn(activateCode)
            }
            ScriptType.AUTO_SKIP_ADDS.key -> {
                activateAutoSkipAds(activateCode)
            }
        }
    }

    private fun activateAutoSkipAds(activateCode: String) {

    }

    @SuppressLint("SimpleDateFormat")
    private fun activteDingdingClockIn(activateCode: String) {
        val isMatch = (("$activateCode==").decodeBase64())?.toString()?.drop(6)
            ?.dropLast(1) == SimpleDateFormat("yyyy-MM-dd-HH").format(Date()) + appContext.packageName
        if (isMatch) {
            showToast("激活成功！")
            MMKVHelper.putBoolean(ScriptType.DINGDING_CLOCK_IN.key, true)
        } else {
            showToast("激活码错误！")
        }
    }
}