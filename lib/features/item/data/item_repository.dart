import 'package:drift/drift.dart';

import '../../../core/database/app_database.dart' show AppDatabase;
import '../../../core/database/localized_collation.dart';
import '../../../core/database/sql_args.dart';
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
import '../domain/item_filter.dart';
import '../domain/item.dart';

class ItemRepository {
  ItemRepository([AppDatabase? database])
    : _db = database ?? AppDatabase.instance;

  final AppDatabase _db;

  Future<Item> getItem(int itemId, String language) async {
    final args = SqlArgs();
    final rowFuture = _db.customSelect(
      '''
          SELECT item.*, item_text.*
          FROM item
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE item.id = ${args.integer(itemId)}
          ''',
      variables: args.variables,
    ).getSingle();

    final (row, sources, usages) = await (
      rowFuture,
      _getItemSources(itemId, language),
      _getItemUsages(itemId, language),
    ).wait;

    return _itemFromRow(row, sources: sources, usages: usages);
  }

  Future<List<Item>> getItemList(
    String language, {
    ItemFilter filter = const ItemFilter(),
  }) async {
    final args = SqlArgs();
    final rarity = filter.rarity ?? const [];
    final icons = (filter.icons ?? const [])
        .map((icon) => icon.dbValue)
        .toList();
    final iconColors = (filter.iconColors ?? const [])
        .map((color) => color.dbValue)
        .toList();
    final name = filter.name != null ? normalizeForSearch(filter.name!) : null;

    final rows = await _db.customSelect(
      '''
          SELECT item.*, item_text.*
          FROM item
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE
            item.id != 0
            AND (${args.text(name)} IS NULL OR (item_text.name_normalized LIKE '%' || ${args.text(name)} || '%' OR item_text.full_name_normalized LIKE '%' || ${args.text(name)} || '%'))
            AND (${args.flag(rarity.isEmpty)} OR item.rarity IN ${args.integers(rarity)}
            )
            AND (${args.flag(icons.isEmpty)} OR item.icon_type IN ${args.texts(icons)}
            )
            AND (${args.flag(iconColors.isEmpty)} OR item.icon_color IN ${args.texts(iconColors)}
            )
          ''',
      variables: args.variables,
    ).get();

    return rows.map((row) => _itemFromRow(row)).toList();
  }

