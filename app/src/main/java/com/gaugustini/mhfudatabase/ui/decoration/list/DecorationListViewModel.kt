package com.gaugustini.mhfudatabase.ui.decoration.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.model.Decoration
import com.gaugustini.mhfudatabase.data.repository.DecorationRepository
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
    private val decorationRepository: DecorationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DecorationListState())
    val uiState: StateFlow<DecorationListState> = _uiState.asStateFlow()

    init {
        loadDecorations()
    }

    private fun loadDecorations() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    decorations = decorationRepository.getDecorationList(),
                )
            }
        }
    }

}
