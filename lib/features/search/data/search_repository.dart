import 'package:drift/drift.dart';

import '../../../core/database/app_database.dart' show AppDatabase;
import '../../../core/database/localized_collation.dart';
import '../../../core/database/sql_args.dart';
import '../../../core/domain/enums.dart';
import '../../armor/domain/armor.dart';
import '../../decoration/domain/decoration.dart';
import '../../item/domain/item.dart';
import '../../location/domain/location.dart';
import '../../monster/domain/monster.dart';
import '../../quest/domain/quest.dart';
import '../../skill/domain/skill.dart';
import '../../weapon/domain/weapon.dart';
import '../domain/search_results.dart';

class SearchRepository {
  SearchRepository([AppDatabase? database])
    : _db = database ?? AppDatabase.instance;

  final AppDatabase _db;

  Future<SearchResults> search(String query, String language) async {
    final normalized = normalizeForSearch(query);

    return SearchResults(
      armors: await _searchArmors(normalized, language),
      decorations: await _searchDecorations(normalized, language),
      items: await _searchItems(normalized, language),
      locations: await _searchLocations(normalized, language),
      monsters: await _searchMonsters(normalized, language),
      quests: await _searchQuests(normalized, language),
      skillTrees: await _searchSkillTrees(normalized, language),
      skills: await _searchSkills(normalized, language),
      weapons: await _searchWeapons(normalized, language),
    );
  }

