import 'dart:io';

import 'package:drift/native.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mhfudatabase/core/database/app_database.dart' show AppDatabase;
import 'package:mhfudatabase/core/database/localized_collation.dart';
import 'package:mhfudatabase/core/domain/enums.dart';
import 'package:mhfudatabase/features/armor/data/armor_repository.dart';
import 'package:mhfudatabase/features/armor/domain/armor_filter.dart';
import 'package:mhfudatabase/features/skill/domain/skill.dart' show SkillTree;

void main() {
  late AppDatabase database;
  late ArmorRepository repository;

  setUpAll(() {
    database = AppDatabase(
      NativeDatabase(
        File('assets/database/data.db'),
        setup: (db) {
          db.createCollation(name: 'LOCALIZED', function: compareLocalized);
        },
      ),
    );
    repository = ArmorRepository(database);
  });

  tearDownAll(() => database.close());

  test('getArmor returns the armor with its skills and recipe', () async {
    final armor = await repository.getArmor(1, 'en');

    expect(armor.id, 1);
    expect(armor.name, isNotEmpty);
    expect(armor.skills, isNotEmpty);
    expect(armor.skills!.first.skillTree.id, isNonZero);
    expect(armor.recipes, isNotEmpty);
  });

  test('getArmorSet returns the set with its armors and totals', () async {
    final armorSet = await repository.getArmorSet(1, 'en');

    expect(armorSet.id, 1);
    expect(armorSet.armors, isNotEmpty);
    expect(armorSet.defense, greaterThan(0));
    expect(
      armorSet.defense,
      armorSet.armors!.fold<int>(0, (sum, armor) => sum + armor.defense),
    );
  });

  test('getArmorList returns armors without a filter', () async {
    final armors = await repository.getArmorList('en');
    expect(armors, isNotEmpty);
  });

  test('getArmorList filters by name', () async {
    final all = await repository.getArmorList('en');
    final target = all.first;

    final filtered = await repository.getArmorList(
      'en',
      filter: ArmorFilter(name: target.name),
    );

    expect(filtered, isNotEmpty);
    expect(filtered.map((armor) => armor.id), contains(target.id));
  });

  test('getArmorList filters by equipment type', () async {
    final filtered = await repository.getArmorList(
      'en',
      filter: const ArmorFilter(type: EquipmentType.armorHead),
    );

    expect(filtered, isNotEmpty);
    expect(
      filtered.every((armor) => armor.type == EquipmentType.armorHead),
      isTrue,
    );
  });

  test('getArmorList filters by skill', () async {
    final skillTree = const SkillTree(
      id: 37,
      name: '',
      category: SkillCategory.combat,
    );

    final filtered = await repository.getArmorList(
      'en',
      filter: ArmorFilter(skills: [skillTree]),
    );

    expect(filtered, isNotEmpty);
    for (final armor in filtered) {
      final refetched = await repository.getArmor(armor.id, 'en');
      expect(
        refetched.skills!.any((point) => point.skillTree.id == 37),
        isTrue,
      );
    }
  });

  test('getArmorSetList returns sets without a filter', () async {
    final sets = await repository.getArmorSetList('en');
    expect(sets, isNotEmpty);
  });

  test('getArmorSetList filters by rarity', () async {
    final all = await repository.getArmorSetList('en');
    final rarity = all.first.rarity;

    final filtered = await repository.getArmorSetList(
      'en',
      filter: ArmorSetFilter(rarity: [rarity]),
    );

    expect(filtered, isNotEmpty);
    expect(filtered.every((set) => set.rarity == rarity), isTrue);
  });
}
