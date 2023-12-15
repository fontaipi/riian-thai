package com.fontaipi.riianthai

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.ui.component.BottomAppBar
import com.fontaipi.riianthai.ui.component.slideInVerticallyComposable
import com.fontaipi.riianthai.ui.page.consonant.detail.ConsonantDetailRoute
import com.fontaipi.riianthai.ui.page.consonant.list.ConsonantsRoute
import com.fontaipi.riianthai.ui.page.flashcard.FlashcardScreen
import com.fontaipi.riianthai.ui.page.learn.navigation.learnGraph
import com.fontaipi.riianthai.ui.page.learn.navigation.learnRoute
import com.fontaipi.riianthai.ui.page.learn.navigation.navigateToLearnGraph
import com.fontaipi.riianthai.ui.page.practice.navigation.navigateToPracticeGraph
import com.fontaipi.riianthai.ui.page.practice.navigation.practiceGraph
import com.fontaipi.riianthai.ui.page.practice.navigation.practiceGraphPattern
import com.fontaipi.riianthai.ui.page.practice.navigation.practiceRoute
import com.fontaipi.riianthai.ui.page.settings.SettingsScreen
import com.fontaipi.riianthai.ui.page.summary.SummaryScreen
import com.fontaipi.riianthai.ui.page.tone.ToneMarksScreen
import com.fontaipi.riianthai.ui.page.vocabulary.VocabularyScreen
import com.fontaipi.riianthai.ui.page.vowel.detail.navigation.navigateToVowel
import com.fontaipi.riianthai.ui.page.vowel.detail.navigation.vowelScreen
import com.fontaipi.riianthai.ui.page.vowel.list.VowelRoute

@Immutable
data class TopAppBarState(
    val title: @Composable (() -> Unit) = {},
    val navigationIcon: @Composable (() -> Unit) = {},
    val actions: @Composable RowScope.() -> Unit = {},
)

@Immutable
data class ScaffoldViewState(
    val topAppBarState: TopAppBarState = TopAppBarState(title = { Text("Riian Thai") }),
    val onFabClick: (() -> Unit)? = null,
    val fabIcon: ImageVector? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiianThaiApp(
    appState: RiianThaiState = rememberRiianThaiAppState(),
) {
    var scaffoldViewState by remember { mutableStateOf(ScaffoldViewState()) }
    NavHost(navController = appState.navController, startDestination = "mainNav") {
        composable("mainNav") {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = scaffoldViewState.topAppBarState.title,
                        navigationIcon = {
                            if (appState.currentTopLevelDestination == null) {
                                IconButton(
                                    onClick = {
                                        appState.bottomNavController.popBackStack()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "backArrow",
                                    )
                                }
                            } else {
                                scaffoldViewState.topAppBarState.navigationIcon()
                            }
                        },
                        actions = {
                            if (appState.currentDestination?.route != "mainNav") {
                                IconButton(
                                    onClick = {
                                        appState.bottomNavController.popBackStack()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Close,
                                        contentDescription = "backArrow",
                                    )
                                }
                            } else {
                                scaffoldViewState.topAppBarState.actions(this)
                            }
                        },
                    )
                },
                floatingActionButton = {
                    scaffoldViewState.onFabClick?.let { action ->
                        FloatingActionButton(
                            onClick = action,
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                        }
                    }
                },
                bottomBar = {
                    BottomAppBar(
                        destinations = TopLevelDestination.entries,
                        onNavigateToDestination = appState::navigateToTopLevelDestination,
                        currentDestination = appState.currentBottomNavDestination,
                    )
                }
            ) { paddingValues ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                ) {
                    BottomNavHost(
                        navController = appState.bottomNavController,
                        updateScaffoldViewState = { scaffoldViewState = it },
                        navigateToConsonantDetail = {
                            appState.navController.navigate("consonants/${it}")
                        },
                    ) {
                        appState.navController.navigateToVowel(it)
                    }
                }
            }
        }

        slideInVerticallyComposable(
            route = "flashcard/{consonantClass}", arguments = listOf(
                navArgument("consonantClass") {
                    type = NavType.EnumType(ConsonantClass::class.java)
                }

            )
        ) {
            FlashcardScreen(
                onBackClick = {
                    appState.bottomNavController.popBackStack()
                }
            )
        }

        slideInVerticallyComposable("summary") {
            SummaryScreen(
                onBackClick = {
                    appState.bottomNavController.popBackStack()
                }
            )
        }

        slideInVerticallyComposable(
            route = "consonants/{consonantId}", arguments = listOf(
                navArgument("consonantId") {
                    type = NavType.LongType
                }
            )
        ) {
            ConsonantDetailRoute(
                onBackClick = {
                    appState.navController.popBackStack()
                }
            )
        }

        vowelScreen(
            onBackClick = {
                appState.navController.popBackStack()
            }
        )
    }
}

@Composable
fun BottomNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    updateScaffoldViewState: (ScaffoldViewState) -> Unit,
    navigateToConsonantDetail: (Long) -> Unit,
    navigateToVowelDetail: (Long) -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = practiceGraphPattern
    ) {
        practiceGraph(
            navigateToFlashcard = {},
            navigateToVocabulary = {
                navController.navigate("vocabulary")
            }
        ) {
            composable("vocabulary") {
                VocabularyScreen()
            }
        }

        learnGraph(
            navigateToConsonants = {
                navController.navigate("consonants")
            },
            navigateToVowels = {
                navController.navigate("vowels")
            },
            navigateToToneMarks = {
                navController.navigate("toneMarks")
            },
        ) {
            composable("consonants") {
                ConsonantsRoute(
                    navigateToConsonantDetail = navigateToConsonantDetail
                )
            }
            composable("vowels") {
                VowelRoute(
                    navigateToVowelDetail = navigateToVowelDetail
                )
            }
            composable("toneMarks") {
                ToneMarksScreen()
            }
        }

        composable(TopLevelDestination.SETTINGS.path) {
            SettingsScreen()
        }
    }
}

@Composable
fun rememberRiianThaiAppState(
    navController: NavHostController = rememberNavController(),
    bottomNavController: NavHostController = rememberNavController(),
): RiianThaiState {
    return remember(navController) {
        RiianThaiState(navController, bottomNavController)
    }
}

class RiianThaiState(
    val navController: NavHostController,
    val bottomNavController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentBottomNavDestination: NavDestination?
        @Composable get() = bottomNavController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentBottomNavDestination?.route) {
            practiceRoute -> TopLevelDestination.PRACTICE
            learnRoute -> TopLevelDestination.LEARN
            TopLevelDestination.SETTINGS.path -> TopLevelDestination.SETTINGS
            else -> null
        }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(bottomNavController.graph.findStartDestination().id) {
                saveState = false
            }
            launchSingleTop = true
        }

        when (topLevelDestination) {
            TopLevelDestination.LEARN -> bottomNavController.navigateToLearnGraph(topLevelNavOptions)
            TopLevelDestination.PRACTICE -> bottomNavController.navigateToPracticeGraph(
                topLevelNavOptions
            )

            TopLevelDestination.SETTINGS -> bottomNavController.navigate(TopLevelDestination.SETTINGS.path)
        }

    }
}