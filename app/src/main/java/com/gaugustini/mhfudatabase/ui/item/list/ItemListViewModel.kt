package com.gaugustini.mhfudatabase.ui.item.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.ItemRepository
import com.gaugustini.mhfudatabase.domain.enums.Language
import com.gaugustini.mhfudatabase.domain.model.Item
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

data class ItemListState(
    val items: List<Item> = emptyList(),
)

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val itemRepository: ItemRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemListState())
    val uiState: StateFlow<ItemListState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadItems(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadItems(language: Language) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(items = itemRepository.getItemList(language.code))
            }
        }
    }

}
