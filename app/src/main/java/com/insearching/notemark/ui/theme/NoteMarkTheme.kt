package com.insearching.notemark.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.window.core.layout.WindowWidthSizeClass

@Composable
fun NoteMarkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    val isTablet = windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT

    val orientation = when {
        isLandscape -> ScreenOrientation.Landscape
        isTablet -> ScreenOrientation.TabletPortrait
        else -> ScreenOrientation.PhonePortrait
    }

    CompositionLocalProvider(
        LocalNoteMarkTypography provides NoteMarkTheme.typography,
        LocalNoteMarkColor provides NoteMarkTheme.color,
        LocalNoteMarkShape provides NoteMarkTheme.shape,
        LocalNoteMarkOffset provides NoteMarkTheme.offset,
        LocalScreenOrientation provides orientation,
        content = content
    )
}

val LocalScreenOrientation = staticCompositionLocalOf<ScreenOrientation> {
    error("No orientation provided")
}

object NoteMarkTheme {
    val typography: NoteMarkTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalNoteMarkTypography.current

    val color: NoteMarkColor
        @Composable
        @ReadOnlyComposable
        get() = LocalNoteMarkColor.current

    val shape: NoteMarkShape
        @Composable
        @ReadOnlyComposable
        get() = LocalNoteMarkShape.current

    val offset: NoteMarkOffset
        @Composable
        @ReadOnlyComposable
        get() = LocalNoteMarkOffset.current

}