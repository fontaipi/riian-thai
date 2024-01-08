package com.fontaipi.riianthai.ui.component

import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.fontaipi.riianthai.model.VowelForm
import com.fontaipi.riianthai.ui.theme.FinalConsonantColor
import com.fontaipi.riianthai.ui.theme.InitialConsonantColor
import com.fontaipi.riianthai.ui.theme.VowelColor

@Composable
fun VowelFormText(
    modifier: Modifier = Modifier,
    vowelForm: VowelForm,
    style: TextStyle = MaterialTheme.typography.headlineLarge
) {
    val annotatedString = buildAnnotatedString {
        vowelForm.format.forEachIndexed { index, char ->
            when {
                char == 'I' -> withStyle(style = SpanStyle(color = InitialConsonantColor)) {
                    append(
                        "-"
                    )
                }

                char == 'E' -> withStyle(style = SpanStyle(color = FinalConsonantColor)) { append("-") }
                index < vowelForm.accentIndicator.length && vowelForm.accentIndicator[index] == '*' -> {}
                else -> withStyle(style = SpanStyle(color = VowelColor)) { append(char.toString()) }
            }
            if (index < vowelForm.format.lastIndex) {
                withStyle(style = SpanStyle(fontSize = 12.sp)) { append(" ") }
            }
        }
    }

    BasicText(modifier = modifier, text = annotatedString, style = style)
}