package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.DecorationDao
import com.gaugustini.mhfudatabase.data.mapper.DecorationMapper
import com.gaugustini.mhfudatabase.domain.model.Decoration
import javax.inject.Inject
import javax.inject.Singleton

//TODO: Add skills and recipe for decorations
/**
 * Data repository for Decoration.
 */
@Singleton
class DecorationRepository @Inject constructor(
    private val decorationDao: DecorationDao,
) {

    /**
     * Returns the decoration with the given ID.
     */
    suspend fun getDecoration(
        decorationId: Int,
        language: String,
    ): Decoration {
        val decorationWithText = decorationDao.getDecoration(decorationId, language)

        return DecorationMapper.toModel(
            decoration = decorationWithText,
            skills = emptyList(),
            recipeA = emptyList(),
            recipeB = emptyList(),
        )
    }

    /**
     * Returns the list of all decorations.
     */
    suspend fun getDecorationList(
        language: String,
    ): List<Decoration> {
        val decorationsWithText = decorationDao.getDecorationList(language)

        return decorationsWithText.map {
            DecorationMapper.toModel(
                decoration = it,
                skills = emptyList(),
                recipeA = emptyList(),
                recipeB = emptyList(),
            )
        }
    }

}
