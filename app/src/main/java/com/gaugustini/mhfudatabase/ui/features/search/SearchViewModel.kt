package com.gaugustini.mhfudatabase.ui.features.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.SearchRepository
import com.gaugustini.mhfudatabase.domain.enums.Language
import com.gaugustini.mhfudatabase.domain.model.SearchResults
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchState(
    val query: String = "",
    val results: SearchResults = SearchResults(),
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val searchRepository: SearchRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchState())
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                observeQuery(language)
            }
            .launchIn(viewModelScope)
    }

    @OptIn(FlowPreview::class)
    private fun observeQuery(language: Language) {
        viewModelScope.launch {
            _uiState
                .map { it.query }
                .debounce(300L)
                .distinctUntilChanged()
                .collectLatest { currentQuery ->
                    if (currentQuery.isNotBlank()) {
                        try {
                            _uiState.update {
                                it.copy(results = searchRepository.search(currentQuery, language.code))
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
