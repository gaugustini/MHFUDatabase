package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.data.model.Item
import com.gaugustini.mhfudatabase.data.model.ItemCombination
import com.gaugustini.mhfudatabase.data.model.ItemLocation
import com.gaugustini.mhfudatabase.data.model.MonsterReward
import com.gaugustini.mhfudatabase.data.model.Weapon

@Dao
interface ItemDao {

    // List Item

    @Query(
        """
        SELECT
            item.id                 AS id,
            item_text.name          AS name,
            item_text.description   AS description,
            item.rarity             AS rarity,
            item.buy_price          AS buyPrice,
            item.sell_price         AS sellPrice,
            item.carry_max          AS carryMax,
            item.category           AS category,
            item.icon_type          AS iconType,
            item.icon_color         AS iconColor
        FROM item
        JOIN item_text
            ON item.id = item_text.item_id
        WHERE
            item_text.language = :language
        """
    )
    suspend fun getItemList(language: String): List<Item>

    // List Item Combination

    @Query(
        """
        SELECT
            item_combination.item_created_id    AS itemCreatedId,
            item_created_text.name              AS itemCreatedName,
            item_created.icon_type              AS itemCreatedIconType,
            item_created.icon_color             AS itemCreatedIconColor,
            item_combination.item_a_id          AS itemAId,
            item_a_text.name                    AS itemAName,
            item_a.icon_type                    AS itemAIconType,
            item_a.icon_color                   AS itemAIconColor,
            item_combination.item_b_id          AS itemBId,
            item_b_text.name                    AS itemBName,
            item_b.icon_type                    AS itemBIconType,
            item_b.icon_color                   AS itemBIconColor,
            item_combination.combination_type   AS type,
            item_combination.quantity_min       AS quantityMin,
            item_combination.quantity_max       AS quantityMax,
            item_combination.percentage         AS percentage            
        FROM item_combination
        JOIN item item_created
            ON item_combination.item_created_id = item_created.id
        JOIN item_text item_created_text
            ON item_combination.item_created_id = item_created_text.item_id
        JOIN item item_a
            ON item_combination.item_a_id = item_a.id
        JOIN item_text item_a_text
            ON item_combination.item_a_id = item_a_text.item_id
        JOIN item item_b
            ON item_combination.item_b_id = item_b.id
        JOIN item_text item_b_text
            ON item_combination.item_b_id = item_b_text.item_id
        WHERE
            item_created_text.language = :language AND
            item_a_text.language = :language AND
            item_b_text.language = :language
        """
    )
    suspend fun getItemCombinationList(language: String): List<ItemCombination>

    // Detail

    @Query(
        """
        SELECT
            item.id                 AS id,
            item_text.name          AS name,
            item_text.description   AS description,
            item.rarity             AS rarity,
            item.buy_price          AS buyPrice,
            item.sell_price         AS sellPrice,
            item.carry_max          AS carryMax,
            item.category           AS category,
            item.icon_type          AS iconType,
            item.icon_color         AS iconColor
        FROM item
        JOIN item_text
            ON item.id = item_text.item_id
        WHERE
            item.id = :id AND
            item_text.language = :language
        """
    )
    suspend fun getItem(id: Int, language: String): Item

    // Item Usages

    @Query(
        """
        SELECT
            item_combination.item_created_id    AS itemCreatedId,
            item_created_text.name              AS itemCreatedName,
            item_created.icon_type              AS itemCreatedIconType,
            item_created.icon_color             AS itemCreatedIconColor,
            item_combination.item_a_id          AS itemAId,
            item_a_text.name                    AS itemAName,
            item_a.icon_type                    AS itemAIconType,
            item_a.icon_color                   AS itemAIconColor,
            item_combination.item_b_id          AS itemBId,
            item_b_text.name                    AS itemBName,
            item_b.icon_type                    AS itemBIconType,
            item_b.icon_color                   AS itemBIconColor,
            item_combination.combination_type   AS type,
            item_combination.quantity_min       AS quantityMin,
            item_combination.quantity_max       AS quantityMax,
            item_combination.percentage         AS percentage            
        FROM item_combination
        JOIN item item_created
            ON item_combination.item_created_id = item_created.id
        JOIN item_text item_created_text
            ON item_combination.item_created_id = item_created_text.item_id
        JOIN item item_a
            ON item_combination.item_a_id = item_a.id
        JOIN item_text item_a_text
            ON item_combination.item_a_id = item_a_text.item_id
        JOIN item item_b
            ON item_combination.item_b_id = item_b.id
        JOIN item_text item_b_text
            ON item_combination.item_b_id = item_b_text.item_id
        WHERE
            item_combination.item_created_id = :id AND
            item_created_text.language = :language AND
            item_a_text.language = :language AND
            item_b_text.language = :language
        """
    )
    suspend fun getItemCombinationListForItemUsages(
        id: Int,
        language: String
    ): List<ItemCombination>

    // Item Usages

