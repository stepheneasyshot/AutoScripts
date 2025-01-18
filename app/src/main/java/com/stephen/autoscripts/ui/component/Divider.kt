package com.stephen.autoscripts.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stephen.autoscripts.ui.theme.darkdevidelineColor
import com.stephen.autoscripts.ui.theme.lightDevidelineColor

@Composable
fun DarkDivider(modifier: Modifier = Modifier) {
    Spacer(modifier.background(darkdevidelineColor))
}

@Composable
fun LightDivider(modifier: Modifier = Modifier) {
    Spacer(modifier.background(lightDevidelineColor))
}