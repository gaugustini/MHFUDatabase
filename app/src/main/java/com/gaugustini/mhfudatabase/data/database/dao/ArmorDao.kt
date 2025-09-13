package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.enums.HunterType
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.data.model.ArmorSet
import com.gaugustini.mhfudatabase.data.model.ItemQuantity
import com.gaugustini.mhfudatabase.data.model.SkillTreePoints

@Dao
interface ArmorDao {

    // List

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
        WHERE
            armor.hunter_type IN ('BOTH', :hunterType) AND
            armor_text.language = :language
        """
    )
    suspend fun getArmorsByHunterType(hunterType: HunterType, language: String): List<Armor>

    @Query(
        """
        SELECT
            armor_set.id            AS id,
            armor_set_text.name     AS name,
            armor_set.rarity        AS rarity,
            armor_set.rank          AS `rank`,
            armor_set.hunter_type   AS hunterType
        FROM armor_set
        JOIN armor_set_text
            ON armor_set.id = armor_set_text.armor_set_id
        WHERE
            armor_set.hunter_type IN ('BOTH', :hunterType) AND
            armor_set_text.language = :language
        """
    )
    suspend fun getArmorSetsByHunterType(hunterType: HunterType, language: String): List<ArmorSet>

    // Detail

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
        WHERE
            armor.id = :id AND
            armor_text.language = :language
        """
    )
    suspend fun getArmor(id: Int, language: String): Armor

    @Query(
        """
        SELECT
            armor_set.id            AS id,
            armor_set_text.name     AS name,
            armor_set.rarity        AS rarity,
            armor_set.rank          AS `rank`,
            armor_set.hunter_type   AS hunterType
        FROM armor_set
        JOIN armor_set_text
            ON armor_set.id = armor_set_text.armor_set_id
        WHERE
            armor_set.id = :id AND
            armor_set_text.language = :language
        """
    )
    suspend fun getArmorSet(id: Int, language: String): ArmorSet

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
        WHERE
            armor.armor_set_id = :id AND
            armor_text.language = :language
        """
    )
    suspend fun getArmorsFromArmorSet(id: Int, language: String): List<Armor>

    @Query(
        """
        SELECT
            armor_skill.skill_tree_id   AS id,
            skill_tree_text.name        AS name,
            armor_skill.point_value     AS pointValue
        FROM armor_skill
        JOIN skill_tree_text
            ON armor_skill.skill_tree_id = skill_tree_text.skill_tree_id
        WHERE
            armor_skill.armor_id = :id AND
            skill_tree_text.language = :language
        """
    )
    suspend fun getSkillsFromArmor(id: Int, language: String): List<SkillTreePoints>

    @Query(
        """
        SELECT
            armor_recipe.item_id    AS id,
            item_text.name          AS name,
            armor_recipe.quantity   AS quantity,
            item.icon_type          AS iconType,
            item.icon_color         AS iconColor
        FROM armor_recipe
        JOIN item
            ON armor_recipe.item_id = item.id
        JOIN item_text
            ON armor_recipe.item_id = item_text.item_id
        WHERE
            armor_recipe.armor_id = :id AND
            item_text.language = :language
        """
    )
    suspend fun getItemsFromArmor(id: Int, language: String): List<ItemQuantity>

}
