import 'package:drift/drift.dart';

import '../../../core/database/app_database.dart' show AppDatabase;
import '../../../core/database/localized_collation.dart';
import '../../../core/database/sql_args.dart';
import '../../../core/domain/enums.dart';
import '../../../core/domain/shared.dart';
import '../../item/domain/item.dart';
import '../domain/weapon_filter.dart';
import '../domain/weapon.dart';

class WeaponRepository {
  WeaponRepository([AppDatabase? database])
    : _db = database ?? AppDatabase.instance;

  final AppDatabase _db;

  Future<Weapon> getWeapon(int weaponId, String language) async {
    final ammoBowFuture = _getAmmoBow(weaponId);
    final ammoBowgunFuture = _getAmmoBowgun(weaponId);
    final recipeCreateFuture = _getWeaponRecipe(weaponId, 'CREATE', language);
    final recipeUpgradeFuture = _getWeaponRecipe(weaponId, 'UPGRADE', language);

    final row = await _getWeaponRow(weaponId, language);
    final type = WeaponType.fromDb(row.data['weapon_type'] as String);

    final relatedWeapons = await _getWeaponListByTypes(
      type.relatedTypes.map((related) => related.dbValue).toList(),
      language,
    );
    final relations = await _getWeaponRelations(
      relatedWeapons.map((weapon) => weapon.id).toList(),
    );
    final graph = _WeaponGraph(relatedWeapons, relations);

    final (ammoBowRow, ammoBowgunRow, recipeCreate, recipeUpgrade) = await (
      ammoBowFuture,
      ammoBowgunFuture,
      recipeCreateFuture,
      recipeUpgradeFuture,
    ).wait;

    return _weaponFromRow(
      row,
      ammoBow: ammoBowRow == null ? null : _ammoBowFromRow(ammoBowRow),
      ammoBowgun: ammoBowgunRow == null
          ? null
          : _ammoBowgunFromRow(ammoBowgunRow),
      recipesCreate: _groupRecipe(recipeCreate),
      recipeUpgrade: recipeUpgrade.map(_itemQuantityFromRow).toList(),
      paths: graph.findPathsToRoot(weaponId),
      upgrades: graph.findDirectUpgrades(weaponId),
      finals: graph.findLeaves(weaponId),
    );
  }

  Future<List<Weapon>> getWeaponList(
    String language, {
    WeaponFilter filter = const WeaponFilter(),
  }) async {
    final args = SqlArgs();
    final numberOfSlots = filter.numberOfSlots ?? const [];
    final rarity = filter.rarity ?? const [];
    final weaponTypes =
        filter.weaponType != null && filter.weaponType!.isNotEmpty
        ? filter.weaponType!.map((type) => type.dbValue).toList()
        : (filter.hunterType != null
              ? WeaponType.forHunterType(
                  filter.hunterType!,
                ).map((type) => type.dbValue).toList()
              : const <String>[]);
    final hasWeaponTypeFilter =
        (filter.weaponType != null && filter.weaponType!.isNotEmpty) ||
        filter.hunterType != null;
    final elementTypes = filter.elementType ?? const [];
    final name = filter.name != null ? normalizeForSearch(filter.name!) : null;

    final rows = await _db.customSelect(
      '''
          SELECT weapon.*, weapon_text.*
          FROM weapon
          JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
            AND weapon_text.language = ${args.text(language)}
          WHERE
            (${args.text(name)} IS NULL OR (weapon_text.name_normalized LIKE '%' || ${args.text(name)} || '%' OR weapon_text.full_name_normalized LIKE '%' || ${args.text(name)} || '%'))
            AND (${args.flag(!hasWeaponTypeFilter)} OR weapon.weapon_type IN ${args.texts(weaponTypes)}
            )
            AND (${args.flag(numberOfSlots.isEmpty)} OR weapon.num_slots IN ${args.integers(numberOfSlots)}
            )
            AND (${args.flag(rarity.isEmpty)} OR weapon.rarity IN ${args.integers(rarity)}
            )
            AND (${args.flag(elementTypes.isEmpty)} OR (
              weapon.element_1 IN ${args.texts(elementTypes.map((element) => element.dbValue).toList())}
              OR weapon.element_2 IN ${args.texts(elementTypes.map((element) => element.dbValue).toList())}
            ))
          ''',
      variables: args.variables,
    ).get();

    return rows.map(_weaponFromRow).toList();
  }

