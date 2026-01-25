package com.gaugustini.mhfudatabase.ui.monster.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.preferences.UserPreferences
import com.gaugustini.mhfudatabase.data.repository.MonsterRepository
import com.gaugustini.mhfudatabase.domain.enums.Language
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.Monster
import com.gaugustini.mhfudatabase.domain.model.MonsterReward
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

data class MonsterDetailState(
    val initialTab: MonsterDetailTab = MonsterDetailTab.SUMMARY,
    val monster: Monster? = null,
    val rewardsLowRank: List<MonsterReward> = emptyList(),
    val rewardsHighRank: List<MonsterReward> = emptyList(),
    val rewardsGRank: List<MonsterReward> = emptyList(),
)

@HiltViewModel
class MonsterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userPreferences: UserPreferences,
    private val monsterRepository: MonsterRepository,
) : ViewModel() {

    private val monsterId: Int = checkNotNull(savedStateHandle["monsterId"])

    private val _uiState = MutableStateFlow(MonsterDetailState())
    val uiState: StateFlow<MonsterDetailState> = _uiState.asStateFlow()

    init {
        observeLanguage()
    }

    private fun observeLanguage() {
        userPreferences.getLanguage()
            .distinctUntilChanged()
            .onEach { language ->
                loadMonsterDetails(language)
            }
            .launchIn(viewModelScope)
    }

    private fun loadMonsterDetails(language: Language) {
        viewModelScope.launch {
            val monster = monsterRepository.getMonster(monsterId, language.code)

            _uiState.update { state ->
                state.copy(
                    monster = monster,
                    rewardsLowRank = monster.rewards?.get(Rank.LOW) ?: emptyList(),
                    rewardsHighRank = monster.rewards?.get(Rank.HIGH) ?: emptyList(),
                    rewardsGRank = monster.rewards?.get(Rank.G) ?: emptyList(),
                )
            }
        }
    }

}
