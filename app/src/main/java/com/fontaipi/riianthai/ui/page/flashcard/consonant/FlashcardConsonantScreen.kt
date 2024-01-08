package com.fontaipi.riianthai.ui.page.flashcard.consonant

import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.VolumeUp
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.ui.component.FlashcardSession
import com.fontaipi.riianthai.ui.component.FlashcardSessionSuccessRate
import com.fontaipi.riianthai.ui.page.flashcard.consonant.component.BackConsonantFlashcard
import kotlinx.coroutines.launch

@Composable
fun FlashcardConsonantRoute(
    viewModel: FlashcardViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val flashcardConsonantState by viewModel.flashcardConsonantState.collectAsStateWithLifecycle()
    val flashcardConsonantUiState by viewModel.flashcardConsonantUiState.collectAsStateWithLifecycle()
    FlashcardConsonantScreen(
        flashcardConsonantState = flashcardConsonantState,
        flashcardUiState = flashcardConsonantUiState,
        turnCard = viewModel::turnCard,
        nextCard = viewModel::nextCard,
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardConsonantScreen(
    flashcardConsonantState: FlashcardConsonantState,
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
            when (flashcardConsonantState) {
                is FlashcardConsonantState.Success -> {
                    val card by remember(flashcardUiState.selectedIndex) {
                        derivedStateOf {
                            flashcardConsonantState.consonants.getOrNull(
                                flashcardUiState.selectedIndex
                            )
                        }
                    }

                    if (flashcardUiState.selectedIndex < flashcardConsonantState.consonants.size) {
                        FlashcardSession(
                            progress = (flashcardUiState.selectedIndex + 1).toFloat() / (flashcardConsonantState.consonants.size),
                            cardFace = flashcardUiState.cardFace,
                            front = {
                                card?.let {
                                    Text(it.thai, style = MaterialTheme.typography.displayLarge)
                                }
                            },
                            back = {
                                card?.let {
                                    BackConsonantFlashcard(
                                        consonant = it
                                    )
                                }
                            },
                            nextCard = {
                                card?.let { card ->
                                    nextCard(card.id, it)
                                }
                            },
                            turnCard = turnCard,
                        )
                    } else {
                        FlashcardConsonantSuccessSection(
                            wrongAnswerConsonants = flashcardConsonantState.consonants.filter { it.id in flashcardUiState.wrongAnswerIds },
                            successRate = 1f - (flashcardUiState.wrongAnswerIds.size / flashcardConsonantState.consonants.size.toFloat())
                        )
                    }
                }

                FlashcardConsonantState.Loading -> CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun FlashcardConsonantSuccessSection(
    successRate: Float,
    wrongAnswerConsonants: List<Consonant>,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        FlashcardSessionSuccessRate(successRate = successRate)
        if (wrongAnswerConsonants.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Your mistakes",
                    style = MaterialTheme.typography.titleLarge
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(wrongAnswerConsonants) {
                        Card(
                            onClick = {
                                coroutineScope.launch {
                                    val mediaPlayer = MediaPlayer()
                                    context.assets.openFd(it.audio)
                                        .use { fd ->
                                            mediaPlayer.setDataSource(
                                                fd.fileDescriptor,
                                                fd.startOffset,
                                                fd.length
                                            )
                                            mediaPlayer.prepare()
                                            mediaPlayer.start()
                                        }
                                }
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                                    .padding(end = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        8.dp
                                    )
                                ) {
                                    AsyncImage(
                                        modifier = Modifier
                                            .size(72.dp)
                                            .clip(MaterialTheme.shapes.medium),
                                        model = "file:///android_asset/${it.picture}",
                                        contentDescription = "",
                                    )
                                    Column {
                                        Text(
                                            text = it.thai,
                                            style = MaterialTheme.typography.headlineLarge
                                        )
                                        Text(
                                            text = it.meaning,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                }
                                Icon(
                                    imageVector = Icons.AutoMirrored.Rounded.VolumeUp,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

