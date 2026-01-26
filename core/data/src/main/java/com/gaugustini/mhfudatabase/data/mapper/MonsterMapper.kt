package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterStatusEntity
import com.gaugustini.mhfudatabase.data.database.relation.ItemWithText
import com.gaugustini.mhfudatabase.data.database.relation.MonsterHitzoneWithText
import com.gaugustini.mhfudatabase.data.database.relation.MonsterRewardWithItem
import com.gaugustini.mhfudatabase.data.database.relation.MonsterWithText
import com.gaugustini.mhfudatabase.data.database.relation.QuestWithText
import com.gaugustini.mhfudatabase.domain.enums.MonsterAilment
import com.gaugustini.mhfudatabase.domain.enums.MonsterState
import com.gaugustini.mhfudatabase.domain.enums.MonsterType
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.Monster
import com.gaugustini.mhfudatabase.domain.model.MonsterAilmentStats
import com.gaugustini.mhfudatabase.domain.model.MonsterDamageStats
import com.gaugustini.mhfudatabase.domain.model.MonsterItemEffectiveness
import com.gaugustini.mhfudatabase.domain.model.MonsterReward

/**
 * Mapper for Monster entities.
 */
object MonsterMapper {

    fun toModel(
        monster: MonsterWithText,
        damageStats: List<MonsterHitzoneWithText>? = null,
        ailmentStats: List<MonsterStatusEntity>? = null,
        itemEffectiveness: List<MonsterItemEntity>? = null,
        rewards: List<MonsterRewardWithItem>? = null,
        quests: List<QuestWithText>? = null,
    ): Monster {
        return Monster(
            id = monster.monster.id,
            name = monster.monsterText.name,
            ecology = monster.monsterText.ecology,
            description = monster.monsterText.description,
            type = MonsterType.fromString(monster.monster.monsterType),
            sizeSmallestMin = monster.monster.sizeSmallestMin,
            sizeSmallestMax = monster.monster.sizeSmallestMax,
            sizeLargestMin = monster.monster.sizeLargestMin,
            sizeLargestMax = monster.monster.sizeLargestMax,
            damageStats = damageStats?.map { toMonsterDamageStats(it) },
            ailmentStats = ailmentStats?.map { toMonsterAilmentStats(it) },
            itemEffectiveness = itemEffectiveness?.map { toMonsterItemEffectiveness(it) },
            rewards = rewards?.map { toMonsterReward(it) }?.groupBy { it.rank },
            quests = quests?.map { QuestMapper.toModel(it) },
        )
    }

    fun toMonsterDamageStats(
        monsterHitzone: MonsterHitzoneWithText,
    ): MonsterDamageStats {
        return MonsterDamageStats(
            monsterId = monsterHitzone.monsterHitzone.monsterId,
            name = monsterHitzone.hitzoneText.name,
            cut = monsterHitzone.monsterHitzone.cut,
            impact = monsterHitzone.monsterHitzone.impact,
            shot = monsterHitzone.monsterHitzone.shot,
            fire = monsterHitzone.monsterHitzone.fire,
            water = monsterHitzone.monsterHitzone.water,
            thunder = monsterHitzone.monsterHitzone.thunder,
            ice = monsterHitzone.monsterHitzone.ice,
            dragon = monsterHitzone.monsterHitzone.dragon,
        )
    }

    fun toMonsterAilmentStats(
        monsterStatusEntity: MonsterStatusEntity,
    ): MonsterAilmentStats {
        return MonsterAilmentStats(
            monsterId = monsterStatusEntity.monsterId,
            type = MonsterAilment.fromString(monsterStatusEntity.status),
            initial = monsterStatusEntity.initial,
            increase = monsterStatusEntity.increase,
            max = monsterStatusEntity.max,
            duration = monsterStatusEntity.duration,
            damage = monsterStatusEntity.damage,
        )
    }

    fun toMonsterItemEffectiveness(
        monsterItemEntity: MonsterItemEntity,
    ): MonsterItemEffectiveness {
        return MonsterItemEffectiveness(
            monsterId = monsterItemEntity.monsterId,
            monsterState = MonsterState.fromString(monsterItemEntity.state),
            canUsePitfallTrap = monsterItemEntity.pitfall,
            canUseShockTrap = monsterItemEntity.shock,
            canUseFlashBomb = monsterItemEntity.flash,
            canUseSonicBomb = monsterItemEntity.sonic,
            canUseDungBomb = monsterItemEntity.dung,
            canUseMeat = monsterItemEntity.meat,
        )
    }

    fun toMonsterReward(
        monsterRewardItem: MonsterRewardWithItem,
    ): MonsterReward {
        return MonsterReward(
            item = ItemMapper.toModel(ItemWithText(monsterRewardItem.item, monsterRewardItem.itemText)),
            condition = monsterRewardItem.rewardConditionText.name,
            rank = Rank.fromString(monsterRewardItem.monsterReward.rank),
            stackSize = monsterRewardItem.monsterReward.stackSize,
            percentage = monsterRewardItem.monsterReward.percentage,
        )
    }

}
