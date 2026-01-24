package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponAmmoBowEntity
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponAmmoBowgunEntity
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.WeaponWithText
import com.gaugustini.mhfudatabase.domain.enums.WeaponAmmo
import com.gaugustini.mhfudatabase.domain.enums.WeaponElement
import com.gaugustini.mhfudatabase.domain.enums.WeaponRecoil
import com.gaugustini.mhfudatabase.domain.enums.WeaponReloadSpeed
import com.gaugustini.mhfudatabase.domain.enums.WeaponShelling
import com.gaugustini.mhfudatabase.domain.enums.WeaponType
import com.gaugustini.mhfudatabase.domain.model.AmmoBow
import com.gaugustini.mhfudatabase.domain.model.AmmoBowgun
import com.gaugustini.mhfudatabase.domain.model.Weapon

/**
 * Mapper for Weapon entities.
 */
object WeaponMapper {

    fun toModel(
        weapon: WeaponWithText,
        ammoBow: WeaponAmmoBowEntity? = null,
        ammoBowgun: WeaponAmmoBowgunEntity? = null,
        recipeCreate: List<EquipmentItemQuantity>? = null,
        recipeUpgrade: List<EquipmentItemQuantity>? = null,
        paths: List<List<Weapon>>? = null,
        finals: List<Weapon>? = null,
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
            ammoBow = ammoBow?.let { toAmmoBow(it) },
            ammoBowgun = ammoBowgun?.let { toAmmoBowgun(it) },
            recipeCreate = recipeCreate?.map { ItemMapper.toItemQuantity(it) },
            recipeUpgrade = recipeUpgrade?.map { ItemMapper.toItemQuantity(it) },
            paths = paths,
            finals = finals,
        )
    }

    fun toAmmoBow(
        ammoBow: WeaponAmmoBowEntity,
    ): AmmoBow {
        return AmmoBow(
            charge1Type = WeaponAmmo.fromString(ammoBow.charge1Type),
            charge1Level = ammoBow.charge1Level,
            charge2Type = WeaponAmmo.fromString(ammoBow.charge2Type),
            charge2Level = ammoBow.charge2Level,
            charge3Type = WeaponAmmo.fromString(ammoBow.charge3Type),
            charge3Level = ammoBow.charge3Level,
            charge4Type = ammoBow.charge4Type?.let { WeaponAmmo.fromString(it) },
            charge4Level = ammoBow.charge4Level,
            power = ammoBow.power,
            close = ammoBow.close,
            paint = ammoBow.paint,
            poison = ammoBow.poison,
            paralysis = ammoBow.paralysis,
            sleep = ammoBow.sleep,
        )
    }

    fun toAmmoBowgun(
        ammoBowgun: WeaponAmmoBowgunEntity,
    ): AmmoBowgun {
        return AmmoBowgun(
            normal = ammoBowgun.normal,
            pierce = ammoBowgun.pierce,
            pellet = ammoBowgun.pellet,
            crag = ammoBowgun.crag,
            clust = ammoBowgun.clust,
            recovery = ammoBowgun.recovery,
            poison = ammoBowgun.poison,
            paralysis = ammoBowgun.paralysis,
            sleep = ammoBowgun.sleep,
            flame = ammoBowgun.flame,
            water = ammoBowgun.water,
            thunder = ammoBowgun.thunder,
            freeze = ammoBowgun.freeze,
            dragon = ammoBowgun.dragon,
            tranq = ammoBowgun.tranq,
            paint = ammoBowgun.paint,
            demon = ammoBowgun.demon,
            armor = ammoBowgun.armor,
            rapidFire = ammoBowgun.rapidFire,
        )
    }

}
