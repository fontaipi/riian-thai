package com.fontaipi.riianthai.ui.page.consonant.detail

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.fontaipi.riianthai.ui.component.WordCard
import com.fontaipi.riianthai.ui.page.consonant.detail.component.DiphthongDialog
import com.fontaipi.riianthai.ui.page.consonant.detail.component.EndingConsonantDialog
import com.fontaipi.riianthai.ui.page.consonant.detail.component.Tag
import com.fontaipi.riianthai.ui.page.flashcard.component.PhoneticText
import com.fontaipi.riianthai.ui.theme.HighClassColor
import com.fontaipi.riianthai.ui.theme.LowClassColor
import com.fontaipi.riianthai.ui.theme.MiddleClassColor
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
        onBackClick = onBackClick
    )
}

@Composable
fun ConsonantDetailScreen(
    consonantDetailState: ConsonantDetailState,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    var showDiphthongDialog by remember { mutableStateOf(false) }
    var showEndingConsonantDialog by remember { mutableStateOf(false) }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.End),
                onClick = onBackClick,
            ) {
                Icon(Icons.Rounded.Close, null)
            }
            when (consonantDetailState) {
                is ConsonantDetailState.Success -> {
                    val coroutineScope = rememberCoroutineScope()
                    val context = LocalContext.current
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val color = when (consonantDetailState.consonant.consonantClass) {
                                ConsonantClass.Low -> LowClassColor
                                ConsonantClass.Mid -> MiddleClassColor
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
                                                imageVector = Icons.Rounded.VolumeUp,
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
                            Divider()
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
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Words with ${consonantDetailState.consonant.thai}",
                                style = MaterialTheme.typography.titleLarge
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                consonantDetailState.consonant.exampleWords.forEach {
                                    WordCard(
                                        modifier = Modifier.fillMaxWidth(),
                                        word = it.thai,
                                        meaning = it.meaning,
                                        onEdit = { },
                                        onDelete = { }
                                    )
                                }
                            }
                        }
                    }
                }

                ConsonantDetailState.Loading -> CircularProgressIndicator()
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
            onBackClick = {}
        )
    }
}