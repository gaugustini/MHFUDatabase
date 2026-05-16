package com.gaugustini.mhfudatabase.ui.features.veggie.list

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

data class VeggieListState(
    val veggieLocations: List<VeggieLocation> = emptyList(),
)

@HiltViewModel
class VeggieListViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val veggieRepository: VeggieRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(VeggieListState())
    val uiState: StateFlow<VeggieListState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadVeggieLocations(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadVeggieLocations(language: Language) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    veggieLocations = veggieRepository.getVeggieLocationList(language.code),
                )
            }
        }
    }

}