  Future<List<FlattenedWeaponNode>> getWeaponTree(
    WeaponType weaponType,
    String language,
  ) async {
    final relatedWeapons = await _getWeaponListByTypes(
      weaponType.relatedTypes.map((related) => related.dbValue).toList(),
      language,
    );
    final relations = await _getWeaponRelations(
      relatedWeapons.map((weapon) => weapon.id).toList(),
    );

    return _WeaponGraph(
      relatedWeapons,
      relations,
    ).buildFlattenedGraph(weaponType);
  }

  Future<QueryRow> _getWeaponRow(int weaponId, String language) {
    final args = SqlArgs();
    return _db.customSelect(
      '''
          SELECT weapon.*, weapon_text.*
          FROM weapon
          JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
            AND weapon_text.language = ${args.text(language)}
          WHERE weapon.id = ${args.integer(weaponId)}
          ''',
      variables: args.variables,
    ).getSingle();
  }

  Future<List<Weapon>> _getWeaponListByTypes(
    List<String> weaponTypes,
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT weapon.*, weapon_text.*
          FROM weapon
          JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
            AND weapon_text.language = ${args.text(language)}
          WHERE weapon.weapon_type IN ${args.texts(weaponTypes)}
          ''',
      variables: args.variables,
    ).get();
    return rows.map(_weaponFromRow).toList();
  }

  Future<List<(int, int)>> _getWeaponRelations(List<int> weaponIds) async {
    if (weaponIds.isEmpty) return const [];
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT weapon_id, parent_weapon_id
          FROM weapon_parent
          WHERE weapon_id IN ${args.integers(weaponIds)}
          ''',
      variables: args.variables,
    ).get();
    return rows
        .map(
          (row) => (
            row.data['weapon_id'] as int,
            row.data['parent_weapon_id'] as int,
          ),
        )
        .toList();
  }

  Future<QueryRow?> _getAmmoBow(int weaponId) async {
    final args = SqlArgs();
    final rows = await _db
        .customSelect(
          'SELECT * FROM weapon_ammo_bow WHERE weapon_id = ${args.integer(weaponId)}',
          variables: args.variables,
        )
        .get();
    return rows.isEmpty ? null : rows.single;
  }

  Future<QueryRow?> _getAmmoBowgun(int weaponId) async {
    final args = SqlArgs();
    final rows = await _db
        .customSelect(
          'SELECT * FROM weapon_ammo_bowgun WHERE weapon_id = ${args.integer(weaponId)}',
          variables: args.variables,
        )
        .get();
    return rows.isEmpty ? null : rows.single;
  }

  Future<List<QueryRow>> _getWeaponRecipe(
    int weaponId,
    String recipeType,
    String language,
  ) {
    final args = SqlArgs();
    return _db.customSelect(
      '''
          SELECT item.*, item_text.*, weapon_recipe.quantity AS quantity,
            weapon_recipe.recipe_variant AS recipeVariant
          FROM weapon_recipe
          JOIN item ON weapon_recipe.item_id = item.id
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE weapon_recipe.weapon_id = ${args.integer(weaponId)}
            AND weapon_recipe.recipe_type = ${args.text(recipeType)}
          ORDER BY quantity DESC
          ''',
      variables: args.variables,
    ).get();
  }

  List<List<ItemQuantity>>? _groupRecipe(List<QueryRow> rows) {
    if (rows.isEmpty) return null;
    final byVariant = <int, List<ItemQuantity>>{};
    for (final row in rows) {
      final variant = row.data['recipeVariant'] as int;
      (byVariant[variant] ??= []).add(_itemQuantityFromRow(row));
    }
    return byVariant.values.toList();
  }

  Weapon _weaponFromRow(
    QueryRow row, {
    AmmoBow? ammoBow,
    AmmoBowgun? ammoBowgun,
    List<List<ItemQuantity>>? recipesCreate,
    List<ItemQuantity>? recipeUpgrade,
    List<List<Weapon>>? paths,
    List<Weapon>? upgrades,
    List<Weapon>? finals,
  }) {
    final element1 = row.data['element_1'] as String?;
    final element2 = row.data['element_2'] as String?;
    final shellingType = row.data['shelling_type'] as String?;
    final reloadSpeed = row.data['reload_speed'] as String?;
    final recoil = row.data['recoil'] as String?;

    return Weapon(
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
      element1: element1 == null ? null : WeaponElement.fromDb(element1),
      element1Value: row.data['element_1_value'] as int?,
      element2: element2 == null ? null : WeaponElement.fromDb(element2),
      element2Value: row.data['element_2_value'] as int?,
      sharpness: row.data['sharpness'] as String?,
      sharpnessPlus: row.data['sharpness_plus'] as String?,
      shellingType: shellingType == null
          ? null
          : WeaponShelling.fromDb(shellingType),
      shellingLevel: row.data['shelling_level'] as int?,
      songNotes: row.data['song_notes'] as String?,
      reloadSpeed: reloadSpeed == null
          ? null
          : WeaponReloadSpeed.fromDb(reloadSpeed),
      recoil: recoil == null ? null : WeaponRecoil.fromDb(recoil),
      buildable: (row.data['buildable'] as int) != 0,
      ammoBow: ammoBow,
      ammoBowgun: ammoBowgun,
      recipesCreate: recipesCreate,
      recipeUpgrade: recipeUpgrade,
      paths: paths,
      upgrades: upgrades,
      finals: finals,
    );
  }

  AmmoBow _ammoBowFromRow(QueryRow row) {
    final charge4Type = row.data['charge_4_type'] as String?;
    return AmmoBow(
      charge1Type: WeaponAmmo.fromDb(row.data['charge_1_type'] as String),
      charge1Level: row.data['charge_1_level'] as int,
      charge2Type: WeaponAmmo.fromDb(row.data['charge_2_type'] as String),
      charge2Level: row.data['charge_2_level'] as int,
      charge3Type: WeaponAmmo.fromDb(row.data['charge_3_type'] as String),
      charge3Level: row.data['charge_3_level'] as int,
      charge4Type: charge4Type == null ? null : WeaponAmmo.fromDb(charge4Type),
      charge4Level: row.data['charge_4_level'] as int?,
      power: (row.data['power'] as int) != 0,
      close: (row.data['close'] as int) != 0,
      paint: (row.data['paint'] as int) != 0,
      poison: (row.data['poison'] as int) != 0,
      paralysis: (row.data['paralysis'] as int) != 0,
      sleep: (row.data['sleep'] as int) != 0,
    );
  }

  AmmoBowgun _ammoBowgunFromRow(QueryRow row) {
    return AmmoBowgun(
      normal: row.data['normal'] as String,
      pierce: row.data['pierce'] as String,
      pellet: row.data['pellet'] as String,
      crag: row.data['crag'] as String,
      clust: row.data['clust'] as String,
      recovery: row.data['recovery'] as String,
      poison: row.data['poison'] as String,
      paralysis: row.data['paralysis'] as String,
      sleep: row.data['sleep'] as String,
      flame: row.data['flame'] as String,
      water: row.data['water'] as String,
      thunder: row.data['thunder'] as String,
      freeze: row.data['freeze'] as String,
      dragon: row.data['dragon'] as String,
      tranq: row.data['tranq'] as String,
      paint: row.data['paint'] as String,
      demon: row.data['demon'] as String,
      armor: row.data['armor'] as String,
      rapidFire: row.data['rapid_fire'] as String?,
    );
  }

  ItemQuantity _itemQuantityFromRow(QueryRow row) {
    return ItemQuantity(
      item: Item(
        id: row.data['id'] as int,
        name: row.data['name'] as String,
        description: row.data['description'] as String,
        rarity: row.data['rarity'] as int,
        buyPrice: row.data['buy_price'] as int?,
        sellPrice: row.data['sell_price'] as int,
        carryMax: row.data['carry_max'] as int,
        iconType: ItemIconType.fromDb(row.data['icon_type'] as String),
        iconColor: ItemIconColor.fromDb(row.data['icon_color'] as String),
      ),
      quantity: row.read<int>('quantity'),
    );
  }
}

