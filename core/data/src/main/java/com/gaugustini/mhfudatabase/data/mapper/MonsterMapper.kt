package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterStatusEntity
import com.gaugustini.mhfudatabase.data.database.relation.ItemWithText
import com.gaugustini.mhfudatabase.data.database.relation.MonsterHitzone
import com.gaugustini.mhfudatabase.data.database.relation.MonsterRewardItem
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
        damageStats: List<MonsterHitzone>? = null,
        ailmentStats: List<MonsterStatusEntity>? = null,
        itemEffectiveness: MonsterItemEntity? = null,
        rewards: List<MonsterRewardItem>? = null,
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
            itemEffectiveness = itemEffectiveness?.let { toMonsterItemEffectiveness(it) },
            rewards = rewards?.map { toMonsterReward(it) }?.groupBy { it.rank },
            quests = quests?.map { QuestMapper.toModel(it) },
        )
    }

    fun toMonsterDamageStats(
        monsterHitzone: MonsterHitzone,
    ): MonsterDamageStats {
        return MonsterDamageStats(
            monsterId = monsterHitzone.monsterHitzoneEntity.monsterId,
            name = monsterHitzone.hitzoneTextEntity.name,
            cut = monsterHitzone.monsterHitzoneEntity.cut,
            impact = monsterHitzone.monsterHitzoneEntity.impact,
            shot = monsterHitzone.monsterHitzoneEntity.shot,
            fire = monsterHitzone.monsterHitzoneEntity.fire,
            water = monsterHitzone.monsterHitzoneEntity.water,
            thunder = monsterHitzone.monsterHitzoneEntity.thunder,
            ice = monsterHitzone.monsterHitzoneEntity.ice,
            dragon = monsterHitzone.monsterHitzoneEntity.dragon,
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
        monsterRewardItem: MonsterRewardItem,
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
