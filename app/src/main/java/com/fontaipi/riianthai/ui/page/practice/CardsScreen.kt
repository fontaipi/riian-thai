package com.fontaipi.riianthai.ui.page.practice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.model.VowelClass

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsScreen(
    navigateToFlashCardScreen: (ConsonantClass) -> Unit,
    navigateToSummaryScreen: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Consonants",
                    style = MaterialTheme.typography.titleLarge
                )
                FilledTonalIconButton(
                    onClick = navigateToSummaryScreen
                ) {
                    Icon(imageVector = Icons.Rounded.BarChart, null)
                }
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(ConsonantClass.entries) {
                    Card(
                        modifier = Modifier.size(96.dp),
                        onClick = { navigateToFlashCardScreen(it) }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = it.name, style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Vowels",
                    style = MaterialTheme.typography.titleLarge
                )
                FilledTonalIconButton(
                    onClick = {}
                ) {
                    Icon(imageVector = Icons.Rounded.BarChart, null)
                }
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(VowelClass.entries) {
                    Card(
                        modifier = Modifier.size(96.dp),
                        onClick = { }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = it.name, style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }
            }
        }
    }
}