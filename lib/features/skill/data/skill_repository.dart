import 'package:drift/drift.dart';

import '../../../core/database/app_database.dart' show AppDatabase;
import '../../../core/database/localized_collation.dart';
import '../../../core/database/sql_args.dart';
import '../../../core/domain/enums.dart';
import '../../../core/domain/shared.dart';
import '../../armor/domain/armor.dart';
import '../../decoration/domain/decoration.dart';
import '../domain/skill_tree_filter.dart';
import '../domain/skill.dart';

class SkillRepository {
  SkillRepository([AppDatabase? database])
    : _db = database ?? AppDatabase.instance;

  final AppDatabase _db;

  Future<SkillTree> getSkillTree(int skillTreeId, String language) async {
    final args = SqlArgs();
    final row = await _db.customSelect(
      '''
          SELECT skill_tree.*, skill_tree_text.*
          FROM skill_tree
          JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = ${args.text(language)}
          WHERE skill_tree.id = ${args.integer(skillTreeId)}
          ''',
      variables: args.variables,
    ).getSingle();

    final skills = await _getSkillsBySkillTreeId(skillTreeId, language);
    return _skillTreeFromRow(row, skills: skills);
  }

  Future<List<SkillTree>> getSkillTreeList(
    String language, {
    SkillTreeFilter filter = const SkillTreeFilter(),
  }) async {
    final args = SqlArgs();
    final name = filter.name != null ? normalizeForSearch(filter.name!) : null;

    final rows = await _db.customSelect(
      '''
          SELECT skill_tree.*, skill_tree_text.*
          FROM skill_tree
          JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = ${args.text(language)}
          WHERE
            (${args.text(name)} IS NULL OR (skill_tree_text.name_normalized LIKE '%' || ${args.text(name)} || '%' OR skill_tree_text.full_name_normalized LIKE '%' || ${args.text(name)} || '%')
              OR EXISTS (
                SELECT 1 FROM skill
                JOIN skill_text ON skill.id = skill_text.skill_id AND skill_text.language = ${args.text(language)}
                WHERE skill.skill_tree_id = skill_tree.id
                  AND (skill_text.name_normalized LIKE '%' || ${args.text(name)} || '%' OR skill_text.full_name_normalized LIKE '%' || ${args.text(name)} || '%')
              )
            )
            AND (${args.text(filter.category?.dbValue)} IS NULL OR skill_tree.category = ${args.text(filter.category?.dbValue)})
          ORDER BY skill_tree_text.name ASC
          ''',
      variables: args.variables,
    ).get();

    return rows.map((row) => _skillTreeFromRow(row)).toList();
  }

  Future<List<Armor>> getArmorListWithSkill(
    int skillTreeId,
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT armor.*, armor_text.*,
            skill_tree.id AS skilltree_id, skill_tree.category AS skilltree_category,
            skill_tree_text.name AS skilltree_name,
            armor_skill.point_value AS points
          FROM armor
          JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = ${args.text(language)}
          JOIN armor_skill
            ON armor.id = armor_skill.armor_id
            AND armor_skill.skill_tree_id = ${args.integer(skillTreeId)}
          JOIN skill_tree ON armor_skill.skill_tree_id = skill_tree.id
          JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = ${args.text(language)}
          ORDER BY armor_skill.point_value DESC, armor.rarity ASC
          ''',
      variables: args.variables,
    ).get();
    return rows.map(_armorWithSkillFromRow).toList();
  }

  Future<List<Decoration>> getDecorationListWithSkill(
    int skillTreeId,
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT decoration.required_slots AS dec_required_slots, item.*, item_text.*,
            skill_tree.id AS skilltree_id, skill_tree.category AS skilltree_category,
            skill_tree_text.name AS skilltree_name,
            decoration_skill.point_value AS points
          FROM decoration
          JOIN item ON decoration.id = item.id
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          JOIN decoration_skill
            ON decoration.id = decoration_skill.decoration_id
            AND decoration_skill.skill_tree_id = ${args.integer(skillTreeId)}
          JOIN skill_tree ON decoration_skill.skill_tree_id = skill_tree.id
          JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = ${args.text(language)}
          ORDER BY decoration_skill.point_value DESC
          ''',
      variables: args.variables,
    ).get();
    return rows.map(_decorationWithSkillFromRow).toList();
  }

  SkillPoint _skillPointFromAliasedRow(QueryRow row) {
    return SkillPoint(
      skillTree: SkillTree(
        id: row.read<int>('skilltree_id'),
        name: row.read<String>('skilltree_name'),
        category: SkillCategory.fromDb(row.read<String>('skilltree_category')),
      ),
      points: row.read<int>('points'),
    );
  }

  Armor _armorWithSkillFromRow(QueryRow row) {
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
      skills: [_skillPointFromAliasedRow(row)],
    );
  }

  Decoration _decorationWithSkillFromRow(QueryRow row) {
    return Decoration(
      id: row.data['id'] as int,
      name: row.data['name'] as String,
      description: row.data['description'] as String,
      rarity: row.data['rarity'] as int,
      buyPrice: row.data['buy_price'] as int? ?? 0,
      sellPrice: row.data['sell_price'] as int,
      requiredSlots: row.read<int>('dec_required_slots'),
      color: ItemIconColor.fromDb(row.data['icon_color'] as String),
      skills: [_skillPointFromAliasedRow(row)],
    );
  }

  Future<List<Skill>> _getSkillsBySkillTreeId(
    int skillTreeId,
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT skill.*, skill_text.*
          FROM skill
          JOIN skill_text
            ON skill.id = skill_text.skill_id
            AND skill_text.language = ${args.text(language)}
          WHERE skill.skill_tree_id = ${args.integer(skillTreeId)}
          ORDER BY skill.required_points DESC
          ''',
      variables: args.variables,
    ).get();
    return rows.map(_skillFromRow).toList();
  }

  SkillTree _skillTreeFromRow(QueryRow row, {List<Skill>? skills}) {
    return SkillTree(
      id: row.data['id'] as int,
      name: row.data['name'] as String,
      category: SkillCategory.fromDb(row.data['category'] as String),
      skills: skills,
    );
  }

  Skill _skillFromRow(QueryRow row) {
    return Skill(
      id: row.data['id'] as int,
      skillTreeId: row.data['skill_tree_id'] as int,
      name: row.data['name'] as String,
      description: row.data['description'] as String,
      requiredPoints: row.data['required_points'] as int,
    );
  }
}
