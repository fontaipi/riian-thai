package com.fontaipi.riianthai.ui.page.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fontaipi.riianthai.data.repository.ConsonantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class PracticeState {
    data class Success(val flashcardProgress: Float) : PracticeState()
    data object Loading : PracticeState()
}

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val consonantRepository: ConsonantRepository,
) : ViewModel() {
    val practiceState = consonantRepository.getFlashcardProgress().map {
        PracticeState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PracticeState.Loading
    )
}