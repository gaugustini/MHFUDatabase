package com.gaugustini.mhfudatabase.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.gaugustini.mhfudatabase.data.database.relation.DecorationWithText

/**
 * [Dao] for Decoration related database operations.
 */
@Dao
interface DecorationDao {

    @Query(
        """
        SELECT decoration.*, item.* ,item_text.* FROM decoration
        JOIN item
            ON decoration.id = item.id
        JOIN item_text
            ON decoration.id = item_text.item_id
            AND item_text.language = :language
        WHERE decoration.id = :decorationId
        """
    )
    suspend fun getDecoration(decorationId: Int, language: String): DecorationWithText

    @Query(
        """
        SELECT decoration.*, item.* ,item_text.* FROM decoration
        JOIN item
            ON decoration.id = item.id
        JOIN item_text
            ON decoration.id = item_text.item_id
            AND item_text.language = :language
        """
    )
    suspend fun getDecorationList(language: String): List<DecorationWithText>

}
