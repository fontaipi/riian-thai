package com.fontaipi.riianthai.ui.page.practice.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fontaipi.riianthai.ui.page.learn.LearnScreen
import com.fontaipi.riianthai.ui.page.practice.PracticeScreen

const val practiceGraphPattern = "practice_graph"
const val practiceRoute = "practice_route"

fun NavController.navigateToPracticeGraph(navOptions: NavOptions? = null) {
    this.navigate(practiceGraphPattern, navOptions)
}

fun NavGraphBuilder.practiceGraph(
    navigateToFlashcard: () -> Unit,
    navigateToVocabulary: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = practiceGraphPattern,
        startDestination = practiceRoute
    ) {
        composable(route = practiceRoute) {
            PracticeScreen(
                navigateToFlashcard = navigateToFlashcard,
                navigateToVocabulary = navigateToVocabulary,
            )
        }
        nestedGraphs()
    }
}