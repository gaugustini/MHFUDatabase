package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.relation.WeaponWithText
import com.gaugustini.mhfudatabase.domain.enums.WeaponAmmo
import com.gaugustini.mhfudatabase.domain.enums.WeaponElement
import com.gaugustini.mhfudatabase.domain.enums.WeaponRecoil
import com.gaugustini.mhfudatabase.domain.enums.WeaponReloadSpeed
import com.gaugustini.mhfudatabase.domain.enums.WeaponShelling
import com.gaugustini.mhfudatabase.domain.enums.WeaponType
import com.gaugustini.mhfudatabase.domain.model.Item
import com.gaugustini.mhfudatabase.domain.model.Weapon

/**
 * Mapper for Weapon entities.
 */
object WeaponMapper {

    fun toModel(
        weapon: WeaponWithText,
        ammoBow: Map<WeaponAmmo, Boolean>?,
        ammoBowgun: Map<WeaponAmmo, String>?,
        charges: Map<WeaponAmmo, Int>?,
        rapidFire: String?,
        recipeCreate: List<Item>,
        recipeUpgrade: List<Item>,
    ): Weapon {
        return Weapon(
            id = weapon.weapon.id,
            name = weapon.weaponText.name,
            description = weapon.weaponText.description,
            type = WeaponType.fromString(weapon.weapon.weaponType),
            rarity = weapon.weapon.rarity,
            affinity = weapon.weapon.affinity,
            defense = weapon.weapon.defense,
            numberOfSlots = weapon.weapon.numberOfSlots,
            attack = weapon.weapon.attack,
            maxAttack = weapon.weapon.maxAttack,
            priceCreate = weapon.weapon.priceCreate,
            priceUpgrade = weapon.weapon.priceUpgrade,
            element1 = weapon.weapon.element1?.let { WeaponElement.fromString(it) },
            element1Value = weapon.weapon.element1Value,
            element2 = weapon.weapon.element2?.let { WeaponElement.fromString(it) },
            element2Value = weapon.weapon.element2Value,
            sharpness = weapon.weapon.sharpness,
            sharpnessPlus = weapon.weapon.sharpnessPlus,
            shellingType = weapon.weapon.shellingType?.let { WeaponShelling.fromString(it) },
            shellingLevel = weapon.weapon.shellingLevel,
            songNotes = weapon.weapon.songNotes,
            reloadSpeed = weapon.weapon.reloadSpeed?.let { WeaponReloadSpeed.fromString(it) },
            recoil = weapon.weapon.recoil?.let { WeaponRecoil.fromString(it) },
            ammoBow = ammoBow,
            ammoBowgun = ammoBowgun,
            charges = charges,
            rapidFire = rapidFire,
            recipeCreate = recipeCreate,
            recipeUpgrade = recipeUpgrade,
        )
    }

}
