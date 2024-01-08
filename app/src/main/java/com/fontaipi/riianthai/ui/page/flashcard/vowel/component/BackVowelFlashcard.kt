package com.fontaipi.riianthai.ui.page.flashcard.vowel.component

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.VolumeUp
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fontaipi.riianthai.model.Vowel
import com.fontaipi.riianthai.model.sampleVowel
import com.fontaipi.riianthai.ui.component.VowelFormText
import com.fontaipi.riianthai.ui.page.consonant.detail.component.Tag
import com.fontaipi.riianthai.ui.page.flashcard.vowel.VowelFormFlashcard
import com.fontaipi.riianthai.ui.page.vowel.detail.component.VowelFormColorCode
import com.fontaipi.riianthai.ui.theme.FinalConsonantColor
import com.fontaipi.riianthai.ui.theme.InitialConsonantColor
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme
import com.fontaipi.riianthai.ui.theme.VowelColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BackVowelFlashcard(
    vowelFormFlashcard: VowelFormFlashcard,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = vowelFormFlashcard.thaiScript,
                style = MaterialTheme.typography.displayMedium
            )
            FilledIconButton(
                onClick = {
                    coroutineScope.launch {
                        val mediaPlayer = MediaPlayer()
                        context.assets.openFd(vowelFormFlashcard.soundFile).use { fd ->
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
        }

//        FlowRow(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            VowelFormColorCode(
//                text = "Initial consonant",
//                color = InitialConsonantColor
//            )
//            VowelFormColorCode(
//                text = "Vowel",
//                color = VowelColor
//            )
//            VowelFormColorCode(
//                text = "Final consonant",
//                color = FinalConsonantColor
//            )
//        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                VowelFormText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    vowelForm = vowelFormFlashcard.vowelForm,
                    style = MaterialTheme.typography.displayLarge
                )
                Text(text = vowelFormFlashcard.vowelForm.note)
                Text(text = "Example word:", style = MaterialTheme.typography.titleMedium)
                OutlinedCard(
                    onClick = {
                        if (vowelFormFlashcard.vowelForm.exampleWord.audioFile.isNotBlank()) {
                            coroutineScope.launch {
                                val mediaPlayer = MediaPlayer()
                                context.assets.openFd(vowelFormFlashcard.vowelForm.exampleWord.audioFile)
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
                    },
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
                                vowelFormFlashcard.vowelForm.exampleWord.thai,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                vowelFormFlashcard.vowelForm.exampleWord.meaning,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        if (vowelFormFlashcard.vowelForm.exampleWord.audioFile.isNotBlank()) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.VolumeUp,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Tag(
                        text = vowelFormFlashcard.vowelClass.name,
                        color = MaterialTheme.colorScheme.tertiaryContainer
                    )
                    Tag(
                        text = vowelFormFlashcard.soundType.name,
                        color = MaterialTheme.colorScheme.tertiaryContainer
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun BackVowelFlashcardPreview() {
    RiianThaiTheme {
        BackVowelFlashcard(
            vowelFormFlashcard = VowelFormFlashcard(
                vowelForm = sampleVowel.writingForms.first(),
                vowelClass = sampleVowel.vowelClass,
                thaiScript = sampleVowel.thaiScript,
                soundFile = sampleVowel.soundFile,

                ),
        )
    }
}