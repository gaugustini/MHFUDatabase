package com.gaugustini.mhfudatabase.ui.userset.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.data.model.EquipmentSetDecoration
import com.gaugustini.mhfudatabase.data.model.UserEquipmentSet
import com.gaugustini.mhfudatabase.data.model.Weapon
import com.gaugustini.mhfudatabase.data.repository.UserEquipmentSetRepository
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

data class UserSetDetailState(
    val set: UserEquipmentSet? = null,
    val weapon: Weapon? = null,
    val armors: List<Armor> = emptyList(),
    val decorations: List<EquipmentSetDecoration> = emptyList(),
)

@HiltViewModel
class UserSetDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userPreferences: UserPreferences,
    private val userEquipmentSetRepository: UserEquipmentSetRepository,
) : ViewModel() {

    private val setId: Int = checkNotNull(savedStateHandle["setId"])

    private val _uiState = MutableStateFlow(UserSetDetailState())
    val uiState: StateFlow<UserSetDetailState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadEquipmentSetDetails(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadEquipmentSetDetails(language: Language) {
        if (setId != 0) {
            viewModelScope.launch {
                val equipmentSetDetails =
                    userEquipmentSetRepository.getEquipmentSetDetails(setId, language)

                _uiState.update { state ->
                    state.copy(
                        set = equipmentSetDetails.set,
                        weapon = equipmentSetDetails.weapon,
                        armors = equipmentSetDetails.armors,
                        decorations = equipmentSetDetails.decorations,
                    )
                }
            }
        }
    }

}
