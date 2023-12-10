package com.fontaipi.riianthai.ui.page.learn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fontaipi.riianthai.ui.page.learn.component.LearningCategoryCard

@Composable
fun LearnScreen(
    navigateToConsonants: () -> Unit,
    navigateToVowels: () -> Unit,
    navigateToToneMarks: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Learn",
            style = MaterialTheme.typography.headlineMedium
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LearningCategoryCard(
                title = "Consonants",
                subtitle = "Learn the consonants of the Thai alphabet",
                onClick = navigateToConsonants,
                color = MaterialTheme.colorScheme.primaryContainer,
                circleColor = MaterialTheme.colorScheme.primary
            )
            LearningCategoryCard(
                title = "Vowels",
                subtitle = "Learn the vowels of the Thai alphabet",
                onClick = {},
                color = MaterialTheme.colorScheme.secondaryContainer,
                circleColor = MaterialTheme.colorScheme.secondary
            )
            LearningCategoryCard(
                title = "Tone marks",
                subtitle = "Learn the tone marks of the Thai alphabet",
                onClick = navigateToToneMarks,
                color = MaterialTheme.colorScheme.tertiaryContainer,
                circleColor = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}