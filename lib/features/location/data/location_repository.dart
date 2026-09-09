import 'package:drift/drift.dart';

import '../../../core/database/app_database.dart' show AppDatabase;
import '../../../core/database/sql_args.dart';
import '../../../core/domain/enums.dart';
import '../../item/domain/item.dart';
import '../../quest/domain/quest.dart';
import '../domain/location.dart';

class LocationRepository {
  LocationRepository([AppDatabase? database])
    : _db = database ?? AppDatabase.instance;

  final AppDatabase _db;

  Future<Location> getLocation(int locationId, String language) async {
    final args = SqlArgs();
    final rowFuture = _db.customSelect(
      '''
          SELECT location.*, location_text.*
          FROM location
          JOIN location_text
            ON location.id = location_text.location_id
            AND location_text.language = ${args.text(language)}
          WHERE location.id = ${args.integer(locationId)}
          ''',
      variables: args.variables,
    ).getSingle();

    final (row, gatheringPoints, quests) = await (
      rowFuture,
      _getGatheringPoints(locationId, language),
      _getQuestsByLocationId(locationId, language),
    ).wait;

    return _locationFromRow(
      row,
      gatheringPoints: _groupByRank(gatheringPoints),
      quests: quests,
    );
  }

  Future<List<Location>> getLocationList(String language) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT location.*, location_text.*
          FROM location
          JOIN location_text
            ON location.id = location_text.location_id
            AND location_text.language = ${args.text(language)}
          ORDER BY location_text.name ASC
          ''',
      variables: args.variables,
    ).get();

    return rows.map(_locationFromRow).toList();
  }

  Future<List<GatheringPoint>> _getGatheringPoints(
    int locationId,
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT
            location_item.rank AS li_rank, location_item.area AS li_area,
            location_item.node AS li_node, location_item.type AS li_type,
            location_item.min AS li_min, location_item.max AS li_max,
            location_item.percentage AS li_percentage,
            item.*, item_text.*
          FROM location_item
          JOIN item ON location_item.item_id = item.id
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE location_item.location_id = ${args.integer(locationId)}
          ORDER BY ${rankOrderCase('location_item.rank')}, location_item.area ASC,
            location_item.node ASC, location_item.percentage DESC
          ''',
      variables: args.variables,
    ).get();

    return rows
        .map(
          (row) => GatheringPoint(
            rank: Rank.fromDb(row.read<String>('li_rank')),
            area: row.read<int>('li_area'),
            node: row.read<int>('li_node'),
            type: GatherType.fromDb(row.read<String>('li_type')),
            min: row.read<int>('li_min'),
            max: row.read<int>('li_max'),
            item: _itemFromRow(row),
            percentage: row.read<int>('li_percentage'),
          ),
        )
        .toList();
  }

  Future<List<Quest>> _getQuestsByLocationId(
    int locationId,
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT quest.*, quest_text.*
          FROM quest
          JOIN quest_text
            ON quest.id = quest_text.quest_id
            AND quest_text.language = ${args.text(language)}
          WHERE quest.location_id = ${args.integer(locationId)}
          ''',
      variables: args.variables,
    ).get();

    return rows.map(_minimalQuestFromRow).toList();
  }

  Map<Rank, List<GatheringPoint>>? _groupByRank(
    List<GatheringPoint> points,
  ) {
    if (points.isEmpty) return null;
    final grouped = <Rank, List<GatheringPoint>>{};
    for (final point in points) {
      (grouped[point.rank] ??= []).add(point);
    }
    return grouped;
  }

  Location _locationFromRow(
    QueryRow row, {
    Map<Rank, List<GatheringPoint>>? gatheringPoints,
    List<Quest>? quests,
  }) {
    return Location(
      id: row.data['id'] as int,
      name: row.data['name'] as String,
      gatheringPoints: gatheringPoints,
      quests: quests,
    );
  }

  Quest _minimalQuestFromRow(QueryRow row) {
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
