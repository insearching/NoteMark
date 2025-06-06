package com.insearching.notemark

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.insearching.notemark.presentation.navigation.Route
import com.insearching.notemark.presentation.navigation.NoteMarkController
import com.insearching.notemark.ui.theme.NoteMarkTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { !viewModel.isReady.value }
        }
        val lightStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        enableEdgeToEdge(lightStyle, lightStyle)

        setContent {
            NoteMarkTheme {
                NoteMarkController(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(NoteMarkTheme.color.surface),
                    startDestination = Route.LandingScreen,
                    controller = rememberNavController()
                )
            }
        }
    }
}