class _WeaponGraph {
  _WeaponGraph(this._weapons, this._relations)
    : _nodes = {
        for (final weapon in _weapons) weapon.id: WeaponNode(weapon: weapon),
      } {
    for (final (weaponId, parentWeaponId) in _relations) {
      final child = _nodes[weaponId];
      final parent = _nodes[parentWeaponId];
      if (child != null && parent != null) {
        child.parents.add(parent);
        parent.children.add(child);
      }
    }
  }

  final List<Weapon> _weapons;
  final List<(int, int)> _relations;
  final Map<int, WeaponNode> _nodes;

  List<WeaponNode> _buildGraphByType(WeaponType type) {
    final clonedNodes = {
      for (final weapon in _weapons) weapon.id: WeaponNode(weapon: weapon),
    };

    for (final (weaponId, parentWeaponId) in _relations) {
      final child = clonedNodes[weaponId];
      final parent = clonedNodes[parentWeaponId];
      if (child == null || parent == null) continue;
      if (parent.weapon.type != type && child.weapon.type != type) continue;
      child.parents.add(parent);
      parent.children.add(child);
    }

    clonedNodes.removeWhere(
      (_, node) =>
          node.weapon.type != type &&
          node.parents.isEmpty &&
          node.children.isEmpty,
    );

    final roots =
        clonedNodes.values.where((node) => node.parents.isEmpty).toList()
          ..sort((a, b) => a.weapon.rarity.compareTo(b.weapon.rarity));
    return roots;
  }

