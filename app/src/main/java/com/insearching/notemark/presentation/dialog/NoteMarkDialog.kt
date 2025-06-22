package com.insearching.notemark.presentation.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign


@Composable
fun NoteMarkDialog(
    onDismissRequest: () -> Unit,
    title: String = "",
    text: String = "",
    confirmText: String = "OK",
    dismissText: String = "Cancel",
    onConfirm: () -> Unit = {},
    onDismiss: (() -> Unit)? = null,
    showDismissButton: Boolean = true
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            if (title.isNotBlank()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        text = {
            if (text.isNotBlank()) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismissRequest()
            }) {
                Text(confirmText)
            }
        },
        dismissButton = {
            if (showDismissButton) {
                TextButton(onClick = {
                    onDismiss?.invoke()
                    onDismissRequest()
                }) {
                    Text(dismissText)
                }
            }
        }
    )
}