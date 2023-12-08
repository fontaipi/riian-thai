package com.fontaipi.riianthai.ui.page.consonant.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

@Composable
fun DiphthongDialog(
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Diphthongs") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "The consonant ย and ว can create diphthongs where it follows a vowel, blending its sound to form a glide.")
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Diphthongs with ย (-y)",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = "Start with the initial vowel and glide into a 'y' sound.")
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Diphthongs with ว (-w)",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = "Start with the initial vowel and glide into a 'w' sound.")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = "Close")
            }
        }
    )
}

@Preview
@Composable
private fun DiphthongDialogPreview() {
    RiianThaiTheme {
        DiphthongDialog(
            onDismissRequest = {}
        )
    }
}