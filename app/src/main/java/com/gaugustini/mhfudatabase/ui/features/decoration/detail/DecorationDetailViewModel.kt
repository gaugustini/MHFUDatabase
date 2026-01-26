package com.gaugustini.mhfudatabase.ui.features.decoration.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.DecorationRepository
import com.gaugustini.mhfudatabase.domain.enums.Language
import com.gaugustini.mhfudatabase.domain.model.Decoration
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

data class DecorationDetailState(
    val decoration: Decoration? = null,
)

@HiltViewModel
class DecorationDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userPreferences: UserPreferences,
    private val decorationRepository: DecorationRepository,
) : ViewModel() {

    private val decorationId: Int = checkNotNull(savedStateHandle["decorationId"])

    private val _uiState = MutableStateFlow(DecorationDetailState())
    val uiState: StateFlow<DecorationDetailState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadDecorationDetails(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadDecorationDetails(language: Language) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    decoration = decorationRepository.getDecoration(decorationId, language.code),
                )
            }
        }
    }

}
