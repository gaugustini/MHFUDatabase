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
 * @property ammoBow The available ammo coatings for Bows.
 * @property ammoBowgun The available ammo types for Bowguns.
 * @property charges The charge levels for Bows.
 * @property rapidFire The rapid fire ammo of Bowguns.
 * @property recipeCreate The list of items required to create the weapon.
 * @property recipeUpgrade The list of items required to create the weapon after upgrading it.
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
    val ammoBow: Map<WeaponAmmo, Boolean>?,
    val ammoBowgun: Map<WeaponAmmo, String>?,
    val charges: Map<WeaponAmmo, Int>?,
    val rapidFire: String?,
    val recipeCreate: List<Item>?,
    val recipeUpgrade: List<Item>?,
)
