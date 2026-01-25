package com.gaugustini.mhfudatabase.ui.skill.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.SkillRepository
import com.gaugustini.mhfudatabase.domain.enums.Language
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.domain.model.SkillTree
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

data class SkillTreeDetailState(
    val initialTab: SkillTreeDetailTab = SkillTreeDetailTab.SKILL_TREE_SUMMARY,
    val skillTree: SkillTree? = null,
    val decorations: List<Decoration> = emptyList(),
    val armors: List<Armor> = emptyList(),
)

@HiltViewModel
class SkillTreeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userPreferences: UserPreferences,
    private val skillRepository: SkillRepository,
) : ViewModel() {

    private val skillTreeId: Int = checkNotNull(savedStateHandle["skillTreeId"])

    private val _uiState = MutableStateFlow(SkillTreeDetailState())
    val uiState: StateFlow<SkillTreeDetailState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadSkillTreeDetails(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadSkillTreeDetails(language: Language) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    skillTree = skillRepository.getSkillTree(skillTreeId, language.code),
//                    decorations = TODO(),
//                    armors = TODO(),
                )
            }
        }
    }

}
