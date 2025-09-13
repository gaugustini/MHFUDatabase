package com.gaugustini.mhfudatabase.data.model

import com.gaugustini.mhfudatabase.data.enums.ElementType
import com.gaugustini.mhfudatabase.data.enums.WeaponRecoil
import com.gaugustini.mhfudatabase.data.enums.WeaponReloadSpeed
import com.gaugustini.mhfudatabase.data.enums.WeaponShelling
import com.gaugustini.mhfudatabase.data.enums.WeaponType

data class Weapon(
    val id: Int,
    val name: String,
    val description: String,
    val type: WeaponType,
    val rarity: Int,
    val affinity: Int,
    val defense: Int,
    val numSlots: Int,
    val attack: Int,
    val maxAttack: Int?,
    val priceCreate: Int?,
    val priceUpgrade: Int?,
    val element1: ElementType?,
    val element1Value: Int?,
    val element2: ElementType?,
    val element2Value: Int?,
    val sharpness: String?,
    val sharpnessPlus: String?,
    val shellingType: WeaponShelling?,
    val shellingLevel: Int?,
    val songNotes: String?,
    val reloadSpeed: WeaponReloadSpeed?,
    val recoil: WeaponRecoil?,
)

data class WeaponDetails(
    val weapon: Weapon,
    val ammoBow: AmmoBow?,
    val ammoBowgun: AmmoBowgun?,
    val recipeCreate: List<ItemQuantity>,
    val recipeUpgrade: List<ItemQuantity>,
    val paths: List<List<Weapon>>,
    val finals: List<Weapon>,
)

data class WeaponRelation(
    val weaponId: Int,
    val parentWeaponId: Int,
)

// TODO: Make immutable parents and children
data class WeaponNode(
    val weapon: Weapon,
    val parents: MutableList<WeaponNode> = mutableListOf(),
    val children: MutableList<WeaponNode> = mutableListOf(),
)

//TODO: Make enum for charge type
data class AmmoBow(
    val charge1Type: String,
    val charge1Level: Int,
    val charge2Type: String,
    val charge2Level: Int,
    val charge3Type: String,
    val charge3Level: Int,
    val charge4Type: String?,
    val charge4Level: Int?,
    val power: Boolean,
    val close: Boolean,
    val paint: Boolean,
    val poison: Boolean,
    val paralysis: Boolean,
    val sleep: Boolean,
)

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
