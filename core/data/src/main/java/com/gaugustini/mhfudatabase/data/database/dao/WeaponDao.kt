package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponAmmoBowEntity
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponAmmoBowgunEntity
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

    @Query("SELECT * from weapon_ammo_bow WHERE weapon_ammo_bow.weapon_id = :weaponId")
    suspend fun getAmmoBowByWeaponId(weaponId: Int): WeaponAmmoBowEntity

    @Query("SELECT * from weapon_ammo_bow")
    suspend fun getAmmoBowList(): List<WeaponAmmoBowEntity>

    @Query("SELECT * from weapon_ammo_bowgun WHERE weapon_ammo_bowgun.weapon_id = :weaponId")
    suspend fun getAmmoBowgunByWeaponId(weaponId: Int): WeaponAmmoBowgunEntity

    @Query("SELECT * from weapon_ammo_bowgun")
    suspend fun getAmmoBowgunList(): List<WeaponAmmoBowgunEntity>

}
