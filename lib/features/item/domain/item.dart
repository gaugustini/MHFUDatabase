import '../../../core/domain/enums.dart';
import '../../../core/domain/shared.dart';
import '../../armor/domain/armor.dart';
import '../../decoration/domain/decoration.dart';
import '../../itemcombination/domain/item_combination.dart';
import '../../location/domain/location.dart';
import '../../monster/domain/monster.dart';
import '../../quest/domain/quest.dart';
import '../../veggie/domain/veggie.dart';
import '../../weapon/domain/weapon.dart';

class const Item({
  required final int id,
  required final String name,
  required final String description,
  required final int rarity,
  final int? buyPrice,
  required final int sellPrice,
  required final int carryMax,
  required final ItemIconType iconType,
  required final ItemIconColor iconColor,
  final ItemSources? sources,
  final ItemUsages? usages,
});

sealed class ItemSource {
  const ItemSource();
}

class const GatheringSource({
  required final Location location,
  required final Rank rank,
  required final int area,
  required final int node,
  required final GatherType type,
  required final int min,
  required final int max,
  required final int percentage,
}) extends ItemSource {}

class const MonsterSource({
  required final Monster monster,
  required final String condition,
  required final Rank rank,
  required final int quantity,
  required final int percentage,
}) extends ItemSource {}

class const QuestSource({
  required final Quest quest,
  required final String condition,
  required final int quantity,
  required final int percentage,
}) extends ItemSource {}

class const VeggieSource({
  required final Location location,
  required final int area,
  required final VeggieTrade trade,
}) extends ItemSource {}

class const ItemSources({
  required final List<ItemCombination> combinations,
  required final List<GatheringSource> locations,
  required final List<MonsterSource> monsterRewards,
  required final List<QuestSource> questRewards,
  required final List<VeggieSource> veggieTrades,
}) {
  bool get isEmpty =>
      combinations.isEmpty &&
      locations.isEmpty &&
      monsterRewards.isEmpty &&
      questRewards.isEmpty &&
      veggieTrades.isEmpty;
}

class const ItemUsages({
  required final List<ItemCombination> combinations,
  required final List<VeggieUsage> veggieTrades,
  required final List<Usage<Armor>> armors,
  required final List<Usage<Decoration>> decorations,
  required final List<Usage<Weapon>> weapons,
}) {
  bool get isEmpty =>
      combinations.isEmpty &&
      veggieTrades.isEmpty &&
      armors.isEmpty &&
      decorations.isEmpty &&
      weapons.isEmpty;
}
