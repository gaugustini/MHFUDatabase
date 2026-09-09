import 'package:drift/drift.dart';

import '../../../core/database/app_database.dart' show AppDatabase;
import '../../../core/database/localized_collation.dart';
import '../../../core/database/sql_args.dart';
import '../../../core/domain/enums.dart';
import '../../../core/domain/shared.dart';
import '../../item/domain/item.dart';
import '../../skill/domain/skill.dart';
import '../domain/armor_filter.dart';
import '../domain/armor.dart';

class ArmorRepository {
  ArmorRepository([AppDatabase? database])
    : _db = database ?? AppDatabase.instance;

  final AppDatabase _db;

  Future<Armor> getArmor(int armorId, String language) async {
    final args = SqlArgs();
    final rowFuture = _db.customSelect(
      '''
          SELECT armor.*, armor_text.*
          FROM armor
          JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = ${args.text(language)}
          WHERE armor.id = ${args.integer(armorId)}
          ''',
      variables: args.variables,
    ).getSingle();

    final (row, skills, recipe) = await (
      rowFuture,
      _getArmorSkills(armorId, language),
      _getArmorRecipe(armorId, language),
    ).wait;

    return _armorFromRow(row, skills: skills, recipes: _groupRecipe(recipe));
  }

  Future<ArmorSet> getArmorSet(int armorSetId, String language) async {
    final args = SqlArgs();
    final rowFuture = _db.customSelect(
      '''
          SELECT
            armor_set.*, armor_set_text.*,
            SUM(armor.defense) AS defense,
            SUM(armor.max_defense) AS maxDefense,
            SUM(armor.fire_res) AS fire,
            SUM(armor.water_res) AS water,
            SUM(armor.thunder_res) AS thunder,
            SUM(armor.ice_res) AS ice,
            SUM(armor.dragon_res) AS dragon
          FROM armor_set
          JOIN armor_set_text
            ON armor_set.id = armor_set_text.armor_set_id
            AND armor_set_text.language = ${args.text(language)}
          JOIN armor ON armor_set.id = armor.armor_set_id
          WHERE armor_set.id = ${args.integer(armorSetId)}
          GROUP BY armor_set.id
          ''',
      variables: args.variables,
    ).getSingle();

    final (row, armors, skills, recipe) = await (
      rowFuture,
      _getArmorListByArmorSetId(armorSetId, language),
      _getArmorSetSkills(armorSetId, language),
      _getArmorSetRecipe(armorSetId, language),
    ).wait;

    return _armorSetFromRow(
      row,
      armors: armors.map((armorRow) => _armorFromRow(armorRow)).toList(),
      skills: skills,
      recipe: recipe,
    );
  }

  Future<List<Armor>> getArmorList(
    String language, {
    ArmorFilter filter = const ArmorFilter(),
  }) async {
    final args = SqlArgs();
    final numberOfSlots = filter.numberOfSlots ?? const [];
    final rarity = filter.rarity ?? const [];
    final skills = filter.skills ?? const [];
    final name = filter.name != null ? normalizeForSearch(filter.name!) : null;

    final rows = await _db.customSelect(
      '''
          SELECT armor.*, armor_text.*
          FROM armor
          JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = ${args.text(language)}
          WHERE
            (${args.text(name)} IS NULL OR (armor_text.name_normalized LIKE '%' || ${args.text(name)} || '%' OR armor_text.full_name_normalized LIKE '%' || ${args.text(name)} || '%'))
            AND (${args.text(filter.type?.dbValue)} IS NULL OR armor.armor_type = ${args.text(filter.type?.dbValue)})
            AND (${args.flag(numberOfSlots.isEmpty)} OR armor.num_slots IN ${args.integers(numberOfSlots)}
            )
            AND (${args.flag(rarity.isEmpty)} OR armor.rarity IN ${args.integers(rarity)}
            )
            AND (${args.text(filter.gender?.dbValue)} IS NULL OR armor.gender IN (${args.text(filter.gender?.dbValue)}, 'BOTH'))
            AND (${args.text(filter.hunterType?.dbValue)} IS NULL OR armor.hunter_type IN (${args.text(filter.hunterType?.dbValue)}, 'BOTH'))
            AND (${args.flag(skills.isEmpty)} OR EXISTS (
              SELECT 1 FROM armor_skill
              WHERE armor_skill.armor_id = armor.id
              AND armor_skill.skill_tree_id IN ${args.integers(skills.map((skill) => skill.id).toList())}
            ))
          ORDER BY armor.armor_set_id ASC
          ''',
      variables: args.variables,
    ).get();

    final skillPointsByArmor = await _groupArmorSkillsByArmorId(language);

    return rows
        .map(
          (row) => _armorFromRow(
            row,
            skills: skillPointsByArmor[row.data['id'] as int],
          ),
        )
        .toList();
  }

