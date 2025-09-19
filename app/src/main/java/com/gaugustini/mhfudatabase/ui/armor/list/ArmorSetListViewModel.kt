package com.gaugustini.mhfudatabase.ui.armor.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.enums.HunterType
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.data.model.ArmorSet
import com.gaugustini.mhfudatabase.data.repository.ArmorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ArmorSetListState(
    val initialTab: ArmorSetListTab = ArmorSetListTab.BLADEMASTER,
    val armorsBySet: Map<Int, List<Armor>> = emptyMap(),
    val armorSetsBlade: List<ArmorSet> = emptyList(),
    val armorSetsGunner: List<ArmorSet> = emptyList(),
    val expandedArmorSetsBlade: Set<Int> = emptySet(),
    val expandedArmorSetsGunner: Set<Int> = emptySet()
)

@HiltViewModel
class ArmorSetListViewModel @Inject constructor(
    private val armorRepository: ArmorRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArmorSetListState())
    val uiState: StateFlow<ArmorSetListState> = _uiState.asStateFlow()

    init {
        loadArmorSets()
    }

    private fun loadArmorSets() {
        viewModelScope.launch {
            val armors = armorRepository.getArmorList()
            val armorSetsBlade = armorRepository.getArmorSetList(HunterType.BLADE)
            val armorSetsGunner = armorRepository.getArmorSetList(HunterType.GUNNER)

            _uiState.update {
                it.copy(
                    armorsBySet = armors.groupBy { armor -> armor.armorSetId },
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
