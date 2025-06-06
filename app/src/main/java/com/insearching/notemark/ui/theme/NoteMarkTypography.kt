package com.insearching.notemark.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.insearching.notemark.R


data class NoteMarkTypography(
    val titleXLarge: TextStyle = TextStyle(
        fontFamily = spaceGrotesk,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 40.sp
    ),
    val titleLarge: TextStyle = TextStyle(
        fontFamily = spaceGrotesk,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 36.sp
    ),
    val titleSmall: TextStyle = TextStyle(
        fontFamily = spaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 24.sp
    ),

    val bodyLarge: TextStyle = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 24.sp
    ),

    val bodyMedium: TextStyle = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp
    ),

    val bodySmall: TextStyle = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp
    )
)

internal val LocalNoteMarkTypography = staticCompositionLocalOf {
    NoteMarkTypography()
}

private val interFontFamily = FontFamily(
    Font(
        resId = R.font.inter_regular,
        weight = FontWeight.Normal,
    ),
    Font(
        resId = R.font.inter_medium,
        weight = FontWeight.Medium
    )
)

private val spaceGrotesk = FontFamily(
    Font(
        resId = R.font.space_grotesk_medium,
        weight = FontWeight.Medium,
    ),
    Font(
        resId = R.font.space_grotesk_bold,
        weight = FontWeight.Bold
    )
)