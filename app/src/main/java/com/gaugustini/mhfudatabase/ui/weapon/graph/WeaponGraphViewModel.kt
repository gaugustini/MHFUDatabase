package com.gaugustini.mhfudatabase.ui.weapon.graph

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.enums.WeaponType
import com.gaugustini.mhfudatabase.data.model.WeaponNode
import com.gaugustini.mhfudatabase.data.repository.WeaponRepository
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
    private val weaponRepository: WeaponRepository,
) : ViewModel() {

    private val weaponType: WeaponType =
        WeaponType.getWeaponTypeFromString(checkNotNull(savedStateHandle["weaponType"]))

    private val _uiState = MutableStateFlow(WeaponGraphState())
    val uiState: StateFlow<WeaponGraphState> = _uiState.asStateFlow()

    init {
        loadWeaponGraph()
    }

    private fun loadWeaponGraph() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    weaponType = weaponType,
                    nodes = weaponRepository.getWeaponGraph(weaponType),
                )
            }
        }
    }

}
