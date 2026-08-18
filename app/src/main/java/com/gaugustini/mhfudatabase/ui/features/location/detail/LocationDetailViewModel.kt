package com.gaugustini.mhfudatabase.ui.features.location.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.LocationRepository
import com.gaugustini.mhfudatabase.domain.enums.Language
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.GatheringPoint
import com.gaugustini.mhfudatabase.domain.model.Location
import com.gaugustini.mhfudatabase.domain.model.Quest
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
    val page: LocationDetailPage = LocationDetailPage.SUMMARY,
    val rank: Rank? = null,
    val area: Int? = null,
    val availableRanks: List<Rank> = emptyList(),
    val availableAreas: List<Int> = emptyList(),
    val location: Location? = null,
    val gatheringPoints: List<GatheringPoint> = emptyList(),
    val quests: List<Quest> = emptyList(),
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
            val location = locationRepository.getLocation(locationId, language.code)

            val availableRanks = location.gatheringPoints?.keys?.toList() ?: emptyList()
            val firstRank = availableRanks.minByOrNull { it.ordinal }
            val firstRankGatheringPoints = location.gatheringPoints?.get(firstRank) ?: emptyList()

            val availableAreas = firstRankGatheringPoints.map { it.area }.distinct()
            val firstArea = availableAreas.minOrNull()

            val gatheringPoints = firstRankGatheringPoints.filter { it.area == firstArea }

            _uiState.update { state ->
                state.copy(
                    rank = firstRank,
                    area = firstArea,
                    availableRanks = availableRanks.sortedBy { it.ordinal },
                    availableAreas = availableAreas.sorted(),
                    location = location,
                    gatheringPoints = gatheringPoints,
                    quests = location.quests ?: emptyList(),
                )
            }
        }
    }

    fun onChangePage(page: LocationDetailPage) {
        _uiState.update { state ->
            state.copy(
                page = page,
            )
        }
    }

    fun onChangeRank(rank: Rank) {
        _uiState.update { state ->
            val rankGatheringPoints = state.location?.gatheringPoints?.get(rank) ?: emptyList()
            val availableAreas = rankGatheringPoints.map { it.area }.distinct()
            val area = if (state.area in availableAreas) state.area else availableAreas.minOrNull()
            val gatheringPoints = rankGatheringPoints.filter { it.area == area }

            state.copy(
                rank = rank,
                area = area,
                availableAreas = availableAreas.sorted(),
                gatheringPoints = gatheringPoints,
            )
        }
    }

    fun onChangeArea(area: Int) {
        _uiState.update { state ->
            val rankGatheringPoints = state.location?.gatheringPoints?.get(state.rank) ?: emptyList()
            val gatheringPoints = rankGatheringPoints.filter { it.area == area }

            state.copy(
                area = area,
                gatheringPoints = gatheringPoints,
            )
        }
    }

}
