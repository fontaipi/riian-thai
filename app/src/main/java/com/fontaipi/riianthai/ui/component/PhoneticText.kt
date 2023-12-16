package com.fontaipi.riianthai.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle

@Composable
fun PhoneticText(text: String, style: TextStyle) {
    val segments = text.split("-")

    val annotatedString = buildAnnotatedString {
        segments.forEach { segment ->
            val parts = segment.split("^")
            append(parts[0])

            if (parts.size > 1) {
                withStyle(
                    style = style.toSpanStyle().copy(
                        baselineShift = BaselineShift.Superscript,
                        fontSize = style.fontSize.times(0.7),
                    )
                ) {
                    append(parts[1])
                }
            }
            append(" ")
        }
    }

    Text(text = annotatedString, style = style)
}