package com.fontaipi.riianthai

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Search
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.ui.component.BottomAppBar
import com.fontaipi.riianthai.ui.component.slideInVerticallyComposable
import com.fontaipi.riianthai.ui.page.cards.CardsScreen
import com.fontaipi.riianthai.ui.page.flashcard.FlashCardScreen
import com.fontaipi.riianthai.ui.page.settings.SettingsScreen
import com.fontaipi.riianthai.ui.page.summary.SummaryScreen
import com.fontaipi.riianthai.ui.page.words.WordsScreen

@Immutable
data class TopAppBarState(
    val title: @Composable (() -> Unit) = {},
    val navigationIcon: @Composable (() -> Unit) = {},
    val actions: @Composable RowScope.() -> Unit = {},
)

@Immutable
data class ScaffoldViewState(
    val topAppBarState: TopAppBarState? = null,
    val onFabClick: (() -> Unit)? = null,
    val fabIcon: ImageVector? = null,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RiianThaiApp() {
    var scaffoldViewState by remember { mutableStateOf(ScaffoldViewState()) }
    val navController = rememberNavController()
    val mainBottomNavController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainNav") {
        composable("mainNav") {
            Scaffold(
                topBar = {
                    scaffoldViewState.topAppBarState?.let {
                        CenterAlignedTopAppBar(
                            title = it.title,
                            navigationIcon = it.navigationIcon,
                            actions = it.actions,
                        )
                    }
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
                        onNavigateToDestination = { destination ->
                            mainBottomNavController.navigate(destination.name)
                        },
                        currentDestination = mainBottomNavController
                            .currentBackStackEntryAsState().value?.destination,
                    )
                }
            ) { paddingValues ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                ) {
                    MainBottomNav(
                        navController = mainBottomNavController,
                        updateScaffoldViewState = { scaffoldViewState = it },
                        navigateToFlashCardScreen = {
                            navController.navigate("flashcard/${it}")
                        },
                        navigateToSummaryScreen = {
                            navController.navigate("summary")
                        }
                    )
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
            FlashCardScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        slideInVerticallyComposable("summary") {
            SummaryScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun MainBottomNav(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    updateScaffoldViewState: (ScaffoldViewState) -> Unit,
    navigateToFlashCardScreen: (ConsonantClass) -> Unit,
    navigateToSummaryScreen: () -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = TopLevelDestination.CARDS.name
    ) {
        composable(TopLevelDestination.CARDS.name) {
            LaunchedEffect(Unit) {
                updateScaffoldViewState(
                    ScaffoldViewState(
                        topAppBarState = TopAppBarState(
                            title = { Text("Riian Thai \uD83C\uDDF9\uD83C\uDDED") },
                        ),
                        onFabClick = null
                    )
                )
            }
            CardsScreen(
                navigateToFlashCardScreen = navigateToFlashCardScreen,
                navigateToSummaryScreen = navigateToSummaryScreen
            )
        }

        composable(TopLevelDestination.WORDS.name) {
            LaunchedEffect(Unit) {
                updateScaffoldViewState(
                    ScaffoldViewState(
                        topAppBarState = TopAppBarState(
                            title = {
                                Text(
                                    text = buildAnnotatedString {
                                        withStyle(
                                            style = SpanStyle(
                                                color = Color(0xFFE91E63)
                                            )
                                        ) {
                                            append("RiianThai")
                                        }
                                        append(" \uD83C\uDDF9\uD83C\uDDED")
                                    },
                                    style = MaterialTheme.typography.displaySmall
                                )
                            },
                            actions = {
                                IconButton(onClick = { }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Search,
                                        contentDescription = null,
                                    )
                                }
                            }
                        ),
                        onFabClick = { }
                    )
                )
            }
            WordsScreen()
        }

        composable(TopLevelDestination.SETTINGS.name) {
            LaunchedEffect(Unit) {
                updateScaffoldViewState(
                    ScaffoldViewState(
                        topAppBarState = TopAppBarState(
                            title = { Text("Settings") },
                        ),
                        onFabClick = null
                    )
                )
            }
            SettingsScreen()
        }
    }
}