package com.fontaipi.riianthai.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FlashcardSessionSuccessRate(
    successRate: Float,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy((-10).dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Congratulations",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "(เก่งมาก)",
                    style = MaterialTheme.typography.headlineLarge
                )
            }
            Text(text = "🎉", style = MaterialTheme.typography.headlineLarge)
        }

        PracticeSessionSummary(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f),
            progress = successRate
        )
    }
}