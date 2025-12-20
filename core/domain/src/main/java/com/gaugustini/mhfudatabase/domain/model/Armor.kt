package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.EquipmentType
import com.gaugustini.mhfudatabase.domain.enums.Gender
import com.gaugustini.mhfudatabase.domain.enums.HunterType

/**
 * Represents a piece of armor in the game.
 *
 * @property id The unique identifier of the armor piece.
 * @property armorSetId The ID of the armor set this piece belongs to.
 * @property name The name of the armor piece.
 * @property description The description of the armor piece in the game.
 * @property type The type of equipment (head, chest, arms, waist, legs).
 * @property hunterType The type of hunter that can wear this armor (Blademaster, Gunner, or both).
 * @property gender The gender that can wear this armor (Male, Female, or both).
 * @property rarity The rarity of the armor piece.
 * @property price The price to craft the armor piece.
 * @property numberOfSlots The number of decoration slots available on the armor piece.
 * @property defense The base defense value of the armor piece.
 * @property maxDefense The maximum defense value of the armor piece when fully upgraded.
 * @property fire The fire resistance of the armor piece.
 * @property water The water resistance of the armor piece.
 * @property thunder The thunder resistance of the armor piece.
 * @property ice The ice resistance of the armor piece.
 * @property dragon The dragon resistance of the armor piece.
 * @property skills The list of skills provided by the armor piece.
 * @property recipe The list of items required to craft the armor piece.
 */
data class Armor(
    val id: Int,
    val armorSetId: Int,
    val name: String,
    val description: String,
    val type: EquipmentType,
    val hunterType: HunterType,
    val gender: Gender,
    val rarity: Int,
    val price: Int,
    val numberOfSlots: Int,
    val defense: Int,
    val maxDefense: Int,
    val fire: Int,
    val water: Int,
    val thunder: Int,
    val ice: Int,
    val dragon: Int,
    val skills: List<SkillTree>,
    val recipe: List<Item>,
)
