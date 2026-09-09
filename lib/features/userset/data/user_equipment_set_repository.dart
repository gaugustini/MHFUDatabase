import 'package:drift/drift.dart';

import '../../../core/database/app_database.dart'
    hide Armor, Decoration, Item, Skill, SkillTree, Weapon;
import '../../../core/database/sql_args.dart';
import '../../../core/domain/enums.dart';
import '../../../core/domain/shared.dart';
import '../../armor/domain/armor.dart';
import '../../decoration/domain/decoration.dart';
import '../../item/domain/item.dart';
import '../../skill/domain/skill.dart';
import '../../weapon/domain/weapon.dart';
import '../domain/user_equipment_set.dart';

class UserEquipmentSetRepository {
  UserEquipmentSetRepository([AppDatabase? database])
    : _db = database ?? AppDatabase.instance;

  final AppDatabase _db;

  Future<List<UserEquipmentSet>> getUserEquipmentSetList(
    String language,
  ) async {
    final rows = await _db.select(_db.userSet).get();
    return rows
        .map(
          (row) => UserEquipmentSet(
            id: row.id,
            name: row.name,
            hunterType: HunterType.fromDb(row.hunterType),
            gender: Gender.fromDb(row.gender),
          ),
        )
        .toList();
  }

  Future<UserEquipmentSet> getUserEquipmentSet(int id, String language) async {
    final set = await (_db.select(
      _db.userSet,
    )..where((row) => row.id.equals(id))).getSingle();

    final weapon = await _getWeapon(set.weaponId, language);
    final armors = await _getArmors(id, language);
    final decorations = await _getDecorations(id, language);
    final skills = await _getSkillPoints(id, language);
    final activeSkills = await _getActiveSkills(skills, language);
    final recipe = await _getRecipe(id, language);

    return UserEquipmentSet(
      id: set.id,
      name: set.name,
      hunterType: HunterType.fromDb(set.hunterType),
      gender: Gender.fromDb(set.gender),
      defense: armors.fold(0, (sum, armor) => sum + armor.defense),
      fire: armors.fold(0, (sum, armor) => sum + armor.fire),
      water: armors.fold(0, (sum, armor) => sum + armor.water),
      thunder: armors.fold(0, (sum, armor) => sum + armor.thunder),
      ice: armors.fold(0, (sum, armor) => sum + armor.ice),
      dragon: armors.fold(0, (sum, armor) => sum + armor.dragon),
      weapon: weapon,
      armors: armors,
      decorations: decorations,
      activeSkills: activeSkills,
      skills: skills,
      recipe: recipe,
    );
  }

  Future<int> createUserEquipmentSet(
    String name,
    HunterType hunterType,
    Gender gender,
  ) async {
    return _db
        .into(_db.userSet)
        .insert(
          UserSetCompanion.insert(
            name: name,
            hunterType: hunterType.dbValue,
            gender: gender.dbValue,
          ),
        );
  }

  Future<void> renameUserEquipmentSet(int id, String name) async {
    await (_db.update(_db.userSet)..where(
          (row) => row.id.equals(id),
        ))
        .write(UserSetCompanion(name: Value(name)));
  }

  Future<void> deleteUserEquipmentSet(int id) async {
    await (_db.delete(_db.userSet)..where((row) => row.id.equals(id))).go();
  }

  Future<void> setWeapon(int setId, int? weaponId) async {
    await (_db.update(_db.userSet)..where(
          (row) => row.id.equals(setId),
        ))
        .write(UserSetCompanion(weaponId: Value(weaponId)));
  }

  Future<void> addArmor(int setId, int armorId, EquipmentType type) async {
    final existing =
        await (_db.select(_db.userSetArmor).join([
              innerJoin(
                _db.armor,
                _db.armor.id.equalsExp(_db.userSetArmor.armorId),
              ),
            ])..where(
              _db.userSetArmor.userSetId.equals(setId) &
                  _db.armor.armorType.equals(type.dbValue),
            ))
            .get();

    for (final row in existing) {
      final current = row.readTable(_db.userSetArmor);
      await (_db.delete(_db.userSetArmor)..where(
            (r) =>
                r.userSetId.equals(setId) & r.armorId.equals(current.armorId),
          ))
          .go();
    }

    await _db
        .into(_db.userSetArmor)
        .insert(
          UserSetArmorCompanion.insert(userSetId: setId, armorId: armorId),
        );
  }

