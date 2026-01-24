package com.gaugustini.mhfudatabase.data.mapper

import com.gaugustini.mhfudatabase.data.database.entity.userset.UserEquipmentSetArmorEntity
import com.gaugustini.mhfudatabase.data.database.entity.userset.UserEquipmentSetDecorationEntity
import com.gaugustini.mhfudatabase.data.database.entity.userset.UserEquipmentSetEntity
import com.gaugustini.mhfudatabase.data.database.relation.ArmorWithText
import com.gaugustini.mhfudatabase.data.database.relation.DecorationWithText
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentItemQuantity
import com.gaugustini.mhfudatabase.data.database.relation.EquipmentSkillTreePoint
import com.gaugustini.mhfudatabase.data.database.relation.SkillWithText
import com.gaugustini.mhfudatabase.data.database.relation.UserSetDecorationWithDecoration
import com.gaugustini.mhfudatabase.data.database.relation.WeaponWithText
import com.gaugustini.mhfudatabase.domain.enums.EquipmentType
import com.gaugustini.mhfudatabase.domain.model.EquipmentDecoration
import com.gaugustini.mhfudatabase.domain.model.UserEquipmentSet

/**
 * Mapper for User Equipment Set entities.
 */
object UserEquipmentSetMapper {

    fun toModel(
        equipmentSet: UserEquipmentSetEntity,
        weapon: WeaponWithText? = null,
        armors: List<ArmorWithText>? = null,
        decorations: List<UserSetDecorationWithDecoration>? = null,
        activeSkills: List<SkillWithText>? = null,
        skills: List<EquipmentSkillTreePoint>? = null,
        recipe: List<EquipmentItemQuantity>? = null,
    ): UserEquipmentSet {
        return UserEquipmentSet(
            id = equipmentSet.id,
            name = equipmentSet.name,
            defense = armors?.sumOf { it.armor.defense } ?: 0,
            fire = armors?.sumOf { it.armor.fireResistance } ?: 0,
            water = armors?.sumOf { it.armor.waterResistance } ?: 0,
            thunder = armors?.sumOf { it.armor.thunderResistance } ?: 0,
            ice = armors?.sumOf { it.armor.iceResistance } ?: 0,
            dragon = armors?.sumOf { it.armor.dragonResistance } ?: 0,
            weapon = weapon?.let { WeaponMapper.toModel(it) },
            armors = armors?.map { ArmorMapper.toModel(it) },
            decorations = decorations?.map { toEquipmentDecoration(it) },
            activeSkills = activeSkills?.map { SkillMapper.toModel(it) },
            skills = skills?.map { SkillTreeMapper.toSkillPoint(it) },
            recipe = recipe?.map { ItemMapper.toItemQuantity(it) },
        )
    }

    fun toEquipmentDecoration(
        decoration: UserSetDecorationWithDecoration,
    ): EquipmentDecoration {
        return EquipmentDecoration(
            equipmentType = EquipmentType.fromString(decoration.userSetDecoration.equipmentType),
            decoration = DecorationMapper.toModel(
                DecorationWithText(decoration.decoration, decoration.item, decoration.itemText)
            ),
            quantity = decoration.userSetDecoration.quantity,
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
        return equipmentSet.armors?.map { armor ->
            UserEquipmentSetArmorEntity(
                userSetId = equipmentSet.id,
                armorId = armor.id,
            )
        } ?: emptyList()
    }

    fun toDecorationEntities(
        equipmentSet: UserEquipmentSet,
    ): List<UserEquipmentSetDecorationEntity> {
        return equipmentSet.decorations?.map { decoration ->
            UserEquipmentSetDecorationEntity(
                userSetId = equipmentSet.id,
                decorationId = decoration.decoration.id,
                equipmentType = decoration.equipmentType.name,
                quantity = decoration.quantity,
            )
        } ?: emptyList()
    }

}
