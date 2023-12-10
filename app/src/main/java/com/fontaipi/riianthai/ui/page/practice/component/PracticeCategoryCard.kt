package com.fontaipi.riianthai.ui.page.practice.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

@Composable
fun PracticeCategoryCard(
    modifier: Modifier = Modifier,
    title: String,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    circleColor: Color = MaterialTheme.colorScheme.primary,
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .padding(16.dp)
                .drawBehind {
                    drawCircle(
                        radius = 164f,
                        color = circleColor.copy(alpha = 0.2f),
                        center = Offset(size.width, 0f)
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .height(6.dp)
                            .weight(1f),
                        progress = { 0.5f },
                        color = circleColor,
                        trackColor = MaterialTheme.colorScheme.surface,
                        strokeCap = StrokeCap.Round
                    )
                    Text(text = "50%", style = MaterialTheme.typography.labelLarge)
                }
            }
        }

    }
}

@Preview
@Composable
private fun PracticeCategoryCardPreview() {
    RiianThaiTheme {
        PracticeCategoryCard(
            modifier = Modifier.size(192.dp),
            title = "Flashcards",
            onClick = {},
            color = MaterialTheme.colorScheme.primaryContainer,
            circleColor = MaterialTheme.colorScheme.primary
        )
    }
}