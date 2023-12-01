package com.fontaipi.riianthai.ui.page.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fontaipi.riianthai.ui.page.summary.component.ConsonantCard
import com.fontaipi.riianthai.ui.theme.Green1
import com.fontaipi.riianthai.ui.theme.Green2
import com.fontaipi.riianthai.ui.theme.Green3
import com.fontaipi.riianthai.ui.theme.Green4

enum class LearningFrequency(val count: Int) {
    None(0),
    VeryLow(1),
    Low(3),
    Middle(5),
    High(10)
}

fun getLearningFrequencyByCount(count: Int): LearningFrequency {
    return when {
        count >= LearningFrequency.High.count -> LearningFrequency.High
        count >= LearningFrequency.Middle.count -> LearningFrequency.Middle
        count >= LearningFrequency.Low.count -> LearningFrequency.Low
        count >= LearningFrequency.VeryLow.count -> LearningFrequency.VeryLow
        else -> LearningFrequency.None
    }
}

@Composable
fun SummaryScreen(
    viewModel: SummaryViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val consonants by viewModel.consonants.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.End),
            onClick = onBackClick
        ) {
            Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 64.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(consonants) {
                val learningFrequency = getLearningFrequencyByCount(it.count)
                val color = when (learningFrequency) {
                    LearningFrequency.None -> MaterialTheme.colorScheme.surfaceVariant
                    LearningFrequency.VeryLow -> Green1
                    LearningFrequency.Low -> Green2
                    LearningFrequency.Middle -> Green3
                    LearningFrequency.High -> Green4
                }
                ConsonantCard(
                    modifier = Modifier.aspectRatio(1f),
                    backgroundColor = color.copy(alpha = 0.75f),
                    consonant = it.thai,
                    onClick = {}
                )
            }
        }
    }
}