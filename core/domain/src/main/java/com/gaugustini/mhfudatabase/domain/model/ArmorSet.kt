package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.HunterType
import com.gaugustini.mhfudatabase.domain.enums.Rank

/**
 * Represents a full set of armor in the game.
 *
 * @property id The unique identifier of the armor set.
 * @property name The name of the armor set.
 * @property rank The rank of the armor set (Low Rank, High Rank, or G Rank).
 * @property hunterType The type of hunter that can wear this armor set (Blademaster, Gunner, or both).
 * @property rarity The rarity of the armor set.
 * @property defense The total base defense of the armor set.
 * @property maxDefense The total maximum defense of the armor set when fully upgraded.
 * @property fire The total fire resistance of the armor set.
 * @property water The total water resistance of the armor set.
 * @property thunder The total thunder resistance of the armor set.
 * @property ice The total ice resistance of the armor set.
 * @property dragon The total dragon resistance of the armor set.
 * @property armors The list of individual armor pieces in this set.
 * @property skills The list of skills provided by the full armor set.
 * @property recipe The list of items required to craft the entire armor set.
 */
// TODO: Add Gender
data class ArmorSet(
    val id: Int,
    val name: String,
    val rank: Rank,
    val hunterType: HunterType,
//    val gender: Gender,
    val rarity: Int,
    val defense: Int,
    val maxDefense: Int,
    val fire: Int,
    val water: Int,
    val thunder: Int,
    val ice: Int,
    val dragon: Int,
    val armors: List<Armor>?,
    val skills: List<SkillPoint>?,
    val recipe: List<ItemQuantity>?,
)
