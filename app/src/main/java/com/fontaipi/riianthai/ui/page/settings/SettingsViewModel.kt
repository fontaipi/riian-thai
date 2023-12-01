package com.fontaipi.riianthai.ui.page.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fontaipi.riianthai.data.repository.ConsonantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val consonantRepository: ConsonantRepository
) : ViewModel() {
    fun deleteProgress() {
        viewModelScope.launch {
            consonantRepository.resetAllCounts()
        }
    }
}