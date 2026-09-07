import 'dart:io';

import 'package:drift/native.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mhfudatabase/core/database/app_database.dart';
import 'package:mhfudatabase/core/database/localized_collation.dart';
import 'package:path/path.dart' as p;
import 'package:sqlite3/sqlite3.dart' as sqlite3;

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  late AppDatabase database;

  setUpAll(() {
    database = AppDatabase(
      NativeDatabase(
        File('assets/database/data.db'),
        setup: (db) {
          db.createCollation(name: 'LOCALIZED', function: compareLocalized);
        },
      ),
    );
  });

  tearDownAll(() => database.close());

  test('ships pre-populated at the current schema version', () async {
    final result = await database
        .customSelect('PRAGMA user_version')
        .getSingle();
    expect(result.data['user_version'], database.schemaVersion);
  });

  test('carries the original reference data', () async {
    expect(await database.select(database.armor).get(), isNotEmpty);
    expect(await database.select(database.weapon).get(), isNotEmpty);
    expect(await database.select(database.monster).get(), isNotEmpty);
    expect(await database.select(database.quest).get(), isNotEmpty);
    expect(await database.select(database.item).get(), isNotEmpty);
  });

  test('starts user-generated tables empty', () async {
    expect(await database.select(database.userSet).get(), isEmpty);
  });

  test('sorts accented names next to their base letter', () async {
    final query = database.customSelect(
      "SELECT name FROM armor_text WHERE language = 'es' "
      "AND name IN ('Malla de acero', 'Máscara de guardián', "
      "'Medias de doncella') ORDER BY name",
    );
    final names = (await query.get())
        .map((row) => row.data['name'] as String)
        .toList();
    expect(names, [
      'Malla de acero',
      'Máscara de guardián',
      'Medias de doncella',
    ]);
  });

  test('migrating from 17 to 18 fixes item values', () async {
    final tempDir = await Directory.systemTemp.createTemp('app_database_test');
    final file = File(p.join(tempDir.path, 'data.db'));
    await File('assets/database/data.db').copy(file.path);
    addTearDown(() => tempDir.delete(recursive: true));

    final old = sqlite3.sqlite3.open(file.path);
    old.userVersion = 17;
    old.execute("UPDATE item SET carry_max = 0 WHERE id = 96;");
    old.close();

    final migrated = AppDatabase(
      NativeDatabase(
        file,
        setup: (db) {
          db.createCollation(name: 'LOCALIZED', function: compareLocalized);
        },
      ),
    );
    addTearDown(migrated.close);

    final item = await (migrated.select(
      migrated.item,
    )..where((row) => row.id.equals(96))).getSingle();
    expect(item.carryMax, -1);

    final version = await migrated
        .customSelect('PRAGMA user_version')
        .getSingle();
    expect(version.data['user_version'], 18);
  });
}
