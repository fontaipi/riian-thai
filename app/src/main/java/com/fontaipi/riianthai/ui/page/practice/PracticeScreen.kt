package com.fontaipi.riianthai.ui.page.practice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fontaipi.riianthai.ui.page.practice.component.PracticeCategoryCard
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

@Composable
fun PracticeRoute(
    viewModel: PracticeViewModel = hiltViewModel(),
    navigateToFlashcard: () -> Unit,
    navigateToVocabulary: () -> Unit,
) {
    val practiceState by viewModel.practiceState.collectAsStateWithLifecycle()
    PracticeScreen(
        practiceState = practiceState,
        navigateToFlashcard = navigateToFlashcard,
        navigateToVocabulary = navigateToVocabulary,
    )
}

@Composable
fun PracticeScreen(
    practiceState: PracticeState,
    navigateToFlashcard: () -> Unit,
    navigateToVocabulary: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (practiceState) {
            is PracticeState.Success -> {
                Text(text = "Practice", style = MaterialTheme.typography.headlineMedium)
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    item {
                        PracticeCategoryCard(
                            title = "Flashcards",
                            onClick = navigateToFlashcard,
                            progress = practiceState.flashcardProgress,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            circleColor = MaterialTheme.colorScheme.primary
                        )
                    }
                    item {
                        PracticeCategoryCard(
                            title = "Vocabulary",
                            onClick = navigateToVocabulary,
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            circleColor = MaterialTheme.colorScheme.secondary
                        )
                    }
                    item {
                        PracticeCategoryCard(
                            title = "Pronunciation",
                            onClick = {},
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            circleColor = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }

            is PracticeState.Loading -> CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
private fun PracticeScreenPreview() {
    RiianThaiTheme {
        PracticeScreen(
            practiceState = PracticeState.Success(0.5f),
            navigateToFlashcard = {},
            navigateToVocabulary = {}
        )
    }
}