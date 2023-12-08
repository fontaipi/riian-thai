package com.fontaipi.riianthai.ui.page.consonant.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fontaipi.riianthai.data.repository.ConsonantRepository
import com.fontaipi.riianthai.model.Consonant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed class ConsonantsState {
    data class Success(val consonants: List<Consonant>) : ConsonantsState()
    data object Loading : ConsonantsState()
}

@HiltViewModel
class ConsonantsViewModel @Inject constructor(
    private val consonantRepository: ConsonantRepository
) : ViewModel() {
    val consonants = consonantRepository.getConsonants().map {
        ConsonantsState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ConsonantsState.Loading
    )
}