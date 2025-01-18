package com.stephen.autoscripts.ui

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.stephen.autoscripts.R
import com.stephen.autoscripts.base.appContext
import com.stephen.autoscripts.data.ScriptType
import com.stephen.autoscripts.data.scriptsList
import com.stephen.autoscripts.ui.component.BasePage
import com.stephen.autoscripts.ui.component.CenterText
import com.stephen.autoscripts.ui.component.CommonButton
import com.stephen.autoscripts.ui.component.WrappedEditText
import com.stephen.autoscripts.ui.theme.groupBackGroundColor
import com.stephen.autoscripts.ui.theme.groupTitleText
import com.stephen.autoscripts.vm.MainStateHolder

@Composable
fun MainView() {
    val serviceActivateSate = MainStateHolder.activateViewState.collectAsState()

    LaunchedEffect(Unit) {
        MainStateHolder.getServiceActivateSate()
    }

    BasePage(title = "自动脚本") {
        Row(modifier = Modifier.padding(20.dp)) {
            CommonButton(
                "跳转到辅助服务列表", onClick = {
                    appContext.startActivity(
                        Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).setFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK
                        )
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }
        LazyColumn {
            items(scriptsList) {
                val isActivated =
                    serviceActivateSate.value.serviceActivateMap?.get(it.key) ?: false
                ServiceItem(itemKey = it, isActivated = isActivated)
            }
        }
    }
}

@Composable
fun ServiceItem(itemKey: ScriptType, isActivated: Boolean) {
    val activateString = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(groupBackGroundColor)
            .padding(vertical = 10.dp, horizontal = 10.dp)
    ) {
        CenterText(
            itemKey.serviceName,
            style = groupTitleText,
        )
        Row(
            modifier = Modifier.padding(top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isActivated) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CenterText("已激活")
                    Image(
                        painter = painterResource(id = R.mipmap.ic_activated),
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .size(20.dp),
                        contentDescription = "activated"
                    )
                }
            } else
                Row(verticalAlignment = Alignment.CenterVertically) {
                    WrappedEditText(
                        value = activateString.value,
                        tipText = "输入激活码",
                        onValueChange = { activateString.value = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 10.dp)
                    )
                    CommonButton(
                        "激活", onClick = {
                            MainStateHolder.activateService(itemKey.key, activateString.value)
                            activateString.value = ""
                        },
                        modifier = Modifier.width(100.dp)
                    )
                }
        }
    }
}