  Future<List<Item>> _getItemListByIds(
    List<int> itemIds,
    String language,
  ) async {
    if (itemIds.isEmpty) return [];
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT item.*, item_text.*
          FROM item
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE item.id IN ${args.integers(itemIds)}
          ''',
      variables: args.variables,
    ).get();
    return rows.map((row) => _itemFromRow(row)).toList();
  }

  Future<List<ItemCombination>> getItemCombinationList(
    String language, {
    ItemCombinationType? type,
  }) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT * FROM item_combination
          WHERE (${args.text(type?.dbValue)} IS NULL OR combination_type = ${args.text(type?.dbValue)})
          ''',
      variables: args.variables,
    ).get();
    return _mapCombinationRows(rows, language);
  }

  Future<ItemSources> _getItemSources(int itemId, String language) async {
    final combinationArgs = SqlArgs();
    final combinationRowsFuture = _db.customSelect(
      '''
          SELECT * FROM item_combination
          WHERE item_created_id = ${combinationArgs.integer(itemId)}
          ''',
      variables: combinationArgs.variables,
    ).get();

    final veggieArgs = SqlArgs();
    final veggieRowsFuture = _db.customSelect(
      '''
          SELECT veggie_trade.*, location_text.*, veggie.location_area AS area
          FROM veggie_trade
          JOIN veggie ON veggie_trade.veggie_id = veggie.id
          JOIN location_text
            ON location_text.location_id = veggie.location_id
            AND location_text.language = ${veggieArgs.text(language)}
          WHERE veggie_trade.item_common_id = ${veggieArgs.integer(itemId)}
            OR veggie_trade.item_rare_id = ${veggieArgs.integer(itemId)}
          ''',
      variables: veggieArgs.variables,
    ).get();

    final locationArgs = SqlArgs();
    final locationRowsFuture = _db.customSelect(
      '''
          SELECT li.location_id AS li_location_id, li.rank AS li_rank, li.area AS li_area,
            li.node AS li_node, li.type AS li_type, li.min AS li_min, li.max AS li_max,
            li.percentage AS li_percentage,
            location.*, location_text.*
          FROM location_item li
          JOIN location ON li.location_id = location.id
          JOIN location_text
            ON location.id = location_text.location_id
            AND location_text.language = ${locationArgs.text(language)}
          WHERE li.item_id = ${locationArgs.integer(itemId)}
          ORDER BY location_text.name ASC, ${rankOrderCase('li.rank')},
            li.area ASC, li.node ASC, li.percentage DESC
          ''',
      variables: locationArgs.variables,
    ).get();

    final monsterArgs = SqlArgs();
    final monsterRowsFuture = _db.customSelect(
      '''
          SELECT mr.rank AS mr_rank, mr.quantity AS mr_quantity, mr.percentage AS mr_percentage,
            rctxt.name AS rctxt_name,
            monster.*, monster_text.*
          FROM monster_reward mr
          JOIN reward_condition_text rctxt
            ON mr.reward_condition_id = rctxt.reward_condition_id
            AND rctxt.language = ${monsterArgs.text(language)}
          JOIN monster ON mr.monster_id = monster.id
          JOIN monster_text
            ON monster.id = monster_text.monster_id
            AND monster_text.language = ${monsterArgs.text(language)}
          WHERE mr.item_id = ${monsterArgs.integer(itemId)}
          ORDER BY monster_text.name ASC, mr.percentage DESC
          ''',
      variables: monsterArgs.variables,
    ).get();

    final questArgs = SqlArgs();
    final questRowsFuture = _db.customSelect(
      '''
          SELECT qr.quantity AS qr_quantity, qr.percentage AS qr_percentage,
            rctxt.name AS rctxt_name,
            quest.*, quest_text.*
          FROM quest_reward qr
          JOIN reward_condition_text rctxt
            ON qr.reward_condition_id = rctxt.reward_condition_id
            AND rctxt.language = ${questArgs.text(language)}
          JOIN quest ON qr.quest_id = quest.id
          JOIN quest_text
            ON quest.id = quest_text.quest_id
            AND quest_text.language = ${questArgs.text(language)}
          WHERE qr.item_id = ${questArgs.integer(itemId)}
          ORDER BY qr.percentage DESC
          ''',
      variables: questArgs.variables,
    ).get();

    final (
      combinationRows,
      veggieRows,
      locationRows,
      monsterRows,
      questRows,
    ) = await (
      combinationRowsFuture,
      veggieRowsFuture,
      locationRowsFuture,
      monsterRowsFuture,
      questRowsFuture,
    ).wait;

    final (combinations, veggieSources) = await (
      _mapCombinationRows(combinationRows, language),
      _mapVeggieRows(
        veggieRows,
        language,
        (traded, common, rare, location, area) => VeggieSource(
          location: location,
          area: area,
          trade: VeggieTrade(
            itemTraded: traded,
            itemCommon: common,
            itemRare: rare,
          ),
        ),
      ),
    ).wait;

    final gatheringSources = locationRows.map(_gatheringSourceFromRow).toList();
    final monsterSources = monsterRows.map(_monsterSourceFromRow).toList();
    final questSources = questRows.map(_questSourceFromRow).toList();

    return ItemSources(
      combinations: combinations,
      locations: gatheringSources,
      monsterRewards: monsterSources,
      questRewards: questSources,
      veggieTrades: veggieSources,
    );
  }

  Future<ItemUsages> _getItemUsages(int itemId, String language) async {
    final combinationArgs = SqlArgs();
    final combinationRowsFuture = _db.customSelect(
      '''
          SELECT * FROM item_combination
          WHERE item_a_id = ${combinationArgs.integer(itemId)}
            OR item_b_id = ${combinationArgs.integer(itemId)}
          ''',
      variables: combinationArgs.variables,
    ).get();

    final veggieArgs = SqlArgs();
    final veggieRowsFuture = _db.customSelect(
      '''
          SELECT veggie_trade.*, location_text.*, veggie.location_area AS area
          FROM veggie_trade
          JOIN veggie ON veggie_trade.veggie_id = veggie.id
          JOIN location_text
            ON location_text.location_id = veggie.location_id
            AND location_text.language = ${veggieArgs.text(language)}
          WHERE veggie_trade.item_traded_id = ${veggieArgs.integer(itemId)}
          ''',
      variables: veggieArgs.variables,
    ).get();

    final armorArgs = SqlArgs();
    final armorRowsFuture = _db.customSelect(
      '''
          SELECT DISTINCT armor.*, armor_text.*, armor_recipe.quantity AS usageQuantity
          FROM armor
          JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = ${armorArgs.text(language)}
          JOIN armor_recipe ON armor.id = armor_recipe.armor_id
          WHERE armor_recipe.item_id = ${armorArgs.integer(itemId)}
          ORDER BY armor_recipe.quantity ASC, armor.rarity ASC
          ''',
      variables: armorArgs.variables,
    ).get();

    final decorationArgs = SqlArgs();
    final decorationRowsFuture = _db.customSelect(
      '''
          SELECT DISTINCT decoration.id AS dec_id, decoration.required_slots AS dec_required_slots,
            item.*, item_text.*, decoration_recipe.quantity AS usageQuantity
          FROM decoration
          JOIN item ON decoration.id = item.id
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${decorationArgs.text(language)}
          JOIN decoration_recipe ON decoration.id = decoration_recipe.decoration_id
          WHERE decoration_recipe.item_id = ${decorationArgs.integer(itemId)}
          ORDER BY decoration_recipe.quantity ASC
          ''',
      variables: decorationArgs.variables,
    ).get();

    final weaponArgs = SqlArgs();
    final weaponRowsFuture = _db.customSelect(
      '''
          SELECT DISTINCT weapon.*, weapon_text.*, weapon_recipe.quantity AS usageQuantity
          FROM weapon
          JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
            AND weapon_text.language = ${weaponArgs.text(language)}
          JOIN weapon_recipe ON weapon.id = weapon_recipe.weapon_id
          WHERE weapon_recipe.item_id = ${weaponArgs.integer(itemId)}
          ORDER BY weapon_recipe.quantity ASC, weapon.rarity ASC
          ''',
      variables: weaponArgs.variables,
    ).get();

    final (
      combinationRows,
      veggieRows,
      armorRows,
      decorationRows,
      weaponRows,
    ) = await (
      combinationRowsFuture,
      veggieRowsFuture,
      armorRowsFuture,
      decorationRowsFuture,
      weaponRowsFuture,
    ).wait;

    final (combinations, veggieUsages) = await (
      _mapCombinationRows(combinationRows, language),
      _mapVeggieRows(
        veggieRows,
        language,
        (traded, common, rare, location, area) => VeggieUsage(
          location: location,
          area: area,
          trade: VeggieTrade(
            itemTraded: traded,
            itemCommon: common,
            itemRare: rare,
          ),
        ),
      ),
    ).wait;

    final armorUsages = armorRows.map(_armorUsageFromRow).toList();
    final decorationUsages = decorationRows
        .map(_decorationUsageFromRow)
        .toList();
    final weaponUsages = weaponRows.map(_weaponUsageFromRow).toList();

    return ItemUsages(
      combinations: combinations,
      veggieTrades: veggieUsages,
      armors: armorUsages,
      decorations: decorationUsages,
      weapons: weaponUsages,
    );
  }

  Future<List<ItemCombination>> _mapCombinationRows(
    List<QueryRow> rows,
    String language,
  ) async {
    if (rows.isEmpty) return [];

    final itemIds = <int>{};
    for (final row in rows) {
      itemIds
        ..add(row.data['item_created_id'] as int)
        ..add(row.data['item_a_id'] as int)
        ..add(row.data['item_b_id'] as int);
    }
    final itemsById = {
      for (final item in await _getItemListByIds(itemIds.toList(), language))
        item.id: item,
    };

    return [
      for (final row in rows)
        if (itemsById[row.data['item_created_id'] as int] != null &&
            itemsById[row.data['item_a_id'] as int] != null &&
            itemsById[row.data['item_b_id'] as int] != null)
          ItemCombination(
            itemCreated: itemsById[row.data['item_created_id'] as int]!,
            itemA: itemsById[row.data['item_a_id'] as int]!,
            itemB: itemsById[row.data['item_b_id'] as int]!,
            type: ItemCombinationType.fromDb(
              row.data['combination_type'] as String,
            ),
            quantityMin: row.data['quantity_min'] as int,
            quantityMax: row.data['quantity_max'] as int,
            percentage: row.data['percentage'] as int,
          ),
    ];
  }

  Future<List<T>> _mapVeggieRows<T>(
    List<QueryRow> rows,
    String language,
    T Function(Item traded, Item common, Item rare, Location location, int area)
    build,
  ) async {
    if (rows.isEmpty) return [];

    final itemIds = <int>{};
    for (final row in rows) {
      itemIds
        ..add(row.data['item_traded_id'] as int)
        ..add(row.data['item_common_id'] as int)
        ..add(row.data['item_rare_id'] as int);
    }
    final itemsById = {
      for (final item in await _getItemListByIds(itemIds.toList(), language))
        item.id: item,
    };

    return [
      for (final row in rows)
        if (itemsById[row.data['item_traded_id'] as int] != null &&
            itemsById[row.data['item_common_id'] as int] != null &&
            itemsById[row.data['item_rare_id'] as int] != null)
          build(
            itemsById[row.data['item_traded_id'] as int]!,
            itemsById[row.data['item_common_id'] as int]!,
            itemsById[row.data['item_rare_id'] as int]!,
            Location(
              id: row.data['location_id'] as int,
              name: row.data['name'] as String,
            ),
            row.data['area'] as int,
          ),
    ];
  }

  GatheringSource _gatheringSourceFromRow(QueryRow row) {
    return GatheringSource(
      location: Location(
        id: row.data['id'] as int,
        name: row.data['name'] as String,
      ),
      rank: Rank.fromDb(row.data['li_rank'] as String),
      area: row.data['li_area'] as int,
      node: row.data['li_node'] as int,
      type: GatherType.fromDb(row.data['li_type'] as String),
      min: row.data['li_min'] as int,
      max: row.data['li_max'] as int,
      percentage: row.data['li_percentage'] as int,
    );
  }

  MonsterSource _monsterSourceFromRow(QueryRow row) {
    return MonsterSource(
      monster: Monster(
        id: row.data['id'] as int,
        name: row.data['name'] as String,
        ecology: row.data['ecology'] as String,
        description: row.data['description'] as String,
        type: MonsterType.fromDb(row.data['monster_type'] as String),
        sizeSmallestMin: _intOrNull(row.data['golden_smallest_min']),
        sizeSmallestMax: _intOrNull(row.data['golden_smallest_max']),
        sizeLargestMin: _intOrNull(row.data['golden_largest_min']),
        sizeLargestMax: _intOrNull(row.data['golden_largest_max']),
      ),
      condition: row.data['rctxt_name'] as String,
      rank: Rank.fromDb(row.data['mr_rank'] as String),
      quantity: row.data['mr_quantity'] as int,
      percentage: row.data['mr_percentage'] as int,
    );
  }

  QuestSource _questSourceFromRow(QueryRow row) {
    return QuestSource(
      quest: _shallowQuestFromRow(row),
      condition: row.data['rctxt_name'] as String,
      quantity: row.data['qr_quantity'] as int,
      percentage: row.data['qr_percentage'] as int,
    );
  }

  Quest _shallowQuestFromRow(QueryRow row) {
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

  Usage<Armor> _armorUsageFromRow(QueryRow row) {
    return Usage(
      craftable: Armor(
        id: row.data['id'] as int,
        armorSetId: row.data['armor_set_id'] as int,
        name: row.data['name'] as String,
        description: row.data['description'] as String,
        type: EquipmentType.fromDb(row.data['armor_type'] as String),
        hunterType: HunterType.fromDb(row.data['hunter_type'] as String),
        gender: Gender.fromDb(row.data['gender'] as String),
        rarity: row.data['rarity'] as int,
        price: row.data['price'] as int,
        numberOfSlots: row.data['num_slots'] as int,
        defense: row.data['defense'] as int,
        maxDefense: row.data['max_defense'] as int,
        fire: row.data['fire_res'] as int,
        water: row.data['water_res'] as int,
        thunder: row.data['thunder_res'] as int,
        ice: row.data['ice_res'] as int,
        dragon: row.data['dragon_res'] as int,
      ),
      quantity: row.data['usageQuantity'] as int,
    );
  }

  Usage<Decoration> _decorationUsageFromRow(QueryRow row) {
    return Usage(
      craftable: Decoration(
        id: row.data['dec_id'] as int,
        name: row.data['name'] as String,
        description: row.data['description'] as String,
        rarity: row.data['rarity'] as int,
        buyPrice: (row.data['buy_price'] as int?) ?? 0,
        sellPrice: row.data['sell_price'] as int,
        requiredSlots: row.data['dec_required_slots'] as int,
        color: ItemIconColor.fromDb(row.data['icon_color'] as String),
      ),
      quantity: row.data['usageQuantity'] as int,
    );
  }

  Usage<Weapon> _weaponUsageFromRow(QueryRow row) {
    return Usage(
      craftable: Weapon(
        id: row.data['id'] as int,
        name: row.data['name'] as String,
        description: row.data['description'] as String,
        type: WeaponType.fromDb(row.data['weapon_type'] as String),
        rarity: row.data['rarity'] as int,
        affinity: row.data['affinity'] as int,
        defense: row.data['defense'] as int,
        numberOfSlots: row.data['num_slots'] as int,
        attack: row.data['attack'] as int,
        maxAttack: row.data['max_attack'] as int?,
        price: row.data['price'] as int,
        buildable: (row.data['buildable'] as int) != 0,
      ),
      quantity: row.data['usageQuantity'] as int,
    );
  }

  int? _intOrNull(Object? value) => switch (value) {
    null => null,
    int value => value,
    double value => value.toInt(),
    _ => throw ArgumentError('Expected an int, got $value'),
  };

  Item _itemFromRow(
    QueryRow row, {
    ItemSources? sources,
    ItemUsages? usages,
  }) {
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
      sources: sources,
      usages: usages,
    );
  }
}
