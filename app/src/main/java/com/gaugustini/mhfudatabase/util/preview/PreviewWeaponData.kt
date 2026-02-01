package com.gaugustini.mhfudatabase.util.preview

import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponParentEntity
import com.gaugustini.mhfudatabase.data.repository.WeaponGraph
import com.gaugustini.mhfudatabase.domain.enums.WeaponAmmo
import com.gaugustini.mhfudatabase.domain.enums.WeaponElement
import com.gaugustini.mhfudatabase.domain.enums.WeaponRecoil
import com.gaugustini.mhfudatabase.domain.enums.WeaponReloadSpeed
import com.gaugustini.mhfudatabase.domain.enums.WeaponShelling
import com.gaugustini.mhfudatabase.domain.enums.WeaponType
import com.gaugustini.mhfudatabase.domain.model.AmmoBow
import com.gaugustini.mhfudatabase.domain.model.AmmoBowgun
import com.gaugustini.mhfudatabase.domain.model.Weapon

object PreviewWeaponData {

    // Weapon

    val weapon = Weapon(
        id = 1,
        name = "Weapon",
        description = "Weapon Description",
        type = WeaponType.GREAT_SWORD,
        rarity = 1,
        affinity = 1,
        defense = 1,
        numberOfSlots = 1,
        attack = 500,
        maxAttack = 600,
        priceCreate = 1000,
        priceUpgrade = 1000,
        element1 = WeaponElement.FIRE,
        element1Value = 100,
        element2 = WeaponElement.WATER,
        element2Value = 100,
        sharpness = "16-5-4",
        sharpnessPlus = "8-3-11-10-6-5-2",
        shellingType = WeaponShelling.NORMAL,
        shellingLevel = 3,
        songNotes = "PYR",
        reloadSpeed = WeaponReloadSpeed.NORMAL,
        recoil = WeaponRecoil.MODERATE,
        ammoBow = null,
        ammoBowgun = null,
        recipeCreate = null,
        recipeUpgrade = null,
        paths = null,
        finals = null,
    )

    val weaponGS = Weapon(
        id = 1,
        name = "Weapon",
        description = "Weapon Description",
        type = WeaponType.GREAT_SWORD,
        rarity = 1,
        affinity = 1,
        defense = 1,
        numberOfSlots = 1,
        attack = 500,
        maxAttack = null,
        priceCreate = 1000,
        priceUpgrade = 1000,
        element1 = WeaponElement.FIRE,
        element1Value = 100,
        element2 = WeaponElement.WATER,
        element2Value = 100,
        sharpness = "16-5-4",
        sharpnessPlus = "8-3-11-10-6-5-2",
        shellingType = null,
        shellingLevel = null,
        songNotes = null,
        reloadSpeed = null,
        recoil = null,
        ammoBow = null,
        ammoBowgun = null,
        recipeCreate = null,
        recipeUpgrade = null,
        paths = null,
        finals = null,
    )

    val weaponHH = Weapon(
        id = 1,
        name = "Weapon",
        description = "Weapon Description",
        type = WeaponType.HUNTING_HORN,
        rarity = 1,
        affinity = 1,
        defense = 1,
        numberOfSlots = 1,
        attack = 500,
        maxAttack = null,
        priceCreate = null,
        priceUpgrade = null,
        element1 = WeaponElement.FIRE,
        element1Value = 100,
        element2 = WeaponElement.WATER,
        element2Value = 100,
        sharpness = "16-5-4",
        sharpnessPlus = "8-3-11-10-6-5-2",
        shellingType = null,
        shellingLevel = null,
        songNotes = "PYR",
        reloadSpeed = null,
        recoil = null,
        ammoBow = null,
        ammoBowgun = null,
        recipeCreate = null,
        recipeUpgrade = null,
        paths = null,
        finals = null,
    )

    val weaponGL = Weapon(
        id = 1,
        name = "Weapon",
        description = "Weapon Description",
        type = WeaponType.GUNLANCE,
        rarity = 1,
        affinity = 1,
        defense = 1,
        numberOfSlots = 1,
        attack = 500,
        maxAttack = null,
        priceCreate = null,
        priceUpgrade = null,
        element1 = WeaponElement.FIRE,
        element1Value = 100,
        element2 = WeaponElement.WATER,
        element2Value = 100,
        sharpness = "16-5-4",
        sharpnessPlus = "8-3-11-10-6-5-2",
        shellingType = WeaponShelling.NORMAL,
        shellingLevel = 1,
        songNotes = null,
        reloadSpeed = null,
        recoil = null,
        ammoBow = null,
        ammoBowgun = null,
        recipeCreate = null,
        recipeUpgrade = null,
        paths = null,
        finals = null,
    )

