package com.stephen.autoscripts.data

const val MMKV_DATABASE_ID = "AUTO_SCRIPTS_DATABASE"

const val DINGDING = "com.alibaba.android.rimet"

val scriptsList = listOf(
    ScriptType.AUTO_SKIP_ADDS,
    ScriptType.DINGDING_CLOCK_IN,
    ScriptType.JAIKAO
)

enum class ScriptType(val key: String, val serviceName: String) {
    AUTO_SKIP_ADDS("AUTO_SKIP_ADDS", "自动跳广告"),
    DINGDING_CLOCK_IN("DINGDING_CLOCK_IN", "钉钉打卡"),
    JAIKAO("JAIKAO", "驾考宝典")
}