  Future<List<Armor>> _searchArmors(String query, String language) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT armor.*, armor_text.*
          FROM armor
          JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = ${args.text(language)}
          WHERE
            armor_text.name_normalized LIKE '%' || ${args.text(query)} || '%'
            OR armor_text.full_name_normalized LIKE '%' || ${args.text(query)} || '%'
          ''',
      variables: args.variables,
    ).get();

    return rows
        .map(
          (row) => Armor(
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
            skills: null,
            recipes: null,
          ),
        )
        .toList();
  }

  Future<List<Decoration>> _searchDecorations(
    String query,
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT
            decoration.id AS dec_id, decoration.required_slots AS dec_required_slots,
            item.*, item_text.*
          FROM decoration
          JOIN item ON decoration.id = item.id
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE
            item_text.name_normalized LIKE '%' || ${args.text(query)} || '%'
            OR item_text.full_name_normalized LIKE '%' || ${args.text(query)} || '%'
          ''',
      variables: args.variables,
    ).get();

    return rows
        .map(
          (row) => Decoration(
            id: row.read<int>('dec_id'),
            name: row.data['name'] as String,
            description: row.data['description'] as String,
            rarity: row.data['rarity'] as int,
            buyPrice: (row.data['buy_price'] as int?) ?? 0,
            sellPrice: row.data['sell_price'] as int,
            requiredSlots: row.read<int>('dec_required_slots'),
            color: ItemIconColor.fromDb(row.data['icon_color'] as String),
            skills: null,
            recipeA: null,
            recipeB: null,
          ),
        )
        .toList();
  }

  Future<List<Item>> _searchItems(String query, String language) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT item.*, item_text.*
          FROM item
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE
            item.id != 0
            AND (item_text.name_normalized LIKE '%' || ${args.text(query)} || '%'
              OR item_text.full_name_normalized LIKE '%' || ${args.text(query)} || '%')
          ''',
      variables: args.variables,
    ).get();

    return rows.map(_itemFromRow).toList();
  }

  Future<List<Location>> _searchLocations(String query, String language) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT location.*, location_text.*
          FROM location
          JOIN location_text
            ON location.id = location_text.location_id
            AND location_text.language = ${args.text(language)}
          WHERE location_text.name_normalized LIKE '%' || ${args.text(query)} || '%'
          ''',
      variables: args.variables,
    ).get();

    return rows
        .map(
          (row) => Location(
            id: row.data['id'] as int,
            name: row.data['name'] as String,
            gatheringPoints: null,
            quests: null,
          ),
        )
        .toList();
  }

  Future<List<Monster>> _searchMonsters(String query, String language) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT monster.*, monster_text.*
          FROM monster
          JOIN monster_text
            ON monster.id = monster_text.monster_id
            AND monster_text.language = ${args.text(language)}
          WHERE monster_text.name_normalized LIKE '%' || ${args.text(query)} || '%'
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
            damageStats: null,
            ailmentStats: null,
            itemEffectiveness: null,
            rewards: null,
            quests: null,
          ),
        )
        .toList();
  }

  Future<List<Quest>> _searchQuests(String query, String language) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT quest.*, quest_text.*
          FROM quest
          JOIN quest_text
            ON quest.id = quest_text.quest_id
            AND quest_text.language = ${args.text(language)}
          WHERE quest_text.name_normalized LIKE '%' || ${args.text(query)} || '%'
          ''',
      variables: args.variables,
    ).get();

    return rows
        .map(
          (row) => Quest(
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
            location: null,
            daytime: (row.data['daytime'] as String?) != null
                ? LocationDaytime.fromDb(row.data['daytime'] as String)
                : null,
            monsters: null,
            rewards: null,
            supplies: null,
          ),
        )
        .toList();
  }

  Future<List<SkillTree>> _searchSkillTrees(
    String query,
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT skill_tree.*, skill_tree_text.*
          FROM skill_tree
          JOIN skill_tree_text
            ON skill_tree.id = skill_tree_text.skill_tree_id
            AND skill_tree_text.language = ${args.text(language)}
          WHERE
            skill_tree_text.name_normalized LIKE '%' || ${args.text(query)} || '%'
            OR skill_tree_text.full_name_normalized LIKE '%' || ${args.text(query)} || '%'
          ''',
      variables: args.variables,
    ).get();

    return rows
        .map(
          (row) => SkillTree(
            id: row.data['id'] as int,
            name: row.data['name'] as String,
            category: SkillCategory.fromDb(row.data['category'] as String),
            skills: null,
          ),
        )
        .toList();
  }

  Future<List<Skill>> _searchSkills(String query, String language) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT skill.*, skill_text.*
          FROM skill
          JOIN skill_text
            ON skill.id = skill_text.skill_id
            AND skill_text.language = ${args.text(language)}
          WHERE
            skill_text.name_normalized LIKE '%' || ${args.text(query)} || '%'
            OR skill_text.full_name_normalized LIKE '%' || ${args.text(query)} || '%'
          ''',
      variables: args.variables,
    ).get();

    return rows
        .map(
          (row) => Skill(
            id: row.data['id'] as int,
            skillTreeId: row.data['skill_tree_id'] as int,
            name: row.data['name'] as String,
            description: row.data['description'] as String,
            requiredPoints: row.data['required_points'] as int,
          ),
        )
        .toList();
  }

  Future<List<Weapon>> _searchWeapons(String query, String language) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT weapon.*, weapon_text.*
          FROM weapon
          JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
            AND weapon_text.language = ${args.text(language)}
          WHERE
            weapon_text.name_normalized LIKE '%' || ${args.text(query)} || '%'
            OR weapon_text.full_name_normalized LIKE '%' || ${args.text(query)} || '%'
          ''',
      variables: args.variables,
    ).get();

    return rows.map(_weaponFromRow).toList();
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
      sources: null,
      usages: null,
    );
  }

  Weapon _weaponFromRow(QueryRow row) {
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
      element1: element1 != null ? WeaponElement.fromDb(element1) : null,
      element1Value: row.data['element_1_value'] as int?,
      element2: element2 != null ? WeaponElement.fromDb(element2) : null,
      element2Value: row.data['element_2_value'] as int?,
      sharpness: row.data['sharpness'] as String?,
      sharpnessPlus: row.data['sharpness_plus'] as String?,
      shellingType: shellingType != null
          ? WeaponShelling.fromDb(shellingType)
          : null,
      shellingLevel: row.data['shelling_level'] as int?,
      songNotes: row.data['song_notes'] as String?,
      reloadSpeed: reloadSpeed != null
          ? WeaponReloadSpeed.fromDb(reloadSpeed)
          : null,
      recoil: recoil != null ? WeaponRecoil.fromDb(recoil) : null,
      buildable: (row.data['buildable'] as int) != 0,
      ammoBow: null,
      ammoBowgun: null,
      recipesCreate: null,
      recipeUpgrade: null,
      paths: null,
      upgrades: null,
      finals: null,
    );
  }
}