  Future<List<ArmorSet>> getArmorSetList(
    String language, {
    ArmorSetFilter filter = const ArmorSetFilter(),
  }) async {
    final args = SqlArgs();
    final rarity = filter.rarity ?? const [];
    final skills = filter.skills ?? const [];
    final hunterType = filter.hunterType != null
        ? [filter.hunterType!.dbValue, HunterType.both.dbValue]
        : const <String>[];
    final name = filter.name != null ? normalizeForSearch(filter.name!) : null;

    final rows = await _db.customSelect(
      '''
          SELECT
            armor_set.*, armor_set_text.*,
            SUM(armor.defense) AS defense,
            SUM(armor.max_defense) AS maxDefense,
            SUM(armor.fire_res) AS fire,
            SUM(armor.water_res) AS water,
            SUM(armor.thunder_res) AS thunder,
            SUM(armor.ice_res) AS ice,
            SUM(armor.dragon_res) AS dragon
          FROM armor_set
          JOIN armor_set_text
            ON armor_set.id = armor_set_text.armor_set_id
            AND armor_set_text.language = ${args.text(language)}
          JOIN armor ON armor_set.id = armor.armor_set_id
          WHERE
            (${args.text(name)} IS NULL OR armor_set_text.name_normalized LIKE '%' || ${args.text(name)} || '%')
            AND (${args.flag(rarity.isEmpty)} OR armor_set.rarity IN ${args.integers(rarity)}
            )
            AND (${args.text(filter.rank?.dbValue)} IS NULL OR armor_set.rank = ${args.text(filter.rank?.dbValue)})
            AND (${args.flag(filter.hunterType == null)} OR armor_set.hunter_type IN ${args.texts(hunterType)}
            )
            AND (${args.text(filter.gender?.dbValue)} IS NULL OR armor_set.gender = ${args.text(filter.gender?.dbValue)})
            AND (${args.flag(skills.isEmpty)} OR EXISTS (
              SELECT 1 FROM armor_skill
              JOIN armor ON armor.id = armor_skill.armor_id
              WHERE armor.armor_set_id = armor_set.id
              AND armor_skill.skill_tree_id IN ${args.integers(skills.map((skill) => skill.id).toList())}
            ))
          GROUP BY armor_set.id
          ''',
      variables: args.variables,
    ).get();

    final armorsByArmorSet = await _groupArmorListByArmorSetId(language);

    return rows
        .map(
          (row) => _armorSetFromRow(
            row,
            armors: armorsByArmorSet[row.data['id'] as int],
          ),
        )
        .toList();
  }

  Future<List<QueryRow>> _getArmorListByArmorSetId(
    int armorSetId,
    String language,
  ) async {
    final args = SqlArgs();
    return _db.customSelect(
      '''
          SELECT armor.*, armor_text.*
          FROM armor
          JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = ${args.text(language)}
          WHERE armor.armor_set_id = ${args.integer(armorSetId)}
          ''',
      variables: args.variables,
    ).get();
  }

