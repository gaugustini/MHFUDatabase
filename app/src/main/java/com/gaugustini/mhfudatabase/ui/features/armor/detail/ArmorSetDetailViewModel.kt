package com.gaugustini.mhfudatabase.ui.features.armor.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.ArmorRepository
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

data class ArmorSetDetailState(
    val armorSet: ArmorSet? = null,
)

@HiltViewModel
class ArmorSetDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userPreferences: UserPreferences,
    private val armorRepository: ArmorRepository,
) : ViewModel() {

    private val armorSetId: Int = checkNotNull(savedStateHandle["armorSetId"])

    private val _uiState = MutableStateFlow(ArmorSetDetailState())
    val uiState: StateFlow<ArmorSetDetailState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadArmorSetDetails(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadArmorSetDetails(language: Language) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    armorSet = armorRepository.getArmorSet(armorSetId, language.code),
                )
            }
        }
    }

}
