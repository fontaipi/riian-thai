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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.fontaipi.riianthai.ui.theme.LocalAlertTheme

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

    val successContainer = LocalAlertTheme.current.successContainer
    val onSuccessContainer = LocalAlertTheme.current.onSuccessContainer

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
        when (cardFace) {
            CardFace.Front -> {
                FilledTonalButton(
                    modifier = Modifier.height(56.dp),
                    onClick = turnCard
                ) {
                    Text(text = "Show answer")
                }
            }

            CardFace.Back -> {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    FilledTonalIconButton(
                        modifier = Modifier.size(56.dp),
                        onClick = { nextCard(false) },
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null
                        )
                    }
                    FilledTonalIconButton(
                        modifier = Modifier.size(56.dp),
                        onClick = { nextCard(true) },
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = successContainer,
                            contentColor = onSuccessContainer
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            imageVector = Icons.Rounded.Check,
                            contentDescription = null
                        )
                    }
                }
            }
        }

    }
}