package com.fontaipi.riianthai.ui.page.vowel.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fontaipi.riianthai.model.SoundType
import com.fontaipi.riianthai.model.VowelClass
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

@Composable
fun VowelRoute(
    viewModel: VowelViewModel = hiltViewModel(),
    navigateToVowelDetail: (Long) -> Unit
) {
    val vowelsState by viewModel.vowels.collectAsStateWithLifecycle()
    VowelScreen(
        vowelsState = vowelsState,
        navigateToVowelDetail = navigateToVowelDetail
    )
}

@Composable
fun VowelScreen(
    vowelsState: VowelsState,
    navigateToVowelDetail: (Long) -> Unit
) {
    var selectedClass by remember { mutableStateOf<VowelClass?>(null) }
    var selectedSoundType by remember { mutableStateOf<SoundType?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Vowels",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            VowelClass.entries.forEach {
                val color = when (it) {
                    VowelClass.Short -> MaterialTheme.colorScheme.primaryContainer.copy(
                        alpha = 0.5f
                    )

                    VowelClass.Long -> MaterialTheme.colorScheme.primaryContainer
                    VowelClass.Special -> MaterialTheme.colorScheme.tertiaryContainer
                }
                FilterChip(
                    leadingIcon = {
                        if (it == selectedClass) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = color
                    ),
                    selected = it == selectedClass,
                    onClick = {
                        selectedClass = if (it == selectedClass) {
                            null
                        } else {
                            it
                        }
                    },
                    label = { Text(text = it.name) }
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            SoundType.entries.forEach {
                FilterChip(
                    leadingIcon = {
                        if (it == selectedSoundType) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    selected = it == selectedSoundType,
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f),
                        selectedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    onClick = {
                        selectedSoundType = if (it == selectedSoundType) {
                            null
                        } else {
                            it
                        }
                    },
                    label = { Text(text = it.name) }
                )
            }
        }

        when (vowelsState) {
            is VowelsState.Success -> {
                val filteredVowels by remember {
                    derivedStateOf {
                        vowelsState.vowels.filter {
                            if (selectedClass == null) {
                                true
                            } else {
                                it.vowelClass == selectedClass
                            }
                        }.filter {
                            if (selectedSoundType == null) {
                                true
                            } else {
                                it.soundType == selectedSoundType
                            }
                        }
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 54.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(filteredVowels) {
                        val color = when (it.vowelClass) {
                            VowelClass.Short -> MaterialTheme.colorScheme.primaryContainer.copy(
                                alpha = 0.5f
                            )

                            VowelClass.Long -> MaterialTheme.colorScheme.primaryContainer
                            VowelClass.Special -> MaterialTheme.colorScheme.tertiaryContainer
                        }
                        Card(
                            modifier = Modifier.aspectRatio(1f),
                            onClick = {
                                navigateToVowelDetail(it.id)
                            },
                            colors = CardDefaults.cardColors(
                                containerColor = color
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = it.thaiScript,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            }
                        }
                    }
                }
            }

            VowelsState.Loading -> CircularProgressIndicator()
        }
    }

}

@Preview
@Composable
private fun VowelScreenPreview() {
    RiianThaiTheme {
        VowelScreen(
            vowelsState = VowelsState.Success(emptyList()),
            navigateToVowelDetail = {}
        )
    }
}