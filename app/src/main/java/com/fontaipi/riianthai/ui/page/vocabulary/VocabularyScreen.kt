package com.fontaipi.riianthai.ui.page.vocabulary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fontaipi.riianthai.ui.page.vocabulary.component.GuessWordCard
import com.fontaipi.riianthai.ui.page.vocabulary.component.GuessWordCardState

@Composable
fun VocabularyScreen() {
    var answerSelected by remember { mutableStateOf<Int?>(null) }
    val correctAnswer = 1
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(text = "Find the word that matches the definition")
        Text(text = "Hello")
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(4) {
                GuessWordCard(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Hello",
                    // if answerSelected is not null show the correct answer an show error if the answer is incorrect
                    state = if (answerSelected != null) {
                        when (it) {
                            correctAnswer -> GuessWordCardState.Correct
                            answerSelected -> GuessWordCardState.Incorrect
                            else -> GuessWordCardState.Neutral
                        }
                    } else GuessWordCardState.Neutral,
                    onClick = {
                        if (answerSelected == null) {
                            answerSelected = it
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun VocabularyScreenPreview() {
    VocabularyScreen()
}