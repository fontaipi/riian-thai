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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fontaipi.riianthai.ui.page.settings.component.SectionTitle
import com.fontaipi.riianthai.ui.page.settings.component.SettingRow

@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val userPreferencesState by viewModel.userPreferencesState.collectAsStateWithLifecycle()
    SettingsScreen(
        userPreferencesState = userPreferencesState,
        toggleDarkTheme = viewModel::toggleDarkTheme,
        toggleDynamicColor = viewModel::toggleDynamicColor,
        deleteProgress = viewModel::deleteProgress,
        viewModel = viewModel
    )

}

@Composable
fun SettingsScreen(
    userPreferencesState: UserPreferencesState,
    toggleDarkTheme: () -> Unit,
    toggleDynamicColor: () -> Unit,
    deleteProgress: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()

    var showConfirmDeleteProgress by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
    ) {
        when (userPreferencesState) {
            is UserPreferencesState.Success -> {
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
                    onClick = toggleDynamicColor,
                ) {
                    Switch(
                        checked = userPreferencesState.userData.useDynamicColor,
                        onCheckedChange = { toggleDynamicColor() }
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
                    onClick = toggleDarkTheme,
                ) {
                    Switch(
                        checked = userPreferencesState.userData.useDarkMode,
                        onCheckedChange = { toggleDarkTheme() }
                    )
                }
                SettingRow(
                    icon = { Icon(imageVector = Icons.Rounded.Delete, contentDescription = null) },
                    title = "Delete progress",
                    onClick = { showConfirmDeleteProgress = true },
                    contentColor = MaterialTheme.colorScheme.error,
                )
            }

            is UserPreferencesState.Loading -> CircularProgressIndicator()
        }
    }

    if (showConfirmDeleteProgress) {
        ConfirmDeleteProgressDialog(
            onConfirmRequest = {
                deleteProgress()
                showConfirmDeleteProgress = false
            },
            onDismissRequest = { showConfirmDeleteProgress = false }
        )
    }
}