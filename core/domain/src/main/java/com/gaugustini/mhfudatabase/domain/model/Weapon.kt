package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.WeaponAmmo
import com.gaugustini.mhfudatabase.domain.enums.WeaponElement
import com.gaugustini.mhfudatabase.domain.enums.WeaponRecoil
import com.gaugustini.mhfudatabase.domain.enums.WeaponReloadSpeed
import com.gaugustini.mhfudatabase.domain.enums.WeaponShelling
import com.gaugustini.mhfudatabase.domain.enums.WeaponType

/**
 * Represents a weapon in the game.
 *
 * @property id The unique identifier of the weapon.
 * @property name The name of the weapon.
 * @property description The description of the weapon in the game.
 * @property type The type of the weapon (e.g., Great Sword, Long Sword, etc.).
 * @property rarity The rarity of the weapon.
 * @property affinity The affinity of the weapon.
 * @property defense The defense bonus provided by the weapon.
 * @property numberOfSlots The number of decoration slots available on the weapon.
 * @property attack The attack power of the weapon.
 * @property maxAttack The maximum attack power of the weapon (for Bowguns).
 * @property priceCreate The price to create the weapon.
 * @property priceUpgrade The price to create the weapon after upgrading it.
 * @property element1 The first elemental attribute of the weapon.
 * @property element1Value The value of the first elemental attribute.
 * @property element2 The second elemental attribute of the weapon.
 * @property element2Value The value of the second elemental attribute.
 * @property sharpness The sharpness levels of the weapon.
 * @property sharpnessPlus The sharpness levels with the Sharpness+1 skill.
 * @property shellingType The type of shelling for Gunlances.
 * @property shellingLevel The level of shelling for Gunlances.
 * @property songNotes The notes for Hunting Horns.
 * @property reloadSpeed The reload speed for Bowguns.
 * @property recoil The recoil for Bowguns.
 * @property ammoBow The available ammo coatings and charges for Bows.
 * @property ammoBowgun The available ammo for Bowguns.
 * @property recipeCreate The list of items required to create the weapon.
 * @property recipeUpgrade The list of items required to create the weapon after upgrading it.
 * @property paths The list of paths to the root of the weapon tree.
 * @property finals The list of weapons that are the finals of the weapon tree.
 */
// TODO: Add better representation for sharpness, songNotes, ammo, rapid fire
data class Weapon(
    val id: Int,
    val name: String,
    val description: String,
    val type: WeaponType,
    val rarity: Int,
    val affinity: Int,
    val defense: Int,
    val numberOfSlots: Int,
    val attack: Int,
    val maxAttack: Int?,
    val priceCreate: Int?,
    val priceUpgrade: Int?,
    val element1: WeaponElement?,
    val element1Value: Int?,
    val element2: WeaponElement?,
    val element2Value: Int?,
    val sharpness: String?,
    val sharpnessPlus: String?,
    val shellingType: WeaponShelling?,
    val shellingLevel: Int?,
    val songNotes: String?,
    val reloadSpeed: WeaponReloadSpeed?,
    val recoil: WeaponRecoil?,
    val ammoBow: AmmoBow?,
    val ammoBowgun: AmmoBowgun?,
    val recipeCreate: List<ItemQuantity>?,
    val recipeUpgrade: List<ItemQuantity>?,
    val paths: List<List<Weapon>>?,
    val finals: List<Weapon>?,
)

/**
 * Represents the ammo for Bows.
 */
data class AmmoBow(
    val charge1Type: WeaponAmmo,
    val charge1Level: Int,
    val charge2Type: WeaponAmmo,
    val charge2Level: Int,
    val charge3Type: WeaponAmmo,
    val charge3Level: Int,
    val charge4Type: WeaponAmmo?,
    val charge4Level: Int?,
    val power: Boolean,
    val close: Boolean,
    val paint: Boolean,
    val poison: Boolean,
    val paralysis: Boolean,
    val sleep: Boolean,
)

/**
 * Represents the ammo for Bowguns.
 */
data class AmmoBowgun(
    val normal: String,
    val pierce: String,
    val pellet: String,
    val crag: String,
    val clust: String,
    val recovery: String,
    val poison: String,
    val paralysis: String,
    val sleep: String,
    val flame: String,
    val water: String,
    val thunder: String,
    val freeze: String,
    val dragon: String,
    val tranq: String,
    val paint: String,
    val demon: String,
    val armor: String,
    val rapidFire: String?,
)

/**
 * Represents a node in the weapon tree.
 */
data class WeaponNode(
    val weapon: Weapon,
    val parents: MutableList<WeaponNode> = mutableListOf(),
    val children: MutableList<WeaponNode> = mutableListOf(),
)
