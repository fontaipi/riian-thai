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
fun EndingConsonantDialog(
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Ending consonant") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Live Endings", style = MaterialTheme.typography.titleMedium)
                    Text(text = "End a syllable with an open sound, leading to longer vowel sounds and often resulting in mid or rising tones. Example: น (n) in คน (khon, \"person\").")
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "Dead Endings", style = MaterialTheme.typography.titleMedium)
                    Text(text = "End a syllable with a closed sound, shortening the vowel sound and typically causing low or falling tones. Example: ก (k) in หมาก (mak, \"chess\").")
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
private fun EndingConsonantDialogPreview() {
    RiianThaiTheme {
        EndingConsonantDialog(
            onDismissRequest = {}
        )
    }
}