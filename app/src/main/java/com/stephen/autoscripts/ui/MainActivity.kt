package com.stephen.autoscripts.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.stephen.autoscripts.ui.theme.AutoScriptsTheme
import com.stephen.autoscripts.ui.theme.backGroundColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions(
            arrayOf(
                "android.permission.POST_NOTIFICATIONS",
                "android.permission.WAKE_LOCK"
            ), 200
        )
        enableEdgeToEdge()
        setContent {
            AutoScriptsTheme {
                Surface(modifier = Modifier
                    .background(backGroundColor)
                    .fillMaxSize(1f)) {
                    MainView()
                }
            }
        }
    }
}