    @Query(
        """
        SELECT
            armor.id                AS id,
            armor.armor_set_id      AS armorSetId,
            armor_text.name         AS name,
            armor_text.description  AS description,
            armor.armor_type        AS type,
            armor.hunter_type       AS hunterType,
            armor.gender            AS gender,
            armor.rarity            AS rarity,
            armor.price             AS price,
            armor.num_slots         AS numberOfSlots,
            armor.defense           AS defense,
            armor.max_defense       AS maxDefense,
            armor.fire_res          AS fire,
            armor.water_res         AS water,
            armor.thunder_res       AS thunder,
            armor.ice_res           AS ice,
            armor.dragon_res        AS dragon
        FROM armor
        JOIN armor_text
            ON armor.id = armor_text.armor_id
        JOIN armor_recipe
            ON armor.id = armor_recipe.armor_id
        WHERE
            armor_recipe.item_id = :id AND
            armor_text.language = :language
        """
    )
    suspend fun getArmorListForItemUsages(id: Int, language: String): List<Armor>

    @Query(
        """
        SELECT
            weapon.id                AS id,
            weapon_text.name         AS name,
            weapon_text.description  AS description,
            weapon.weapon_type       AS type,
            weapon.rarity            AS rarity,
            weapon.affinity          AS affinity,
            weapon.defense           AS defense,
            weapon.num_slots         AS numSlots,
            weapon.attack            AS attack,
            weapon.max_attack        AS maxAttack,
            weapon.price_create      AS priceCreate,
            weapon.price_upgrade     AS priceUpgrade,
            weapon.element_1         AS element1,
            weapon.element_1_value   AS element1Value,
            weapon.element_2         AS element2,
            weapon.element_2_value   AS element2Value,
            weapon.sharpness         AS sharpness,
            weapon.sharpness_plus    AS sharpnessPlus,
            weapon.shelling_type     AS shellingType,
            weapon.shelling_level    AS shellingLevel,
            weapon.song_notes        AS songNotes,
            weapon.reload_speed      AS reloadSpeed,
            weapon.recoil            AS recoil
        FROM weapon
        JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
        JOIN weapon_recipe
            ON weapon.id = weapon_recipe.weapon_id
        WHERE
            weapon_recipe.item_id = :id AND
            weapon_text.language = :language
        """
    )
    suspend fun getWeaponListForItemUsages(id: Int, language: String): List<Weapon>

    // Item Sources

    @Query(
        """
        SELECT
            item_combination.item_created_id    AS itemCreatedId,
            item_created_text.name              AS itemCreatedName,
            item_created.icon_type              AS itemCreatedIconType,
            item_created.icon_color             AS itemCreatedIconColor,
            item_combination.item_a_id          AS itemAId,
            item_a_text.name                    AS itemAName,
            item_a.icon_type                    AS itemAIconType,
            item_a.icon_color                   AS itemAIconColor,
            item_combination.item_b_id          AS itemBId,
            item_b_text.name                    AS itemBName,
            item_b.icon_type                    AS itemBIconType,
            item_b.icon_color                   AS itemBIconColor,
            item_combination.combination_type   AS type,
            item_combination.quantity_min       AS quantityMin,
            item_combination.quantity_max       AS quantityMax,
            item_combination.percentage         AS percentage            
        FROM item_combination
        JOIN item item_created
            ON item_combination.item_created_id = item_created.id
        JOIN item_text item_created_text
            ON item_combination.item_created_id = item_created_text.item_id
        JOIN item item_a
            ON item_combination.item_a_id = item_a.id
        JOIN item_text item_a_text
            ON item_combination.item_a_id = item_a_text.item_id
        JOIN item item_b
            ON item_combination.item_b_id = item_b.id
        JOIN item_text item_b_text
            ON item_combination.item_b_id = item_b_text.item_id
        WHERE
            item_combination.item_a_id = :id OR
            item_combination.item_b_id = :id AND
            item_created_text.language = :language AND
            item_a_text.language = :language AND
            item_b_text.language = :language

        """
    )
    suspend fun getItemCombinationListForItemSources(
        id: Int,
        language: String
    ): List<ItemCombination>

    @Query(
        """
        SELECT
            item.id                     AS id,
            item_text.name              AS name,
            location_item.rank          AS `rank`,
            location_item.gather_type   AS type,
            location_item.area          AS area,
            item.icon_type              AS iconType,
            item.icon_color             AS iconColor
        FROM location_item
        JOIN item
            ON location_item.item_id = item.id
        JOIN item_text
            ON location_item.item_id = item_text.item_id
        WHERE
            location_item.item_id = :id AND
            item_text.language = :language
        ORDER BY
            location_item.area, location_item.gather_type, item_text.name
        """
    )
    suspend fun getItemLocationListForItemSources(id: Int, language: String): List<ItemLocation>

    @Query(
        """
        SELECT
            monster_reward.item_id      AS itemId,
            item_text.name              AS itemName,
            item.icon_type              AS itemIconType,
            item.icon_color             AS itemIconColor,
            reward_condition_text.name  AS condition,
            monster_reward.rank         AS `rank`,
            monster_reward.stack_size   AS stackSize,
            monster_reward.percentage   AS percentage
        FROM monster_reward
        JOIN item_text
            ON monster_reward.item_id = item_text.item_id
        JOIN item
            ON monster_reward.item_id = item.id
        JOIN reward_condition_text
            ON monster_reward.reward_condition_id = reward_condition_text.reward_condition_id
        WHERE
            monster_reward.item_id = :id AND
            item_text.language = :language AND
            reward_condition_text.language = :language
        """
    )
    suspend fun getMonsterRewardListForItemSources(id: Int, language: String): List<MonsterReward>

}
