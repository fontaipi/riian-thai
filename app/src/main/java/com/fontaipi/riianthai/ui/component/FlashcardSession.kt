package com.fontaipi.riianthai.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun FlashcardSession(
    progress: Float,
    cardFace: CardFace,
    front: @Composable () -> Unit,
    back: @Composable () -> Unit,
    nextCard: (Boolean) -> Unit,
    turnCard: () -> Unit,
) {

    val progressAnimation by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(), label = "progressAnimation"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
                cardFace = cardFace,
                onClick = turnCard,
                front = front,
                back = back
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            FilledTonalIconButton(
                onClick = { nextCard(false) }
            ) {
                Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
            }
            FilledTonalIconButton(
                onClick = { nextCard(true) }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null
                )
            }
        }
    }
}