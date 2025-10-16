package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.model.Armor
import com.gaugustini.mhfudatabase.data.model.Decoration
import com.gaugustini.mhfudatabase.data.model.Item
import com.gaugustini.mhfudatabase.data.model.Location
import com.gaugustini.mhfudatabase.data.model.Monster
import com.gaugustini.mhfudatabase.data.model.Quest
import com.gaugustini.mhfudatabase.data.model.Skill
import com.gaugustini.mhfudatabase.data.model.SkillTree
import com.gaugustini.mhfudatabase.data.model.Weapon

@Dao
interface SearchDao {

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
            armor_text.name LIKE '%' || :query || '%' AND
            armor_text.language = :language
        """
    )
    suspend fun searchArmor(query: String, language: String): List<Armor>

    @Query(
        """
        SELECT
            decoration.id               AS id,
            item_text.name              AS name,
            item_text.description       AS description,
            item.rarity                 AS rarity,
            decoration.required_slots   AS requiredSlots,
            item.buy_price              AS buyPrice,
            item.sell_price             AS sellPrice,
            item.icon_type              AS iconType,
            item.icon_color             AS iconColor
        FROM decoration
        JOIN item
            ON decoration.id = item.id
        JOIN item_text
            ON decoration.id = item_text.item_id
        WHERE
            item_text.name LIKE '%' || :query || '%' AND
            item_text.language = :language
        """
    )
    suspend fun searchDecoration(query: String, language: String): List<Decoration>

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
            item_text.name LIKE '%' || :query || '%' AND
            item_text.language = :language
        """
    )
    suspend fun searchItem(query: String, language: String): List<Item>

    @Query(
        """
        SELECT
            location.id         AS id,
            location_text.name  AS name
        FROM location
        JOIN location_text
            ON location.id = location_text.location_id
        WHERE
            location_text.name LIKE '%' || :query || '%' AND
            location_text.language = :language
        """
    )
    suspend fun searchLocation(query: String, language: String): List<Location>

    @Query(
        """
        SELECT
            monster.id                  AS id,
            monster_text.name           AS name,
            monster_text.ecology        AS ecology,
            monster_text.description    AS description,
            monster.monster_type        AS type,
            monster.golden_smallest_min AS sizeSmallestMin,
            monster.golden_smallest_max AS sizeSmallestMax,
            monster.golden_largest_min  AS sizeLargestMin,
            monster.golden_largest_max  AS sizeLargestMax
        FROM monster
        JOIN monster_text
            ON monster.id = monster_text.monster_id
        WHERE
            monster_text.name LIKE '%' || :query || '%' AND
            monster_text.language = :language
        """
    )
    suspend fun searchMonster(query: String, language: String): List<Monster>

    @Query(
        """
        SELECT
            quest.id               AS id,
            quest_text.name        AS name,
            quest_text.goal        AS goal,
            quest.goal_type        AS goalType,
            quest_text.client      AS client,
            quest_text.description AS description,
            quest.hub_type         AS hubType,
            quest.stars            AS stars,
            quest.quest_type       AS questType,
            quest.reward           AS reward,
            quest.fee              AS fee,
            quest.time_limit       AS timeLimit,
            quest.location_id      AS locationId,
            location_text.name     AS locationName
        FROM quest
        JOIN quest_text
            ON quest.id = quest_text.quest_id
        JOIN location_text
            ON quest.location_id = location_text.location_id
        WHERE
            quest_text.name LIKE '%' || :query || '%' AND
            quest_text.language = :language AND
            location_text.language = :language
        """
    )
    suspend fun searchQuest(query: String, language: String): List<Quest>

    @Query(
        """
        SELECT
            skill_tree.id           AS id,
            skill_tree_text.name    AS name,
            skill_tree.category     AS category
        FROM skill_tree
        JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
        WHERE
            skill_tree_text.name LIKE '%' || :query || '%' AND
            skill_tree_text.language = :language
        """
    )
    suspend fun searchSkillTree(query: String, language: String): List<SkillTree>

    @Query(
        """
        SELECT
            skill.skill_tree_id     AS skillTreeId,
            skill_text.name         AS name,
            skill_text.description  AS description,
            skill.required_points   AS requiredPoints
        FROM skill
        JOIN skill_text
            ON skill.id = skill_text.skill_id
        WHERE
            skill_text.name LIKE '%' || :query || '%' AND
            skill_text.language = :language
        """
    )
    suspend fun searchSkill(query: String, language: String): List<Skill>

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
            weapon_text.name LIKE '%' || :query || '%' AND
            weapon_text.language = :language
        """
    )
    suspend fun searchWeapon(query: String, language: String): List<Weapon>

}
