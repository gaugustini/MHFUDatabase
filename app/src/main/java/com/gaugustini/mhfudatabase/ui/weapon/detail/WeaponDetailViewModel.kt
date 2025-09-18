package com.gaugustini.mhfudatabase.ui.weapon.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.model.AmmoBow
import com.gaugustini.mhfudatabase.data.model.AmmoBowgun
import com.gaugustini.mhfudatabase.data.model.ItemQuantity
import com.gaugustini.mhfudatabase.data.model.Weapon
import com.gaugustini.mhfudatabase.data.repository.WeaponRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WeaponDetailState(
    val initialTab: WeaponDetailTab = WeaponDetailTab.SUMMARY,
    val weapon: Weapon? = null,
    val ammoBow: AmmoBow? = null,
    val ammoBowgun: AmmoBowgun? = null,
    val recipeCreate: List<ItemQuantity> = emptyList(),
    val recipeUpgrade: List<ItemQuantity> = emptyList(),
    val paths: List<List<Weapon>> = emptyList(),
    val finals: List<Weapon> = emptyList(),
)

@HiltViewModel
class WeaponDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val weaponRepository: WeaponRepository,
) : ViewModel() {

    private val weaponId: Int = checkNotNull(savedStateHandle["weaponId"])

    private val _uiState = MutableStateFlow(WeaponDetailState())
    val uiState: StateFlow<WeaponDetailState> = _uiState.asStateFlow()

    init {
        loadWeaponDetails()
    }

    private fun loadWeaponDetails() {
        viewModelScope.launch {
            val weaponDetails = weaponRepository.getWeaponDetails(weaponId)

            _uiState.update { state ->
                state.copy(
                    weapon = weaponDetails.weapon,
                    ammoBow = weaponDetails.ammoBow,
                    ammoBowgun = weaponDetails.ammoBowgun,
                    recipeCreate = weaponDetails.recipeCreate,
                    recipeUpgrade = weaponDetails.recipeUpgrade,
                    paths = weaponDetails.paths,
                    finals = weaponDetails.finals,
                )
            }
        }
    }

}
