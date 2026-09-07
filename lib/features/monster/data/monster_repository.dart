import 'package:drift/drift.dart';

import '../../../core/database/app_database.dart' show AppDatabase;
import '../../../core/database/localized_collation.dart';
import '../../../core/database/sql_args.dart';
import '../../../core/domain/enums.dart';
import '../../item/domain/item.dart';
import '../../quest/domain/quest.dart';
import '../domain/monster_filter.dart';
import '../domain/monster.dart';

class MonsterRepository {
  MonsterRepository([AppDatabase? database])
    : _db = database ?? AppDatabase.instance;

  final AppDatabase _db;

  Future<Monster> getMonster(int monsterId, String language) async {
    final args = SqlArgs();
    final rowFuture = _db.customSelect(
      '''
          SELECT monster.*, monster_text.*
          FROM monster
          JOIN monster_text
            ON monster.id = monster_text.monster_id
            AND monster_text.language = ${args.text(language)}
          WHERE monster.id = ${args.integer(monsterId)}
          ''',
      variables: args.variables,
    ).getSingle();

    final (
      row,
      damageStats,
      ailmentStats,
      itemEffectiveness,
      rewards,
      quests,
    ) = await (
      rowFuture,
      _getDamageStats(monsterId, language),
      _getAilmentStats(monsterId),
      _getItemEffectiveness(monsterId),
      _getRewards(monsterId, language),
      _getQuests(monsterId, language),
    ).wait;

    final rewardsByRank = <Rank, List<MonsterReward>>{};
    for (final reward in rewards) {
      (rewardsByRank[reward.rank] ??= []).add(reward);
    }

    return _monsterFromRow(
      row,
      damageStats: damageStats,
      ailmentStats: ailmentStats,
      itemEffectiveness: itemEffectiveness,
      rewards: rewardsByRank.isEmpty ? null : rewardsByRank,
      quests: quests.isEmpty ? null : quests,
    );
  }

  Future<List<Monster>> getMonsterList(
    String language, {
    MonsterFilter filter = const MonsterFilter(),
  }) async {
    final args = SqlArgs();
    final name = filter.name != null ? normalizeForSearch(filter.name!) : null;

    final rows = await _db.customSelect(
      '''
          SELECT monster.*, monster_text.*
          FROM monster
          JOIN monster_text
            ON monster.id = monster_text.monster_id
            AND monster_text.language = ${args.text(language)}
          WHERE
            (${args.text(name)} IS NULL OR monster_text.name_normalized LIKE '%' || ${args.text(name)} || '%')
            AND (${args.text(filter.ecology)} IS NULL OR monster_text.ecology = ${args.text(filter.ecology)})
            AND (${args.text(filter.type?.dbValue)} IS NULL OR monster.monster_type = ${args.text(filter.type?.dbValue)})
          ORDER BY monster_text.name ASC
          ''',
      variables: args.variables,
    ).get();

    return rows.map(_monsterFromRow).toList();
  }

  Future<List<MonsterDamageStats>> _getDamageStats(
    int monsterId,
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT monster_hitzone.*, hitzone_text.name AS hitzone_name
          FROM monster_hitzone
          JOIN hitzone_text
            ON monster_hitzone.hitzone_id = hitzone_text.hitzone_id
            AND hitzone_text.language = ${args.text(language)}
          WHERE monster_hitzone.monster_id = ${args.integer(monsterId)}
          ''',
      variables: args.variables,
    ).get();

    return rows
        .map(
          (row) => MonsterDamageStats(
            monsterId: row.data['monster_id'] as int,
            name: row.data['hitzone_name'] as String,
            cut: row.data['cut'] as int,
            impact: row.data['impact'] as int,
            shot: row.data['shot'] as int,
            fire: row.data['fire'] as int,
            water: row.data['water'] as int,
            thunder: row.data['thunder'] as int,
            ice: row.data['ice'] as int,
            dragon: row.data['dragon'] as int,
          ),
        )
        .toList();
  }

  Future<List<MonsterAilmentStats>> _getAilmentStats(int monsterId) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT * FROM monster_status WHERE monster_id = ${args.integer(monsterId)}
          ''',
      variables: args.variables,
    ).get();