  Future<void> removeArmor(int setId, EquipmentType type) async {
    final rows =
        await (_db.select(_db.userSetArmor).join([
              innerJoin(
                _db.armor,
                _db.armor.id.equalsExp(_db.userSetArmor.armorId),
              ),
            ])..where(
              _db.userSetArmor.userSetId.equals(setId) &
                  _db.armor.armorType.equals(type.dbValue),
            ))
            .get();

    for (final row in rows) {
      final current = row.readTable(_db.userSetArmor);
      await (_db.delete(_db.userSetArmor)..where(
            (r) =>
                r.userSetId.equals(setId) & r.armorId.equals(current.armorId),
          ))
          .go();
    }
  }

  Future<void> addDecoration(
    int setId,
    int decorationId,
    EquipmentType equipmentType,
  ) async {
    final existing =
        await (_db.select(_db.userSetDecoration)..where(
              (row) =>
                  row.userSetId.equals(setId) &
                  row.decorationId.equals(decorationId) &
                  row.equipmentType.equals(equipmentType.dbValue),
            ))
            .getSingleOrNull();

    if (existing == null) {
      await _db
          .into(_db.userSetDecoration)
          .insert(
            UserSetDecorationCompanion.insert(
              userSetId: setId,
              decorationId: decorationId,
              equipmentType: equipmentType.dbValue,
              quantity: 1,
            ),
          );
    } else {
      await (_db.update(_db.userSetDecoration)..where(
            (row) =>
                row.userSetId.equals(setId) &
                row.decorationId.equals(decorationId) &
                row.equipmentType.equals(equipmentType.dbValue),
          ))
          .write(
            UserSetDecorationCompanion(quantity: Value(existing.quantity + 1)),
          );
    }
  }

  Future<void> removeDecoration(
    int setId,
    int decorationId,
    EquipmentType equipmentType,
  ) async {
    final existing =
        await (_db.select(_db.userSetDecoration)..where(
              (row) =>
                  row.userSetId.equals(setId) &
                  row.decorationId.equals(decorationId) &
                  row.equipmentType.equals(equipmentType.dbValue),
            ))
            .getSingleOrNull();
    if (existing == null) return;

    if (existing.quantity > 1) {
      await (_db.update(_db.userSetDecoration)..where(
            (row) =>
                row.userSetId.equals(setId) &
                row.decorationId.equals(decorationId) &
                row.equipmentType.equals(equipmentType.dbValue),
          ))
          .write(
            UserSetDecorationCompanion(quantity: Value(existing.quantity - 1)),
          );
    } else {
      await (_db.delete(_db.userSetDecoration)..where(
            (row) =>
                row.userSetId.equals(setId) &
                row.decorationId.equals(decorationId) &
                row.equipmentType.equals(equipmentType.dbValue),
          ))
          .go();
    }
  }

