import '../../../core/domain/enums.dart';
import '../../../core/domain/shared.dart';

class const Armor({
  required final int id,
  required final int armorSetId,
  required final String name,
  required final String description,
  required final EquipmentType type,
  required final HunterType hunterType,
  required final Gender gender,
  required final int rarity,
  required final int price,
  required final int numberOfSlots,
  required final int defense,
  required final int maxDefense,
  required final int fire,
  required final int water,
  required final int thunder,
  required final int ice,
  required final int dragon,
  final List<SkillPoint>? skills,
  final List<List<ItemQuantity>>? recipes,
});

class const ArmorSet({
  required final int id,
  required final String name,
  required final Rank rank,
  required final HunterType hunterType,
  required final Gender gender,
  required final int rarity,
  required final int defense,
  required final int maxDefense,
  required final int fire,
  required final int water,
  required final int thunder,
  required final int ice,
  required final int dragon,
  final List<Armor>? armors,
  final List<SkillPoint>? skills,
  final List<ItemQuantity>? recipe,
});
