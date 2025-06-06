package com.insearching.notemark.presentation.components

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.insearching.notemark.R
import com.insearching.notemark.ui.theme.NoteMarkTheme

@Composable
fun NoteMarkTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    supportingText: String = "",
    isError: Boolean = false,
    isPassword: Boolean = false,
) {
    val focusRequester = remember { FocusRequester() }
    var passwordVisible by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = NoteMarkTheme.typography.bodyMedium,
            color = NoteMarkTheme.color.onSurface
        )

        Spacer(modifier = Modifier.height(NoteMarkTheme.offset.medium))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isFocused = it.hasFocus
                }
                .focusRequester(focusRequester)
                .focusable(),
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = NoteMarkTheme.typography.bodyLarge,
                    color = NoteMarkTheme.color.onSurfaceVar
                )
            },
            supportingText = {
                if (supportingText.isNotEmpty() && isFocused) {
                    Text(
                        text = supportingText,
                        style = NoteMarkTheme.typography.bodySmall,
                        color = if (isError) NoteMarkTheme.color.error else NoteMarkTheme.color.onSurfaceVar
                    )
                }
            },
            isError = isError,
            singleLine = true,
            shape = NoteMarkTheme.shape.large,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (isPassword) {
                    val icon =
                        if (!passwordVisible) R.drawable.ic_show_pass else R.drawable.ic_hide_pass
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            },
            textStyle = NoteMarkTheme.typography.bodyLarge.copy(
                color = NoteMarkTheme.color.onSurfaceVar
            ),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                unfocusedContainerColor = NoteMarkTheme.color.surface,
                focusedBorderColor = NoteMarkTheme.color.onSurfaceVar,
                focusedContainerColor = Color.Transparent,
                focusedTextColor = NoteMarkTheme.color.onSurfaceVar,
                errorBorderColor = NoteMarkTheme.color.error,
                cursorColor = if (isError) NoteMarkTheme.color.error else NoteMarkTheme.color.primary,
            ),
            label = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteMarkTextFieldPreview() {
    NoteMarkTheme {
        NoteMarkTextField(
            value = "Input",
            onValueChange = {},
            label = "Label",
            placeholder = "Placeholder",
            isError = false,
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteMarkTextFieldErrorPreview() {
    NoteMarkTextField(
        value = "",
        onValueChange = {},
        label = "Label",
        placeholder = "Placeholder",
        supportingText = "Password must be at least 8 characters and include a number or symbol",
        isError = true,
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun NoteMarkTextFieldFocusedPreview() {
    NoteMarkTextField(
        modifier = Modifier,
        value = "",
        onValueChange = {},
        label = "Label",
        supportingText = "Use 8+ characters with a number or symbol for better security",
        placeholder = "Placeholder",
        isError = false,
    )
}