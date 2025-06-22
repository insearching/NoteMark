package com.insearching.notemark

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.insearching.notemark.presentation.navigation.NoteMarkController
import com.insearching.notemark.presentation.navigation.Route
import com.insearching.notemark.ui.theme.NoteMarkTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { !viewModel.isReady.value && viewModel.isLoggedIn.value != null }
        }
        val lightStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        enableEdgeToEdge(lightStyle, lightStyle)

        setContent {
            NoteMarkTheme {
                val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
                isLoggedIn?.let {
                    NoteMarkController(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(NoteMarkTheme.color.surface),
                        startDestination = if (it) Route.NoteListScreen else Route.LandingScreen,
                        controller = rememberNavController()
                    )
                }
            }
        }
    }
}