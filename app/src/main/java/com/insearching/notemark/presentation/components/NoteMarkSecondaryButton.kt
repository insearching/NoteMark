package com.insearching.notemark.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insearching.notemark.ui.theme.NoteMarkTheme

@Composable
fun NoteMarkSecondaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        shape = NoteMarkTheme.shape.medium,
        border = BorderStroke(1.dp, NoteMarkTheme.color.primary),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = NoteMarkTheme.color.primary,
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
    ) {
        Text(
            modifier = Modifier.padding(vertical = NoteMarkTheme.offset.little),
            text = text,
            style = NoteMarkTheme.typography.titleSmall
        )
    }
}

@Preview
@Composable
private fun NoteMarkSecondaryButtonPreview() {
    NoteMarkTheme {
        NoteMarkSecondaryButton(
            onClick = {},
            text = "Button"
        )
    }
}