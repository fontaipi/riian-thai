package com.fontaipi.riianthai.ui.page.learn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LearnScreen(
    navigateToConsonants: () -> Unit,
    navigateToVowels: () -> Unit,
    navigateToToneMarks: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Learn",
            style = MaterialTheme.typography.titleLarge
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                onClick = navigateToConsonants
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                ) {
                    Text(text = "Consonants")
                }
            }
            Card(
                onClick = {}
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                ) {
                    Text(text = "Vowels")
                }
            }
            Card(
                onClick = navigateToToneMarks
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                ) {
                    Text(text = "Tone marks")
                }
            }
        }
    }
}