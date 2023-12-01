package com.fontaipi.riianthai

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Style
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Style
import androidx.compose.material.icons.rounded.Translate
import androidx.compose.ui.graphics.vector.ImageVector

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: String,
    val titleText: String,
    val path: String
) {
    CARDS(
        selectedIcon = Icons.Rounded.Style,
        unselectedIcon = Icons.Outlined.Style,
        iconText = "Flashcards",
        titleText = "Flashcards",
        path = "/flashcards"
    ),
    WORDS(
        selectedIcon = Icons.Rounded.Translate,
        unselectedIcon = Icons.Outlined.Translate,
        iconText = "Words",
        titleText = "Words",
        path = "/words"
    ),
    SETTINGS(
        selectedIcon = Icons.Rounded.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        iconText = "Settings",
        titleText = "Settings",
        path = "/settings"
    ),
}