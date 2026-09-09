import '../../../core/domain/enums.dart';
import '../../item/domain/item.dart';
import '../../quest/domain/quest.dart';

class const Monster({
  required final int id,
  required final String name,
  required final String ecology,
  required final String description,
  required final MonsterType type,
  final int? sizeSmallestMin,
  final int? sizeSmallestMax,
  final int? sizeLargestMin,
  final int? sizeLargestMax,
  final List<MonsterDamageStats>? damageStats,
  final List<MonsterAilmentStats>? ailmentStats,
  final MonsterItemEffectiveness? itemEffectiveness,
  final Map<Rank, List<MonsterReward>>? rewards,
  final List<Quest>? quests,
});

class const MonsterDamageStats({
  required final int monsterId,
  required final String name,
  required final int cut,
  required final int impact,
  required final int shot,
  required final int fire,
  required final int water,
  required final int thunder,
  required final int ice,
  required final int dragon,
});

class const MonsterAilmentStats({
  required final int monsterId,
  required final MonsterAilment type,
  required final int initial,
  required final int increase,
  required final int max,
  required final int duration,
  required final int damage,
});

class const MonsterItemEffectiveness({
  required final int monsterId,
  required final bool flashBomb,
  final int? timeFlashBomb,
  required final bool sonicBombNormal,
  required final bool sonicBombEnraged,
  required final bool shockTrap,
  final int? timeShockTrap,
  required final bool pitfallTrapNormal,
  required final bool pitfallTrapEnraged,
  final int? timePitfallTrapUnseen,
  final int? timePitfallTrapNormal,
  final int? timePitfallTrapEnraged,
  required final bool canUseMeat,
  required final bool canUseDungBomb,
});

class const MonsterReward({
  required final Item item,
  required final String condition,
  required final Rank rank,
  required final int quantity,
  final int? percentage,
});
