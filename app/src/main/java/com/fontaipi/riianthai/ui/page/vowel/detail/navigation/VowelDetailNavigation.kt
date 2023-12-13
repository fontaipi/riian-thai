package com.fontaipi.riianthai.ui.page.vowel.detail.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.fontaipi.riianthai.ui.component.slideInVerticallyComposable
import com.fontaipi.riianthai.ui.page.vowel.detail.VowelDetailRoute

internal const val vowelIdArg = "vowelId"

internal class VowelArgs(val vowelId: Long) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull<Long>(savedStateHandle[vowelIdArg]))
}

fun NavController.navigateToVowel(vowelId: Long) {
    this.navigate("vowel_route/$vowelId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.vowelScreen(
    onBackClick: () -> Unit,
) {
    slideInVerticallyComposable(
        route = "vowel_route/{$vowelIdArg}",
        arguments = listOf(
            navArgument(vowelIdArg) { type = NavType.LongType },
        ),
    ) {
        VowelDetailRoute(onBackClick = onBackClick)
    }
}