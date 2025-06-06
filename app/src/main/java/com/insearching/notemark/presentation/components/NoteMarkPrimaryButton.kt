package com.insearching.notemark.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.insearching.notemark.ui.theme.NoteMarkTheme

@Composable
fun NoteMarkPrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    Button(
        modifier = modifier.height(56.dp),
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
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                color = NoteMarkTheme.color.onPrimary
            )
        } else {
            Text(
                modifier = Modifier.padding(vertical = NoteMarkTheme.offset.little),
                text = text,
                style = NoteMarkTheme.typography.titleSmall,
                color = if (enabled) NoteMarkTheme.color.onPrimary else NoteMarkTheme.color.onSurface
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun NoteMarkPrimaryButtonPreview() {
    NoteMarkTheme {
        NoteMarkPrimaryButton(
            modifier = Modifier.width(120.dp),
            text = "Button",
            onClick = {},
            enabled = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteMarkPrimaryButtonDisabledPreview() {
    NoteMarkTheme {
        NoteMarkPrimaryButton(
            modifier = Modifier.width(120.dp),
            text = "Button",
            onClick = {},
            enabled = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteMarkPrimaryButtonLoadingPreview() {
    NoteMarkTheme {
        NoteMarkPrimaryButton(
            modifier = Modifier.width(120.dp),
            text = "Button",
            onClick = {},
            enabled = true,
            isLoading = true
        )
    }
}