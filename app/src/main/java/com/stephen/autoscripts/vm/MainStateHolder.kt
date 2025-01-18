package com.stephen.autoscripts.vm

import com.stephen.autoscripts.data.ScriptType
import com.stephen.autoscripts.utils.ActivateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

object MainStateHolder {

    private val serviceActivateFlow =
        MutableStateFlow(ServiceActivateSate(null))

    val activateViewState = serviceActivateFlow.asStateFlow()

    fun getServiceActivateSate() {
        CoroutineScope(Dispatchers.IO).launch {
            val isAutoSkipAdsActivate =
                ActivateManager.getActivateStatus(ScriptType.AUTO_SKIP_ADDS.key)
            val isDingdingClockInActivate =
                ActivateManager.getActivateStatus(ScriptType.DINGDING_CLOCK_IN.key)
            val isJiakaoActivate =
                ActivateManager.getActivateStatus(ScriptType.JAIKAO.key)

            serviceActivateFlow.update {
                it.copy(
                    serviceActivateMap = mapOf(
                        ScriptType.AUTO_SKIP_ADDS.key to isAutoSkipAdsActivate,
                        ScriptType.DINGDING_CLOCK_IN.key to isDingdingClockInActivate,
                        ScriptType.JAIKAO.key to isJiakaoActivate
                    )
                )
            }

            serviceActivateFlow.value = serviceActivateFlow.value.toUiSate()
        }
    }

    fun activateService(key: String, authenticator: String) {
        CoroutineScope(Dispatchers.IO).launch {
            ActivateManager.setActivateStatus(key, authenticator)
            delay(100L)
            getServiceActivateSate()
        }
    }
}

data class ServiceActivateSate(
    val serviceActivateMap: Map<String, Boolean>? = null,
) {
    fun toUiSate() =
        ServiceActivateSate(serviceActivateMap)
}