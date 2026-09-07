import 'dart:io';

import 'package:drift/native.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mhfudatabase/core/database/app_database.dart' show AppDatabase;
import 'package:mhfudatabase/core/database/localized_collation.dart';
import 'package:mhfudatabase/features/search/data/search_repository.dart';

void main() {
  late AppDatabase database;
  late SearchRepository repository;

  setUpAll(() {
    database = AppDatabase(
      NativeDatabase(
        File('assets/database/data.db'),
        setup: (db) {
          db.createCollation(name: 'LOCALIZED', function: compareLocalized);
        },
      ),
    );
    repository = SearchRepository(database);
  });

  tearDownAll(() => database.close());

  test('finds armors and weapons matching a query', () async {
    final results = await repository.search('rathalos', 'en');

    expect(results.isEmpty, isFalse);
    expect(results.armors, isNotEmpty);
    expect(results.weapons, isNotEmpty);
  });

  test('finds results without accents typed', () async {
    final results = await repository.search('pocion', 'es');
    expect(results.items, isNotEmpty);
  });

  test('returns empty results for a nonsense query', () async {
    final results = await repository.search('zzzznonexistentzzzz', 'en');
    expect(results.isEmpty, isTrue);
  });
}
