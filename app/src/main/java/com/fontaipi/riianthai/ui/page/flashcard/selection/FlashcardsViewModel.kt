package com.fontaipi.riianthai.ui.page.flashcard.selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fontaipi.riianthai.data.repository.ConsonantRepository
import com.fontaipi.riianthai.data.repository.VowelRepository
import com.fontaipi.riianthai.ui.page.consonant.detail.ConsonantDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class FlashcardsState {
    data class Success(val consonantProgress: Float, val vowelProgress: Float) : FlashcardsState()
    data object Loading : FlashcardsState()
}

@HiltViewModel
class FlashcardsViewModel @Inject constructor(
    private val consonantRepository: ConsonantRepository,
    private val vowelRepository: VowelRepository,
) : ViewModel() {

    val flashcardsState = combine(
        consonantRepository.getFlashcardProgress(),
        vowelRepository.getFlashcardProgress(),
    ) { consonantCount, vowelCount ->
        FlashcardsState.Success(
            consonantProgress = consonantCount,
            vowelProgress = vowelCount,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = FlashcardsState.Loading
    )
}