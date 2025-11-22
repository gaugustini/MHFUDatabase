package com.gaugustini.mhfudatabase.ui.decoration.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.model.Decoration
import com.gaugustini.mhfudatabase.data.model.ItemQuantity
import com.gaugustini.mhfudatabase.data.model.SkillTreePoints
import com.gaugustini.mhfudatabase.data.repository.DecorationRepository
import com.gaugustini.mhfudatabase.ui.components.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DecorationDetailState(
    val decoration: Decoration? = null,
    val skills: List<SkillTreePoints> = emptyList(),
    val recipeA: List<ItemQuantity> = emptyList(),
    val recipeB: List<ItemQuantity> = emptyList(),
)

@HiltViewModel
class DecorationDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    userPreferences: UserPreferences,
    private val decorationRepository: DecorationRepository,
) : BaseViewModel(userPreferences) {

    private val decorationId: Int = checkNotNull(savedStateHandle["decorationId"])

    private val _uiState = MutableStateFlow(DecorationDetailState())
    val uiState: StateFlow<DecorationDetailState> = _uiState.asStateFlow()

    override fun onLanguageChanged(language: Language) {
        loadDecorationDetails(language)
    }

    private fun loadDecorationDetails(language: Language) {
        viewModelScope.launch {
            val decorationDetails = decorationRepository.getDecorationDetails(decorationId, language)

            _uiState.update {
                it.copy(
                    decoration = decorationDetails.decoration,
                    skills = decorationDetails.skills,
                    recipeA = decorationDetails.recipeA,
                    recipeB = decorationDetails.recipeB,
                )
            }
        }
    }

}
