package com.gaugustini.mhfudatabase.ui.weapon.graph

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.enums.WeaponType
import com.gaugustini.mhfudatabase.data.model.WeaponNode
import com.gaugustini.mhfudatabase.data.repository.WeaponRepository
import com.gaugustini.mhfudatabase.ui.components.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WeaponGraphState(
    val weaponType: WeaponType = WeaponType.GREAT_SWORD,
    val nodes: List<WeaponNode> = emptyList(),
)

@HiltViewModel
class WeaponGraphViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    userPreferences: UserPreferences,
    private val weaponRepository: WeaponRepository,
) : BaseViewModel(userPreferences) {

    private val weaponType: WeaponType =
        WeaponType.getWeaponTypeFromString(checkNotNull(savedStateHandle["weaponType"]))

    private val _uiState = MutableStateFlow(WeaponGraphState())
    val uiState: StateFlow<WeaponGraphState> = _uiState.asStateFlow()

    override fun onLanguageChanged(language: Language) {
        loadWeaponGraph(language)
    }

    private fun loadWeaponGraph(language: Language) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    weaponType = weaponType,
                    nodes = weaponRepository.getWeaponGraph(weaponType, language),
                )
            }
        }
    }

}
