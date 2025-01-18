package com.stephen.autoscripts

import android.app.Application
import com.stephen.autoscripts.data.MMKV_DATABASE_ID
import com.stephen.autoscripts.data.ScriptType
import com.stephen.commonhelper.datastore.MMKVHelper
import com.stephen.commonhelper.utils.LogSetting
import com.stephen.commonhelper.utils.infoLog
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AutoScriptsApplication : Application() {

    companion object {
        lateinit var instance: Application
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        LogSetting.initLogSettings("AutoClock", LogSetting.LOG_VERBOSE)
        infoLog("=========>onCreate<==========")
        MMKVHelper.init(this, MMKV_DATABASE_ID, false)

        // test
        MainScope().launch {
            MMKVHelper.putBoolean(ScriptType.JAIKAO.key, true)
        }
    }
}