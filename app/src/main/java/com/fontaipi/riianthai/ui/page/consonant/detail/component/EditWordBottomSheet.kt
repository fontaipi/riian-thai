package com.fontaipi.riianthai.ui.page.consonant.detail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fontaipi.riianthai.model.Word
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditWordBottomSheet(
    word: Word,
    onDismissRequest: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    var wordThai by remember { mutableStateOf(word.thai) }
    var wordMeaning by remember { mutableStateOf(word.meaning) }

    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = {
            coroutineScope.launch { bottomSheetState.hide() }
            onDismissRequest()
        },
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Edit Word",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = wordThai,
                onValueChange = { wordThai = it },
                label = { Text("Thai") },
                singleLine = true,
            )
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = wordMeaning,
                onValueChange = { wordMeaning = it },
                label = { Text("Meaning") },
                singleLine = true,
            )
            Row(
                modifier = Modifier.align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(
                    onClick = {
                        coroutineScope.launch { bottomSheetState.hide() }
                        onDismissRequest()
                    }
                ) {
                    Text(text = "Cancel")
                }
                TextButton(
                    onClick = {
                        coroutineScope.launch { bottomSheetState.hide() }
                        onConfirm(wordThai, wordMeaning)
                    }
                ) {
                    Text(text = "Confirm")
                }
            }
        }
    }
}