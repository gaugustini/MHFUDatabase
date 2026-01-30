package com.gaugustini.mhfudatabase.ui.features.quest.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.QuestRepository
import com.gaugustini.mhfudatabase.domain.enums.Language
import com.gaugustini.mhfudatabase.domain.model.Quest
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

data class QuestDetailState(
    val quest: Quest? = null,
)

@HiltViewModel
class QuestDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userPreferences: UserPreferences,
    private val questRepository: QuestRepository,
) : ViewModel() {

    private val questId: Int = checkNotNull(savedStateHandle["questId"])

    private val _uiState = MutableStateFlow(QuestDetailState())
    val uiState: StateFlow<QuestDetailState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadQuestDetails(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadQuestDetails(language: Language) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    quest = questRepository.getQuest(questId, language.code),
                )
            }
        }
    }

}
