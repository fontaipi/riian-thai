package com.fontaipi.riianthai.ui.page.summary.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fontaipi.riianthai.ui.theme.Green1
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsonantCard(
    modifier: Modifier = Modifier,
    consonant: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.size(64.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = consonant, style = MaterialTheme.typography.headlineLarge)
        }
    }
}

@Preview
@Composable
private fun ConsonantCardPreview() {
    RiianThaiTheme {
        ConsonantCard(
            consonant = "ก",
            backgroundColor = Green1.copy(alpha = 0.8f),
            onClick = {}
        )
    }
}