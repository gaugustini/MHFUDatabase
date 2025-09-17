package com.gaugustini.mhfudatabase.ui.quest.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.model.Monster
import com.gaugustini.mhfudatabase.data.model.Quest
import com.gaugustini.mhfudatabase.data.repository.QuestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class QuestDetailState(
    val quest: Quest? = null,
    val monsters: List<Monster> = emptyList(),
)

@HiltViewModel
class QuestDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val questRepository: QuestRepository,
) : ViewModel() {

    private val questId: Int = checkNotNull(savedStateHandle["questId"])

    private val _uiState = MutableStateFlow(QuestDetailState())
    val uiState: StateFlow<QuestDetailState> = _uiState.asStateFlow()

    init {
        loadQuestDetails()
    }

    private fun loadQuestDetails() {
        viewModelScope.launch {
            val questDetails = questRepository.getQuestDetails(questId)

            _uiState.update { state ->
                state.copy(
                    quest = questDetails.quest,
                    monsters = questDetails.monsters,
                )
            }
        }
    }

}
