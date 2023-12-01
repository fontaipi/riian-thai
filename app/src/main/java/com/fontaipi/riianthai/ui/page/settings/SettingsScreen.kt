package com.fontaipi.riianthai.ui.page.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fontaipi.riianthai.ui.page.settings.component.SectionTitle
import com.fontaipi.riianthai.ui.page.settings.component.SettingRow

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        SectionTitle(
            title = "Appearance",
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        SettingRow(
            icon = {
                Icon(
                    imageVector = Icons.Filled.Palette,
                    contentDescription = null
                )
            },
            title = "Dynamic color",
            onClick = {},
        ) {
            Switch(
                checked = false,
                onCheckedChange = { }
            )
        }
        SettingRow(
            icon = {
                Icon(
                    imageVector = Icons.Filled.DarkMode,
                    contentDescription = null
                )
            },
            title = "Dark theme",
            subTitle = "Use dark theme automatically",
            onClick = {},
        ) {
            Switch(
                checked = true,
                onCheckedChange = { }
            )
        }
        SettingRow(
            icon = { Icon(imageVector = Icons.Rounded.Delete, contentDescription = null) },
            title = "Delete progress",
            onClick = viewModel::deleteProgress,
            contentColor = MaterialTheme.colorScheme.error,
        )
    }
}