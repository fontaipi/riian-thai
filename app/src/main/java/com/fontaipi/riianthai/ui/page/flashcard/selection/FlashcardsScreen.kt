package com.fontaipi.riianthai.ui.page.flashcard.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.model.VowelClass
import com.fontaipi.riianthai.ui.page.practice.component.FlashcardCategoryCard
import com.fontaipi.riianthai.ui.theme.HighClassColor
import com.fontaipi.riianthai.ui.theme.LowClassColor
import com.fontaipi.riianthai.ui.theme.MidClassColor
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

@Composable
fun FlashcardsRoute(
    navigateToFlashcardConsonant: (ConsonantClass?) -> Unit,
) {
    FlashcardsScreen(
        navigateToFlashcardConsonant = navigateToFlashcardConsonant,
    )
}

@Composable
fun FlashcardsScreen(
    navigateToFlashcardConsonant: (ConsonantClass?) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Flashcards",
            style = MaterialTheme.typography.headlineMedium
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Consonants",
                style = MaterialTheme.typography.titleLarge
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(ConsonantClass.entries) {
                    val color = when (it) {
                        ConsonantClass.Low -> LowClassColor
                        ConsonantClass.Mid -> MidClassColor
                        ConsonantClass.High -> HighClassColor
                    }
                    FlashcardCategoryCard(
                        modifier = Modifier.size(128.dp),
                        title = it.name,
                        onClick = { navigateToFlashcardConsonant(it) },
                        color = color,
                        circleColor = color
                    )
                }
                item {
                    FlashcardCategoryCard(
                        modifier = Modifier.size(128.dp),
                        title = "All",
                        onClick = { navigateToFlashcardConsonant(null) },
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Vowels",
                style = MaterialTheme.typography.titleLarge
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(VowelClass.entries) {
                    val (color, circleColor) = when (it) {
                        VowelClass.Short -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) to MaterialTheme.colorScheme.primary
                        VowelClass.Long -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.primary
                        VowelClass.Special -> MaterialTheme.colorScheme.tertiaryContainer to MaterialTheme.colorScheme.tertiary
                    }
                    FlashcardCategoryCard(
                        modifier = Modifier.size(128.dp),
                        title = it.name,
                        onClick = { /*TODO*/ },
                        color = color,
                        circleColor = circleColor
                    )
                }
                item {
                    FlashcardCategoryCard(
                        modifier = Modifier.size(128.dp),
                        title = "All",
                        onClick = { /*TODO*/ },
                    )
                }
            }
        }

    }
}

@Preview
@Composable
private fun FlashcardsScreenPreview() {
    RiianThaiTheme {
        FlashcardsScreen(
            navigateToFlashcardConsonant = {}
        )
    }
}