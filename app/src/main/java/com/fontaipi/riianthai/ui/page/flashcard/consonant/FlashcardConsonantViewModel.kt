package com.fontaipi.riianthai.ui.page.flashcard.consonant

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fontaipi.riianthai.data.repository.ConsonantRepository
import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.ui.component.CardFace
import com.fontaipi.riianthai.ui.page.flashcard.consonant.navigation.FlashcardConsonantArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class FlashcardConsonantState {
    data class Success(val consonants: List<Consonant>) : FlashcardConsonantState()
    data object Loading : FlashcardConsonantState()
}

data class FlashcardUiState(
    val selectedIndex: Int = 0,
    val cardFace: CardFace = CardFace.Front,
    val wrongAnswerIds: Set<Long> = emptySet(),
)

@HiltViewModel
class FlashcardViewModel @Inject constructor(
    private val consonantRepository: ConsonantRepository,
) : ViewModel() {
    private val _flashcardConsonantState =
        MutableStateFlow<FlashcardConsonantState>(FlashcardConsonantState.Loading)
    val flashcardConsonantState = _flashcardConsonantState.asStateFlow()

    private val _flashcardUiState = MutableStateFlow(FlashcardUiState())
    val flashcardConsonantUiState = _flashcardUiState.asStateFlow()

    init {
        viewModelScope.launch {
            val consonants = consonantRepository.getNotLearnedConsonants().first()
            _flashcardConsonantState.update { FlashcardConsonantState.Success(consonants) }
        }
    }

    fun turnCard() {
        _flashcardUiState.update { it.copy(cardFace = it.cardFace.next()) }
    }

    fun nextCard(id: Long, success: Boolean) {
        viewModelScope.launch {
            val state = flashcardConsonantState.first()
            if (state is FlashcardConsonantState.Success) {
                if (_flashcardUiState.value.selectedIndex < state.consonants.size) {
                    _flashcardUiState.update {
                        it.copy(
                            selectedIndex = it.selectedIndex + 1,
                            cardFace = CardFace.Front,
                            wrongAnswerIds = if (success) it.wrongAnswerIds else it.wrongAnswerIds + id
                        )
                    }
                    if (success) {
                        consonantRepository.incrementCount(id)
                    }
                }
            }
        }
    }
}
