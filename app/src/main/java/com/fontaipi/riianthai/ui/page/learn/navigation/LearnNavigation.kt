package com.fontaipi.riianthai.ui.page.learn.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.fontaipi.riianthai.ui.page.learn.LearnScreen

private const val learnGraphPattern = "learn_graph"
const val learnRoute = "learn_route"

fun NavController.navigateToLearnGraph(navOptions: NavOptions? = null) {
    this.navigate(learnGraphPattern, navOptions)
}

fun NavGraphBuilder.learnGraph(
    navigateToConsonants: () -> Unit,
    navigateToToneMarks: () -> Unit,
    nestedGraphs: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = learnGraphPattern,
        startDestination = learnRoute
    ) {
        composable(route = learnRoute) {
            LearnScreen(
                navigateToConsonants = navigateToConsonants,
                navigateToToneMarks = navigateToToneMarks,
                navigateToVowels = { /*TODO*/ },
            )
        }
        nestedGraphs()
    }
}