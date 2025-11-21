package com.gaugustini.mhfudatabase.ui.location.list

import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.model.Location
import com.gaugustini.mhfudatabase.data.repository.LocationRepository
import com.gaugustini.mhfudatabase.ui.components.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LocationListState(
    val locations: List<Location> = emptyList(),
)

@HiltViewModel
class LocationListViewModel @Inject constructor(
    userPreferences: UserPreferences,
    private val locationRepository: LocationRepository,
) : BaseViewModel(userPreferences) {

    private val _uiState = MutableStateFlow(LocationListState())
    val uiState: StateFlow<LocationListState> = _uiState.asStateFlow()

    override fun onLanguageChanged(language: Language) {
        loadLocations(language)
    }

    private fun loadLocations(language: Language) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(locations = locationRepository.getLocationList(language))
            }
        }
    }

}
