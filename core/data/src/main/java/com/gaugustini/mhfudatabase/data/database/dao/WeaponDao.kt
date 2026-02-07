package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponAmmoBowEntity
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponAmmoBowgunEntity
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponParentEntity
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.WeaponWithText

/**
 * [Dao] for Weapon related database operations.
 */
@Dao
interface WeaponDao {

    @Query(
        """
        SELECT
            weapon.*,
            weapon_text.*
        FROM weapon
        JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
            AND weapon_text.language = :language
        WHERE weapon.id = :weaponId
        """
    )
    suspend fun getWeapon(weaponId: Int, language: String): WeaponWithText

    @Query(
        """
        SELECT
            weapon.*,
            weapon_text.*
        FROM weapon
        JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
            AND weapon_text.language = :language
        WHERE
            (:name IS NULL OR (weapon_text.name LIKE '%' || :name || '%' OR weapon_text.full_name LIKE '%' || :name || '%'))
            AND (:hasWeaponTypeFilter = 0 OR weapon.weapon_type IN (:weaponType))
            AND (:hasSlotFilter = 0 OR weapon.num_slots IN (:numberOfSlots))
            AND (:hasRarityFilter = 0 OR weapon.rarity IN (:rarity))
            AND (:hasElementFilter = 0 OR (weapon.element_1 IN (:elementType) OR weapon.element_2 IN (:elementType)))
        """
    )
    suspend fun getWeaponList(
        language: String,
        name: String?,
        weaponType: List<String>?,
        hasWeaponTypeFilter: Boolean,
        numberOfSlots: List<Int>?,
        hasSlotFilter: Boolean,
        rarity: List<Int>?,
        hasRarityFilter: Boolean,
        elementType: List<String>?,
        hasElementFilter: Boolean,
    ): List<WeaponWithText>

    @Query(
        """
        SELECT
            weapon.*,
            weapon_text.*
        FROM weapon
        JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
            AND weapon_text.language = :language
        WHERE weapon.weapon_type IN (:weaponTypes)
        """
    )
    suspend fun getWeaponListByWeaponTypes(
        weaponTypes: List<String>,
        language: String
    ): List<WeaponWithText>

    @Query(
        """
        SELECT
            weapon_ammo_bow.*
        FROM weapon_ammo_bow
        WHERE weapon_ammo_bow.weapon_id = :weaponId
        """
    )
    suspend fun getAmmoBowByWeaponId(weaponId: Int): WeaponAmmoBowEntity?

    @Query(
        """
        SELECT weapon_ammo_bowgun.*
        FROM weapon_ammo_bowgun
        WHERE weapon_ammo_bowgun.weapon_id = :weaponId
        """
    )
    suspend fun getAmmoBowgunByWeaponId(weaponId: Int): WeaponAmmoBowgunEntity?

    @Query(
        """
        SELECT
            weapon_recipe.weapon_id AS equipmentId,
            item.*,
            item_text.*,
            weapon_recipe.quantity
        FROM weapon_recipe
        JOIN item
            ON weapon_recipe.item_id = item.id
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE
            weapon_recipe.weapon_id = :weaponId
            AND weapon_recipe.recipe_type = :recipeType
        ORDER BY quantity DESC
        """
    )
    suspend fun getWeaponRecipeByWeaponId(
        weaponId: Int,
        recipeType: String,
        language: String
    ): List<EquipmentItemQuantity>

    @Query(
        """
        SELECT
            weapon_parent.*
        FROM weapon_parent
        WHERE weapon_parent.weapon_id IN (:weaponIds)
        """
    )
    suspend fun getWeaponRelationsByWeaponIds(weaponIds: List<Int>): List<WeaponParentEntity>

}
