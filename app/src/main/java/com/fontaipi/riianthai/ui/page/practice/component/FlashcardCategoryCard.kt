package com.fontaipi.riianthai.ui.page.practice.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

@Composable
fun FlashcardCategoryCard(
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
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview
@Composable
private fun PracticeCategoryCardPreview() {
    RiianThaiTheme {
        FlashcardCategoryCard(
            modifier = Modifier.size(192.dp),
            title = "High",
            onClick = {},
            color = MaterialTheme.colorScheme.primaryContainer,
            circleColor = MaterialTheme.colorScheme.primary
        )
    }
}