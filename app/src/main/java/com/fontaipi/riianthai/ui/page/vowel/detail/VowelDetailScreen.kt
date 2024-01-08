package com.fontaipi.riianthai.ui.page.vowel.detail

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.VolumeUp
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fontaipi.riianthai.model.Vowel
import com.fontaipi.riianthai.model.VowelClass
import com.fontaipi.riianthai.model.VowelForm
import com.fontaipi.riianthai.model.Word
import com.fontaipi.riianthai.ui.component.VowelFormText
import com.fontaipi.riianthai.ui.page.consonant.detail.component.Tag
import com.fontaipi.riianthai.ui.page.vowel.detail.component.VowelFormColorCode
import com.fontaipi.riianthai.ui.theme.FinalConsonantColor
import com.fontaipi.riianthai.ui.theme.InitialConsonantColor
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme
import com.fontaipi.riianthai.ui.theme.VowelColor
import kotlinx.coroutines.launch

@Composable
fun VowelDetailRoute(
    viewModel: VowelDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val vowelDetailState by viewModel.vowel.collectAsStateWithLifecycle()
    VowelDetailScreen(
        vowelDetailState = vowelDetailState,
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun VowelDetailScreen(
    vowelDetailState: VowelDetailState,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (vowelDetailState) {
                is VowelDetailState.Success -> {
                    val color = when (vowelDetailState.vowel.vowelClass) {
                        VowelClass.Short -> MaterialTheme.colorScheme.primaryContainer.copy(
                            alpha = 0.5f
                        )

                        VowelClass.Long -> MaterialTheme.colorScheme.primaryContainer
                        VowelClass.Special -> MaterialTheme.colorScheme.tertiaryContainer
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Sound",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Text(
                                    text = vowelDetailState.vowel.thaiScript,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }

                            FilledTonalIconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        val mediaPlayer = MediaPlayer()
                                        context.assets.openFd(vowelDetailState.vowel.soundFile)
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
                                Icon(
                                    imageVector = Icons.Rounded.PlayArrow,
                                    contentDescription = "Sound"
                                )
                            }
                        }
                        FlowRow(
                            verticalArrangement = Arrangement.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            Tag(
                                text = vowelDetailState.vowel.vowelClass.name,
                                color = color,
                                style = MaterialTheme.typography.labelMedium
                            )
                            Tag(
                                text = vowelDetailState.vowel.soundType.name,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }


                    if (vowelDetailState.vowel.note.isNotEmpty()) {
                        Text(text = vowelDetailState.vowel.note)
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = "Forms", style = MaterialTheme.typography.titleLarge)
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                VowelFormColorCode(
                                    text = "Initial consonant",
                                    color = InitialConsonantColor
                                )
                                VowelFormColorCode(
                                    text = "Vowel",
                                    color = VowelColor
                                )
                                VowelFormColorCode(
                                    text = "Final consonant",
                                    color = FinalConsonantColor
                                )
                            }
                        }

                        LazyColumn {
                            itemsIndexed(vowelDetailState.vowel.writingForms) { index, vowelForm ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                                    ) {
                                        VowelFormText(
                                            modifier = Modifier.weight(1f),
                                            vowelForm = vowelForm
                                        )
                                        Card(
                                            modifier = Modifier.weight(1f),
                                            onClick = {
                                                if (vowelForm.exampleWord.audioFile.isNotBlank()) {
                                                    coroutineScope.launch {
                                                        val mediaPlayer = MediaPlayer()
                                                        context.assets.openFd(vowelForm.exampleWord.audioFile)
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
                                            }
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(10.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column {
                                                    Text(
                                                        vowelForm.exampleWord.thai,
                                                        style = MaterialTheme.typography.titleLarge
                                                    )
                                                    Text(
                                                        vowelForm.exampleWord.meaning,
                                                        style = MaterialTheme.typography.titleMedium
                                                    )
                                                }
                                                if (vowelForm.exampleWord.audioFile.isNotBlank()) {
                                                    Icon(
                                                        imageVector = Icons.AutoMirrored.Rounded.VolumeUp,
                                                        contentDescription = null
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    if (vowelForm.note.isNotEmpty()) {
                                        Text(
                                            text = vowelForm.note,
                                            color = MaterialTheme.colorScheme.outline
                                        )
                                    }
                                    if (index < vowelDetailState.vowel.writingForms.lastIndex) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        HorizontalDivider()
                                    }
                                }
                            }
                        }
                    }
                }

                is VowelDetailState.Loading -> {
                    CircularProgressIndicator()
                }
            }
        }
    }

}

@Preview
@Composable
private fun VowelDetailScreenPreview() {
    RiianThaiTheme {
        VowelDetailScreen(
            vowelDetailState = VowelDetailState.Success(
                vowel = Vowel(
                    id = 1,
                    thaiScript = "อิ",
                    vowelClass = VowelClass.Long,
                    audioFile = "",
                    soundFile = "",
                    writingForms = listOf(
                        VowelForm(
                            id = 1,
                            format = "I",
                            accentIndicator = "",
                            note = "This is a note",
                            exampleWord = Word(
                                thai = "ไอ",
                                meaning = "eye",
                                audioFile = "",
                            )
                        ),
                        VowelForm(
                            id = 2,
                            format = "E",
                            accentIndicator = "*",
                            note = "",
                            exampleWord = Word(
                                thai = "ไอ",
                                meaning = "eye",
                                audioFile = "",
                            )
                        ),
                    ),
                    note = "The first vowel of the Thai alphabet, pronounced as the 'ee' in 'seen', but shorter. The vowel is pronounced with a high tone."
                )
            ),
            onBackClick = {}
        )
    }
}