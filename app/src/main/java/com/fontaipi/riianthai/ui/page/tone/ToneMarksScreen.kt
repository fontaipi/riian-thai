package com.fontaipi.riianthai.ui.page.tone

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.model.ToneMark
import com.fontaipi.riianthai.ui.page.consonant.detail.component.Tag
import com.fontaipi.riianthai.ui.component.PhoneticText
import com.fontaipi.riianthai.ui.theme.HighClassColor
import com.fontaipi.riianthai.ui.theme.LowClassColor
import com.fontaipi.riianthai.ui.theme.MidClassColor
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

enum class SyllableType {
    Live,
    Dead,
    Any
}

enum class Tone(val symbol: String) {
    Mid("ˉ"),
    Low("ˋ"),
    High("ˊ"),
    Falling("ˆ"),
    Rising("ˇ")
}

data class ToneMarkRule(
    val toneMark: ToneMark,
    val consonantClass: ConsonantClass,
    val resultingTone: Tone
)

val toneMarks = listOf(
    ToneMark(id = 1, thai = "ไม้เอก", phonetic = "maai^H-aehk^L", symbol = "\u0e48"),
    ToneMark(id = 2, thai = "ไม้โท", phonetic = "maai^H-tho^M", symbol = "\u0e49"),
    ToneMark(id = 3, thai = "ไม้ตรี", phonetic = "maai^H-dtree^M", symbol = "\u0e4a"),
    ToneMark(id = 4, thai = "ไม้จัตวา", phonetic = "maai^H-jat^L-dta^L-waa^M", symbol = "\u0e4b")
)

val toneMarkRules = listOf(
    ToneMarkRule(
        toneMark = toneMarks[0],
        consonantClass = ConsonantClass.Low,
        resultingTone = Tone.Falling
    ),
    ToneMarkRule(
        toneMark = toneMarks[1],
        consonantClass = ConsonantClass.Low,
        resultingTone = Tone.High
    ),
    ToneMarkRule(
        toneMark = toneMarks[2],
        consonantClass = ConsonantClass.Low,
        resultingTone = Tone.High
    ),
    ToneMarkRule(
        toneMark = toneMarks[3],
        consonantClass = ConsonantClass.Low,
        resultingTone = Tone.Rising
    ),

    ToneMarkRule(
        toneMark = toneMarks[0],
        consonantClass = ConsonantClass.Mid,
        resultingTone = Tone.Low
    ),
    ToneMarkRule(
        toneMark = toneMarks[1],
        consonantClass = ConsonantClass.Mid,
        resultingTone = Tone.Falling
    ),
    ToneMarkRule(
        toneMark = toneMarks[2],
        consonantClass = ConsonantClass.Mid,
        resultingTone = Tone.High
    ),
    ToneMarkRule(
        toneMark = toneMarks[3],
        consonantClass = ConsonantClass.Mid,
        resultingTone = Tone.Rising
    ),

    ToneMarkRule(
        toneMark = toneMarks[0],
        consonantClass = ConsonantClass.High,
        resultingTone = Tone.Low
    ),
    ToneMarkRule(
        toneMark = toneMarks[1],
        consonantClass = ConsonantClass.High,
        resultingTone = Tone.Falling
    ),
    ToneMarkRule(
        toneMark = toneMarks[2],
        consonantClass = ConsonantClass.High,
        resultingTone = Tone.High
    ),
    ToneMarkRule(
        toneMark = toneMarks[3],
        consonantClass = ConsonantClass.High,
        resultingTone = Tone.Rising
    )
)


@Composable
fun ToneMarksScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(text = "Tone Marks", style = MaterialTheme.typography.headlineMedium)
        ToneMarkRulesTable(
            toneMarkRules = toneMarkRules
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToneMarkRulesTable(
    modifier: Modifier = Modifier,
    toneMarkRules: List<ToneMarkRule>,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp),
    ) {
        item {
            Row(
                modifier = modifier.height(IntrinsicSize.Max)
            ) {
                DiagonalHeaderCell(
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    textTop = "Class",
                    textBottom = "Tone Mark"
                )
                VerticalDivider()
                ConsonantClass.entries.forEachIndexed { index, consonantClass ->
                    TableCell(
                        modifier = Modifier.weight(1f)
                    ) {
                        val color = when (consonantClass) {
                            ConsonantClass.Low -> LowClassColor
                            ConsonantClass.Mid -> MidClassColor
                            ConsonantClass.High -> HighClassColor
                        }
                        Tag(text = consonantClass.name, color = color)
                    }
                    if (index < ConsonantClass.entries.size - 1) {
                        VerticalDivider()
                    }
                }
            }
            HorizontalDivider()
        }

        val toneMarkToneRules = toneMarkRules.groupBy { it.toneMark }.toList()
        itemsIndexed(toneMarkToneRules) { index, (toneMark, toneRules) ->
            Row(
                modifier = modifier.height(IntrinsicSize.Max)
            ) {
                TableCell(
                    modifier = Modifier.weight(1f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ThaiToneMark(
                            text = toneMark.symbol,
                            style = MaterialTheme.typography.displayMedium
                        )
                        Column {
                            Text(text = toneMark.thai, style = MaterialTheme.typography.titleMedium)
                            PhoneticText(
                                text = toneMark.phonetic,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }

                    }
                }
                VerticalDivider()
                toneRules.forEachIndexed { index, toneRule ->
                    TableCell(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            toneRule.resultingTone.symbol,
                            style = MaterialTheme.typography.displayLarge
                        )
                        Text(
                            text = toneRule.resultingTone.name,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                    if (index < ConsonantClass.entries.size - 1) {
                        VerticalDivider()
                    }
                }
            }
            if (index < toneMarkToneRules.size - 1) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun DiagonalHeaderCell(
    modifier: Modifier = Modifier,
    style: TextStyle,
    color: Color,
    textTop: String,
    textBottom: String
) {
    val strokeColor = MaterialTheme.colorScheme.outlineVariant

    val topTextMeasurer = rememberTextMeasurer()
    val bottomTextMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
            .fillMaxSize()
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        drawLine(
            color = strokeColor,
            strokeWidth = 1.dp.toPx(),
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = canvasWidth, y = canvasHeight),
        )

        drawText(
            textMeasurer = topTextMeasurer,
            text = textTop,
            style = style.copy(color = color),
            topLeft = Offset(
                x = canvasWidth - topTextMeasurer.measure(textTop, style).size.width - 20f,
                y = 0f,
            )
        )

        drawText(
            textMeasurer = bottomTextMeasurer,
            text = textBottom,
            style = style.copy(color = color),
            topLeft = Offset(
                x = 0f,
                y = canvasHeight - bottomTextMeasurer.measure(textTop, style).lastBaseline - 20f,
            )
        )
    }
}

@Composable
fun TableCell(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun ThaiToneMark(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle
) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = style.toSpanStyle()
        ) {
            withStyle(
                style = SpanStyle(fontSize = 1.sp)
            ) {
                append(text = " ")
            }
            append(text)
        }
    }

    Text(text = annotatedString, style = style)
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ToneMarksScreenPreview() {
    RiianThaiTheme {
        ToneMarksScreen()
    }
}