  List<List<Weapon>> findPathsToRoot(int weaponId) {
    final startNode = _nodes[weaponId];
    if (startNode == null) return const [];

    List<List<Weapon>> dfs(WeaponNode node, List<Weapon> path) {
      final newPath = [node.weapon, ...path];
      if (node.parents.isEmpty) return [newPath];
      return [
        for (final parent in node.parents) ...dfs(parent, newPath),
      ];
    }

    return dfs(startNode, const []);
  }

  List<Weapon> findLeaves(int weaponId) {
    final startNode = _nodes[weaponId];
    if (startNode == null) return const [];

    final visited = <int>{};
    final leaves = <Weapon>[];

    void dfs(WeaponNode node) {
      if (node.children.isEmpty) {
        if (visited.add(node.weapon.id)) leaves.add(node.weapon);
        return;
      }
      for (final child in node.children) {
        dfs(child);
      }
    }

    dfs(startNode);
    return leaves;
  }

  List<Weapon> findDirectUpgrades(int weaponId) {
    return _nodes[weaponId]?.children.map((node) => node.weapon).toList() ??
        const [];
  }

  List<FlattenedWeaponNode> buildFlattenedGraph(WeaponType type) {
    final roots = _buildGraphByType(type);

    final flattened = <FlattenedWeaponNode>[];
    var nextUniqueId = 0;

    void flatten(WeaponNode node, int depth, bool isLast) {
      flattened.add(
        FlattenedWeaponNode(
          uniqueId: nextUniqueId++,
          weapon: node.weapon,
          depth: depth,
          hasChildren: node.children.isNotEmpty,
          isLastInGroup: isLast,
        ),
      );

      final children = node.children.toList()
        ..sort((a, b) => a.weapon.rarity.compareTo(b.weapon.rarity));
      for (var i = 0; i < children.length; i++) {
        flatten(children[i], depth + 1, i == children.length - 1);
      }
    }

    for (var i = 0; i < roots.length; i++) {
      flatten(roots[i], 0, i == roots.length - 1);
    }

    return flattened;
  }
}
