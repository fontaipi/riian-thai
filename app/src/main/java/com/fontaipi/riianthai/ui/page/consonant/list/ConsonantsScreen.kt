package com.fontaipi.riianthai.ui.page.consonant.list

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
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.model.EndingClass
import com.fontaipi.riianthai.model.EndingSound
import com.fontaipi.riianthai.ui.page.summary.component.ConsonantCard
import com.fontaipi.riianthai.ui.theme.HighClassColor
import com.fontaipi.riianthai.ui.theme.LowClassColor
import com.fontaipi.riianthai.ui.theme.MiddleClassColor
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

@Composable
fun ConsonantsRoute(
    navigateToConsonantDetail: (Long) -> Unit,
    viewModel: ConsonantsViewModel = hiltViewModel()
) {
    val consonantsState by viewModel.consonants.collectAsStateWithLifecycle()
    ConsonantsScreen(
        navigateToConsonantDetail = navigateToConsonantDetail,
        consonantsState = consonantsState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsonantsScreen(
    navigateToConsonantDetail: (Long) -> Unit,
    consonantsState: ConsonantsState
) {
    var selectedClass by remember { mutableStateOf<ConsonantClass?>(null) }
    var selectedEndingClass by remember { mutableStateOf<EndingClass?>(null) }
    var selectedEndingSound by remember { mutableStateOf<EndingSound?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
    ) {
        Text(text = "Consonants", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ConsonantClass.entries.forEach {
                val color = when (it) {
                    ConsonantClass.Low -> LowClassColor
                    ConsonantClass.Mid -> MiddleClassColor
                    ConsonantClass.High -> HighClassColor
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
            EndingClass.entries.filterNot { it == EndingClass.None }.forEach {
                FilterChip(
                    leadingIcon = {
                        if (it == selectedEndingClass) {
                            Icon(
                                imageVector = Icons.Rounded.Check,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    selected = it == selectedEndingClass,
                    onClick = {
                        selectedEndingClass = if (it == selectedEndingClass) {
                            null
                        } else {
                            it
                        }
                    },
                    label = { Text(text = it.name) }
                )
            }

            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
            ) {
                FilterChip(
                    modifier = Modifier.menuAnchor(),
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        iconColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    selected = selectedEndingSound != null,
                    onClick = {
                        expanded = true
                    },
                    label = {
                        Text(text = selectedEndingSound?.name ?: "Ending sound")
                    }
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(
                        text = { Text("") },
                        onClick = {
                            selectedEndingSound = null
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                    EndingSound.entries.filterNot { it == EndingSound.Impossible }.forEach {
                        DropdownMenuItem(
                            text = { Text(it.name) },
                            onClick = {
                                selectedEndingSound = it
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        when (consonantsState) {
            is ConsonantsState.Success -> {
                val filteredConsonants by remember {
                    derivedStateOf {
                        consonantsState.consonants.filter {
                            if (selectedClass == null) {
                                true
                            } else {
                                it.consonantClass == selectedClass
                            }
                        }.filter {
                            if (selectedEndingClass == null) {
                                true
                            } else {
                                it.endingSound.endingClass == selectedEndingClass
                            }
                        }.filter {
                            if (selectedEndingSound == null) {
                                true
                            } else {
                                it.endingSound == selectedEndingSound
                            }
                        }
                    }
                }

                LazyVerticalGrid(
                    modifier = Modifier.weight(1f),
                    columns = GridCells.Adaptive(minSize = 54.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(filteredConsonants) {
                        val color = when (it.consonantClass) {
                            ConsonantClass.Low -> LowClassColor
                            ConsonantClass.Mid -> MiddleClassColor
                            ConsonantClass.High -> HighClassColor
                        }
                        ConsonantCard(
                            modifier = Modifier.aspectRatio(1f),
                            backgroundColor = color,
                            consonant = it.thai,
                            onClick = { navigateToConsonantDetail(it.id) }
                        )
                    }
                }
            }

            ConsonantsState.Loading -> {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ClassChip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    selected: Boolean = false
) {
    Surface(
        modifier = modifier,
        color = if (selected) color.copy(alpha = 0.75f) else color.copy(alpha = 0.25f),
        onClick = onClick,
    ) {
        Box(
            modifier = Modifier.size(height = 78.dp, width = 48.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                modifier = Modifier.rotate(270f),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview
@Composable
private fun ClassClipPreview() {
    RiianThaiTheme {
        ClassChip(
            text = "Low",
            onClick = {},
            color = LowClassColor
        )
    }
}