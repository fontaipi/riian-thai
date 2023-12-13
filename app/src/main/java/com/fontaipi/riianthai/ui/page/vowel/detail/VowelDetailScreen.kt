package com.fontaipi.riianthai.ui.page.vowel.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fontaipi.riianthai.model.VowelForm

@Composable
fun VowelDetailRoute(
    viewModel: VowelDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val vowelDetailState by viewModel.vowel.collectAsStateWithLifecycle()
    VowelDetailScreen(
        vowelDetailState = vowelDetailState,
        onBackClick = onBackClick
    )
}

@Composable
fun VowelDetailScreen(
    vowelDetailState: VowelDetailState,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.End),
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "Back"
            )
        }
        when (vowelDetailState) {
            is VowelDetailState.Success -> {
                val vowel = vowelDetailState.vowel
                Column {
                    Text(text = vowel.thaiScript)
                    vowel.writingForms.forEach { vowelForm ->
                        Column {
                            VowelFormText(vowelForm = vowelForm)
                        }
                    }
                }
            }

            is VowelDetailState.Loading -> {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun VowelFormText(vowelForm: VowelForm) {
    val annotatedString = buildAnnotatedString {
        vowelForm.format.forEachIndexed { index, char ->
            when {
                char == 'I' -> withStyle(style = SpanStyle(color = Color.Blue)) { append("อ") }
                char == 'E' -> {
                    if (index > 0 && vowelForm.accentIndicator[index - 1] == '*') {
                        withStyle(style = SpanStyle(color = Color.Green)) { append(vowelForm.format[index - 1].toString()) }
                    }
                    withStyle(style = SpanStyle(color = Color.Red)) { append("ก") }
                }

                vowelForm.accentIndicator[index] == '*' -> {}
                else -> withStyle(style = SpanStyle(color = Color.Green)) { append(char.toString()) }
            }
        }
    }

    BasicText(text = annotatedString)
}