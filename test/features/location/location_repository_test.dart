import 'dart:io';

import 'package:drift/native.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mhfudatabase/core/database/app_database.dart' show AppDatabase;
import 'package:mhfudatabase/core/database/localized_collation.dart';
import 'package:mhfudatabase/core/domain/enums.dart';
import 'package:mhfudatabase/features/location/data/location_repository.dart';

void main() {
  late AppDatabase database;
  late LocationRepository repository;

  setUpAll(() {
    database = AppDatabase(
      NativeDatabase(
        File('assets/database/data.db'),
        setup: (db) {
          db.createCollation(name: 'LOCALIZED', function: compareLocalized);
        },
      ),
    );
    repository = LocationRepository(database);
  });

  tearDownAll(() => database.close());

  test('getLocationList returns locations', () async {
    final locations = await repository.getLocationList('en');
    expect(locations, isNotEmpty);
  });

  test(
    'getLocation returns gathering points grouped by rank and its quests',
    () async {
      final location = await repository.getLocation(1, 'en');

      expect(location.id, 1);
      expect(location.name, isNotEmpty);
      expect(location.gatheringPoints, isNotNull);
      expect(location.gatheringPoints!.keys, contains(Rank.low));
      expect(location.gatheringPoints![Rank.low], isNotEmpty);
      expect(location.gatheringPoints![Rank.low]!.first.item.name, isNotEmpty);
      expect(location.quests, isNotEmpty);
    },
  );
}
