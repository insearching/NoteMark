package com.insearching.notemark.presentation.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.insearching.notemark.R

@Composable
fun DiscardChangesDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    NoteMarkDialog(
        title = stringResource(R.string.discard_changes_title),
        text = stringResource(R.string.discard_changes_description),
        confirmText = stringResource(R.string.discard),
        dismissText = stringResource(R.string.keep_editing),
        onConfirm = onConfirm,
        onDismiss = onDismiss,
        onDismissRequest = onDismiss
    )
}