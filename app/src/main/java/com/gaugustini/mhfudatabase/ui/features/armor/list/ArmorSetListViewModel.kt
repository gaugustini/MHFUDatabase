package com.gaugustini.mhfudatabase.ui.features.armor.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.ArmorRepository
import com.gaugustini.mhfudatabase.domain.enums.Language
import com.gaugustini.mhfudatabase.domain.filter.ArmorSetFilter
import com.gaugustini.mhfudatabase.domain.model.ArmorSet
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

data class ArmorSetListState(
    val language: Language = Language.ENGLISH,
    val filter: ArmorSetFilter = ArmorSetFilter(),
    val armorSets: List<ArmorSet> = emptyList(),
    val expandedArmorSets: Set<Int> = emptySet()
)

@HiltViewModel
class ArmorSetListViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val armorRepository: ArmorRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArmorSetListState())
    val uiState: StateFlow<ArmorSetListState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                _uiState.update { it.copy(language = language) }
                loadArmorSets()
            }
            .launchIn(viewModelScope)
    }

    private fun loadArmorSets() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    armorSets = armorRepository.getArmorSetList(state.language.code)
                )
            }
        }
    }

    fun onToggleExpansion(armorSetId: Int) {
        _uiState.update { state ->
            val newSet =
                if (armorSetId in state.expandedArmorSets)
                    state.expandedArmorSets - armorSetId
                else
                    state.expandedArmorSets + armorSetId

            state.copy(expandedArmorSets = newSet)
        }
    }

    fun onFilterChange(filter: ArmorSetFilter) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    armorSets = armorRepository.getArmorSetList(state.language.code, filter),
                    filter = filter
                )
            }
        }
    }

}
