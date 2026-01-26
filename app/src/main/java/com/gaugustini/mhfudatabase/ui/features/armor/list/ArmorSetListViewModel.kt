package com.gaugustini.mhfudatabase.ui.features.armor.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.ArmorRepository
import com.gaugustini.mhfudatabase.domain.enums.HunterType
import com.gaugustini.mhfudatabase.domain.enums.Language
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
    val initialTab: ArmorSetListTab = ArmorSetListTab.BLADEMASTER,
    val armorSetsBlade: List<ArmorSet> = emptyList(),
    val armorSetsGunner: List<ArmorSet> = emptyList(),
    val expandedArmorSetsBlade: Set<Int> = emptySet(),
    val expandedArmorSetsGunner: Set<Int> = emptySet()
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
                loadArmorSets(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadArmorSets(language: Language) {
        viewModelScope.launch {
            val armorSets = armorRepository.getArmorSetList(language.code)
            val armorSetsBlade = armorSets.filter {
                it.hunterType == HunterType.BLADE || it.hunterType == HunterType.BOTH
            }
            val armorSetsGunner = armorSets.filter {
                it.hunterType == HunterType.GUNNER || it.hunterType == HunterType.BOTH
            }

            _uiState.update { state ->
                state.copy(
                    armorSetsBlade = armorSetsBlade,
                    armorSetsGunner = armorSetsGunner,
                )
            }
        }
    }

    fun toggleExpansion(armorSetId: Int, hunterType: HunterType) {
        _uiState.update { state ->
            when (hunterType) {
                HunterType.BLADE -> {
                    val newSet =
                        if (armorSetId in state.expandedArmorSetsBlade)
                            state.expandedArmorSetsBlade - armorSetId
                        else
                            state.expandedArmorSetsBlade + armorSetId

                    state.copy(expandedArmorSetsBlade = newSet)
                }

                HunterType.GUNNER -> {
                    val newSet =
                        if (armorSetId in state.expandedArmorSetsGunner)
                            state.expandedArmorSetsGunner - armorSetId
                        else
                            state.expandedArmorSetsGunner + armorSetId

                    state.copy(expandedArmorSetsGunner = newSet)
                }

                else -> state
            }
        }
    }

}
