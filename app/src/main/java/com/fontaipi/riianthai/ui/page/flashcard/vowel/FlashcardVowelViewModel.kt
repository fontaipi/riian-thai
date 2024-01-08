package com.fontaipi.riianthai.ui.page.flashcard.vowel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fontaipi.riianthai.data.repository.VowelRepository
import com.fontaipi.riianthai.ui.component.CardFace
import com.fontaipi.riianthai.ui.page.flashcard.consonant.FlashcardConsonantState
import com.fontaipi.riianthai.ui.page.flashcard.consonant.FlashcardUiState
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

sealed class FlashcardVowelState {
    data class Success(val flashcards: List<VowelFormFlashcard>) : FlashcardVowelState()
    data object Loading : FlashcardVowelState()
}

@HiltViewModel
class FlashcardVowelViewModel @Inject constructor(
    private val vowelRepository: VowelRepository,
) : ViewModel() {
    private val _flashcardVowelState =
        MutableStateFlow<FlashcardVowelState>(FlashcardVowelState.Loading)
    val flashcardVowelState = _flashcardVowelState.asStateFlow()

    private val _flashcardUiState = MutableStateFlow(FlashcardUiState())
    val flashcardConsonantUiState = _flashcardUiState.asStateFlow()

    init {
        viewModelScope.launch {
            val vowels = vowelRepository.getNotLearnedVowels().first()
            val cards = vowels.flatMap { vowel ->
                vowel.writingForms.filter { it.count < 10 }.map { form ->
                    VowelFormFlashcard(
                        vowelForm = form,
                        vowelClass = vowel.vowelClass,
                        soundType = vowel.soundType,
                        thaiScript = vowel.thaiScript,
                        soundFile = vowel.soundFile
                    )
                }
            }
            _flashcardVowelState.update { FlashcardVowelState.Success(cards) }
        }
    }

    fun turnCard() {
        _flashcardUiState.update { it.copy(cardFace = it.cardFace.next()) }
    }

    fun nextCard(id: Long, success: Boolean) {
        viewModelScope.launch {
            val state = flashcardVowelState.first()
            if (state is FlashcardVowelState.Success) {
                if (_flashcardUiState.value.selectedIndex < state.flashcards.size) {
                    _flashcardUiState.update {
                        it.copy(
                            selectedIndex = it.selectedIndex + 1,
                            cardFace = CardFace.Front,
                            wrongAnswerIds = if (success) it.wrongAnswerIds else it.wrongAnswerIds + id
                        )
                    }
                    if (success) {
                        vowelRepository.incrementVowelFormCount(id)
                    }
                }
            }
        }
    }
}
