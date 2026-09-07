import 'dart:io';

import 'package:flutter_test/flutter_test.dart';
import 'package:mhfudatabase/core/database/database_migration.dart';
import 'package:path/path.dart' as p;
import 'package:sqlite3/sqlite3.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  late Directory tempDir;
  late File file;

  setUp(() async {
    tempDir = await Directory.systemTemp.createTemp('database_migration_test');
    file = File(p.join(tempDir.path, 'data.db'));

    final old = sqlite3.open(file.path);
    old.userVersion = 10;
    old.execute('''
      CREATE TABLE user_set (
        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
        name TEXT NOT NULL,
        hunter_type TEXT NOT NULL,
        gender TEXT NOT NULL,
        weapon_id INTEGER
      );
      CREATE TABLE user_set_armor (
        user_set_id INTEGER NOT NULL,
        armor_id INTEGER NOT NULL,
        PRIMARY KEY (user_set_id, armor_id)
      );
      INSERT INTO user_set (id, name, hunter_type, gender, weapon_id)
        VALUES (1, 'My Hunter', 'BLADEMASTER', 'MALE', NULL);
      INSERT INTO user_set_armor (user_set_id, armor_id) VALUES (1, 5);
    ''');
    old.close();
  });

  tearDown(() => tempDir.delete(recursive: true));

  test('leaves an already-current database untouched', () async {
    final db = sqlite3.open(file.path);
    db.userVersion = expectedSchemaVersion;
    db.execute('DELETE FROM user_set_armor; DELETE FROM user_set;');
    db.close();

    await migrateOutdatedDatabaseIfNeeded(file);

    final result = sqlite3.open(file.path);
    expect(result.select('SELECT * FROM user_set'), isEmpty);
    result.close();
  });

  test('replaces an outdated database while keeping user sets', () async {
    await migrateOutdatedDatabaseIfNeeded(file);

    final result = sqlite3.open(file.path);
    expect(result.userVersion, expectedSchemaVersion);
    expect(result.select('SELECT * FROM armor'), isNotEmpty);

    final userSets = result.select('SELECT * FROM user_set');
    expect(userSets, hasLength(1));
    expect(userSets.first['name'], 'My Hunter');

    final userSetArmor = result.select('SELECT * FROM user_set_armor');
    expect(userSetArmor, hasLength(1));
    expect(userSetArmor.first['armor_id'], 5);

    result.close();
  });

  test('migrates a database from before user sets existed', () async {
    file.deleteSync();
    final old = sqlite3.open(file.path);
    old.userVersion = 5;
    old.close();

    await migrateOutdatedDatabaseIfNeeded(file);

    final result = sqlite3.open(file.path);
    expect(result.userVersion, expectedSchemaVersion);
    expect(result.select('SELECT * FROM armor'), isNotEmpty);
    expect(result.select('SELECT * FROM user_set'), isEmpty);
    result.close();
  });
}
