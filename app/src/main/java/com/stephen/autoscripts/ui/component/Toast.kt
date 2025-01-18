package com.stephen.autoscripts.ui.component

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * 弹出式提示框
 *
 * @param visible 是否显示
 * @param title 标题
 * @param duration 显示的时长
 * @param onClose 关闭事件
 */
@Composable
fun WeToast(
    visible: Boolean,
    title: String,
    duration: Duration = 2000.milliseconds,
    onClose: () -> Unit
) {
    var localVisible by remember {
        mutableStateOf(visible)
    }

    LaunchedEffect(visible, duration, title) {
        if (visible && duration != Duration.INFINITE) {
            delay(duration)
            onClose()
        }
    }
    LaunchedEffect(visible) {
        if (!visible) {
            delay(150)
        }
        localVisible = visible
    }

    val positionProvider = remember {
        object : PopupPositionProvider {
            override fun calculatePosition(
                anchorBounds: IntRect,
                windowSize: IntSize,
                layoutDirection: LayoutDirection,
                popupContentSize: IntSize
            ): IntOffset {
                return IntOffset(windowSize.width / 2 - popupContentSize.width / 2, 44)
            }
        }
    }
    if (visible || localVisible) {
        Popup(popupPositionProvider = positionProvider) {
            Box(
                modifier = Modifier.width(400.dp).heightIn(44.dp).clip(RoundedCornerShape(50)),
                contentAlignment = Alignment.TopCenter
            ) {
                AnimatedVisibility(
                    visible = visible && localVisible,
                    enter = fadeIn() + scaleIn(tween(100), initialScale = 0.8f),
                    exit = fadeOut() + scaleOut(tween(100), targetScale = 0.8f)
                ) {
                    Box(
                        modifier = Modifier.width(400.dp).heightIn(44.dp).clip(RoundedCornerShape(50))
                            .background(Color(0xCC4C4C4C)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


@Stable
interface ToastState {
    /**
     * 是否显示
     */
    val visible: Boolean

    /**
     * 显示提示框
     */
    fun show(
        title: String,
        duration: Duration = 3000.milliseconds
    )

    /**
     * 隐藏提示框
     */
    fun hide()
}

@Composable
fun rememberToastState(): ToastState {
    val state = remember { ToastStateImpl() }

    state.props?.let { props ->
        WeToast(
            visible = state.visible,
            title = props.title,
            duration = props.duration,
        ) {
            state.hide()
        }
    }

    return state
}

private class ToastStateImpl : ToastState {
    override var visible by mutableStateOf(false)
    var props by mutableStateOf<ToastProps?>(null)
        private set

    override fun show(title: String, duration: Duration) {
        props = ToastProps(title, duration)
        visible = true
    }

    override fun hide() {
        visible = false
    }
}

private data class ToastProps(
    val title: String,
    val duration: Duration,
)