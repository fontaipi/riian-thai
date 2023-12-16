package com.fontaipi.riianthai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fontaipi.riianthai.datastore.RiianThaiPreferences
import com.fontaipi.riianthai.model.UserData
import com.fontaipi.riianthai.ui.page.settings.UserPreferencesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val preferences: RiianThaiPreferences,
) : ViewModel() {
    val userPreferencesState = preferences.userData.map<UserData, UserPreferencesState> {
        UserPreferencesState.Success(it)
    }.onStart {
        emit(UserPreferencesState.Loading)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = UserPreferencesState.Loading
    )
}