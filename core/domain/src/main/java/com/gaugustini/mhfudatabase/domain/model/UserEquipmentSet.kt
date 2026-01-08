package com.gaugustini.mhfudatabase.domain.model

/**
 * Represents a user-created equipment set.
 *
 * @property id The unique identifier of the user equipment set.
 * @property name The name of the equipment set.
 * @property defense The total base defense of the equipment set.
 * @property fire The total fire resistance of the equipment set.
 * @property water The total water resistance of the equipment set.
 * @property thunder The total thunder resistance of the equipment set.
 * @property ice The total ice resistance of the equipment set.
 * @property dragon The total dragon resistance of the equipment set.
 * @property weapon The weapon in the equipment set.
 * @property armors The list of armor pieces in the equipment set.
 * @property decorations The list of decorations in the equipment set.
 * @property activeSkills The list of active skills in the equipment set.
 * @property skills The list of skills in the equipment set.
 * @property recipe The list of items required to craft the entire equipment set.
 */
data class UserEquipmentSet(
    val id: Int,
    val name: String,
    val defense: Int,
    val fire: Int,
    val water: Int,
    val thunder: Int,
    val ice: Int,
    val dragon: Int,
    val weapon: Weapon?,
    val armors: List<Armor>,
    val decorations: List<Decoration>,
    val activeSkills: List<Skill>,
    val skills: List<SkillTree>,
    val recipe: List<Item>,
)
