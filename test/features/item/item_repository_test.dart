import 'dart:io';

import 'package:drift/native.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mhfudatabase/core/database/app_database.dart' show AppDatabase;
import 'package:mhfudatabase/core/database/localized_collation.dart';
import 'package:mhfudatabase/features/item/data/item_repository.dart';
import 'package:mhfudatabase/features/item/domain/item_filter.dart';

void main() {
  late AppDatabase database;
  late ItemRepository repository;

  setUpAll(() {
    database = AppDatabase(
      NativeDatabase(
        File('assets/database/data.db'),
        setup: (db) {
          db.createCollation(name: 'LOCALIZED', function: compareLocalized);
        },
      ),
    );
    repository = ItemRepository(database);
  });

  tearDownAll(() => database.close());

  test('getItemList returns items without a filter', () async {
    final items = await repository.getItemList('en');
    expect(items, isNotEmpty);
  });

  test('getItemList filters by name', () async {
    final all = await repository.getItemList('en');
    final target = all.first;

    final filtered = await repository.getItemList(
      'en',
      filter: ItemFilter(name: target.name),
    );

    expect(filtered.map((item) => item.id), contains(target.id));
  });

  test('getItem resolves gathering, monster, and quest sources', () async {
    final gathered = await repository.getItem(254, 'en');
    expect(gathered.sources!.locations, isNotEmpty);

    final rewarded = await repository.getItem(54, 'en');
    expect(rewarded.sources!.monsterRewards, isNotEmpty);

    final questReward = await repository.getItem(160, 'en');
    expect(questReward.sources!.questRewards, isNotEmpty);
  });

  test('getItem resolves combination and veggie sources/usages', () async {
    final combinationResult = await repository.getItem(7, 'en');
    expect(combinationResult.sources!.combinations, isNotEmpty);
    expect(combinationResult.sources!.veggieTrades, isNotEmpty);
  });

  test('getItem resolves crafting usages', () async {
    final armorMaterial = await repository.getItem(329, 'en');
    expect(armorMaterial.usages!.armors, isNotEmpty);

    final weaponMaterial = await repository.getItem(201, 'en');
    expect(weaponMaterial.usages!.weapons, isNotEmpty);

    final decorationMaterial = await repository.getItem(356, 'en');
    expect(decorationMaterial.usages!.decorations, isNotEmpty);
  });
}
