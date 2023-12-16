package com.fontaipi.riianthai.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fontaipi.riianthai.ui.theme.Green3
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

internal val Green10 = Color(0xFF00210B)
internal val Green20 = Color(0xFF003919)
internal val Green30 = Color(0xFF005227)
internal val Green40 = Color(0xFF006D36)
internal val Green80 = Color(0xFF0EE37C)
internal val Green90 = Color(0xFF5AFF9D)

@Composable
fun PracticeSessionSummary(
    modifier: Modifier = Modifier,
    progress: Float = 0.5f
) {
    var animationLoaded by rememberSaveable { mutableStateOf(false) }
    val progressAnimation by animateFloatAsState(
        targetValue = if (animationLoaded) progress else 0f,
        animationSpec = tween(durationMillis = 700), label = "progressAnimation"
    )

    LaunchedEffect(animationLoaded) {
        animationLoaded = true
    }

    Box(
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = modifier,
            progress = { progressAnimation },
            strokeWidth = 16.dp,
            color = Green3,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
            strokeCap = StrokeCap.Round
        )
        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold),
            color = Green3
        )
    }

}

@Preview
@Composable
private fun PracticeSessionSummaryPreview() {
    RiianThaiTheme {
        PracticeSessionSummary(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1f),
        )
    }
}