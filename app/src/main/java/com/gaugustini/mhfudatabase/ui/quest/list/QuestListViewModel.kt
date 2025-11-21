package com.gaugustini.mhfudatabase.ui.quest.list

import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.enums.HubType
import com.gaugustini.mhfudatabase.data.model.Quest
import com.gaugustini.mhfudatabase.data.repository.QuestRepository
import com.gaugustini.mhfudatabase.ui.components.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    userPreferences: UserPreferences,
    private val questRepository: QuestRepository,
) : BaseViewModel(userPreferences) {

    private val _uiState = MutableStateFlow(QuestListState())
    val uiState: StateFlow<QuestListState> = _uiState.asStateFlow()

    override fun onLanguageChanged(language: Language) {
        loadQuests(language)
    }

    private fun loadQuests(language: Language) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    quests = questRepository.getQuestList(language),
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
