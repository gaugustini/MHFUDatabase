package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.ArmorSetWithText
import com.gaugustini.mhfudatabase.domain.enums.HunterType
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.ArmorSet
import com.gaugustini.mhfudatabase.domain.model.Item
import com.gaugustini.mhfudatabase.domain.model.SkillTree

/**
 * Mapper for Armor Set entities.
 */
object ArmorSetMapper {

    fun toModel(
        armorSet: ArmorSetWithText,
        armors: List<Armor>,
    ): ArmorSet {
        val defense = armors.sumOf { it.defense }
        val maxDefense = armors.sumOf { it.maxDefense }
        val fire = armors.sumOf { it.fire }
        val water = armors.sumOf { it.water }
        val thunder = armors.sumOf { it.thunder }
        val ice = armors.sumOf { it.ice }
        val dragon = armors.sumOf { it.dragon }

        val skillsFromArmors = armors.flatMap { it.skills }.groupBy { it.id }
        val recipeFromArmors = armors.flatMap { it.recipe }.groupBy { it.id }

        val skills = skillsFromArmors.map { it ->
            SkillTree(
                id = it.key,
                name = it.value.first().name,
                category = it.value.first().category,
                skills = it.value.first().skills,
                points = it.value.sumOf { it.points ?: 0 }
            )
        }
        val recipe = recipeFromArmors.map { it ->
            Item(
                id = it.key,
                name = it.value.first().name,
                description = it.value.first().description,
                rarity = it.value.first().rarity,
                buyPrice = it.value.first().buyPrice,
                sellPrice = it.value.first().sellPrice,
                carryMax = it.value.first().carryMax,
                iconType = it.value.first().iconType,
                iconColor = it.value.first().iconColor,
                quantity = it.value.sumOf { it.quantity ?: 0 },
                percentage = null,
            )
        }

        return ArmorSet(
            id = armorSet.armorSet.id,
            name = armorSet.armorSetText.name,
            rank = Rank.fromString(armorSet.armorSet.rank),
            hunterType = HunterType.fromString(armorSet.armorSet.hunterType),
            rarity = armorSet.armorSet.rarity,
            defense = defense,
            maxDefense = maxDefense,
            fire = fire,
            water = water,
            thunder = thunder,
            ice = ice,
            dragon = dragon,
            armors = armors,
            skills = skills,
            recipe = recipe,
        )
    }

}
