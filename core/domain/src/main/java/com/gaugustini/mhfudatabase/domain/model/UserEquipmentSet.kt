package com.gaugustini.mhfudatabase.domain.model

import com.gaugustini.mhfudatabase.domain.enums.EquipmentType

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
 * @property skills The list of skills points in the equipment set.
 * @property recipe The list of items required to craft the entire equipment set.
 */
data class UserEquipmentSet(
    val id: Int = 0,
    val name: String = "",
    val defense: Int = 0,
    val fire: Int = 0,
    val water: Int = 0,
    val thunder: Int = 0,
    val ice: Int = 0,
    val dragon: Int = 0,
    val weapon: Weapon? = null,
    val armors: List<Armor>? = null,
    val decorations: List<EquipmentDecoration>? = null,
    val activeSkills: List<Skill>? = null,
    val skills: List<SkillPoint>? = null,
    val recipe: List<ItemQuantity>? = null,
) {

    fun changeWeapon(newWeapon: Weapon): UserEquipmentSet {
        return this.copy(
            weapon = newWeapon,
            decorations = decorations?.filter { it.equipmentType != EquipmentType.WEAPON }
        )
    }

    fun changeArmor(newArmor: Armor): UserEquipmentSet {
        val updatedArmors =
            armors?.filter { it.type != newArmor.type }?.plus(newArmor) ?: listOf(newArmor)

        return this.copy(
            armors = updatedArmors,
            decorations = decorations?.filter { it.equipmentType != newArmor.type }
        )
    }

    fun removeArmor(equipmentType: EquipmentType): UserEquipmentSet {
        return this.copy(
            armors = armors?.filter { it.type != equipmentType },
            decorations = decorations?.filter { it.equipmentType != equipmentType }
        )
    }

    fun addDecoration(
        newDecoration: EquipmentDecoration,
    ): UserEquipmentSet {
        val currentDecorations = decorations ?: emptyList()

        val existing = currentDecorations.find {
            it.decoration.id == newDecoration.decoration.id && it.equipmentType == newDecoration.equipmentType
        }

        val updatedDecorations = if (existing == null) {
            currentDecorations + newDecoration
        } else {
            currentDecorations.map {
                if (it == existing) it.copy(quantity = it.quantity + 1) else it
            }
        }

        return this.copy(decorations = updatedDecorations)
    }

    fun removeDecoration(
        decorationId: Int,
        equipmentType: EquipmentType,
    ): UserEquipmentSet {
        val currentDecorations = decorations ?: return this

        val existing = currentDecorations.find {
            it.decoration.id == decorationId && it.equipmentType == equipmentType
        } ?: return this

        val updatedDecorations = if (existing.quantity > 1) {
            currentDecorations.map {
                if (it == existing) it.copy(quantity = it.quantity - 1) else it
            }
        } else {
            currentDecorations - existing
        }

        return this.copy(decorations = updatedDecorations)
    }

}

/**
 * Represents a decoration inserted in the equipment set.
 */
data class EquipmentDecoration(
    val equipmentType: EquipmentType,
    val decoration: Decoration,
    val quantity: Int,
)
