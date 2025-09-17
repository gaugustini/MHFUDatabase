package com.gaugustini.mhfudatabase.ui.itemcombination.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.model.ItemCombination
import com.gaugustini.mhfudatabase.data.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ItemCombinationListState(
    val itemCombinations: List<ItemCombination> = emptyList(),
)

@HiltViewModel
class ItemCombinationListViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ItemCombinationListState())
    val uiState: StateFlow<ItemCombinationListState> = _uiState.asStateFlow()

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    itemCombinations = itemRepository.getItemCombinationList(),
                )
            }
        }
    }

}
