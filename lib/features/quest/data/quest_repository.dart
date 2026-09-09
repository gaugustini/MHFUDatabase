import 'package:drift/drift.dart';

import '../../../core/database/app_database.dart' show AppDatabase;
import '../../../core/database/localized_collation.dart';
import '../../../core/database/sql_args.dart';
import '../../../core/domain/enums.dart';
import '../../item/domain/item.dart';
import '../../location/domain/location.dart';
import '../../monster/domain/monster.dart';
import '../domain/quest_filter.dart';
import '../domain/quest.dart';

class QuestRepository {
  QuestRepository([AppDatabase? database])
    : _db = database ?? AppDatabase.instance;

  final AppDatabase _db;

  Future<Quest> getQuest(int questId, String language) async {
    final args = SqlArgs();
    final rowFuture = _db.customSelect(
      '''
          SELECT quest.*, quest_text.*
          FROM quest
          JOIN quest_text
            ON quest.id = quest_text.quest_id
            AND quest_text.language = ${args.text(language)}
          WHERE quest.id = ${args.integer(questId)}
          ''',
      variables: args.variables,
    ).getSingle();

    final (row, location, monsters, rewards, supplies) = await (
      rowFuture,
      _getLocation(questId, language),
      _getMonsters(questId, language),
      _getRewards(questId, language),
      _getSupplies(questId, language),
    ).wait;

    return _questFromRow(
      row,
      location: location,
      monsters: monsters.isEmpty ? null : monsters,
      rewards: rewards.isEmpty ? null : rewards,
      supplies: supplies.isEmpty ? null : supplies,
    );
  }

  Future<List<Quest>> getQuestList(
    String language, {
    QuestFilter filter = const QuestFilter(),
  }) async {
    final args = SqlArgs();
    final stars = filter.stars ?? const [];
    final types = (filter.type ?? const []).map((type) => type.dbValue).toList();
    final locations = filter.locations ?? const [];
    final name = filter.name != null ? normalizeForSearch(filter.name!) : null;

    final rows = await _db.customSelect(
      '''
          SELECT quest.*, quest_text.*
          FROM quest
          JOIN quest_text
            ON quest.id = quest_text.quest_id
            AND quest_text.language = ${args.text(language)}
          WHERE
            (${args.text(name)} IS NULL OR quest_text.name_normalized LIKE '%' || ${args.text(name)} || '%')
            AND (${args.text(filter.hub?.dbValue)} IS NULL OR quest.hub_type = ${args.text(filter.hub?.dbValue)})
            AND (${args.flag(stars.isEmpty)} OR quest.stars IN ${args.integers(stars)}
            )
            AND (${args.flag(types.isEmpty)} OR quest.quest_type IN ${args.texts(types)}
            )
            AND (${args.text(filter.goal?.dbValue)} IS NULL OR quest.goal_type = ${args.text(filter.goal?.dbValue)})
            AND (${args.flag(locations.isEmpty)} OR quest.location_id IN ${args.integers(locations)}
            )
          ''',
      variables: args.variables,
    ).get();

    return rows.map(_questFromRow).toList();
  }

  Future<Location> _getLocation(int questId, String language) async {
    final args = SqlArgs();
    final row = await _db.customSelect(
      '''
          SELECT location.*, location_text.*
          FROM quest
          JOIN location ON quest.location_id = location.id
          JOIN location_text
            ON location.id = location_text.location_id
            AND location_text.language = ${args.text(language)}
          WHERE quest.id = ${args.integer(questId)}
          ''',
      variables: args.variables,
    ).getSingle();

    return Location(
      id: row.data['id'] as int,
      name: row.data['name'] as String,
    );
  }

