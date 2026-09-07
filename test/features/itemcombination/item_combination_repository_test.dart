import 'dart:io';

import 'package:drift/native.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mhfudatabase/core/database/app_database.dart' show AppDatabase;
import 'package:mhfudatabase/core/database/localized_collation.dart';
import 'package:mhfudatabase/features/item/data/item_repository.dart';
import 'package:mhfudatabase/features/itemcombination/data/item_combination_repository.dart';

void main() {
  late AppDatabase database;
  late ItemCombinationRepository repository;

  setUpAll(() {
    database = AppDatabase(
      NativeDatabase(
        File('assets/database/data.db'),
        setup: (db) {
          db.createCollation(name: 'LOCALIZED', function: compareLocalized);
        },
      ),
    );
    repository = ItemCombinationRepository(ItemRepository(database));
  });

  tearDownAll(() => database.close());

  test('getItemCombinationList returns real combinations', () async {
    final combinations = await repository.getItemCombinationList('en');

    expect(combinations, isNotEmpty);
    final first = combinations.first;
    expect(first.itemCreated.name, isNotEmpty);
    expect(first.itemA.name, isNotEmpty);
    expect(first.itemB.name, isNotEmpty);
  });
}
