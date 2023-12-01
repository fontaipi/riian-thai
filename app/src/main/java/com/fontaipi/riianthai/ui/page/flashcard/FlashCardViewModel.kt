package com.fontaipi.riianthai.ui.page.flashcard

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fontaipi.riianthai.data.repository.ConsonantRepository
import com.fontaipi.riianthai.model.Consonant
import com.fontaipi.riianthai.model.ConsonantClass
import com.fontaipi.riianthai.ui.page.flashcard.component.CardFace
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class FlashCardItemArgs(val consonantClass: ConsonantClass) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(savedStateHandle["consonantClass"] ?: ConsonantClass.All)
}

data class FlashCardState(
    val consonants: List<Consonant> = listOf(),
    val selectedIndex: Int = 0,
    val cardFace: CardFace = CardFace.Front
)

@HiltViewModel
class FlashCardViewModel @Inject constructor(
    private val consonantRepository: ConsonantRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val flashCardItemArgs = FlashCardItemArgs(savedStateHandle)

    private val _flashCardState = MutableStateFlow(FlashCardState())
    val flashCardState = _flashCardState.asStateFlow()

    init {
        viewModelScope.launch {
            _flashCardState.update {
                it.copy(
                    consonants = consonantRepository.getConsonants(flashCardItemArgs.consonantClass)
                        .first()
                )
            }
        }
    }

    fun shuffle() {
        _flashCardState.update {
            it.copy(
                consonants = it.consonants.shuffled(),
                selectedIndex = 0,
                cardFace = CardFace.Front
            )
        }
    }

    fun restart() {
        _flashCardState.update { it.copy(selectedIndex = 0, cardFace = CardFace.Front) }
    }

    fun turnCard() {
        _flashCardState.update { it.copy(cardFace = it.cardFace.next()) }
    }

    fun nextCard(id: Long, success: Boolean) {
        viewModelScope.launch {
            if (success) {
                consonantRepository.incrementCount(id)
            }
        }
        if (_flashCardState.value.selectedIndex < _flashCardState.value.consonants.size - 1) {
            _flashCardState.update {
                it.copy(
                    selectedIndex = it.selectedIndex + 1,
                    cardFace = CardFace.Front
                )
            }
        }
    }
}
