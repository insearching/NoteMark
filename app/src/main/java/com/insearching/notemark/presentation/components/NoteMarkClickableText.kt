package com.insearching.notemark.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.insearching.notemark.core.extension.noRippleClickable
import com.insearching.notemark.ui.theme.NoteMarkTheme

@Composable
fun NoteMarkClickableText(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    Text(
        modifier = modifier.noRippleClickable(onClick = onClick),
        text = text,
        style = NoteMarkTheme.typography.titleSmall,
        color = NoteMarkTheme.color.primary,
    )
}

@Preview
@Composable
private fun NoteMarkClickableTextPreview() {
    NoteMarkTheme {
        NoteMarkClickableText(
            text = "Clickable text",
            onClick = {},
        )
    }
}