  Future<List<Monster>> _getMonsters(int questId, String language) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT monster.*, monster_text.*
          FROM quest_monster
          JOIN monster ON quest_monster.monster_id = monster.id
          JOIN monster_text
            ON monster.id = monster_text.monster_id
            AND monster_text.language = ${args.text(language)}
          WHERE quest_monster.quest_id = ${args.integer(questId)}
          ORDER BY monster_text.name ASC
          ''',
      variables: args.variables,
    ).get();

    return rows
        .map(
          (row) => Monster(
            id: row.data['id'] as int,
            name: row.data['name'] as String,
            ecology: row.data['ecology'] as String,
            description: row.data['description'] as String,
            type: MonsterType.fromDb(row.data['monster_type'] as String),
            sizeSmallestMin: (row.data['golden_smallest_min'] as num?)?.toInt(),
            sizeSmallestMax: (row.data['golden_smallest_max'] as num?)?.toInt(),
            sizeLargestMin: (row.data['golden_largest_min'] as num?)?.toInt(),
            sizeLargestMax: (row.data['golden_largest_max'] as num?)?.toInt(),
          ),
        )
        .toList();
  }

  Future<List<QuestReward>> _getRewards(int questId, String language) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT
            quest_reward.quantity AS reward_quantity,
            quest_reward.percentage AS reward_percentage,
            reward_condition_text.reward_condition_id AS condition_id,
            reward_condition_text.name AS condition_name,
            item.*, item_text.*
          FROM quest_reward
          JOIN reward_condition_text
            ON quest_reward.reward_condition_id = reward_condition_text.reward_condition_id
            AND reward_condition_text.language = ${args.text(language)}
          JOIN item ON quest_reward.item_id = item.id
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE quest_reward.quest_id = ${args.integer(questId)}
          ORDER BY condition_id ASC, reward_percentage DESC
          ''',
      variables: args.variables,
    ).get();

    return rows
        .map(
          (row) => QuestReward(
            item: _itemFromRow(row),
            condition: row.data['condition_name'] as String,
            quantity: row.data['reward_quantity'] as int,
            percentage: row.data['reward_percentage'] as int,
          ),
        )
        .toList();
  }

  Future<List<QuestSupply>> _getSupplies(int questId, String language) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT
            quest_supply.quantity AS supply_quantity,
            quest_supply.box_order AS supply_box_order,
            item.*, item_text.*
          FROM quest_supply
          JOIN item ON quest_supply.item_id = item.id
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE quest_supply.quest_id = ${args.integer(questId)}
          ORDER BY supply_box_order ASC
          ''',
      variables: args.variables,
    ).get();

    return rows
        .map(
          (row) => QuestSupply(
            item: _itemFromRow(row),
            quantity: row.data['supply_quantity'] as int,
            boxOrder: row.data['supply_box_order'] as int,
          ),
        )
        .toList();
  }

  Quest _questFromRow(
    QueryRow row, {
    Location? location,
    List<Monster>? monsters,
    List<QuestReward>? rewards,
    List<QuestSupply>? supplies,
  }) {
    return Quest(
      id: row.data['id'] as int,
      name: row.data['name'] as String,
      goal: row.data['goal'] as String,
      client: row.data['client'] as String,
      description: row.data['description'] as String,
      group: QuestGroup.fromDb(row.data['group'] as String),
      questType: QuestType.fromDb(row.data['quest_type'] as String),
      goalType: QuestGoal.fromDb(row.data['goal_type'] as String),
      hubType: HubType.fromDb(row.data['hub_type'] as String),
      stars: row.data['stars'] as int,
      reward: row.data['reward'] as int,
      fee: row.data['fee'] as int,
      timeLimit: row.data['time_limit'] as int,
      location: location,
      daytime: (row.data['daytime'] as String?) != null
          ? LocationDaytime.fromDb(row.data['daytime'] as String)
          : null,
      monsters: monsters,
      rewards: rewards,
      supplies: supplies,
    );
  }

  Item _itemFromRow(QueryRow row) {
    return Item(
      id: row.data['id'] as int,
      name: row.data['name'] as String,
      description: row.data['description'] as String,
      rarity: row.data['rarity'] as int,
      buyPrice: row.data['buy_price'] as int?,
      sellPrice: row.data['sell_price'] as int,
      carryMax: row.data['carry_max'] as int,
      iconType: ItemIconType.fromDb(row.data['icon_type'] as String),
      iconColor: ItemIconColor.fromDb(row.data['icon_color'] as String),
    );
  }
}
