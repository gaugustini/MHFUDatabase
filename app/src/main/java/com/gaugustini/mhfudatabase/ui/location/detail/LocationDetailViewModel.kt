package com.gaugustini.mhfudatabase.ui.location.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.enums.Rank
import com.gaugustini.mhfudatabase.data.model.ItemLocation
import com.gaugustini.mhfudatabase.data.model.Location
import com.gaugustini.mhfudatabase.data.repository.LocationRepository
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
    val itemsLowRank: List<ItemLocation> = emptyList(),
    val itemsHighRank: List<ItemLocation> = emptyList(),
    val itemsGRank: List<ItemLocation> = emptyList(),
    val itemsTreasure: List<ItemLocation> = emptyList(),
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
            val locationDetails = locationRepository.getLocationDetails(locationId, language)
            _uiState.update { state ->
                state.copy(
                    location = locationDetails.location,
                    itemsLowRank = locationDetails.items.filter { it.rank == Rank.LOW },
                    itemsHighRank = locationDetails.items.filter { it.rank == Rank.HIGH },
                    itemsGRank = locationDetails.items.filter { it.rank == Rank.G },
                    itemsTreasure = locationDetails.items.filter { it.rank == Rank.TREASURE },
                )
            }
        }
    }

}
