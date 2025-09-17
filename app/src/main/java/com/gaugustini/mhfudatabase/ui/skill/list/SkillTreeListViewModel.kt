package com.gaugustini.mhfudatabase.ui.skill.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.model.SkillTree
import com.gaugustini.mhfudatabase.data.repository.SkillRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SkillTreeListState(
    val skills: List<SkillTree> = emptyList(),
)

@HiltViewModel
class SkillTreeListViewModel @Inject constructor(
    private val skillRepository: SkillRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SkillTreeListState())
    val uiState: StateFlow<SkillTreeListState> = _uiState.asStateFlow()

    init {
        loadSkills()
    }

    private fun loadSkills() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(skills = skillRepository.getSkillTreeList())
            }
        }
    }

}
