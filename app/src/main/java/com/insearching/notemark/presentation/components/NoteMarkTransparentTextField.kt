package com.insearching.notemark.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.insearching.notemark.ui.theme.NoteMarkTheme

@Composable
fun NoteMarkTransparentTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = NoteMarkTheme.typography.titleSmall,
    color: Color = NoteMarkTheme.color.onSurface,
    placeholder: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle.copy(color = color),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            Box {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = textStyle.copy(color = NoteMarkTheme.color.onSurfaceVar)
                    )
                }
                innerTextField()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun NoteMarkTransparentTextFieldPreview() {
    NoteMarkTheme {
        NoteMarkTransparentTextField(
            value = "",
            onValueChange = {},
            placeholder = "Note title"
        )
    }
}