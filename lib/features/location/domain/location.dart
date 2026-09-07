import '../../../core/domain/enums.dart';
import '../../item/domain/item.dart';
import '../../quest/domain/quest.dart';

class const Location({
  required final int id,
  required final String name,
  final Map<Rank, List<GatheringPoint>>? gatheringPoints,
  final List<Quest>? quests,
});

class const GatheringPoint({
  required final Rank rank,
  required final int area,
  required final int node,
  required final GatherType type,
  required final int min,
  required final int max,
  required final Item item,
  required final int percentage,
});
