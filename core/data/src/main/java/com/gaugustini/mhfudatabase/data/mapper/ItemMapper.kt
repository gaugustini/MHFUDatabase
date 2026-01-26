package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.ArmorWithItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.ArmorWithText
import com.gaugustini.mhfudatabase.data.database.relation.DecorationWithItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.DecorationWithText
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.ItemWithText
import com.gaugustini.mhfudatabase.data.database.relation.LocationItemWithLocation
import com.gaugustini.mhfudatabase.data.database.relation.LocationWithText
import com.gaugustini.mhfudatabase.data.database.relation.MonsterRewardWithMonster
import com.gaugustini.mhfudatabase.data.database.relation.MonsterWithText
import com.gaugustini.mhfudatabase.data.database.relation.WeaponWithItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.WeaponWithText
import com.gaugustini.mhfudatabase.domain.enums.GatherType
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor
import com.gaugustini.mhfudatabase.domain.enums.ItemIconType
import com.gaugustini.mhfudatabase.domain.enums.Rank
import com.gaugustini.mhfudatabase.domain.model.GatheringSource
import com.gaugustini.mhfudatabase.domain.model.Item
import com.gaugustini.mhfudatabase.domain.model.ItemCombination
import com.gaugustini.mhfudatabase.domain.model.ItemQuantity
import com.gaugustini.mhfudatabase.domain.model.ItemSources
import com.gaugustini.mhfudatabase.domain.model.ItemUsages
import com.gaugustini.mhfudatabase.domain.model.MonsterSource
import com.gaugustini.mhfudatabase.domain.model.Usage

/**
 * Mapper for Item entities.
 */
object ItemMapper {

    fun toModel(
        item: ItemWithText,
        sources: ItemSources? = null,
        usages: ItemUsages? = null,
    ): Item {
        return Item(
            id = item.item.id,
            name = item.itemText.name,
            description = item.itemText.description,
            rarity = item.item.rarity,
            buyPrice = item.item.buyPrice,
            sellPrice = item.item.sellPrice,
            carryMax = item.item.carryMax,
            iconType = ItemIconType.fromString(item.item.iconType),
            iconColor = ItemIconColor.fromString(item.item.iconColor),
            sources = sources,
            usages = usages,
        )
    }

    fun toItemQuantity(
        equipmentItemQuantity: EquipmentItemQuantity,
    ): ItemQuantity {
        return ItemQuantity(
            item = Item(
                id = equipmentItemQuantity.item.id,
                name = equipmentItemQuantity.itemText.name,
                description = equipmentItemQuantity.itemText.description,
                rarity = equipmentItemQuantity.item.rarity,
                buyPrice = equipmentItemQuantity.item.buyPrice,
                sellPrice = equipmentItemQuantity.item.sellPrice,
                carryMax = equipmentItemQuantity.item.carryMax,
                iconType = ItemIconType.fromString(equipmentItemQuantity.item.iconType),
                iconColor = ItemIconColor.fromString(equipmentItemQuantity.item.iconColor),
                sources = null,
                usages = null,
            ),
            quantity = equipmentItemQuantity.quantity,
        )
    }

    fun toItemSources(
        combinations: List<ItemCombination>,
        locations: List<LocationItemWithLocation>,
        monsterRewards: List<MonsterRewardWithMonster>,
    ): ItemSources {
        val gatheringSources = locations.map {
            GatheringSource(
                location = LocationMapper.toModel(LocationWithText(it.location, it.locationText)),
                rank = Rank.fromString(it.locationItem.rank),
                type = GatherType.fromString(it.locationItem.gatherType),
                area = it.locationItem.area,
            )
        }
        val monsterSources = monsterRewards.map {
            MonsterSource(
                monster = MonsterMapper.toModel(MonsterWithText(it.monster, it.monsterText)),
                condition = it.rewardConditionText.name,
                rank = Rank.fromString(it.monsterReward.rank),
                stackSize = it.monsterReward.stackSize,
                percentage = it.monsterReward.percentage,
            )
        }

        return ItemSources(
            combinations = combinations,
            locations = gatheringSources,
            monsterRewards = monsterSources,
        )
    }

    fun toItemUsages(
        combinations: List<ItemCombination>,
        armors: List<ArmorWithItemQuantity>,
        decorations: List<DecorationWithItemQuantity>,
        weapons: List<WeaponWithItemQuantity>,
    ): ItemUsages {
        val armorUsages = armors.map {
            Usage(
                craftable = ArmorMapper.toModel(ArmorWithText(it.armor, it.armorText)),
                quantity = it.quantity,
            )
        }
        val decorationUsages = decorations.map {
            Usage(
                craftable = DecorationMapper.toModel(
                    DecorationWithText(it.decoration, it.item, it.itemText)
                ),
                quantity = it.quantity,
            )
        }
        val weaponUsages = weapons.map {
            Usage(
                craftable = WeaponMapper.toModel(WeaponWithText(it.weapon, it.weaponText)),
                quantity = it.quantity
            )
        }

        return ItemUsages(
            combinations = combinations,
            armors = armorUsages,
            decorations = decorationUsages,
            weapons = weaponUsages,
        )
    }

}
