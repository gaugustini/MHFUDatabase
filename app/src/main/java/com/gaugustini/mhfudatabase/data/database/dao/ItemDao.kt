package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.model.Item
import com.gaugustini.mhfudatabase.data.model.ItemCombination

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

}
