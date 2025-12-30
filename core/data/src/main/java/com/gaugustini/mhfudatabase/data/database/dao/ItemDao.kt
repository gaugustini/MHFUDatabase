package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemCombinationEntity
import com.gaugustini.mhfudatabase.data.database.relation.ItemWithText

/**
 * [Dao] for Item related database operations.
 */
@Dao
interface ItemDao {

    @Query(
        """
        SELECT item.*, item_text.* FROM item
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE item.id = :itemId
        """
    )
    suspend fun getItem(itemId: Int, language: String): ItemWithText

    @Query(
        """
        SELECT item.*, item_text.* FROM item
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        """
    )
    suspend fun getItemList(language: String): List<ItemWithText>

    @Query(
        """
        SELECT item.*, item_text.* FROM item
        JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = :language
        WHERE item.id IN (:itemIds)
        """
    )
    suspend fun getItemListByIds(itemIds: List<Int>, language: String): List<ItemWithText>

    @Query("SELECT item_combination.* FROM item_combination")
    suspend fun getItemCombinationList(): List<ItemCombinationEntity>

}
