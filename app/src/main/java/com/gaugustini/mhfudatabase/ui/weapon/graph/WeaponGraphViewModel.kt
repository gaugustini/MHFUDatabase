package com.gaugustini.mhfudatabase.ui.weapon.graph

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.WeaponRepository
import com.gaugustini.mhfudatabase.domain.enums.Language
import com.gaugustini.mhfudatabase.domain.enums.WeaponType
import com.gaugustini.mhfudatabase.domain.model.WeaponNode
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

data class WeaponGraphState(
    val weaponType: WeaponType = WeaponType.GREAT_SWORD,
    val nodes: List<WeaponNode> = emptyList(),
)

@HiltViewModel
class WeaponGraphViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userPreferences: UserPreferences,
    private val weaponRepository: WeaponRepository,
) : ViewModel() {

    private val weaponType: WeaponType =
        WeaponType.fromString(checkNotNull(savedStateHandle["weaponType"]))

    private val _uiState = MutableStateFlow(WeaponGraphState())
    val uiState: StateFlow<WeaponGraphState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadWeaponGraph(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadWeaponGraph(language: Language) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    weaponType = weaponType,
                    nodes = weaponRepository.getWeaponTree(weaponType, language.code),
                )
            }
        }
    }

}
