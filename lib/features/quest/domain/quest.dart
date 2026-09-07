import '../../../core/domain/enums.dart';
import '../../item/domain/item.dart';
import '../../location/domain/location.dart';
import '../../monster/domain/monster.dart';

class const Quest({
  required final int id,
  required final String name,
  required final String goal,
  required final String client,
  required final String description,
  required final QuestGroup group,
  required final QuestType questType,
  required final QuestGoal goalType,
  required final HubType hubType,
  required final int stars,
  required final int reward,
  required final int fee,
  required final int timeLimit,
  final Location? location,
  final LocationDaytime? daytime,
  final List<Monster>? monsters,
  final List<QuestReward>? rewards,
  final List<QuestSupply>? supplies,
});

class const QuestReward({
  required final Item item,
  required final String condition,
  required final int quantity,
  required final int percentage,
});

class const QuestSupply({
  required final Item item,
  required final int quantity,
  required final int boxOrder,
});
