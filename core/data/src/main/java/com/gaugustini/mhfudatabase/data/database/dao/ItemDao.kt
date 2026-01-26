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
    suspend fun getCombinationSources(itemId: Int): List<ItemCombinationEntity>

    @Query(
        """
        SELECT 
            li.location_id AS li_location_id, li.item_id AS li_item_id, li.rank AS li_rank, li.gather_type AS li_gather_type, li.area AS li_area,
            location.*,
            location_text.*
        FROM location_item li
        JOIN location
            ON li.location_id = location.id
        JOIN location_text
            ON location.id = location_text.location_id
            AND location_text.language = :language
        WHERE li.item_id = :itemId
        """
    )
    suspend fun getLocationSources(itemId: Int, language: String): List<LocationItemWithLocation>

    @Query(
        """
        SELECT
            mr.monster_id AS mr_monster_id, mr.reward_condition_id AS mr_reward_condition_id, mr.item_id AS mr_item_id, mr.rank AS mr_rank, mr.stack_size AS mr_stack_size, mr.percentage AS mr_percentage,
            rctxt.reward_condition_id AS rctxt_reward_condition_id, rctxt.language AS rctxt_language, rctxt.name AS rctxt_name,
            monster.*,
            monster_text.*
        FROM monster_reward mr
        JOIN reward_condition_text rctxt
            ON mr.reward_condition_id = rctxt.reward_condition_id
            AND rctxt.language = :language
        JOIN monster
            ON mr.monster_id = monster.id
        JOIN monster_text
            ON monster.id = monster_text.monster_id
            AND monster_text.language = :language
        WHERE mr.item_id = :itemId
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
    suspend fun getCombinationUsages(itemId: Int): List<ItemCombinationEntity>

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
            decoration.id AS dec_id, decoration.required_slots AS dec_required_slots,
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
