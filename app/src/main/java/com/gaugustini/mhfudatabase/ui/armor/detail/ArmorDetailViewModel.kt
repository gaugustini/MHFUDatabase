package com.gaugustini.mhfudatabase.ui.armor.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.data.model.ArmorSet
import com.gaugustini.mhfudatabase.data.model.ItemQuantity
import com.gaugustini.mhfudatabase.data.model.SkillTreePoints
import com.gaugustini.mhfudatabase.data.repository.ArmorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ArmorDetailState(
    val initialTab: ArmorDetailTab = ArmorDetailTab.ARMOR_DETAIL,
    val armor: Armor? = null,
    val armorSkills: List<SkillTreePoints> = emptyList(),
    val armorRecipe: List<ItemQuantity> = emptyList(),
    val armorSet: ArmorSet? = null,
    val armorSetArmors: List<Armor> = emptyList(),
    val armorSetSkills: List<SkillTreePoints> = emptyList(),
    val armorSetRecipe: List<ItemQuantity> = emptyList(),
)

@HiltViewModel
class ArmorDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val armorRepository: ArmorRepository,
) : ViewModel() {

    private val armorId: Int = checkNotNull(savedStateHandle["armorId"])

    private val _uiState = MutableStateFlow(ArmorDetailState())
    val uiState: StateFlow<ArmorDetailState> = _uiState.asStateFlow()

    init {
        loadArmorDetails()
    }

    private fun loadArmorDetails() {
        viewModelScope.launch {
            val armorDetails = armorRepository.getArmorDetails(armorId)
            val armorSetDetails = armorRepository.getArmorSetDetails(armorDetails.armor.armorSetId)

            _uiState.update { state ->
                state.copy(
                    armor = armorDetails.armor,
                    armorSkills = armorDetails.skills,
                    armorRecipe = armorDetails.recipe,
                    armorSet = armorSetDetails.armorSet,
                    armorSetArmors = armorSetDetails.armors,
                    armorSetSkills = armorSetDetails.skills,
                    armorSetRecipe = armorSetDetails.recipe,
                )
            }
        }
    }

}
