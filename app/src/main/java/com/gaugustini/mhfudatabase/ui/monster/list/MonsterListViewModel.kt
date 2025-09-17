package com.gaugustini.mhfudatabase.ui.monster.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.model.Monster
import com.gaugustini.mhfudatabase.data.repository.MonsterRepository
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
    private val monsterRepository: MonsterRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MonsterListState())
    val uiState: StateFlow<MonsterListState> = _uiState.asStateFlow()

    init {
        loadMonsters()
    }

    private fun loadMonsters() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(monsters = monsterRepository.getMonsterList())
            }
        }
    }

}
