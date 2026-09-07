import '../../../core/domain/enums.dart';
import '../../../core/domain/shared.dart';
import '../../armor/domain/armor.dart';
import '../../decoration/domain/decoration.dart';
import '../../skill/domain/skill.dart';
import '../../weapon/domain/weapon.dart';

class const UserEquipmentSet({
  final int id = 0,
  final String name = '',
  final HunterType hunterType = HunterType.both,
  final Gender gender = Gender.both,
  final int defense = 0,
  final int fire = 0,
  final int water = 0,
  final int thunder = 0,
  final int ice = 0,
  final int dragon = 0,
  final Weapon? weapon,
  final List<Armor>? armors,
  final List<EquipmentDecoration>? decorations,
  final List<Skill>? activeSkills,
  final List<SkillPoint>? skills,
  final List<ItemQuantity>? recipe,
}) {
  UserEquipmentSet changeWeapon(Weapon newWeapon) => UserEquipmentSet(
    id: id,
    name: name,
    hunterType: hunterType,
    gender: gender,
    defense: defense,
    fire: fire,
    water: water,
    thunder: thunder,
    ice: ice,
    dragon: dragon,
    weapon: newWeapon,
    armors: armors,
    decorations: decorations
        ?.where(
          (decoration) => decoration.equipmentType != EquipmentType.weapon,
        )
        .toList(),
    activeSkills: activeSkills,
    skills: skills,
    recipe: recipe,
  );

  UserEquipmentSet changeArmor(Armor newArmor) {
    final updatedArmors = [
      ...?armors?.where((armor) => armor.type != newArmor.type),
      newArmor,
    ];
    return UserEquipmentSet(
      id: id,
      name: name,
      hunterType: hunterType,
      gender: gender,
      defense: defense,
      fire: fire,
      water: water,
      thunder: thunder,
      ice: ice,
      dragon: dragon,
      weapon: weapon,
      armors: updatedArmors,
      decorations: decorations
          ?.where((decoration) => decoration.equipmentType != newArmor.type)
          .toList(),
      activeSkills: activeSkills,
      skills: skills,
      recipe: recipe,
    );
  }

  UserEquipmentSet removeArmor(EquipmentType equipmentType) => UserEquipmentSet(
    id: id,
    name: name,
    hunterType: hunterType,
    gender: gender,
    defense: defense,
    fire: fire,
    water: water,
    thunder: thunder,
    ice: ice,
    dragon: dragon,
    weapon: weapon,
    armors: armors?.where((armor) => armor.type != equipmentType).toList(),
    decorations: decorations
        ?.where((decoration) => decoration.equipmentType != equipmentType)
        .toList(),
    activeSkills: activeSkills,
    skills: skills,
    recipe: recipe,
  );

  UserEquipmentSet addDecoration(EquipmentDecoration newDecoration) {
    final current = decorations ?? const <EquipmentDecoration>[];
    final existingIndex = current.indexWhere(
      (decoration) =>
          decoration.decoration.id == newDecoration.decoration.id &&
          decoration.equipmentType == newDecoration.equipmentType,
    );

    final updated = existingIndex == -1
        ? [...current, newDecoration]
        : [
            for (var i = 0; i < current.length; i++)
              if (i == existingIndex)
                EquipmentDecoration(
                  equipmentType: current[i].equipmentType,
                  decoration: current[i].decoration,
                  quantity: current[i].quantity + 1,
                )
              else
                current[i],
          ];

    return UserEquipmentSet(
      id: id,
      name: name,
      hunterType: hunterType,
      gender: gender,
      defense: defense,
      fire: fire,
      water: water,
      thunder: thunder,
      ice: ice,
      dragon: dragon,
      weapon: weapon,
      armors: armors,
      decorations: updated,
      activeSkills: activeSkills,
      skills: skills,
      recipe: recipe,
    );
  }

  UserEquipmentSet removeDecoration(
    int decorationId,
    EquipmentType equipmentType,
  ) {
    final current = decorations;
    if (current == null) return this;

    final existingIndex = current.indexWhere(
      (decoration) =>
          decoration.decoration.id == decorationId &&
          decoration.equipmentType == equipmentType,
    );
    if (existingIndex == -1) return this;

    final existing = current[existingIndex];
    final updated = existing.quantity > 1
        ? [
            for (var i = 0; i < current.length; i++)
              if (i == existingIndex)
                EquipmentDecoration(
                  equipmentType: existing.equipmentType,
                  decoration: existing.decoration,
                  quantity: existing.quantity - 1,
                )
              else
                current[i],
          ]
        : [
            for (var i = 0; i < current.length; i++)
              if (i != existingIndex) current[i],
          ];

    return UserEquipmentSet(
      id: id,
      name: name,
      hunterType: hunterType,
      gender: gender,
      defense: defense,
      fire: fire,
      water: water,
      thunder: thunder,
      ice: ice,
      dragon: dragon,
      weapon: weapon,
      armors: armors,
      decorations: updated,
      activeSkills: activeSkills,
      skills: skills,
      recipe: recipe,
    );
  }
}

class const EquipmentDecoration({
  required final EquipmentType equipmentType,
  required final Decoration decoration,
  required final int quantity,
});
