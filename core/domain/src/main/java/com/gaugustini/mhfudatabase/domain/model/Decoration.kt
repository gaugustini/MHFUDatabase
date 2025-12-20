package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.EquipmentType
import com.gaugustini.mhfudatabase.domain.enums.ItemIconColor

/**
 * Represents a decoration that can be slotted into equipment to provide skill points.
 *
 * @property id The unique identifier of the decoration.
 * @property name The name of the decoration.
 * @property description The description of the decoration in the game.
 * @property rarity The rarity of the decoration.
 * @property buyPrice The price to buy the decoration.
 * @property sellPrice The price to sell the decoration.
 * @property requiredSlots The number of slots required to equip this decoration.
 * @property color The color of the decoration's icon.
 * @property skills The list of skill points provided by this decoration.
 * @property recipeA The first possible recipe to craft this decoration.
 * @property recipeB The second possible recipe to craft this decoration.
 * @property equipmentType The type of equipment this decoration is used (for user equipment).
 * @property quantity The quantity of the decoration (for user equipment).
 */
data class Decoration(
    val id: Int,
    val name: String,
    val description: String,
    val rarity: Int,
    val buyPrice: Int,
    val sellPrice: Int,
    val requiredSlots: Int,
    val color: ItemIconColor,
    val skills: List<SkillTree>,
    val recipeA: List<Item>,
    val recipeB: List<Item>,
    val equipmentType: EquipmentType?,
    val quantity: Int?,
)
