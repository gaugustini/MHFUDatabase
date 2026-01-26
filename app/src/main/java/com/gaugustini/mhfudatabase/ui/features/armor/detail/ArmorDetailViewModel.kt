package com.gaugustini.mhfudatabase.ui.features.armor.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.ArmorRepository
import com.gaugustini.mhfudatabase.domain.enums.Language
import com.gaugustini.mhfudatabase.domain.model.Armor
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

data class ArmorDetailState(
    val initialTab: ArmorDetailTab = ArmorDetailTab.ARMOR_DETAIL,
    val armor: Armor? = null,
    val armorSet: ArmorSet? = null,
)

@HiltViewModel
class ArmorDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userPreferences: UserPreferences,
    private val armorRepository: ArmorRepository,
) : ViewModel() {

    private val armorId: Int = checkNotNull(savedStateHandle["armorId"])

    private val _uiState = MutableStateFlow(ArmorDetailState())
    val uiState: StateFlow<ArmorDetailState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadArmorDetails(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadArmorDetails(language: Language) {
        viewModelScope.launch {
            val armor = armorRepository.getArmor(armorId, language.code)
            val armorSet = armorRepository.getArmorSet(armor.armorSetId, language.code)

            _uiState.update { state ->
                state.copy(
                    armor = armor,
                    armorSet = armorSet,
                )
            }
        }
    }

}
