package com.gaugustini.mhfudatabase.ui.item.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.model.Item
import com.gaugustini.mhfudatabase.data.model.ItemSources
import com.gaugustini.mhfudatabase.data.model.ItemUsages
import com.gaugustini.mhfudatabase.data.repository.ItemRepository
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

data class ItemDetailState(
    val initialTab: ItemDetailTab = ItemDetailTab.ITEM_SUMMARY,
    val item: Item? = null,
    val sources: ItemSources = ItemSources(emptyList(), emptyList(), emptyList()),
    val usages: ItemUsages = ItemUsages(emptyList(), emptyList(), emptyList(), emptyList()),
)

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userPreferences: UserPreferences,
    private val itemRepository: ItemRepository,
) : ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle["itemId"])

    private val _uiState = MutableStateFlow(ItemDetailState())
    val uiState: StateFlow<ItemDetailState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadItemDetails(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadItemDetails(language: Language) {
        viewModelScope.launch {
            val itemDetails = itemRepository.getItemDetails(itemId, language)

            _uiState.update { state ->
                state.copy(
                    item = itemDetails.item,
                    sources = itemDetails.sources,
                    usages = itemDetails.usages,
                )
            }
        }
    }

}
