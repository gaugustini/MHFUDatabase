import 'package:drift/drift.dart';

import '../../../core/database/app_database.dart' show AppDatabase;
import '../../../core/database/localized_collation.dart';
import '../../../core/database/sql_args.dart';
import '../../../core/domain/enums.dart';
import '../../../core/domain/shared.dart';
import '../../item/domain/item.dart';
import '../../skill/domain/skill.dart';
import '../domain/decoration_filter.dart';
import '../domain/decoration.dart';

class DecorationRepository {
  DecorationRepository([AppDatabase? database])
    : _db = database ?? AppDatabase.instance;

  final AppDatabase _db;

  Future<Decoration> getDecoration(int decorationId, String language) async {
    final args = SqlArgs();
    final row = await _db.customSelect(
      '''
          SELECT decoration.required_slots AS dec_required_slots, item.*, item_text.*
          FROM decoration
          JOIN item ON decoration.id = item.id
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE decoration.id = ${args.integer(decorationId)}
          ''',
      variables: args.variables,
    ).getSingle();

    final skills = await _getDecorationSkills(decorationId, language);
    final recipeA = await _getDecorationRecipe(decorationId, 1, language);
    final recipeB = await _getDecorationRecipe(decorationId, 2, language);

    return _decorationFromRow(
      row,
      skills: skills,
      recipeA: recipeA,
      recipeB: recipeB,
    );
  }

  Future<List<Decoration>> getDecorationList(
    String language, {
    DecorationFilter filter = const DecorationFilter(),
  }) async {
    final args = SqlArgs();
    final numberOfSlots = filter.numberOfSlots ?? const [];
    final skills = filter.skills ?? const [];
    final name = filter.name != null ? normalizeForSearch(filter.name!) : null;

    final rows = await _db.customSelect(
      '''
          SELECT decoration.required_slots AS dec_required_slots, decoration.shop_order AS dec_shop_order,
            item.*, item_text.*
          FROM decoration
          JOIN item ON decoration.id = item.id
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE
            (${args.text(name)} IS NULL OR (item_text.name_normalized LIKE '%' || ${args.text(name)} || '%' OR item_text.full_name_normalized LIKE '%' || ${args.text(name)} || '%'))
            AND (${args.integer(filter.maxAvailableSlots)} IS NULL OR decoration.required_slots <= ${args.integer(filter.maxAvailableSlots)})
            AND (${args.flag(numberOfSlots.isEmpty)} OR decoration.required_slots IN ${args.integers(numberOfSlots)}
            )
            AND (${args.flag(skills.isEmpty)} OR EXISTS (
              SELECT 1 FROM decoration_skill
              WHERE decoration_skill.decoration_id = decoration.id
              AND decoration_skill.skill_tree_id IN ${args.integers(skills.map((skill) => skill.id).toList())}
            ))
          ORDER BY dec_shop_order ASC
          ''',
      variables: args.variables,
    ).get();

    final skillPointsByDecoration = await _groupDecorationSkillsByDecorationId(
      language,
    );

    return rows
        .map(
          (row) => _decorationFromRow(
            row,
            skills: skillPointsByDecoration[row.data['id'] as int],
          ),
        )
        .toList();
  }

  Future<List<SkillPoint>> _getDecorationSkills(
    int decorationId,
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT skill_tree.*, skill_tree_text.*, decoration_skill.point_value AS points
          FROM decoration_skill
          JOIN skill_tree ON decoration_skill.skill_tree_id = skill_tree.id
          JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = ${args.text(language)}
          WHERE decoration_skill.decoration_id = ${args.integer(decorationId)}
          ORDER BY points DESC
          ''',
      variables: args.variables,
    ).get();
    return rows.map(_skillPointFromRow).toList();
  }

  Future<Map<int, List<SkillPoint>>> _groupDecorationSkillsByDecorationId(
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT decoration_skill.decoration_id AS equipmentId, skill_tree.*, skill_tree_text.*,
            decoration_skill.point_value AS points
          FROM decoration_skill
          JOIN skill_tree ON decoration_skill.skill_tree_id = skill_tree.id
          JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = ${args.text(language)}
          ORDER BY points DESC
          ''',
      variables: args.variables,
    ).get();

    final grouped = <int, List<SkillPoint>>{};
    for (final row in rows) {
      final decorationId = row.data['equipmentId'] as int;
      (grouped[decorationId] ??= []).add(_skillPointFromRow(row));
    }
    return grouped;
  }

  Future<List<ItemQuantity>> _getDecorationRecipe(
    int decorationId,
    int recipeVariant,
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT item.*, item_text.*, decoration_recipe.quantity AS quantity
          FROM decoration_recipe
          JOIN item ON decoration_recipe.item_id = item.id
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE decoration_recipe.decoration_id = ${args.integer(decorationId)}
            AND decoration_recipe.recipe_variant = ${args.integer(recipeVariant)}
          ORDER BY quantity DESC
          ''',
      variables: args.variables,
    ).get();
    return rows.map(_itemQuantityFromRow).toList();
  }

  Decoration _decorationFromRow(
    QueryRow row, {
    List<SkillPoint>? skills,
    List<ItemQuantity>? recipeA,
    List<ItemQuantity>? recipeB,
  }) {
    return Decoration(
      id: row.data['id'] as int,
      name: row.data['name'] as String,
      description: row.data['description'] as String,
      rarity: row.data['rarity'] as int,
      buyPrice: row.data['buy_price'] as int? ?? 0,
      sellPrice: row.data['sell_price'] as int,
      requiredSlots: row.read<int>('dec_required_slots'),
      color: ItemIconColor.fromDb(row.data['icon_color'] as String),
      skills: skills,
      recipeA: recipeA,
      recipeB: recipeB,
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
