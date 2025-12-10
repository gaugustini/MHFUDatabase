package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.enums.WeaponType
import com.gaugustini.mhfudatabase.data.model.AmmoBow
import com.gaugustini.mhfudatabase.data.model.AmmoBowgun
import com.gaugustini.mhfudatabase.data.model.ItemQuantity
import com.gaugustini.mhfudatabase.data.model.Weapon
import com.gaugustini.mhfudatabase.data.model.WeaponRelation

@Dao
interface WeaponDao {

    // Graph

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
        WHERE
            weapon.weapon_type IN (:type) AND
            weapon_text.language = :language
        """
    )
    suspend fun getWeaponsByWeaponTypes(type: List<WeaponType>, language: String): List<Weapon>

    @Query(
        """
        SELECT
            weapon_parent.weapon_id     AS weaponId,
            weapon_parent.parent_weapon_id AS parentWeaponId
        FROM weapon_parent
        WHERE
            weapon_parent.weapon_id IN (:idList)
        """
    )
    suspend fun getWeaponRelationsForWeapons(idList: List<Int>): List<WeaponRelation>

    // Detail

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
        WHERE
            weapon.id = :id AND
            weapon_text.language = :language
        """
    )
    suspend fun getWeapon(id: Int, language: String): Weapon

    @Query(
        """
        SELECT
            weapon_ammo_bow.charge_1_type   AS charge1Type,
            weapon_ammo_bow.charge_1_level  AS charge1Level,
            weapon_ammo_bow.charge_2_type   AS charge2Type,
            weapon_ammo_bow.charge_2_level  AS charge2Level,
            weapon_ammo_bow.charge_3_type   AS charge3Type,
            weapon_ammo_bow.charge_3_level  AS charge3Level,
            weapon_ammo_bow.charge_4_type   AS charge4Type,
            weapon_ammo_bow.charge_4_level  AS charge4Level,
            weapon_ammo_bow.power           AS power,
            weapon_ammo_bow.close           AS close,
            weapon_ammo_bow.paint           AS paint,
            weapon_ammo_bow.poison          AS poison,
            weapon_ammo_bow.paralysis       AS paralysis,
            weapon_ammo_bow.sleep           AS sleep
        FROM weapon_ammo_bow
        WHERE
            weapon_ammo_bow.weapon_id = :id
        """
    )
    suspend fun getAmmoBowForWeapon(id: Int): AmmoBow?

    @Query(
        """
        SELECT
            weapon_ammo_bowgun.normal       AS normal,
            weapon_ammo_bowgun.pierce       AS pierce,
            weapon_ammo_bowgun.pellet       AS pellet,
            weapon_ammo_bowgun.crag         AS crag,
            weapon_ammo_bowgun.clust        AS clust,
            weapon_ammo_bowgun.recovery     AS recovery,
            weapon_ammo_bowgun.poison       AS poison,
            weapon_ammo_bowgun.paralysis    AS paralysis,
            weapon_ammo_bowgun.sleep        AS sleep,
            weapon_ammo_bowgun.flame        AS flame,
            weapon_ammo_bowgun.water        AS water,
            weapon_ammo_bowgun.thunder      AS thunder,
            weapon_ammo_bowgun.freeze       AS freeze,
            weapon_ammo_bowgun.dragon       AS dragon,
            weapon_ammo_bowgun.tranq        AS tranq,
            weapon_ammo_bowgun.paint        AS paint,
            weapon_ammo_bowgun.demon        AS demon,
            weapon_ammo_bowgun.armor        AS armor,
            weapon_ammo_bowgun.rapid_fire   AS rapidFire
        FROM weapon_ammo_bowgun
        WHERE
            weapon_ammo_bowgun.weapon_id = :id
        """
    )
    suspend fun getAmmoBowgunForWeapon(id: Int): AmmoBowgun?

    @Query(
        """
        SELECT
            weapon_recipe.item_id   AS id,
            item_text.name          AS name,
            weapon_recipe.quantity  AS quantity,
            item.icon_type          AS iconType,
            item.icon_color         AS iconColor
        FROM weapon_recipe
        JOIN item
            ON weapon_recipe.item_id = item.id
        JOIN item_text
            ON weapon_recipe.item_id = item_text.item_id
        WHERE
            weapon_recipe.weapon_id = :id AND
            weapon_recipe.recipe_type = :recipe AND
            item_text.language = :language
        """
    )
    suspend fun getItemsForWeapon(id: Int, recipe: String, language: String): List<ItemQuantity>

    // User Equipment Set

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
        WHERE
            weapon_text.language = :language
        ORDER BY weapon.weapon_type
        """
    )
    suspend fun getWeaponListForUserEquipmentSet(language: String): List<Weapon>

}
