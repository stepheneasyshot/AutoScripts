package com.stephen.autoscripts.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stephen.autoscripts.ui.theme.pageTitleText

@Composable
fun BasePage(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(10.dp)) {
        CenterText(
            text = title,
            style = pageTitleText,
            modifier = Modifier.padding(top = 45.dp, bottom = 20.dp)
        )
        content()
    }
}