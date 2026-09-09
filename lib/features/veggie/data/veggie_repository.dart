import 'package:drift/drift.dart';

import '../../../core/database/app_database.dart' show AppDatabase;
import '../../../core/database/sql_args.dart';
import '../../../core/domain/enums.dart';
import '../../item/domain/item.dart';
import '../../location/domain/location.dart';
import '../domain/veggie.dart';

class VeggieRepository {
  VeggieRepository([AppDatabase? database])
    : _db = database ?? AppDatabase.instance;

  final AppDatabase _db;

  Future<VeggieLocation> getVeggieLocation(
    int veggieId,
    String language,
  ) async {
    final args = SqlArgs();
    final row = await _db.customSelect(
      '''
          SELECT
            veggie.id AS veggie_id, veggie.location_id AS veggie_location_id,
            veggie.location_area AS veggie_location_area,
            location_text.*
          FROM veggie
          JOIN location_text
            ON veggie.location_id = location_text.location_id
            AND location_text.language = ${args.text(language)}
          WHERE veggie.id = ${args.integer(veggieId)}
          ''',
      variables: args.variables,
    ).getSingle();

    final trades = await _getVeggieTradeList(veggieId, language);

    return _veggieLocationFromRow(row, trades: trades);
  }

  Future<List<VeggieLocation>> getVeggieLocationList(String language) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT
            veggie.id AS veggie_id, veggie.location_id AS veggie_location_id,
            veggie.location_area AS veggie_location_area,
            location_text.*
          FROM veggie
          JOIN location_text
            ON veggie.location_id = location_text.location_id
            AND location_text.language = ${args.text(language)}
          ORDER BY location_text.name
          ''',
      variables: args.variables,
    ).get();

    return rows.map(_veggieLocationFromRow).toList();
  }

  Future<List<VeggieTrade>> _getVeggieTradeList(
    int veggieId,
    String language,
  ) async {
    final args = SqlArgs();
    final tradeRows = await _db.customSelect(
      '''
          SELECT * FROM veggie_trade WHERE veggie_id = ${args.integer(veggieId)}
          ''',
      variables: args.variables,
    ).get();

    if (tradeRows.isEmpty) return const [];

    final itemIds = <int>{
      for (final row in tradeRows) ...[
        row.read<int>('item_traded_id'),
        row.read<int>('item_common_id'),
        row.read<int>('item_rare_id'),
      ],
    }.toList();

    final itemArgs = SqlArgs();
    final itemRows = await _db.customSelect(
      '''
          SELECT item.*, item_text.*
          FROM item
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${itemArgs.text(language)}
          WHERE item.id IN ${itemArgs.integers(itemIds)}
          ''',
      variables: itemArgs.variables,
    ).get();

    final itemsById = {
      for (final row in itemRows) row.data['id'] as int: _itemFromRow(row),
    };

    final trades = <VeggieTrade>[];
    for (final row in tradeRows) {
      final traded = itemsById[row.read<int>('item_traded_id')];
      final common = itemsById[row.read<int>('item_common_id')];
      final rare = itemsById[row.read<int>('item_rare_id')];
      if (traded != null && common != null && rare != null) {
        trades.add(
          VeggieTrade(itemTraded: traded, itemCommon: common, itemRare: rare),
        );
      }
    }
    trades.sort((a, b) => a.itemTraded.name.compareTo(b.itemTraded.name));
    return trades;
  }

  VeggieLocation _veggieLocationFromRow(
    QueryRow row, {
    List<VeggieTrade>? trades,
  }) {
    return VeggieLocation(
      id: row.read<int>('veggie_id'),
      location: Location(
        id: row.read<int>('veggie_location_id'),
        name: row.data['name'] as String,
      ),
      locationArea: row.read<int>('veggie_location_area'),
      trades: trades,
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
