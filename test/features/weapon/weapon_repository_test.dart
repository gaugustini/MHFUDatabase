import 'dart:io';

import 'package:drift/native.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mhfudatabase/core/database/app_database.dart' show AppDatabase;
import 'package:mhfudatabase/core/database/localized_collation.dart';
import 'package:mhfudatabase/core/domain/enums.dart';
import 'package:mhfudatabase/features/weapon/data/weapon_repository.dart';
import 'package:mhfudatabase/features/weapon/domain/weapon_filter.dart';

void main() {
  late AppDatabase database;
  late WeaponRepository repository;

  setUpAll(() {
    database = AppDatabase(
      NativeDatabase(
        File('assets/database/data.db'),
        setup: (db) {
          db.createCollation(name: 'LOCALIZED', function: compareLocalized);
        },
      ),
    );
    repository = WeaponRepository(database);
  });

  tearDownAll(() => database.close());

  test('getWeapon returns a bowgun weapon with ammo and recipes', () async {
    final weapon = await repository.getWeapon(1149, 'en');

    expect(weapon.id, 1149);
    expect(weapon.name, isNotEmpty);
    expect(weapon.ammoBowgun, isNotNull);
    expect(weapon.ammoBow, isNull);
  });

  test('getWeapon returns a bow weapon with charges', () async {
    final weapon = await repository.getWeapon(1385, 'en');

    expect(weapon.ammoBow, isNotNull);
    expect(weapon.ammoBowgun, isNull);
    expect(weapon.ammoBow!.charge1Type, isNotNull);
  });

  test('getWeapon reports both elements on a dual-element weapon', () async {
    final weapon = await repository.getWeapon(486, 'en');

    expect(weapon.element1, WeaponElement.ice);
    expect(weapon.element2, WeaponElement.poison);
  });

  test('getWeapon resolves multi-level paths, upgrades and finals', () async {
    // Edelweiss Ice Blade+ (251) is 11 levels deep in the Great Sword tree.
    final weapon = await repository.getWeapon(251, 'en');

    expect(weapon.name, 'Edelweiss Ice Blade+');
    expect(weapon.paths, isNotEmpty);
    expect(weapon.paths!.first.length, greaterThan(5));
    expect(weapon.paths!.first.first.id, isNot(251));
    expect(weapon.paths!.first.last.id, 251);
  });

  test('getWeaponTree flattens the Great Sword / Long Sword family', () async {
    // 251 (Edelweiss Ice Blade+) is a Long Sword; requesting the tree by its
    // own type keeps its whole lineage, even where it crosses into shared
    // Great Sword nodes near the root.
    final nodes = await repository.getWeaponTree(WeaponType.longSword, 'en');

    expect(nodes, isNotEmpty);
    expect(nodes.every((node) => node.depth >= 0), isTrue);
    expect(nodes.first.depth, 0);

    final edelweiss = nodes.firstWhere((node) => node.weapon.id == 251);
    expect(edelweiss.depth, greaterThanOrEqualTo(5));
  });

  test(
    'getWeaponTree only keeps cross-type nodes that touch the requested type',
    () async {
      final greatSwordNodes = await repository.getWeaponTree(
        WeaponType.greatSword,
        'en',
      );

      expect(
        greatSwordNodes.where((node) => node.weapon.id == 251),
        isEmpty,
      );
    },
  );

  test('getWeaponList returns weapons without a filter', () async {
    final weapons = await repository.getWeaponList('en');
    expect(weapons, isNotEmpty);
  });

  test('getWeaponList filters by weapon type', () async {
    final weapons = await repository.getWeaponList(
      'en',
      filter: const WeaponFilter(weaponType: [WeaponType.bow]),
    );

    expect(weapons, isNotEmpty);
    expect(weapons.every((weapon) => weapon.type == WeaponType.bow), isTrue);
  });
}
