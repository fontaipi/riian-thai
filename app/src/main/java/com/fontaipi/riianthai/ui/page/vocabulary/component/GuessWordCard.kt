package com.fontaipi.riianthai.ui.page.vocabulary.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fontaipi.riianthai.ui.theme.LocalAlertTheme
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

enum class GuessWordCardState {
    Correct,
    Incorrect,
    Neutral
}

@Composable
fun GuessWordCard(
    modifier: Modifier = Modifier,
    text: String,
    state: GuessWordCardState = GuessWordCardState.Neutral,
    onClick: () -> Unit
) {
    val successContainer = LocalAlertTheme.current.successContainer
    val onSuccessContainer = LocalAlertTheme.current.onSuccessContainer

    val (containerColor, contentColor) = when (state) {
        GuessWordCardState.Correct -> successContainer to onSuccessContainer
        GuessWordCardState.Incorrect -> MaterialTheme.colorScheme.errorContainer to MaterialTheme.colorScheme.onErrorContainer
        GuessWordCardState.Neutral -> MaterialTheme.colorScheme.surface to MaterialTheme.colorScheme.onSurface
    }
    OutlinedCard(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.outlinedCardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GuessWordCardPreview() {
    RiianThaiTheme {
        GuessWordCard(
            modifier = Modifier.fillMaxWidth(),
            state = GuessWordCardState.Correct,
            text = "Hello",
            onClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GuessWordCardDarkPreview() {
    RiianThaiTheme {
        GuessWordCard(
            modifier = Modifier.fillMaxWidth(),
            state = GuessWordCardState.Correct,
            text = "Hello",
            onClick = {}
        )
    }
}