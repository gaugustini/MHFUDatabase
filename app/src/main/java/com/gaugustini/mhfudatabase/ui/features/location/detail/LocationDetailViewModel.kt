package com.gaugustini.mhfudatabase.ui.features.location.detail

import androidx.lifecycle.SavedStateHandle
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

data class LocationDetailState(
    val initialTab: LocationDetailTab = LocationDetailTab.LOW_RANK,
    val location: Location? = null,
)

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userPreferences: UserPreferences,
    private val locationRepository: LocationRepository,
) : ViewModel() {

    private val locationId: Int = checkNotNull(savedStateHandle["locationId"])

    private val _uiState = MutableStateFlow(LocationDetailState())
    val uiState: StateFlow<LocationDetailState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadLocationDetails(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadLocationDetails(language: Language) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    location = locationRepository.getLocation(locationId, language.code),
                )
            }
        }
    }

}
