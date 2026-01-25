package com.gaugustini.mhfudatabase.ui.features.skill.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.SkillRepository
import com.gaugustini.mhfudatabase.domain.enums.Language
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

data class SkillTreeListState(
    val skills: List<SkillTree> = emptyList(),
)

@HiltViewModel
class SkillTreeListViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val skillRepository: SkillRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SkillTreeListState())
    val uiState: StateFlow<SkillTreeListState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadSkills(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadSkills(language: Language) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(skills = skillRepository.getSkillTreeList(language.code))
            }
        }
    }

}
