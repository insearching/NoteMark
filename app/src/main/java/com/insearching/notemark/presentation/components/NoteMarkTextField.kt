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
import androidx.compose.runtime.derivedStateOf
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
    supportText: String? = null,
    errorText: String? = null,
    isPassword: Boolean = false,
) {
    val focusRequester = remember { FocusRequester() }
    var passwordVisible by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    val isError by remember(errorText, isFocused) {
        derivedStateOf {
            errorText?.isBlank() == false && !isFocused
        }
    }

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
                if (errorText?.isBlank() == false && !isFocused) {
                    Text(
                        text = errorText,
                        style = NoteMarkTheme.typography.bodySmall,
                        color = NoteMarkTheme.color.error
                    )
                } else if (supportText?.isBlank() == false && isFocused) {
                    Text(
                        text = supportText,
                        style = NoteMarkTheme.typography.bodySmall,
                        color = NoteMarkTheme.color.onSurfaceVar
                    )
                }
            },
            isError = isError,
            singleLine = true,
            shape = NoteMarkTheme.shape.large,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation('*') else VisualTransformation.None,
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
                focusedBorderColor = NoteMarkTheme.color.primary,
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
        errorText = "Password must be at least 8 characters and include a number or symbol",
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
        errorText = "Use 8+ characters with a number or symbol for better security",
        placeholder = "Placeholder",
    )
}