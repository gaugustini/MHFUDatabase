package com.gaugustini.mhfudatabase.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.model.SearchResults
import com.gaugustini.mhfudatabase.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchState(
    val query: String = "",
    val results: SearchResults = SearchResults(),
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchState())
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()

    init {
        observeQuery()
    }

    @OptIn(FlowPreview::class)
    private fun observeQuery() {
        viewModelScope.launch {
            _uiState
                .map { it.query }
                .debounce(300L)
                .distinctUntilChanged()
                .collectLatest { currentQuery ->
                    if (currentQuery.isNotBlank()) {
                        try {
                            _uiState.update {
                                it.copy(results = searchRepository.search(currentQuery))
                            }
                        } catch (e: Exception) {
                            Log.e("SearchViewModel", "Error while searching: $currentQuery", e)

                            _uiState.update { it.copy(results = SearchResults()) }
                        }
                    }
                }
        }
    }

    fun onQueryChange(newQuery: String) {
        _uiState.update { it.copy(query = newQuery) }
    }

    fun onClearQuery() {
        _uiState.value = SearchState()
    }

}
