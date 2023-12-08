package com.fontaipi.riianthai.ui.page.consonant.detail

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.VolumeOff
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.ui.component.WordCard
import com.fontaipi.riianthai.ui.page.flashcard.component.PhoneticText
import com.fontaipi.riianthai.ui.theme.HighClassColor
import com.fontaipi.riianthai.ui.theme.LowClassColor
import com.fontaipi.riianthai.ui.theme.MiddleClassColor
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme
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
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                                ConsonantClass.All -> MaterialTheme.colorScheme.surfaceVariant
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
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
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
                                        IconButton(
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
                                    modifier = Modifier.weight(1f),
                                    horizontalAlignment = Alignment.CenterHorizontally
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
                                    Text(text = "/k/", style = MaterialTheme.typography.titleMedium)
                                }
                                Tag(
                                    icon = Icons.Rounded.VolumeOff,
                                    text = "Silent",
                                    color = MaterialTheme.colorScheme.tertiaryContainer,
                                )
                            }
                            Divider()
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Live ending consonant",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Tag(
                                    text = "Dead",
                                    color = MaterialTheme.colorScheme.tertiaryContainer,
                                )
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
                                repeat(20) {
                                    WordCard(
                                        modifier = Modifier.fillMaxWidth(),
                                        word = "สวัสดี",
                                        meaning = "Hello",
                                        onClick = { },
                                        onDelete = { }) {

                                    }
                                }
                            }
                        }
                    }
                }

                ConsonantDetailState.Loading -> CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun Tag(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    text: String,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
) {
    Surface(
        shape = CircleShape,
        color = color,
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            icon?.let {
                Icon(imageVector = it, contentDescription = it.name)
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
        }
    }
}

@Preview
@Composable
private fun IconTagPreview() {
    RiianThaiTheme {
        Tag(
            icon = Icons.Rounded.VolumeUp,
            text = "Silent",
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
                    picture = "",
                    audio = "consonants/ก.mp3"
                )
            ),
            onBackClick = {}
        )
    }
}