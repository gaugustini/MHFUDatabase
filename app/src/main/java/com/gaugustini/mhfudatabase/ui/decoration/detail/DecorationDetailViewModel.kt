package com.gaugustini.mhfudatabase.ui.decoration.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.model.Decoration
import com.gaugustini.mhfudatabase.data.model.ItemQuantity
import com.gaugustini.mhfudatabase.data.model.SkillTreePoints
import com.gaugustini.mhfudatabase.data.repository.DecorationRepository
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
    private val decorationRepository: DecorationRepository,
) : ViewModel() {

    private val decorationId: Int = checkNotNull(savedStateHandle["decorationId"])

    private val _uiState = MutableStateFlow(DecorationDetailState())
    val uiState: StateFlow<DecorationDetailState> = _uiState.asStateFlow()

    init {
        loadDecorationDetails()
    }

    private fun loadDecorationDetails() {
        viewModelScope.launch {
            val decorationDetails = decorationRepository.getDecorationDetails(decorationId)

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
