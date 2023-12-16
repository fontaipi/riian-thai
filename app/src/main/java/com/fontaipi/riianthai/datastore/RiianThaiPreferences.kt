package com.fontaipi.riianthai.datastore

import androidx.datastore.core.DataStore
import com.fontaipi.riianthai.model.UserData
import com.fontaipi.riianthai.proto.copy
import com.fontaipi.riianthai.proto.UserPreferences
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RiianThaiPreferences @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val userData = userPreferences.data.map {
        UserData(
            useDynamicColor = it.useDynamicColor,
            useDarkMode = it.useDarkMode,
        )
    }

    suspend fun toggleDynamicColors() {
        userPreferences.updateData {
            it.copy { useDynamicColor = !useDynamicColor }
        }
    }

    suspend fun toggleDarkMode() {
        userPreferences.updateData {
            it.copy { useDarkMode = !useDarkMode }
        }
    }
}