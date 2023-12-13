package com.fontaipi.riianthai.ui.page.vowel.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fontaipi.riianthai.model.Vowel
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme

@Composable
fun VowelRoute(
    viewModel: VowelViewModel = hiltViewModel(),
    navigateToVowelDetail: (Long) -> Unit
) {
    val vowels by viewModel.vowels.collectAsStateWithLifecycle()
    VowelScreen(
        vowels = vowels,
        navigateToVowelDetail = navigateToVowelDetail
    )
}

@Composable
fun VowelScreen(
    vowels: List<Vowel>,
    navigateToVowelDetail: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Vowels",
            style = MaterialTheme.typography.headlineMedium
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 54.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(vowels) {
                Card(
                    modifier = Modifier.aspectRatio(1f),
                    onClick = {
                        navigateToVowelDetail(it.id)
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = it.thaiScript)
                    }

                }
            }
        }
    }

}

@Preview
@Composable
private fun VowelScreenPreview() {
    RiianThaiTheme {
        VowelScreen(
            vowels = listOf(),
            navigateToVowelDetail = {}
        )
    }
}