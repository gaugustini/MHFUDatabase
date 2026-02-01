package com.gaugustini.mhfudatabase.data.database.relation

import androidx.room.Embedded
import com.gaugustini.mhfudatabase.data.database.entity.decoration.DecorationEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillTreeEntity
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillTreeTextEntity

/**
 * Represents the relationship between a decoration and the points of a skill tree present in it.
 */
data class DecorationWithSkillTreePoint(
    @Embedded(prefix = "dec_") val decoration: DecorationEntity,
    @Embedded val item: ItemEntity,
    @Embedded val itemText: ItemTextEntity,
    @Embedded(prefix = "sk_") val skillTree: SkillTreeEntity,
    @Embedded(prefix = "sktxt_") val skillTreeText: SkillTreeTextEntity,
    val points: Int,
)
