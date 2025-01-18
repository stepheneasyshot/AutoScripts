package com.stephen.autoscripts.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.stephen.autoscripts.ui.theme.buttonBackGroundColor

@Composable
fun CommonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color:Color = buttonBackGroundColor
) {
    CenterText(
        modifier = modifier.clickable {
            onClick()
        }.clip(RoundedCornerShape(10))
            .background(color)
            .shadow(1.dp, spotColor = Color.White)
            .padding(10.dp),
        text = text
    )
}