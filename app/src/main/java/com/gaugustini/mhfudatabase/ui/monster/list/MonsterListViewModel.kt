package com.gaugustini.mhfudatabase.ui.monster.list

import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.model.Monster
import com.gaugustini.mhfudatabase.data.repository.MonsterRepository
import com.gaugustini.mhfudatabase.ui.components.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MonsterListState(
    val initialTab: MonsterListTab = MonsterListTab.LARGE,
    val monsters: List<Monster> = emptyList(),
)

@HiltViewModel
class MonsterListViewModel @Inject constructor(
    userPreferences: UserPreferences,
    private val monsterRepository: MonsterRepository,
) : BaseViewModel(userPreferences) {

    private val _uiState = MutableStateFlow(MonsterListState())
    val uiState: StateFlow<MonsterListState> = _uiState.asStateFlow()

    override fun onLanguageChanged(language: Language) {
        loadMonsters(language)
    }

    private fun loadMonsters(language: Language) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(monsters = monsterRepository.getMonsterList(language))
            }
        }
    }

}
