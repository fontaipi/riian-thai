package com.fontaipi.riianthai.ui.page.vowel.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fontaipi.riianthai.data.repository.VowelRepository
import com.fontaipi.riianthai.model.Vowel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class VowelsState {
    data class Success(val vowels: List<Vowel>) : VowelsState()
    data object Loading : VowelsState()
}

@HiltViewModel
class VowelViewModel @Inject constructor(
    private val vowelRepository: VowelRepository
) : ViewModel() {
    val vowels = vowelRepository.getVowels().map { VowelsState.Success(it) }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = VowelsState.Loading
    )
}