package com.fontaipi.riianthai.ui.page.flashcard.consonant.component

import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.ui.component.PhoneticText
import com.fontaipi.riianthai.ui.page.consonant.detail.component.Tag
import com.fontaipi.riianthai.ui.theme.HighClassColor
import com.fontaipi.riianthai.ui.theme.LowClassColor
import com.fontaipi.riianthai.ui.theme.MidClassColor
import kotlinx.coroutines.launch

@Composable
fun BackConsonantFlashcard(
    consonant: Consonant
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (consonant.picture.isNotEmpty()) {
            AsyncImage(
                modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                model = "file:///android_asset/${consonant.picture}",
                contentDescription = "",
            )
        }
        Text(
            text = "${consonant.associatedWord} (${consonant.meaning})",
            style = MaterialTheme.typography.titleLarge
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = consonant.thai,
                style = MaterialTheme.typography.displayLarge
            )
            PhoneticText(
                text = consonant.phonetic,
                style = MaterialTheme.typography.titleLarge
            )
        }
        FilledIconButton(
            onClick = {
                coroutineScope.launch {
                    val mediaPlayer = MediaPlayer()
                    context.assets.openFd(consonant.audio).use { fd ->
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
        val color = when (consonant.consonantClass) {
            ConsonantClass.Low -> LowClassColor
            ConsonantClass.Mid -> MidClassColor
            ConsonantClass.High -> HighClassColor
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Tag(
                text = consonant.consonantClass.name,
                color = color
            )
        }

    }
}