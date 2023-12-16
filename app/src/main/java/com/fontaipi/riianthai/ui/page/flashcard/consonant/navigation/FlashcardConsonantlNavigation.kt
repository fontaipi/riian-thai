package com.fontaipi.riianthai.ui.page.flashcard.consonant.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.ui.component.slideInVerticallyComposable
import com.fontaipi.riianthai.ui.page.flashcard.consonant.FlashcardConsonantRoute

internal const val consonantClassArg = "consonantClass"

internal class FlashcardConsonantArgs(val consonantClass: ConsonantClass?) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(getConsonantClassFromSavedStateHandle(savedStateHandle))

    private companion object {
        fun getConsonantClassFromSavedStateHandle(savedStateHandle: SavedStateHandle): ConsonantClass? {
            val consonantClassString = savedStateHandle.get<String>(consonantClassArg)
            return if (consonantClassString == null) {
                null
            } else {
                ConsonantClass.valueOf(consonantClassString)
            }
        }
    }
}

fun NavController.navigateToFlashcardConsonant(consonantClass: ConsonantClass?) {
    this.navigate("flashcard_consonant_route/${consonantClass?.name}") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.flashcardConsonantScreen(
    onBackClick: () -> Unit,
) {
    slideInVerticallyComposable(
        route = "flashcard_consonant_route/{$consonantClassArg}",
        arguments = listOf(
            navArgument(consonantClassArg) {
                type = NavType.StringType
                nullable = true
            }
        ),
    ) {
        FlashcardConsonantRoute(onBackClick = onBackClick)
    }
}