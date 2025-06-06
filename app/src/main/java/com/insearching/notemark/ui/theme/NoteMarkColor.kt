package com.insearching.notemark.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class NoteMarkColor(
    val primary: Color = Color(0xFF5977F7),
    val primaryOpacity10: Color = Color(0x1A5977F7),
    val onPrimary: Color = Color(0xFFFFFFFF),
    val onPrimaryOpacity12: Color = Color(0x1FFFFFFF),
    val onSurface: Color = Color(0xFF1B1B1C),
    val onSurfaceVar: Color = Color(0xFF535364),
    val surface: Color = Color(0xFFEFEFF2),
    val surfaceLowest: Color = Color(0xFFFFFFFF),
    val error: Color = Color(0xFFE1294B),
)


internal val LocalNoteMarkColor = staticCompositionLocalOf {
    NoteMarkColor()
}