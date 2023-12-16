package com.fontaipi.riianthai.ui.page.flashcard.consonant

import android.media.MediaPlayer
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.ui.page.consonant.detail.component.Tag
import com.fontaipi.riianthai.ui.page.flashcard.consonant.component.FlashCard
import com.fontaipi.riianthai.ui.page.flashcard.consonant.component.PhoneticText
import com.fontaipi.riianthai.ui.theme.HighClassColor
import com.fontaipi.riianthai.ui.theme.LowClassColor
import com.fontaipi.riianthai.ui.theme.MidClassColor
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
        flashcardConsonantUiState = flashcardConsonantUiState,
        turnCard = viewModel::turnCard,
        nextCard = viewModel::nextCard,
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashcardConsonantScreen(
    flashcardConsonantState: FlashcardConsonantState,
    flashcardConsonantUiState: FlashcardConsonantUiState,
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
                    val context = LocalContext.current
                    val coroutineScope = rememberCoroutineScope()
                    val card by remember(flashcardConsonantUiState.selectedIndex) {
                        derivedStateOf {
                            flashcardConsonantState.consonants.getOrNull(
                                flashcardConsonantUiState.selectedIndex
                            )
                        }
                    }

                    val progressAnimation by animateFloatAsState(
                        targetValue = (flashcardConsonantUiState.selectedIndex + 1).toFloat() / flashcardConsonantState.consonants.size,
                        animationSpec = tween(), label = "progressAnimation"
                    )

                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp),
                        progress = { progressAnimation },
                        strokeCap = StrokeCap.Round
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(20.dp)
                    ) {
                        FlashCard(
                            cardFace = flashcardConsonantUiState.cardFace,
                            onClick = turnCard,
                            front = {
                                card?.let {
                                    Text(it.thai, style = MaterialTheme.typography.displayLarge)
                                }
                            },
                            back = {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    card?.let {
                                        if (it.picture.isNotEmpty()) {
                                            AsyncImage(
                                                model = "file:///android_asset/${it.picture}",
                                                contentDescription = "",
                                            )
                                        }
                                        Text(
                                            text = "${it.associatedWord} (${it.meaning})",
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = it.thai,
                                                style = MaterialTheme.typography.displayLarge
                                            )
                                            PhoneticText(
                                                text = it.phonetic,
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                        }
                                        FilledIconButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    val mediaPlayer = MediaPlayer()
                                                    context.assets.openFd(it.audio).use { fd ->
                                                        mediaPlayer.setDataSource(
                                                            fd.fileDescriptor,
                                                            fd.startOffset,
                                                            fd.length
                                                        )
                                                        mediaPlayer.prepare()
                                                        mediaPlayer.start()
                                                    }
                                                }
                                            },
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.PlayArrow,
                                                contentDescription = null
                                            )
                                        }
                                        val color = when (it.consonantClass) {
                                            ConsonantClass.Low -> LowClassColor
                                            ConsonantClass.Mid -> MidClassColor
                                            ConsonantClass.High -> HighClassColor
                                        }
                                        Box(
                                            modifier = Modifier.fillMaxWidth(),
                                            contentAlignment = Alignment.CenterEnd
                                        ) {
                                            Tag(
                                                text = it.consonantClass.name,
                                                color = color
                                            )
                                        }

                                    }
                                }
                            }
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        FilledTonalIconButton(
                            enabled = flashcardConsonantUiState.selectedIndex < flashcardConsonantState.consonants.size - 1,
                            onClick = {
                                card?.let {
                                    nextCard(it.id, false)
                                }
                            }
                        ) {
                            Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
                        }
                        FilledTonalIconButton(
                            enabled = flashcardConsonantUiState.selectedIndex < flashcardConsonantState.consonants.size - 1,
                            onClick = {
                                card?.let {
                                    nextCard(it.id, true)
                                }
                            }
                        ) {
                            Icon(imageVector = Icons.Rounded.Check, contentDescription = null)
                        }
                    }
                }

                FlashcardConsonantState.Loading -> CircularProgressIndicator()
            }
        }
    }
}

