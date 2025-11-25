package com.gaugustini.mhfudatabase.ui.monster.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaugustini.mhfudatabase.data.Language
import com.gaugustini.mhfudatabase.data.UserPreferences
import com.gaugustini.mhfudatabase.data.enums.Rank
import com.gaugustini.mhfudatabase.data.model.AilmentStatus
import com.gaugustini.mhfudatabase.data.model.Hitzone
import com.gaugustini.mhfudatabase.data.model.Monster
import com.gaugustini.mhfudatabase.data.model.MonsterItemUsage
import com.gaugustini.mhfudatabase.data.model.MonsterReward
import com.gaugustini.mhfudatabase.data.repository.MonsterRepository
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
    val damage: List<Hitzone> = emptyList(),
    val status: List<AilmentStatus> = emptyList(),
    val items: List<MonsterItemUsage> = emptyList(),
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
            val monsterDetails = monsterRepository.getMonsterDetails(monsterId, language)

            _uiState.update { state ->
                state.copy(
                    monster = monsterDetails.monster,
                    damage = monsterDetails.damage,
                    status = monsterDetails.status,
                    items = monsterDetails.item,
                    rewardsLowRank = monsterDetails.reward.filter { it.rank == Rank.LOW },
                    rewardsHighRank = monsterDetails.reward.filter { it.rank == Rank.HIGH },
                    rewardsGRank = monsterDetails.reward.filter { it.rank == Rank.G },
                )
            }
        }
    }

}
