package com.fontaipi.riianthai.ui.page.flashcard

import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.ui.page.flashcard.component.FlashCard
import com.fontaipi.riianthai.ui.page.flashcard.component.PhoneticText
import kotlinx.coroutines.launch

@Composable
fun FlashcardScreen(
    viewModel: FlashcardViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val flashCardState by viewModel.flashCardState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    var filter by remember { mutableStateOf<ConsonantClass?>(null) }
    val filteredCards by remember { derivedStateOf { flashCardState.consonants.filter { filter == null || it.consonantClass == filter } } }
    val card by remember { derivedStateOf { filteredCards.getOrNull(flashCardState.selectedIndex) } }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.End),
            onClick = onBackClick,
        ) {
            Icon(Icons.Rounded.Close, null)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "${flashCardState.selectedIndex + 1}/${filteredCards.size}",
                style = MaterialTheme.typography.labelLarge
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = viewModel::shuffle
                ) {
                    Icon(imageVector = Icons.Rounded.Shuffle, contentDescription = null)
                }
                IconButton(
                    onClick = viewModel::restart
                ) {
                    Icon(imageVector = Icons.Rounded.Refresh, contentDescription = null)
                }
            }

        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(20.dp)
        ) {
            FlashCard(
                cardFace = flashCardState.cardFace,
                onClick = viewModel::turnCard,
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
                                Text(text = it.thai, style = MaterialTheme.typography.displayLarge)
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
                            Surface(
                                modifier = Modifier.align(Alignment.End),
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.secondary,
                            ) {
                                Text(
                                    modifier = Modifier.padding(
                                        horizontal = 16.dp,
                                        vertical = 4.dp
                                    ),
                                    text = it.consonantClass.name,
                                    style = MaterialTheme.typography.titleMedium
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
                enabled = flashCardState.selectedIndex < filteredCards.size - 1,
                onClick = {
                    card?.let {
                        viewModel.nextCard(it.id, false)
                    }
                }
            ) {
                Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
            }
            FilledTonalIconButton(
                enabled = flashCardState.selectedIndex < filteredCards.size - 1,
                onClick = {
                    card?.let {
                        viewModel.nextCard(it.id, true)
                    }
                }
            ) {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = null)
            }
        }
    }
}

