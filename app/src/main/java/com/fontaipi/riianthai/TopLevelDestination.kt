package com.fontaipi.riianthai

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Style
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Style
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
    LEARN(
        selectedIcon = Icons.Rounded.School,
        unselectedIcon = Icons.Outlined.School,
        iconText = "Learn",
        titleText = "Learn",
        path = "/learn"
    ),
    SETTINGS(
        selectedIcon = Icons.Rounded.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        iconText = "Settings",
        titleText = "Settings",
        path = "/settings"
    ),
}