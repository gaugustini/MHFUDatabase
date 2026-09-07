import '../../../core/domain/enums.dart';
import '../../skill/domain/skill.dart';

class const ArmorFilter({
  final String? name,
  final EquipmentType? type,
  final List<int>? numberOfSlots,
  final List<int>? rarity,
  final Gender? gender,
  final HunterType? hunterType,
  final List<SkillTree>? skills,
});

class const ArmorSetFilter({
  final String? name,
  final List<int>? rarity,
  final Rank? rank,
  final HunterType? hunterType,
  final Gender? gender,
  final List<SkillTree>? skills,
});
