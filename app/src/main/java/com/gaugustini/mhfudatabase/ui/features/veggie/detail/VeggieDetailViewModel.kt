package com.gaugustini.mhfudatabase.ui.features.veggie.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.VeggieRepository
import com.gaugustini.mhfudatabase.domain.enums.Language
import com.gaugustini.mhfudatabase.domain.model.VeggieLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VeggieDetailState(
    val veggieLocation: VeggieLocation? = null,
)

@HiltViewModel
class VeggieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userPreferences: UserPreferences,
    private val veggieRepository: VeggieRepository,
) : ViewModel() {

    private val veggieId: Int = checkNotNull(savedStateHandle["veggieId"])

    private val _uiState = MutableStateFlow(VeggieDetailState())
    val uiState: StateFlow<VeggieDetailState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadVeggieTrades(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadVeggieTrades(language: Language) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    veggieLocation = veggieRepository.getVeggieLocation(veggieId, language.code),
                )
            }
        }
    }

}
