import '../../armor/domain/armor.dart';
import '../../decoration/domain/decoration.dart';
import '../../item/domain/item.dart';
import '../../location/domain/location.dart';
import '../../monster/domain/monster.dart';
import '../../quest/domain/quest.dart';
import '../../skill/domain/skill.dart';
import '../../weapon/domain/weapon.dart';

class const SearchResults({
  final List<Armor> armors = const [],
  final List<Decoration> decorations = const [],
  final List<Item> items = const [],
  final List<Location> locations = const [],
  final List<Monster> monsters = const [],
  final List<Quest> quests = const [],
  final List<SkillTree> skillTrees = const [],
  final List<Skill> skills = const [],
  final List<Weapon> weapons = const [],
}) {
  bool get isEmpty =>
      armors.isEmpty &&
      decorations.isEmpty &&
      items.isEmpty &&
      locations.isEmpty &&
      monsters.isEmpty &&
      quests.isEmpty &&
      skillTrees.isEmpty &&
      skills.isEmpty &&
      weapons.isEmpty;
}
