package com.insearching.notemark.presentation.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.insearching.notemark.R

@Composable
fun DeleteNoteDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    NoteMarkDialog(
        title = stringResource(R.string.delete_dialog_title),
        text = stringResource(R.string.delete_dialog_text),
        confirmText = stringResource(R.string.confirm),
        dismissText = stringResource(R.string.cancel),
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        onDismissRequest = onDismiss
    )
}