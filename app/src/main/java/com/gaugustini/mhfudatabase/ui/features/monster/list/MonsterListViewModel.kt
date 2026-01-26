package com.gaugustini.mhfudatabase.ui.features.monster.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.MonsterRepository
import com.gaugustini.mhfudatabase.domain.enums.Language
import com.gaugustini.mhfudatabase.domain.model.Monster
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

data class MonsterListState(
    val initialTab: MonsterListTab = MonsterListTab.LARGE,
    val monsters: List<Monster> = emptyList(),
)

@HiltViewModel
class MonsterListViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val monsterRepository: MonsterRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MonsterListState())
    val uiState: StateFlow<MonsterListState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadMonsters(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadMonsters(language: Language) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(monsters = monsterRepository.getMonsterList(language.code))
            }
        }
    }

}
