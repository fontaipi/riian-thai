package com.fontaipi.riianthai.ui.page.flashcard.vowel

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fontaipi.riianthai.model.SoundType
import com.fontaipi.riianthai.model.VowelClass
import com.fontaipi.riianthai.model.VowelForm
import com.fontaipi.riianthai.ui.component.CardFace
import com.fontaipi.riianthai.ui.component.FlashcardSession
import com.fontaipi.riianthai.ui.component.VowelFormText
import com.fontaipi.riianthai.ui.page.flashcard.consonant.FlashcardUiState
import com.fontaipi.riianthai.ui.page.flashcard.vowel.component.BackVowelFlashcard

data class VowelFormFlashcard(
    val vowelForm: VowelForm,
    val vowelClass: VowelClass,
    val soundType: SoundType = SoundType.Monophthong,
    val thaiScript: String,
    val soundFile: String,
)

@Composable
fun FlashcardVowelRoute(
    onBackClick: () -> Unit,
    viewModel: FlashcardVowelViewModel = hiltViewModel(),
) {
    val flashcardVowelState by viewModel.flashcardVowelState.collectAsStateWithLifecycle()
    val flashcardUiState by viewModel.flashcardConsonantUiState.collectAsStateWithLifecycle()
    FlashcardVowelScreen(
        onBackClick = onBackClick,
        flashcardVowelState = flashcardVowelState,
        flashcardUiState = flashcardUiState,
        turnCard = viewModel::turnCard,
        nextCard = viewModel::nextCard,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardVowelScreen(
    flashcardVowelState: FlashcardVowelState,
    flashcardUiState: FlashcardUiState,
    turnCard: () -> Unit,
    nextCard: (Long, Boolean) -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (flashcardVowelState) {
                is FlashcardVowelState.Success -> {
                    val card by remember(flashcardUiState.selectedIndex) {
                        derivedStateOf {
                            flashcardVowelState.flashcards.getOrNull(
                                flashcardUiState.selectedIndex
                            )
                        }
                    }
                    FlashcardSession(
                        progress = (flashcardUiState.selectedIndex + 1).toFloat() / (flashcardVowelState.flashcards.size),
                        cardFace = flashcardUiState.cardFace,
                        front = {
                            card?.let {
                                VowelFormText(
                                    vowelForm = it.vowelForm,
                                    style = MaterialTheme.typography.displayLarge
                                )
                            }
                        },
                        back = {
                            card?.let {
                                BackVowelFlashcard(
                                    vowelFormFlashcard = it
                                )
                            }
                        },
                        nextCard = {
                            card?.let { card ->
                                nextCard(card.vowelForm.id, it)
                            }
                        },
                        turnCard = turnCard,
                    )
                }

                FlashcardVowelState.Loading -> CircularProgressIndicator()
            }
        }
    }
}