package com.fontaipi.riianthai.ui.page.learn.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

@Composable
fun LearningCategoryCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    circleColor: Color = MaterialTheme.colorScheme.primary,
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = color
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .drawBehind {
                    drawCircle(
                        radius = 240f,
                        color = circleColor.copy(alpha = 0.2f),
                        center = Offset(size.width, size.height)
                    )
                },
        ) {
            Column {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(text = subtitle, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


@Preview
@Composable
private fun LearningCategoryCardPreview() {
    RiianThaiTheme {
        LearningCategoryCard(
            title = "Consonants",
            subtitle = "Learn the consonants of the Thai alphabet",
            onClick = {},
            color = MaterialTheme.colorScheme.primaryContainer
        )
    }
}