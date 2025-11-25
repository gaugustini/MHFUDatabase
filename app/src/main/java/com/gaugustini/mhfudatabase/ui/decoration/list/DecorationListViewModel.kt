package com.gaugustini.mhfudatabase.ui.decoration.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.model.Decoration
import com.gaugustini.mhfudatabase.data.repository.DecorationRepository
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

data class DecorationListState(
    val decorations: List<Decoration> = emptyList(),
)

@HiltViewModel
class DecorationListViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val decorationRepository: DecorationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DecorationListState())
    val uiState: StateFlow<DecorationListState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadDecorations(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadDecorations(language: Language) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    decorations = decorationRepository.getDecorationList(language),
                )
            }
        }
    }

}
