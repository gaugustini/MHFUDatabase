package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemCombinationEntity
import com.gaugustini.mhfudatabase.data.database.relation.ArmorWithItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.DecorationWithItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.ItemWithText
import com.gaugustini.mhfudatabase.data.database.relation.LocationItemWithLocation
import com.gaugustini.mhfudatabase.data.database.relation.MonsterRewardWithMonster
import com.gaugustini.mhfudatabase.data.database.relation.WeaponWithItemQuantity

/**
 * [Dao] for Item related database operations.
 */
@Dao
interface ItemDao {

    @Query(
        """
        SELECT
            item.*,
            item_text.*
        FROM item
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE item.id = :itemId
        """
    )
    suspend fun getItem(itemId: Int, language: String): ItemWithText

    @Query(
        """
        SELECT
            item.*,
            item_text.*
        FROM item
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        """
    )
    suspend fun getItemList(language: String): List<ItemWithText>

    @Query(
        """
        SELECT
            item.*,
            item_text.*
        FROM item
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE item.id IN (:itemIds)
        """
    )
    suspend fun getItemListByItemIds(itemIds: List<Int>, language: String): List<ItemWithText>

    @Query(
        """
        SELECT
            item_combination.*
        FROM item_combination
        """
    )
    suspend fun getItemCombinationList(): List<ItemCombinationEntity>

    @Query(
        """
        SELECT
            item_combination.*
        FROM item_combination
        WHERE item_combination.item_created_id = :itemId
        """
    )
    suspend fun getCombinationSources(itemId: Int, language: String): List<ItemCombinationEntity>

    @Query(
        """
        SELECT 
            location_item.*,
            location.*,
            location_text.*
        FROM location_item
        JOIN location
            ON location_item.location_id = location.id
        JOIN location_text
            ON location.id = location_text.location_id
            AND location_text.language = :language
        WHERE location_item.item_id = :itemId
        """
    )
    suspend fun getLocationSources(itemId: Int, language: String): List<LocationItemWithLocation>

    @Query(
        """
        SELECT
            monster_reward.*,
            reward_condition_text.*,
            monster.*,
            monster_text.*
        FROM monster_reward
        JOIN reward_condition_text
            ON monster_reward.reward_condition_id = reward_condition_text.reward_condition_id
            AND reward_condition_text.language = :language
        JOIN monster
            ON monster_reward.monster_id = monster.id
        JOIN monster_text
            ON monster.id = monster_text.monster_id
            AND monster_text.language = :language
        WHERE monster_reward.item_id = :itemId
        """
    )
    suspend fun getMonsterRewardSources(itemId: Int, language: String): List<MonsterRewardWithMonster>

    @Query(
        """
        SELECT
            item_combination.*
        FROM item_combination
        WHERE
            item_combination.item_a_id = :itemId
            OR item_combination.item_b_id = :itemId
        """
    )
    suspend fun getCombinationUsages(itemId: Int, language: String): List<ItemCombinationEntity>

    @Query(
        """
        SELECT 
            armor.*,
            armor_text.*,
            armor_recipe.quantity
        FROM armor
        JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = :language
        JOIN armor_recipe
            ON armor.id = armor_recipe.armor_id
        WHERE armor_recipe.item_id = :itemId
        """
    )
    suspend fun getArmorUsages(itemId: Int, language: String): List<ArmorWithItemQuantity>

    @Query(
        """
        SELECT
            decoration.*,
            item.*,
            item_text.*,
            decoration_recipe.quantity
        FROM decoration
        JOIN item
            ON decoration.id = item.id
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        JOIN decoration_recipe
            ON decoration.id = decoration_recipe.decoration_id
        WHERE decoration_recipe.item_id = :itemId
        """
    )
    suspend fun getDecorationUsages(itemId: Int, language: String): List<DecorationWithItemQuantity>

    @Query(
        """
        SELECT
            weapon.*,
            weapon_text.*,
            weapon_recipe.quantity
        FROM weapon
        JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
            AND weapon_text.language = :language
        JOIN weapon_recipe
            ON weapon.id = weapon_recipe.weapon_id
        WHERE weapon_recipe.item_id = :itemId
        """
    )
    suspend fun getWeaponsUsages(itemId: Int, language: String): List<WeaponWithItemQuantity>

}
