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
        SELECT weapon.*, weapon_text.* FROM weapon
        JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
            AND weapon_text.language = :language
        WHERE weapon.id = :weaponId
        """
    )
    suspend fun getWeapon(weaponId: Int, language: String): WeaponWithText

    @Query(
        """
        SELECT weapon.*, weapon_text.* FROM weapon
        JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
            AND weapon_text.language = :language
        """
    )
    suspend fun getWeaponList(language: String): List<WeaponWithText>

    @Query(
        """
        SELECT weapon.*, weapon_text.* FROM weapon
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

    @Query("SELECT * from weapon_ammo_bow WHERE weapon_ammo_bow.weapon_id = :weaponId")
    suspend fun getAmmoBowByWeaponId(weaponId: Int): WeaponAmmoBowEntity

    @Query("SELECT * from weapon_ammo_bowgun WHERE weapon_ammo_bowgun.weapon_id = :weaponId")
    suspend fun getAmmoBowgunByWeaponId(weaponId: Int): WeaponAmmoBowgunEntity

    @Query(
        """
        SELECT weapon_recipe.weapon_id AS equipmentId, weapon_recipe.quantity, item.*, item_text.*
        FROM weapon_recipe
        JOIN item
            ON weapon_recipe.item_id = item.id
        JOIN item_text
            ON weapon_recipe.item_id = item_text.item_id
            AND item_text.language = :language
        WHERE weapon_recipe.weapon_id = :weaponId AND weapon_recipe.recipe_type = :recipeType
        """
    )
    suspend fun getWeaponRecipeByWeaponId(
        weaponId: Int,
        recipeType: String,
        language: String
    ): List<EquipmentItemQuantity>

    @Query("SELECT * FROM weapon_parent WHERE weapon_parent.weapon_id IN (:weaponIds)")
    suspend fun getWeaponRelationsByWeaponIds(weaponIds: List<Int>): List<WeaponParentEntity>

}