    val weaponHBG = Weapon(
        id = 1,
        name = "Weapon",
        description = "Weapon Description",
        type = WeaponType.HEAVY_BOWGUN,
        rarity = 1,
        affinity = 1,
        defense = 1,
        numberOfSlots = 1,
        attack = 500,
        maxAttack = 600,
        priceCreate = null,
        priceUpgrade = null,
        element1 = null,
        element1Value = null,
        element2 = null,
        element2Value = null,
        sharpness = null,
        sharpnessPlus = null,
        shellingType = null,
        shellingLevel = null,
        songNotes = null,
        reloadSpeed = WeaponReloadSpeed.SLOW,
        recoil = WeaponRecoil.WEAK,
        ammoBow = null,
        ammoBowgun = null,
        recipeCreate = null,
        recipeUpgrade = null,
        paths = null,
        finals = null,
    )

    val weaponBow = Weapon(
        id = 1,
        name = "Weapon",
        description = "Weapon Description",
        type = WeaponType.BOW,
        rarity = 1,
        affinity = 1,
        defense = 1,
        numberOfSlots = 1,
        attack = 500,
        maxAttack = null,
        priceCreate = null,
        priceUpgrade = null,
        element1 = null,
        element1Value = null,
        element2 = null,
        element2Value = null,
        sharpness = null,
        sharpnessPlus = null,
        shellingType = null,
        shellingLevel = null,
        songNotes = null,
        reloadSpeed = null,
        recoil = null,
        ammoBow = null,
        ammoBowgun = null,
        recipeCreate = null,
        recipeUpgrade = null,
        paths = null,
        finals = null,
    )

    val weaponList = listOf(
        weaponGS.copy(id = 1, name = "Weapon 1"),
        weaponHH.copy(id = 2, name = "Weapon 2"),
        weaponGL.copy(id = 3, name = "Weapon 3"),
        weaponHBG.copy(id = 4, name = "Weapon 4"),
        weaponBow.copy(id = 5, name = "Weapon 5"),
    )

    // Weapon Ammo Bow

    val ammoBow = AmmoBow(
        charge1Type = WeaponAmmo.NORMAL_RAPID,
        charge1Level = 1,
        charge2Type = WeaponAmmo.PIERCE,
        charge2Level = 2,
        charge3Type = WeaponAmmo.PELLET_SCATTER,
        charge3Level = 3,
        charge4Type = WeaponAmmo.NORMAL_RAPID,
        charge4Level = 4,
        power = false,
        close = true,
        paint = true,
        poison = true,
        paralysis = false,
        sleep = true,
    )

    // Weapon Ammo Bowgun

    val ammoBowgun = AmmoBowgun(
        normal = "6-6-6",
        pierce = "6-6-6",
        pellet = "6-6-6",
        crag = "1-1-1",
        clust = "1-1-1",
        recovery = "1-1",
        poison = "1-1",
        paralysis = "1-1",
        sleep = "1-1",
        flame = "3",
        water = "3",
        thunder = "3",
        freeze = "3",
        dragon = "3",
        tranq = "3",
        paint = "3",
        demon = "3",
        armor = "3",
        rapidFire = "Pierce S Lv1",
    )

    // Weapon Tree

    private val weaponTreeData = listOf(
        weaponGS,
        weaponGS.copy(id = 2),
        weaponGS.copy(id = 3),
        weaponGS.copy(id = 4),
        weaponGS.copy(id = 5),
        weaponGS.copy(id = 6),
    )

    val weaponNodeList = WeaponGraph(
        weapons = weaponTreeData,
        relations = listOf(
            WeaponParentEntity(2, 1),
            WeaponParentEntity(3, 2),
            WeaponParentEntity(4, 3),
            WeaponParentEntity(5, 3),
            WeaponParentEntity(6, 2),
        )
    ).buildGraphByType(WeaponType.GREAT_SWORD)

}
