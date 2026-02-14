package com.gaugustini.mhfudatabase.data.repository

import com.gaugustini.mhfudatabase.data.database.dao.DecorationDao
import com.gaugustini.mhfudatabase.data.mapper.DecorationMapper
import com.gaugustini.mhfudatabase.domain.filter.DecorationFilter
import com.gaugustini.mhfudatabase.domain.model.Decoration
import javax.inject.Inject
import javax.inject.Singleton

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
        return DecorationMapper.toModel(
            decoration = decorationDao.getDecoration(decorationId, language),
            skills = decorationDao.getDecorationSkillsByDecorationId(decorationId, language),
            recipeA = decorationDao.getDecorationRecipeByDecorationId(decorationId, 1, language),
            recipeB = decorationDao.getDecorationRecipeByDecorationId(decorationId, 2, language),
        )
    }

    /**
     * Returns the list of all decorations or filtering by [DecorationFilter].
     * Note: skills and recipes are not populated.
     */
    suspend fun getDecorationList(
        language: String,
        filter: DecorationFilter = DecorationFilter(),
    ): List<Decoration> {
        return decorationDao.getDecorationList(
            language = language,
            name = filter.name,
            numberOfSlots = filter.numberOfSlots,
            hasSlotFilter = !filter.numberOfSlots.isNullOrEmpty(),
            skills = filter.skills?.map { it.id },
            hasSkillFilter = !filter.skills.isNullOrEmpty(),
        ).map { DecorationMapper.toModel(it) }
    }

}
