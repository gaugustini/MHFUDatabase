package com.gaugustini.mhfudatabase.ui.decoration.list

import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.model.Decoration
import com.gaugustini.mhfudatabase.data.repository.DecorationRepository
import com.gaugustini.mhfudatabase.ui.components.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DecorationListState(
    val decorations: List<Decoration> = emptyList(),
)

@HiltViewModel
class DecorationListViewModel @Inject constructor(
    userPreferences: UserPreferences,
    private val decorationRepository: DecorationRepository,
) : BaseViewModel(userPreferences) {

    private val _uiState = MutableStateFlow(DecorationListState())
    val uiState: StateFlow<DecorationListState> = _uiState.asStateFlow()

    override fun onLanguageChanged(language: Language) {
        loadDecorations(language)
    }

    private fun loadDecorations(language: Language) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    decorations = decorationRepository.getDecorationList(language),
                )
            }
        }
    }

}
