package com.gaugustini.mhfudatabase.ui.features.quest.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.QuestRepository
import com.gaugustini.mhfudatabase.domain.enums.HubType
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

data class QuestListState(
    val initialTab: QuestListTab = QuestListTab.VILLAGE,
    val quests: List<Quest> = emptyList(),
    val expandedStarSectionsVillage: Set<Int> = emptySet(),
    val expandedStarSectionsGuild: Set<Int> = emptySet(),
)

@HiltViewModel
class QuestListViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val questRepository: QuestRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuestListState())
    val uiState: StateFlow<QuestListState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadQuests(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadQuests(language: Language) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    quests = questRepository.getQuestList(language.code),
                )
            }
        }
    }

    fun toggleExpansion(starSection: Int, hubType: HubType) {
        _uiState.update { state ->
            when (hubType) {
                HubType.VILLAGE -> {
                    val newSet =
                        if (starSection in state.expandedStarSectionsVillage) {
                            state.expandedStarSectionsVillage - starSection
                        } else {
                            state.expandedStarSectionsVillage + starSection
                        }

                    state.copy(expandedStarSectionsVillage = newSet)
                }

                HubType.GUILD -> {
                    val newSet =
                        if (starSection in state.expandedStarSectionsGuild) {
                            state.expandedStarSectionsGuild - starSection
                        } else {
                            state.expandedStarSectionsGuild + starSection
                        }

                    state.copy(expandedStarSectionsGuild = newSet)
                }
            }
        }
    }

}
