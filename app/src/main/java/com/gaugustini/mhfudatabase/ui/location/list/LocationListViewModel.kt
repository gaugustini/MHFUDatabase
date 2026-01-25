package com.gaugustini.mhfudatabase.ui.location.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.LocationRepository
import com.gaugustini.mhfudatabase.domain.enums.Language
import com.gaugustini.mhfudatabase.domain.model.Location
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

data class LocationListState(
    val locations: List<Location> = emptyList(),
)

@HiltViewModel
class LocationListViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val locationRepository: LocationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LocationListState())
    val uiState: StateFlow<LocationListState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadLocations(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadLocations(language: Language) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(locations = locationRepository.getLocationList(language.code))
            }
        }
    }

}
