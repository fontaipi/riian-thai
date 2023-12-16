package com.fontaipi.riianthai.ui.page.consonant.detail

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.VolumeUp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.model.EndingSound
import com.fontaipi.riianthai.model.Word
import com.fontaipi.riianthai.ui.component.WordCard
import com.fontaipi.riianthai.ui.page.consonant.detail.component.ConfirmDeleteWordDialog
import com.fontaipi.riianthai.ui.page.consonant.detail.component.DiphthongDialog
import com.fontaipi.riianthai.ui.page.consonant.detail.component.EndingConsonantDialog
import com.fontaipi.riianthai.ui.page.consonant.detail.component.Tag
import com.fontaipi.riianthai.ui.page.consonant.detail.component.UpsertWordBottomSheet
import com.fontaipi.riianthai.ui.component.PhoneticText
import com.fontaipi.riianthai.ui.theme.HighClassColor
import com.fontaipi.riianthai.ui.theme.LowClassColor
import com.fontaipi.riianthai.ui.theme.MidClassColor
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@Composable
fun ConsonantDetailRoute(
    onBackClick: () -> Unit,
    viewModel: ConsonantDetailViewModel = hiltViewModel()
) {
    val consonantDetailState by viewModel.consonant.collectAsStateWithLifecycle()
    ConsonantDetailScreen(
        consonantDetailState = consonantDetailState,
        insertWord = viewModel::insertWord,
        updateWord = viewModel::updateWord,
        deleteWord = viewModel::deleteWord,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsonantDetailScreen(
    consonantDetailState: ConsonantDetailState,
    insertWord: (Word, Long) -> Unit,
    updateWord: (Word) -> Unit,
    deleteWord: (Word) -> Unit,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    var showDiphthongDialog by remember { mutableStateOf(false) }
    var showEndingConsonantDialog by remember { mutableStateOf(false) }
    var showConfirmDeleteWordDialog by remember { mutableStateOf(false) }

    var showEditWordBottomSheet by remember { mutableStateOf(false) }
    var selectedWord by remember { mutableStateOf<Word?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingsValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingsValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (consonantDetailState) {
                is ConsonantDetailState.Success -> {
                    val coroutineScope = rememberCoroutineScope()
                    val context = LocalContext.current

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val color = when (consonantDetailState.consonant.consonantClass) {
                                ConsonantClass.Low -> LowClassColor
                                ConsonantClass.Mid -> MidClassColor
                                ConsonantClass.High -> HighClassColor
                            }
                            Tag(
                                text = consonantDetailState.consonant.consonantClass.name,
                                color = color
                            )
                            Text(
                                text = DecimalFormat("00").format(consonantDetailState.consonant.id),
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(3 / 5f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = consonantDetailState.consonant.thai,
                                        style = MaterialTheme.typography.displayLarge.copy(fontSize = 104.sp)
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text(
                                                text = "${consonantDetailState.consonant.thai}อ ${consonantDetailState.consonant.associatedWord}",
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                            PhoneticText(
                                                text = consonantDetailState.consonant.phonetic,
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                        }
                                        FilledIconButton(
                                            onClick = {
                                                coroutineScope.launch {
                                                    val mediaPlayer = MediaPlayer()
                                                    context.assets.openFd(consonantDetailState.consonant.audio)
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
                                            },
                                        ) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Rounded.VolumeUp,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }
                                Column(
                                    modifier = Modifier.weight(2 / 5f),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    if (consonantDetailState.consonant.picture.isNotEmpty()) {
                                        AsyncImage(
                                            modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                                            model = "file:///android_asset/${consonantDetailState.consonant.picture}",
                                            contentDescription = "",
                                        )
                                    }
                                    Text(
                                        text = "${consonantDetailState.consonant.associatedWord} (${consonantDetailState.consonant.meaning})",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                            HorizontalDivider()
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Text(
                                        "Ending sound:",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = consonantDetailState.consonant.endingSound.phonetic,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                                if (consonantDetailState.consonant.endingSound != EndingSound.Impossible) {
                                    Column(
                                        horizontalAlignment = Alignment.End,
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            Tag(text = consonantDetailState.consonant.endingSound.endingClass.name)
                                            IconButton(onClick = {
                                                showEndingConsonantDialog = true
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Outlined.Info,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                        if (consonantDetailState.consonant.endingSound == EndingSound.Y || consonantDetailState.consonant.endingSound == EndingSound.W) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                            ) {
                                                Tag(text = "Diphthong")
                                                IconButton(onClick = {
                                                    showDiphthongDialog = true
                                                }) {
                                                    Icon(
                                                        imageVector = Icons.Outlined.Info,
                                                        contentDescription = null
                                                    )
                                                }
                                            }
                                        }
                                    }

                                } else {
                                    Text(
                                        text = "Invalid consonant ending",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Words with ${consonantDetailState.consonant.thai}",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                FilledIconButton(
                                    onClick = {
                                        showEditWordBottomSheet = true
                                    }
                                ) {
                                    Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                                }
                            }


                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                consonantDetailState.consonant.exampleWords.forEach {
                                    WordCard(
                                        modifier = Modifier.fillMaxWidth(),
                                        word = it.thai,
                                        meaning = it.meaning,
                                        onEdit = {
                                            selectedWord = it
                                            showEditWordBottomSheet = true
                                        },
                                        onDelete = {
                                            selectedWord = it
                                            showConfirmDeleteWordDialog = true
                                        }
                                    )
                                }
                            }
                        }
                    }
                    if (showDiphthongDialog) {
                        DiphthongDialog(
                            onDismissRequest = { showDiphthongDialog = false }
                        )
                    }

                    if (showEndingConsonantDialog) {
                        EndingConsonantDialog(
                            onDismissRequest = { showEndingConsonantDialog = false }
                        )
                    }

                    if (showEditWordBottomSheet) {
                        UpsertWordBottomSheet(
                            word = selectedWord,
                            onConfirm = { thai, meaning ->
                                if (selectedWord == null) {
                                    insertWord(
                                        Word(thai = thai, meaning = meaning),
                                        consonantDetailState.consonant.id
                                    )
                                } else {
                                    updateWord(selectedWord!!)
                                }
                                showEditWordBottomSheet = false
                                selectedWord = null
                            },
                            onDismissRequest = {
                                showEditWordBottomSheet = false
                                selectedWord = null
                            }
                        )
                    }

                    if (showConfirmDeleteWordDialog && selectedWord != null) {
                        ConfirmDeleteWordDialog(
                            onConfirmRequest = {
                                deleteWord(selectedWord!!)
                                showConfirmDeleteWordDialog = false
                                selectedWord = null
                            },
                            onDismissRequest = {
                                showConfirmDeleteWordDialog = false
                                selectedWord = null
                            }
                        )
                    }
                }

                ConsonantDetailState.Loading -> CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
private fun ConsonantDetailScreenPreview() {
    MaterialTheme {
        ConsonantDetailScreen(
            consonantDetailState = ConsonantDetailState.Success(
                Consonant(
                    id = 1,
                    thai = "ก",
                    phonetic = "gaaw^M-gai^L",
                    associatedWord = "ไก่",
                    meaning = "Chicken",
                    consonantClass = ConsonantClass.Low,
                    endingSound = EndingSound.Impossible,
                    picture = "",
                    audio = "consonants/ก.mp3"
                )
            ),
            insertWord = { _, _ -> },
            updateWord = {},
            deleteWord = {},
            onBackClick = {}
        )
    }
}