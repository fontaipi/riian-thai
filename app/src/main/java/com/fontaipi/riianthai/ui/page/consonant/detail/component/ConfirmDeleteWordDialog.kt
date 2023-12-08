package com.fontaipi.riianthai.ui.page.consonant.detail.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

@Composable
fun ConfirmDeleteWordDialog(
    onConfirmRequest: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Delete word") },
        icon = {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        },
        text = {
            Text(text = "Are you sure you want to delete this word?")
        },

        confirmButton = {
            FilledTonalButton(
                onClick = onConfirmRequest
            ) {
                Text(text = "Delete")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "Cancel")
            }
        }
    )
}

@Preview
@Composable
private fun ConfirmDeleteWordDialogPreview() {
    RiianThaiTheme {
        ConfirmDeleteWordDialog(
            onConfirmRequest = {},
            onDismissRequest = {}
        )
    }
}