    return rows
        .map(
          (row) => MonsterAilmentStats(
            monsterId: row.data['monster_id'] as int,
            type: MonsterAilment.fromDb(row.data['status'] as String),
            initial: row.data['initial'] as int,
            increase: row.data['increase'] as int,
            max: row.data['max'] as int,
            duration: row.data['duration'] as int,
            damage: row.data['damage'] as int,
          ),
        )
        .toList();
  }

  Future<MonsterItemEffectiveness?> _getItemEffectiveness(int monsterId) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT * FROM monster_item WHERE monster_id = ${args.integer(monsterId)}
          ''',
      variables: args.variables,
    ).get();
    if (rows.isEmpty) return null;

    final row = rows.first;
    return MonsterItemEffectiveness(
      monsterId: row.data['monster_id'] as int,
      flashBomb: _asBool(row.data['flash']),
      timeFlashBomb: row.data['time_flash'] as int?,
      sonicBombNormal: _asBool(row.data['sonic_normal']),
      sonicBombEnraged: _asBool(row.data['sonic_enraged']),
      shockTrap: _asBool(row.data['shock']),
      timeShockTrap: row.data['time_shock'] as int?,
      pitfallTrapNormal: _asBool(row.data['pitfall_normal']),
      pitfallTrapEnraged: _asBool(row.data['pitfall_enraged']),
      timePitfallTrapUnseen: row.data['time_pitfall_unseen'] as int?,
      timePitfallTrapNormal: row.data['time_pitfall_normal'] as int?,
      timePitfallTrapEnraged: row.data['time_pitfall_enraged'] as int?,
      canUseMeat: _asBool(row.data['meat']),
      canUseDungBomb: _asBool(row.data['dung']),
    );
  }

  Future<List<MonsterReward>> _getRewards(
    int monsterId,
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT
            monster_reward.rank AS reward_rank,
            monster_reward.quantity AS reward_quantity,
            monster_reward.percentage AS reward_percentage,
            reward_condition_text.reward_condition_id AS condition_id,
            reward_condition_text.name AS condition_name,
            item.*, item_text.*
          FROM monster_reward
          JOIN reward_condition_text
            ON monster_reward.reward_condition_id = reward_condition_text.reward_condition_id
            AND reward_condition_text.language = ${args.text(language)}
          JOIN item ON monster_reward.item_id = item.id
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE monster_reward.monster_id = ${args.integer(monsterId)}
          ORDER BY ${rankOrderCase('monster_reward.rank')}, condition_id ASC,
            reward_percentage DESC
          ''',
      variables: args.variables,
    ).get();

    return rows
        .map(
          (row) => MonsterReward(
            item: _itemFromRow(row),
            condition: row.data['condition_name'] as String,
            rank: Rank.fromDb(row.data['reward_rank'] as String),
            quantity: row.data['reward_quantity'] as int,
            percentage: row.data['reward_percentage'] as int?,
          ),
        )
        .toList();
  }

  Future<List<Quest>> _getQuests(int monsterId, String language) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT quest.*, quest_text.*
          FROM quest_monster
          JOIN quest ON quest_monster.quest_id = quest.id
          JOIN quest_text
            ON quest.id = quest_text.quest_id
            AND quest_text.language = ${args.text(language)}
          WHERE quest_monster.monster_id = ${args.integer(monsterId)}
          ORDER BY quest_text.name ASC
          ''',
      variables: args.variables,
    ).get();

    return rows.map(_questFromRow).toList();
  }

  bool _asBool(Object? value) => (value as int) != 0;

  Monster _monsterFromRow(
    QueryRow row, {
    List<MonsterDamageStats>? damageStats,
    List<MonsterAilmentStats>? ailmentStats,
    MonsterItemEffectiveness? itemEffectiveness,
    Map<Rank, List<MonsterReward>>? rewards,
    List<Quest>? quests,
  }) {
    return Monster(
      id: row.data['id'] as int,
      name: row.data['name'] as String,
      ecology: row.data['ecology'] as String,
      description: row.data['description'] as String,
      type: MonsterType.fromDb(row.data['monster_type'] as String),
      sizeSmallestMin: (row.data['golden_smallest_min'] as num?)?.toInt(),
      sizeSmallestMax: (row.data['golden_smallest_max'] as num?)?.toInt(),
      sizeLargestMin: (row.data['golden_largest_min'] as num?)?.toInt(),
      sizeLargestMax: (row.data['golden_largest_max'] as num?)?.toInt(),
      damageStats: damageStats,
      ailmentStats: ailmentStats,
      itemEffectiveness: itemEffectiveness,
      rewards: rewards,
      quests: quests,
    );
  }

  Quest _questFromRow(QueryRow row) {
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
      daytime: (row.data['daytime'] as String?) != null
          ? LocationDaytime.fromDb(row.data['daytime'] as String)
          : null,
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
