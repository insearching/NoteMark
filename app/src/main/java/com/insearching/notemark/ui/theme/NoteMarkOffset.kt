package com.insearching.notemark.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class NoteMarkOffset(
    val empty: Dp = 0.dp,
    val micro: Dp = 2.dp,
    val tiny: Dp = 4.dp,
    val mini: Dp = 6.dp,
    val little: Dp = 8.dp,
    val compact: Dp = 10.dp,
    val small: Dp = 12.dp,
    val medium: Dp = 16.dp,
    val intermediate: Dp = 18.dp,
    val regular: Dp = 20.dp,
    val average: Dp = 24.dp,
    val large: Dp = 28.dp,
    val great: Dp = 32.dp,
    val immense: Dp = 36.dp,
    val significant: Dp = 38.dp,
    val huge: Dp = 40.dp,
    val massive: Dp = 46.dp,
    val enormous: Dp = 50.dp,
    val big: Dp = 62.dp,
    val giant: Dp = 78.dp,
)

internal val LocalNoteMarkOffset = staticCompositionLocalOf { NoteMarkOffset() }