package com.gaugustini.mhfudatabase.ui.features.monster.detail

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
    val page: MonsterDetailPage = MonsterDetailPage.SUMMARY,
    val monster: Monster? = null,
    val rewardRank: Rank? = null,
    val availableRewardRanks: List<Rank> = emptyList(),
    val rewards: List<MonsterReward> = emptyList(),
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
            val availableRewardRanks = monster.rewards?.keys?.toList() ?: emptyList()
            val firstRewardRank = availableRewardRanks.minByOrNull { it.ordinal }
            val firstRewardRankRewards = monster.rewards?.get(firstRewardRank) ?: emptyList()

            _uiState.update { state ->
                state.copy(
                    monster = monster,
                    rewardRank = firstRewardRank,
                    availableRewardRanks = availableRewardRanks.sortedBy { it.ordinal },
                    rewards = firstRewardRankRewards,
                )
            }
        }
    }

    fun onChangePage(page: MonsterDetailPage) {
        _uiState.update { state ->
            state.copy(
                page = page,
            )
        }
    }

    fun onChangeRank(rank: Rank) {
        _uiState.update { state ->
            state.copy(
                rewardRank = rank,
                rewards = state.monster?.rewards?.get(rank) ?: emptyList(),
            )
        }
    }

}
