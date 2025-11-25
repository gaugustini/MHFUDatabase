package com.gaugustini.mhfudatabase.ui.skill.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.model.Skill
import com.gaugustini.mhfudatabase.data.model.SkillPointsArmor
import com.gaugustini.mhfudatabase.data.model.SkillPointsDecoration
import com.gaugustini.mhfudatabase.data.model.SkillTree
import com.gaugustini.mhfudatabase.data.repository.SkillRepository
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
    val skills: List<Skill> = emptyList(),
    val decorations: List<SkillPointsDecoration> = emptyList(),
    val armors: List<SkillPointsArmor> = emptyList(),
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
            val skillTreeDetails = skillRepository.getSkillTreeDetails(skillTreeId, language)

            _uiState.update { state ->
                state.copy(
                    skillTree = skillTreeDetails.skillTree,
                    skills = skillTreeDetails.skills,
                    decorations = skillTreeDetails.decorations,
                    armors = skillTreeDetails.armors,
                )
            }
        }
    }

}
