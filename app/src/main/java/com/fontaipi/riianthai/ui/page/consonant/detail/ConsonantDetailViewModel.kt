package com.fontaipi.riianthai.ui.page.consonant.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fontaipi.riianthai.data.repository.ConsonantRepository
import com.fontaipi.riianthai.model.Consonant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class ConsonantDetailState {
    data class Success(val consonant: Consonant) : ConsonantDetailState()
    data object Loading : ConsonantDetailState()
}

internal class ConsonantItemArgs(val id: Long) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull<Long>(savedStateHandle["consonantId"]))
}

@HiltViewModel
class ConsonantDetailViewModel @Inject constructor(
    private val consonantRepository: ConsonantRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val consonantItemArgs = ConsonantItemArgs(savedStateHandle)

    val consonant = consonantRepository.getConsonantById(consonantItemArgs.id).map {
        ConsonantDetailState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ConsonantDetailState.Loading
    )
}