package com.fontaipi.riianthai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.fontaipi.riianthai.data.database.dao.ConsonantDao
import com.fontaipi.riianthai.data.database.dao.VowelDao
import com.fontaipi.riianthai.data.database.dao.VowelFormDao
import com.fontaipi.riianthai.data.database.dao.WordDao
import com.fontaipi.riianthai.ui.page.settings.UserPreferencesState
import com.fontaipi.riianthai.ui.theme.RiianThaiTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var consonantDao: ConsonantDao

    @Inject
    lateinit var vowelDao: VowelDao

    @Inject
    lateinit var vowelFormDao: VowelFormDao

    @Inject
    lateinit var wordDao: WordDao

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var userPreferencesState: UserPreferencesState by mutableStateOf(UserPreferencesState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userPreferencesState
                    .onEach {
                        userPreferencesState = it
                    }.collect()
            }
        }

        // Keep the splash screen on-screen until the UI state is loaded. This condition is
        // evaluated each time the app needs to be redrawn so it should be fast to avoid blocking
        // the UI.
        splashScreen.setKeepOnScreenCondition {
            when (userPreferencesState) {
                UserPreferencesState.Loading -> true
                is UserPreferencesState.Success -> false
            }
        }

        CoroutineScope(Dispatchers.IO).launch {

        }

        enableEdgeToEdge()

        setContent {
            val darkTheme = shouldUseDarkTheme(userPreferencesState)

            // Update the edge to edge configuration to match the theme
            // This is the same parameters as the default enableEdgeToEdge call, but we manually
            // resolve whether or not to show dark theme using uiState, since it can be different
            // than the configuration's dark theme value based on the user preference.
            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        android.graphics.Color.TRANSPARENT,
                        android.graphics.Color.TRANSPARENT,
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim,
                    ) { darkTheme },
                )
                onDispose { }
            }

            RiianThaiTheme(
                darkTheme = darkTheme,
                disableDynamicTheming = shouldDisableDynamicTheming(userPreferencesState),
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    RiianThaiApp()
                }
            }
        }
    }
}

/**
 * Returns `true` if the dynamic color is disabled, as a function of the [uiState].
 */
@Composable
private fun shouldDisableDynamicTheming(
    userPreferencesState: UserPreferencesState,
): Boolean = when (userPreferencesState) {
    UserPreferencesState.Loading -> false
    is UserPreferencesState.Success -> !userPreferencesState.userData.useDynamicColor
}

/**
 * Returns `true` if dark theme should be used, as a function of the [userPreferencesState] and the
 * current system context.
 */
@Composable
private fun shouldUseDarkTheme(
    userPreferencesState: UserPreferencesState,
): Boolean = when (userPreferencesState) {
    UserPreferencesState.Loading -> isSystemInDarkTheme()
    is UserPreferencesState.Success -> userPreferencesState.userData.useDarkMode
}

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)