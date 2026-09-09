import 'dart:io';

import 'package:drift/native.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mhfudatabase/core/database/app_database.dart' show AppDatabase;
import 'package:mhfudatabase/core/database/localized_collation.dart';
import 'package:mhfudatabase/features/veggie/data/veggie_repository.dart';

void main() {
  late AppDatabase database;
  late VeggieRepository repository;

  setUpAll(() {
    database = AppDatabase(
      NativeDatabase(
        File('assets/database/data.db'),
        setup: (db) {
          db.createCollation(name: 'LOCALIZED', function: compareLocalized);
        },
      ),
    );
    repository = VeggieRepository(database);
  });

  tearDownAll(() => database.close());

  test('getVeggieLocationList returns veggie locations', () async {
    final veggieLocations = await repository.getVeggieLocationList('en');
    expect(veggieLocations, isNotEmpty);
  });

  test('getVeggieLocation returns its trades', () async {
    final veggieLocation = await repository.getVeggieLocation(1, 'en');

    expect(veggieLocation.id, 1);
    expect(veggieLocation.location.name, isNotEmpty);
    expect(veggieLocation.locationArea, 7);
    expect(veggieLocation.trades, isNotEmpty);
    expect(veggieLocation.trades!.first.itemTraded.name, isNotEmpty);
    expect(veggieLocation.trades!.first.itemCommon.name, isNotEmpty);
    expect(veggieLocation.trades!.first.itemRare.name, isNotEmpty);
  });
}
