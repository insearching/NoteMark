package com.insearching.notemark.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.insearching.notemark.core.ScreenSizesPreview
import com.insearching.notemark.ui.theme.NoteMarkTheme

@Composable
fun NoteMarkPrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    Button(
        modifier = modifier
            .height(56.dp),
        onClick = onClick,
        enabled = enabled,
        shape = NoteMarkTheme.shape.medium,
        colors = ButtonDefaults.buttonColors(
            containerColor = NoteMarkTheme.color.primary,
            contentColor = NoteMarkTheme.color.onPrimary,
            disabledContainerColor = NoteMarkTheme.color.onSurface.copy(alpha = 0.12f),
            disabledContentColor = NoteMarkTheme.color.onSurface
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(
            modifier = Modifier.padding(vertical = NoteMarkTheme.offset.little),
            text = text,
            style = NoteMarkTheme.typography.titleSmall,
            color = if (enabled) NoteMarkTheme.color.onPrimary else NoteMarkTheme.color.onSurface
        )
    }
}

@Composable
@ScreenSizesPreview
private fun NoteMarkPrimaryButtonPreview() {
    NoteMarkTheme {
        NoteMarkPrimaryButton(
            onClick = {},
            enabled = true,
            text = "Button"
        )
    }
}

@ScreenSizesPreview
@Composable
private fun NoteMarkPrimaryButtonDisabledPreview() {
    NoteMarkTheme {
        NoteMarkPrimaryButton(
            onClick = {},
            enabled = false,
            text = "Button"
        )
    }
}