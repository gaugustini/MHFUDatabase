package com.gaugustini.mhfudatabase.ui.weapon.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.model.AmmoBow
import com.gaugustini.mhfudatabase.data.model.AmmoBowgun
import com.gaugustini.mhfudatabase.data.model.ItemQuantity
import com.gaugustini.mhfudatabase.data.model.Weapon
import com.gaugustini.mhfudatabase.data.repository.WeaponRepository
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
    private val userPreferences: UserPreferences,
    private val weaponRepository: WeaponRepository,
) : ViewModel() {

    private val weaponId: Int = checkNotNull(savedStateHandle["weaponId"])

    private val _uiState = MutableStateFlow(WeaponDetailState())
    val uiState: StateFlow<WeaponDetailState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadWeaponDetails(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadWeaponDetails(language: Language) {
        viewModelScope.launch {
            val weaponDetails = weaponRepository.getWeaponDetails(weaponId, language)

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
