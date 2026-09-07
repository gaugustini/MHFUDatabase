import 'dart:io';

import 'package:drift/native.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mhfudatabase/core/database/app_database.dart' show AppDatabase;
import 'package:mhfudatabase/core/database/localized_collation.dart';
import 'package:mhfudatabase/core/domain/enums.dart';
import 'package:mhfudatabase/features/monster/data/monster_repository.dart';
import 'package:mhfudatabase/features/monster/domain/monster_filter.dart';

void main() {
  late AppDatabase database;
  late MonsterRepository repository;

  setUpAll(() {
    database = AppDatabase(
      NativeDatabase(
        File('assets/database/data.db'),
        setup: (db) {
          db.createCollation(name: 'LOCALIZED', function: compareLocalized);
        },
      ),
    );
    repository = MonsterRepository(database);
  });

  tearDownAll(() => database.close());

  test(
    'getMonster returns damage stats, ailments, item effectiveness, rewards, and quests',
    () async {
      final monster = await repository.getMonster(2, 'en');

      expect(monster.id, 2);
      expect(monster.name, isNotEmpty);
      expect(monster.damageStats, isNotEmpty);
      expect(monster.rewards, isNotNull);
      expect(monster.rewards!.length, greaterThan(1));
    },
  );

  test('getMonster returns ailment stats and item effectiveness', () async {
    final monster = await repository.getMonster(4, 'en');

    expect(monster.ailmentStats, isNotEmpty);
    expect(monster.itemEffectiveness, isNotNull);
  });

  test('getMonster returns the quests it appears in', () async {
    final monster = await repository.getMonster(17, 'en');

    expect(monster.quests, isNotEmpty);
  });

  test('getMonsterList returns monsters without a filter', () async {
    final monsters = await repository.getMonsterList('en');
    expect(monsters, isNotEmpty);
  });

  test('getMonsterList filters by name', () async {
    final all = await repository.getMonsterList('en');
    final target = all.first;

    final filtered = await repository.getMonsterList(
      'en',
      filter: MonsterFilter(name: target.name),
    );

    expect(filtered, isNotEmpty);
    expect(filtered.map((monster) => monster.id), contains(target.id));
  });

  test('getMonsterList filters by type', () async {
    final filtered = await repository.getMonsterList(
      'en',
      filter: const MonsterFilter(type: MonsterType.large),
    );

    expect(filtered, isNotEmpty);
    expect(
      filtered.every((monster) => monster.type == MonsterType.large),
      isTrue,
    );
  });
}
