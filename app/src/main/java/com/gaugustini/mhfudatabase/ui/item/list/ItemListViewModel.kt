package com.gaugustini.mhfudatabase.ui.item.list

import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.model.Item
import com.gaugustini.mhfudatabase.data.repository.ItemRepository
import com.gaugustini.mhfudatabase.ui.components.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ItemListState(
    val items: List<Item> = emptyList(),
)

@HiltViewModel
class ItemListViewModel @Inject constructor(
    userPreferences: UserPreferences,
    private val itemRepository: ItemRepository,
) : BaseViewModel(userPreferences) {

    private val _uiState = MutableStateFlow(ItemListState())
    val uiState: StateFlow<ItemListState> = _uiState.asStateFlow()

    override fun onLanguageChanged(language: Language) {
        loadItems(language)
    }

    private fun loadItems(language: Language) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(items = itemRepository.getItemList(language))
            }
        }
    }

}
