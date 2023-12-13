package com.fontaipi.riianthai.ui.page.vowel.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fontaipi.riianthai.data.repository.VowelRepository
import com.fontaipi.riianthai.model.Vowel
import com.fontaipi.riianthai.ui.page.vowel.detail.navigation.VowelArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class VowelDetailState {
    data class Success(val vowel: Vowel) : VowelDetailState()
    data object Loading : VowelDetailState()
}

@HiltViewModel
class VowelDetailViewModel @Inject constructor(
    private val vowelRepository: VowelRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val vowelArgs = VowelArgs(savedStateHandle)

    val vowel = vowelRepository.getVowel(vowelArgs.vowelId).map {
        VowelDetailState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = VowelDetailState.Loading
    )
}