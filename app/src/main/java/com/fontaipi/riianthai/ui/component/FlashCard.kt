package com.fontaipi.riianthai.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

enum class CardFace {
    Front, Back;

    fun next(): CardFace {
        return if (this == Front) {
            Back
        } else {
            Front
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlashCard(
    cardFace: CardFace,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    back: @Composable () -> Unit = {},
    front: @Composable () -> Unit = {},
) {
    Card(
        onClick = onClick,
        modifier = modifier
    ) {
        if (cardFace == CardFace.Front) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                front()
            }
        } else {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                back()
            }
        }
    }
}