  Future<Map<int, List<Armor>>> _groupArmorListByArmorSetId(
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT armor.*, armor_text.*
          FROM armor
          JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = ${args.text(language)}
          ''',
      variables: args.variables,
    ).get();

    final grouped = <int, List<Armor>>{};
    for (final row in rows) {
      final armorSetId = row.data['armor_set_id'] as int;
      (grouped[armorSetId] ??= []).add(_armorFromRow(row));
    }
    return grouped;
  }

  Future<List<SkillPoint>> _getArmorSkills(int armorId, String language) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT skill_tree.*, skill_tree_text.*, armor_skill.point_value AS points
          FROM armor_skill
          JOIN skill_tree ON armor_skill.skill_tree_id = skill_tree.id
          JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = ${args.text(language)}
          WHERE armor_skill.armor_id = ${args.integer(armorId)}
          ORDER BY points DESC
          ''',
      variables: args.variables,
    ).get();
    return rows.map(_skillPointFromRow).toList();
  }

  Future<Map<int, List<SkillPoint>>> _groupArmorSkillsByArmorId(
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT armor_skill.armor_id AS equipmentId, skill_tree.*, skill_tree_text.*,
            armor_skill.point_value AS points
          FROM armor_skill
          JOIN skill_tree ON armor_skill.skill_tree_id = skill_tree.id
          JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = ${args.text(language)}
          ORDER BY points DESC
          ''',
      variables: args.variables,
    ).get();

    final grouped = <int, List<SkillPoint>>{};
    for (final row in rows) {
      final armorId = row.data['equipmentId'] as int;
      (grouped[armorId] ??= []).add(_skillPointFromRow(row));
    }
    return grouped;
  }

  Future<List<QueryRow>> _getArmorRecipe(int armorId, String language) async {
    final args = SqlArgs();
    return _db.customSelect(
      '''
          SELECT item.*, item_text.*, armor_recipe.quantity AS quantity,
            armor_recipe.recipe_variant AS recipeVariant
          FROM armor_recipe
          JOIN item ON armor_recipe.item_id = item.id
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE armor_recipe.armor_id = ${args.integer(armorId)}
          ORDER BY quantity DESC
          ''',
      variables: args.variables,
    ).get();
  }

  Future<List<SkillPoint>> _getArmorSetSkills(
    int armorSetId,
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT skill_tree.*, skill_tree_text.*, SUM(armor_skill.point_value) AS points
          FROM armor_set
          JOIN armor ON armor_set.id = armor.armor_set_id
          JOIN armor_skill ON armor.id = armor_skill.armor_id
          JOIN skill_tree ON armor_skill.skill_tree_id = skill_tree.id
          JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = ${args.text(language)}
          WHERE armor_set.id = ${args.integer(armorSetId)}
          GROUP BY skill_tree.id
          ORDER BY points DESC
          ''',
      variables: args.variables,
    ).get();
    return rows.map(_skillPointFromRow).toList();
  }

  Future<List<ItemQuantity>> _getArmorSetRecipe(
    int armorSetId,
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT item.*, item_text.*, SUM(armor_recipe.quantity) AS quantity
          FROM armor_set
          JOIN armor ON armor_set.id = armor.armor_set_id
          JOIN armor_recipe
            ON armor.id = armor_recipe.armor_id
            AND armor_recipe.recipe_variant = 1
          JOIN item ON armor_recipe.item_id = item.id
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE armor_set.id = ${args.integer(armorSetId)}
          GROUP BY item.id
          ORDER BY quantity DESC
          ''',
      variables: args.variables,
    ).get();
    return rows.map(_itemQuantityFromRow).toList();
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

  Armor _armorFromRow(
    QueryRow row, {
    List<SkillPoint>? skills,
    List<List<ItemQuantity>>? recipes,
  }) {
    return Armor(
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
      skills: skills,
      recipes: recipes,
    );
  }

  ArmorSet _armorSetFromRow(
    QueryRow row, {
    List<Armor>? armors,
    List<SkillPoint>? skills,
    List<ItemQuantity>? recipe,
  }) {
    return ArmorSet(
      id: row.data['id'] as int,
      name: row.data['name'] as String,
      rank: Rank.fromDb(row.data['rank'] as String),
      hunterType: HunterType.fromDb(row.data['hunter_type'] as String),
      gender: Gender.fromDb(row.data['gender'] as String),
      rarity: row.data['rarity'] as int,
      defense: row.read<int>('defense'),
      maxDefense: row.read<int>('maxDefense'),
      fire: row.read<int>('fire'),
      water: row.read<int>('water'),
      thunder: row.read<int>('thunder'),
      ice: row.read<int>('ice'),
      dragon: row.read<int>('dragon'),
      armors: armors,
      skills: skills,
      recipe: recipe,
    );
  }

  SkillPoint _skillPointFromRow(QueryRow row) {
    return SkillPoint(
      skillTree: SkillTree(
        id: row.data['id'] as int,
        name: row.data['name'] as String,
        category: SkillCategory.fromDb(row.data['category'] as String),
      ),
      points: row.read<int>('points'),
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