  Future<Weapon?> _getWeapon(int? weaponId, String language) async {
    if (weaponId == null) return null;

    final args = SqlArgs();
    final row = await _db.customSelect(
      '''
          SELECT weapon.*, weapon_text.*
          FROM weapon
          JOIN weapon_text
            ON weapon.id = weapon_text.weapon_id
            AND weapon_text.language = ${args.text(language)}
          WHERE weapon.id = ${args.integer(weaponId)}
          ''',
      variables: args.variables,
    ).getSingleOrNull();
    if (row == null) return null;

    final element1 = row.data['element_1'] as String?;
    final element2 = row.data['element_2'] as String?;
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
      buildable: (row.data['buildable'] as int) != 0,
    );
  }

  Future<List<Armor>> _getArmors(int setId, String language) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT armor.*, armor_text.*
          FROM user_set_armor
          JOIN armor ON user_set_armor.armor_id = armor.id
          JOIN armor_text
            ON armor.id = armor_text.armor_id
            AND armor_text.language = ${args.text(language)}
          WHERE user_set_armor.user_set_id = ${args.integer(setId)}
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
          ),
        )
        .toList();
  }

  Future<List<EquipmentDecoration>> _getDecorations(
    int setId,
    String language,
  ) async {
    final args = SqlArgs();
    final rows = await _db.customSelect(
      '''
          SELECT
            usd.equipment_type AS usd_equipment_type, usd.quantity AS usd_quantity,
            decoration.id AS dec_id, decoration.required_slots AS dec_required_slots,
            item.*, item_text.*
          FROM user_set_decoration usd
          JOIN decoration ON usd.decoration_id = decoration.id
          JOIN item ON decoration.id = item.id
          JOIN item_text
            ON item.id = item_text.item_id
            AND item_text.language = ${args.text(language)}
          WHERE usd.user_set_id = ${args.integer(setId)}
          ''',
      variables: args.variables,
    ).get();

    return rows
        .map(
          (row) => EquipmentDecoration(
            equipmentType: EquipmentType.fromDb(
              row.read<String>('usd_equipment_type'),
            ),
            decoration: Decoration(
              id: row.read<int>('dec_id'),
              name: row.data['name'] as String,
              description: row.data['description'] as String,
              rarity: row.data['rarity'] as int,
              buyPrice: (row.data['buy_price'] as int?) ?? 0,
              sellPrice: row.data['sell_price'] as int,
              requiredSlots: row.read<int>('dec_required_slots'),
              color: ItemIconColor.fromDb(row.data['icon_color'] as String),
            ),
            quantity: row.read<int>('usd_quantity'),
          ),
        )
        .toList();
  }

  Future<List<SkillPoint>> _getSkillPoints(int setId, String language) async {
    var armorSkills = await _skillPointRows(
      '''
      SELECT user_set_armor.user_set_id AS equipmentId, skill_tree.*, skill_tree_text.*,
        SUM(armor_skill.point_value) AS points
      FROM user_set_armor
      JOIN armor_skill ON user_set_armor.armor_id = armor_skill.armor_id
      JOIN skill_tree ON armor_skill.skill_tree_id = skill_tree.id
      JOIN skill_tree_text
        ON skill_tree.id = skill_tree_text.skill_tree_id
        AND skill_tree_text.language = ?
      WHERE user_set_armor.user_set_id = ?
      GROUP BY skill_tree.id
      ''',
      [Variable<String>(language), Variable<int>(setId)],
    );
    var decorationSkills = await _skillPointRows(
      '''
      SELECT user_set_decoration.user_set_id AS equipmentId, skill_tree.*, skill_tree_text.*,
        SUM(decoration_skill.point_value * user_set_decoration.quantity) AS points
      FROM user_set_decoration
      JOIN decoration_skill
        ON user_set_decoration.decoration_id = decoration_skill.decoration_id
      JOIN skill_tree ON decoration_skill.skill_tree_id = skill_tree.id
      JOIN skill_tree_text
        ON skill_tree.id = skill_tree_text.skill_tree_id
        AND skill_tree_text.language = ?
      WHERE user_set_decoration.user_set_id = ?
      GROUP BY skill_tree.id
      ''',
      [Variable<String>(language), Variable<int>(setId)],
    );

    // Torso Up (skill tree id 1) duplicates the chest piece's own skill
    // contribution once per point already accumulated from it.
    final torsoMultiplier = armorSkills
        .firstWhere(
          (point) => point.skillTree.id == 1,
          orElse: () => const SkillPoint(
            skillTree: SkillTree(
              id: 1,
              name: '',
              category: SkillCategory.combat,
            ),
            points: 0,
          ),
        )
        .points;

    if (torsoMultiplier > 0) {
      final torsoArmorSkills = await _skillPointRows(
        '''
        SELECT armor_skill.armor_id AS equipmentId, skill_tree.*, skill_tree_text.*,
          armor_skill.point_value AS points
        FROM user_set_armor
        JOIN armor ON user_set_armor.armor_id = armor.id
        JOIN armor_skill ON armor.id = armor_skill.armor_id
        JOIN skill_tree ON armor_skill.skill_tree_id = skill_tree.id
        JOIN skill_tree_text
          ON skill_tree.id = skill_tree_text.skill_tree_id
          AND skill_tree_text.language = ?
        WHERE user_set_armor.user_set_id = ? AND armor.armor_type = 'CHEST'
        ''',
        [Variable<String>(language), Variable<int>(setId)],
      );
      final torsoDecorationSkills = await _skillPointRows(
        '''
        SELECT decoration_skill.decoration_id AS equipmentId, skill_tree.*, skill_tree_text.*,
          decoration_skill.point_value AS points
        FROM user_set_decoration
        JOIN decoration_skill
          ON user_set_decoration.decoration_id = decoration_skill.decoration_id
        JOIN skill_tree ON decoration_skill.skill_tree_id = skill_tree.id
        JOIN skill_tree_text
          ON skill_tree.id = skill_tree_text.skill_tree_id
          AND skill_tree_text.language = ?
        WHERE user_set_decoration.user_set_id = ? AND user_set_decoration.equipment_type = 'CHEST'
        ''',
        [Variable<String>(language), Variable<int>(setId)],
      );

      armorSkills = [
        ...armorSkills,
        ...torsoArmorSkills.map(
          (point) => SkillPoint(
            skillTree: point.skillTree,
            points: point.points * torsoMultiplier,
          ),
        ),
      ];
      decorationSkills = [
        ...decorationSkills,
        ...torsoDecorationSkills.map(
          (point) => SkillPoint(
            skillTree: point.skillTree,
            points: point.points * torsoMultiplier,
          ),
        ),
      ];
    }

    final grouped = <int, SkillPoint>{};
    for (final point in [...armorSkills, ...decorationSkills]) {
      final existing = grouped[point.skillTree.id];
      grouped[point.skillTree.id] = SkillPoint(
        skillTree: point.skillTree,
        points: (existing?.points ?? 0) + point.points,
      );
    }

    final result = grouped.values.toList()
      ..sort((a, b) => b.points.compareTo(a.points));
    return result;
  }

  Future<List<SkillPoint>> _skillPointRows(
    String sql,
    List<Variable> variables,
  ) async {
    final rows = await _db.customSelect(sql, variables: variables).get();
    return rows
        .map(
          (row) => SkillPoint(
            skillTree: SkillTree(
              id: row.data['id'] as int,
              name: row.data['name'] as String,
              category: SkillCategory.fromDb(row.data['category'] as String),
            ),
            points: row.read<int>('points'),
          ),
        )
        .toList();
  }

  Future<List<Skill>> _getActiveSkills(
    List<SkillPoint> skillPoints,
    String language,
  ) async {
    final activeSkills = <Skill>[];
    for (final point in skillPoints) {
      if (point.points.abs() < 10) continue;

      final args = SqlArgs();
      final row = await _db.customSelect(
        '''
            SELECT skill.*, skill_text.*
            FROM skill
            JOIN skill_text
              ON skill.id = skill_text.skill_id
              AND skill_text.language = ${args.text(language)}
            WHERE
              skill.skill_tree_id = ${args.integer(point.skillTree.id)}
              AND (
                (${args.integer(point.points)} >= 10 AND skill.required_points > 0 AND skill.required_points <= ${args.integer(point.points)})
                OR (${args.integer(point.points)} <= -10 AND skill.required_points < 0 AND skill.required_points >= ${args.integer(point.points)})
              )
            ORDER BY ABS(skill.required_points) DESC
            LIMIT 1
            ''',
        variables: args.variables,
      ).getSingleOrNull();
      if (row == null) continue;

      activeSkills.add(
        Skill(
          id: row.data['id'] as int,
          skillTreeId: row.data['skill_tree_id'] as int,
          name: row.data['name'] as String,
          description: row.data['description'] as String,
          requiredPoints: row.data['required_points'] as int,
        ),
      );
    }
    return activeSkills;
  }

  Future<List<ItemQuantity>> _getRecipe(int setId, String language) async {
    var weaponMaterials = await _itemQuantityRows(
      '''
      SELECT item.*, item_text.*, SUM(weapon_recipe.quantity) AS quantity
      FROM user_set
      JOIN weapon_recipe
        ON user_set.weapon_id = weapon_recipe.weapon_id
        AND weapon_recipe.recipe_type = 'CREATE'
        AND weapon_recipe.recipe_variant = 1
      JOIN item ON weapon_recipe.item_id = item.id
      JOIN item_text
        ON item.id = item_text.item_id
        AND item_text.language = ?
      WHERE user_set.id = ?
      GROUP BY item.id
      ''',
      [Variable<String>(language), Variable<int>(setId)],
    );
    if (weaponMaterials.isEmpty) {
      weaponMaterials = await _itemQuantityRows(
        '''
        SELECT item.*, item_text.*, SUM(weapon_recipe.quantity) AS quantity
        FROM user_set
        JOIN weapon_recipe
          ON user_set.weapon_id = weapon_recipe.weapon_id
          AND weapon_recipe.recipe_type = 'UPGRADE'
          AND weapon_recipe.recipe_variant = 1
        JOIN item ON weapon_recipe.item_id = item.id
        JOIN item_text
          ON item.id = item_text.item_id
          AND item_text.language = ?
        WHERE user_set.id = ?
        GROUP BY item.id
        ''',
        [Variable<String>(language), Variable<int>(setId)],
      );
    }

    final armorMaterials = await _itemQuantityRows(
      '''
      SELECT item.*, item_text.*, SUM(armor_recipe.quantity) AS quantity
      FROM user_set_armor
      JOIN armor_recipe
        ON user_set_armor.armor_id = armor_recipe.armor_id
        AND armor_recipe.recipe_variant = 1
      JOIN item ON armor_recipe.item_id = item.id
      JOIN item_text
        ON item.id = item_text.item_id
        AND item_text.language = ?
      WHERE user_set_armor.user_set_id = ?
      GROUP BY item.id
      ''',
      [Variable<String>(language), Variable<int>(setId)],
    );

    final decorationMaterials = await _itemQuantityRows(
      '''
      SELECT item.*, item_text.*,
        SUM(decoration_recipe.quantity * user_set_decoration.quantity) AS quantity
      FROM user_set_decoration
      JOIN decoration_recipe
        ON user_set_decoration.decoration_id = decoration_recipe.decoration_id
        AND decoration_recipe.recipe_variant = 1
      JOIN item ON decoration_recipe.item_id = item.id
      JOIN item_text
        ON item.id = item_text.item_id
        AND item_text.language = ?
      WHERE user_set_decoration.user_set_id = ?
      GROUP BY item.id
      ''',
      [Variable<String>(language), Variable<int>(setId)],
    );

    final grouped = <int, ItemQuantity>{};
    for (final entry in [
      ...weaponMaterials,
      ...armorMaterials,
      ...decorationMaterials,
    ]) {
      final existing = grouped[entry.item.id];
      grouped[entry.item.id] = ItemQuantity(
        item: entry.item,
        quantity: (existing?.quantity ?? 0) + entry.quantity,
      );
    }

    final result = grouped.values.toList()
      ..sort((a, b) => b.quantity.compareTo(a.quantity));
    return result;
  }

  Future<List<ItemQuantity>> _itemQuantityRows(
    String sql,
    List<Variable> variables,
  ) async {
    final rows = await _db.customSelect(sql, variables: variables).get();
    return rows
        .map(
          (row) => ItemQuantity(
            item: Item(
              id: row.data['id'] as int,
              name: row.data['name'] as String,
              description: row.data['description'] as String,
              rarity: row.data['rarity'] as int,
              buyPrice: row.data['buy_price'] as int?,
              sellPrice: row.data['sell_price'] as int,
              carryMax: row.data['carry_max'] as int,
              iconType: ItemIconType.fromDb(row.data['icon_type'] as String),
              iconColor: ItemIconColor.fromDb(
                row.data['icon_color'] as String,
              ),
            ),
            quantity: row.read<int>('quantity'),
          ),
        )
        .toList();
  }
}
