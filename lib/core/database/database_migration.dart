import 'dart:io';

import 'package:flutter/services.dart' show rootBundle;
import 'package:path/path.dart' as p;
import 'package:path_provider/path_provider.dart';
import 'package:sqlite3/sqlite3.dart';

// Matches `AppDatabase.schemaVersion`.
const expectedSchemaVersion = 18;

const userGeneratedTables = [
  'user_set',
  'user_set_armor',
  'user_set_decoration',
];

/// Resolves the database file, seeding or migrating it as needed.
Future<File> resolveDatabaseFile() async {
  final directory = await _databaseDirectory();
  final file = File(p.join(directory.path, 'data.db'));

  if (await file.exists()) {
    await migrateOutdatedDatabaseIfNeeded(file);
  } else {
    await seedDatabaseFromAsset(file);
  }

  return file;
}

Future<Directory> _databaseDirectory() async {
  if (!Platform.isAndroid) {
    return getApplicationDocumentsDirectory();
  }

  final filesDir = await getApplicationSupportDirectory();
  final directory = Directory(p.join(filesDir.parent.path, 'databases'));
  return directory.create(recursive: true);
}

Future<void> seedDatabaseFromAsset(File file) async {
  final blob = await rootBundle.load('assets/database/data.db');
  final buffer = blob.buffer;
  await file.writeAsBytes(
    buffer.asUint8List(blob.offsetInBytes, blob.lengthInBytes),
  );
}

/// Replaces a database below `expectedSchemaVersion` with the bundled one,
/// carrying over `userGeneratedTables`' rows, since those are the only data
/// the app itself generates rather than ships. Safe for any older schema,
/// since only the reference data changes between versions — these tables'
/// own shape never has.
Future<void> migrateOutdatedDatabaseIfNeeded(File file) async {
  final existing = sqlite3.open(file.path);
  final isCurrent = existing.userVersion >= expectedSchemaVersion;
  if (isCurrent) {
    existing.close();
    return;
  }

  final preservedRows = <String, List<Map<String, Object?>>>{
    for (final table in userGeneratedTables)
      if (_tableExists(existing, table))
        table: existing
            .select('SELECT * FROM $table')
            .map(Map<String, Object?>.from)
            .toList(),
  };
  existing.close();

  await seedDatabaseFromAsset(file);

  final upgraded = sqlite3.open(file.path);
  for (final entry in preservedRows.entries) {
    if (entry.value.isEmpty) continue;

    final columns = entry.value.first.keys.toList();
    final statement = upgraded.prepare(
      'INSERT INTO ${entry.key} (${columns.join(', ')}) '
      'VALUES (${columns.map((_) => '?').join(', ')})',
    );
    for (final row in entry.value) {
      statement.execute(columns.map((column) => row[column]).toList());
    }
    statement.close();
  }
  upgraded.close();
}

bool _tableExists(Database database, String name) {
  return database.select(
    "SELECT 1 FROM sqlite_master WHERE type = 'table' AND name = ?",
    [name],
  ).isNotEmpty;
}
