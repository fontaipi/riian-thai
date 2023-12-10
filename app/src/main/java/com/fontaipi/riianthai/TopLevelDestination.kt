package com.fontaipi.riianthai

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Interests
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Interests
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconText: String,
    val titleText: String,
    val path: String
) {
    PRACTICE(
        selectedIcon = Icons.Rounded.Interests,
        unselectedIcon = Icons.Outlined.Interests,
        iconText = "Practice",
        titleText = "Practice",
        path = "/practice"
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