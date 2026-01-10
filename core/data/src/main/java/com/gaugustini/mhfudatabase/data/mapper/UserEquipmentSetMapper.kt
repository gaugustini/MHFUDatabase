package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.entity.userset.UserEquipmentSetArmorEntity
import com.gaugustini.mhfudatabase.data.database.entity.userset.UserEquipmentSetDecorationEntity
import com.gaugustini.mhfudatabase.data.database.entity.userset.UserEquipmentSetEntity
import com.gaugustini.mhfudatabase.domain.model.Armor
import com.gaugustini.mhfudatabase.domain.model.Decoration
import com.gaugustini.mhfudatabase.domain.model.UserEquipmentSet
import com.gaugustini.mhfudatabase.domain.model.Weapon

/**
 * Mapper for User Equipment Set entities.
 */
object UserEquipmentSetMapper {

    fun toModel(
        equipmentSet: UserEquipmentSetEntity,
        weapon: Weapon?,
        armors: List<Armor>,
        decorations: List<Decoration>,
    ): UserEquipmentSet {
        val defense = armors.sumOf { it.defense }
        val fire = armors.sumOf { it.fire }
        val water = armors.sumOf { it.water }
        val thunder = armors.sumOf { it.thunder }
        val ice = armors.sumOf { it.ice }
        val dragon = armors.sumOf { it.dragon }

        return UserEquipmentSet(
            id = equipmentSet.id,
            name = equipmentSet.name,
            defense = defense,
            fire = fire,
            water = water,
            thunder = thunder,
            ice = ice,
            dragon = dragon,
            weapon = weapon,
            armors = armors,
            decorations = decorations,
            activeSkills = emptyList(),
            skills = emptyList(),
            recipe = emptyList(),
        )
    }

    fun toEntity(
        equipmentSet: UserEquipmentSet,
    ): UserEquipmentSetEntity {
        return UserEquipmentSetEntity(
            id = equipmentSet.id,
            name = equipmentSet.name,
            weaponId = equipmentSet.weapon?.id,
        )
    }

    fun toArmorEntities(
        equipmentSet: UserEquipmentSet,
    ): List<UserEquipmentSetArmorEntity> {
        return equipmentSet.armors.map { armor ->
            UserEquipmentSetArmorEntity(
                userSetId = equipmentSet.id,
                armorId = armor.id,
            )
        }
    }

    fun toDecorationEntities(
        equipmentSet: UserEquipmentSet,
    ): List<UserEquipmentSetDecorationEntity> {
        return equipmentSet.decorations.map { decoration ->
            UserEquipmentSetDecorationEntity(
                userSetId = equipmentSet.id,
                decorationId = decoration.id,
                equipmentType = decoration.equipmentType!!.name,
                quantity = decoration.quantity!!,
            )
        }
    }

}
