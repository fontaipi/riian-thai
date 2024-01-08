package com.fontaipi.riianthai.ui.page.flashcard.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.model.VowelClass
import com.fontaipi.riianthai.ui.page.practice.component.FlashcardCategoryCard
import com.fontaipi.riianthai.ui.theme.HighClassColor
import com.fontaipi.riianthai.ui.theme.LowClassColor
import com.fontaipi.riianthai.ui.theme.MidClassColor
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

@Composable
fun FlashcardsRoute(
    viewModel: FlashcardsViewModel = hiltViewModel(),
    navigateToFlashcardConsonant: (ConsonantClass?) -> Unit,
    navigateToFlashcardVowel: (VowelClass?) -> Unit,
) {
    val flashcardsState by viewModel.flashcardsState.collectAsStateWithLifecycle()
    FlashcardsScreen(
        flashcardsState = flashcardsState,
        navigateToFlashcardConsonant = navigateToFlashcardConsonant,
        navigateToFlashcardVowel = navigateToFlashcardVowel,
    )
}

@Composable
fun FlashcardsScreen(
    flashcardsState: FlashcardsState,
    navigateToFlashcardConsonant: (ConsonantClass?) -> Unit,
    navigateToFlashcardVowel: (VowelClass?) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Flashcards",
            style = MaterialTheme.typography.headlineMedium
        )
        when (flashcardsState) {
            is FlashcardsState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        onClick = { navigateToFlashcardConsonant(null) },
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween

                        ) {
                            Text(text = "Consonants", style = MaterialTheme.typography.titleMedium)
                            Column(
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "${
                                        flashcardsState.consonantProgress.times(100).toInt()
                                    }%",
                                    style = MaterialTheme.typography.labelLarge
                                )
                                LinearProgressIndicator(
                                    modifier = Modifier
                                        .height(6.dp)
                                        .width(120.dp),
                                    progress = { flashcardsState.consonantProgress },
                                    trackColor = MaterialTheme.colorScheme.surface,
                                    strokeCap = StrokeCap.Round
                                )
                            }
                        }
                    }
                    Card(
                        onClick = { navigateToFlashcardVowel(null) },
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) {
                            Text(text = "Vowels", style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = "Start your flashcard session",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            FlashcardsState.Loading -> CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
private fun FlashcardsScreenPreview() {
    RiianThaiTheme {
        FlashcardsScreen(
            flashcardsState = FlashcardsState.Success(
                consonantProgress = 0.5f,
                vowelProgress = 0.5f
            ),
            navigateToFlashcardConsonant = {},
            navigateToFlashcardVowel = {}
        )
    }
}