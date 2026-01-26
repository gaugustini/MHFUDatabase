package com.gaugustini.mhfudatabase.ui.features.userset.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.repository.UserEquipmentSetRepository
import com.gaugustini.mhfudatabase.domain.model.UserEquipmentSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class UserSetListState(
    val sets: List<UserEquipmentSet> = emptyList(),
)

@HiltViewModel
class UserSetListViewModel @Inject constructor(
    private val userEquipmentSetRepository: UserEquipmentSetRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserSetListState())
    val uiState: StateFlow<UserSetListState> = _uiState.asStateFlow()

    init {
        observeEquipmentSets()
    }

    private fun observeEquipmentSets() {
        userEquipmentSetRepository.getEquipmentSets()
            .distinctUntilChanged()
            .onEach { sets ->
                _uiState.update { state ->
                    state.copy(sets = sets)
                }
            }
            .launchIn(viewModelScope)
    }

}
