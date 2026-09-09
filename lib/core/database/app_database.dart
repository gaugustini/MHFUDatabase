import 'package:drift/drift.dart';
import 'package:drift_flutter/drift_flutter.dart';
import 'package:flutter/services.dart' show rootBundle;

import 'database_migration.dart'
    if (dart.library.js_interop) 'database_migration_web.dart';
import 'localized_collation.dart';

part 'app_database.g.dart';

@DriftDatabase(include: {'tables.drift'})
class AppDatabase extends _$AppDatabase {
  AppDatabase([QueryExecutor? executor]) : super(executor ?? _openConnection());

  static final instance = AppDatabase();

  // Kept as a literal instead of referencing `expectedSchemaVersion` in
  // database_migration.dart, since drift's code generator can't read a
  // value through an import; the two must stay equal.
  @override
  int get schemaVersion => 18;

  @override
  MigrationStrategy get migration => MigrationStrategy(
    onUpgrade: (migrator, from, to) async {
      if (from < 18) {
        for (final entry in _v18ItemFixes.entries) {
          await (update(item)
            ..where((row) => row.id.equals(entry.key))).write(entry.value);
        }
      }
    },
    beforeOpen: (details) async {
      await customStatement('PRAGMA foreign_keys = ON');
    },
  );
}

const _carryMax1 = ItemCompanion(carryMax: Value(1));
const _carryMax5 = ItemCompanion(carryMax: Value(5));
const _carryMax8 = ItemCompanion(carryMax: Value(8));
const _carryMax10 = ItemCompanion(carryMax: Value(10));
const _carryMax20 = ItemCompanion(carryMax: Value(20));
const _carryMax50 = ItemCompanion(carryMax: Value(50));
const _carryMax99 = ItemCompanion(carryMax: Value(99));
const _carryMaxUnlimited = ItemCompanion(carryMax: Value(-1));
const _rarity2 = ItemCompanion(rarity: Value(2));
const _rarity4 = ItemCompanion(rarity: Value(4));
const _rarity5 = ItemCompanion(rarity: Value(5));
const _rarity7 = ItemCompanion(rarity: Value(7));
const _iconTypeMonster = ItemCompanion(iconType: Value('MONSTER'));
const _carryMax10IconColorSky = ItemCompanion(
  carryMax: Value(10),
  iconColor: Value('SKY'),
);

final _v18ItemFixes = <int, ItemCompanion>{
  32: _carryMax1,
  65: _carryMax5,
  85: _rarity4,
  96: _carryMaxUnlimited,
  121: _carryMax8,
  123: _carryMax8,
  128: const ItemCompanion(iconColor: Value('WHITE')),
  150: _rarity4,
  164: _rarity2,
  169: _rarity2,
  211: _carryMax10,
  212: _carryMax10,
  213: _carryMax10,
  218: _carryMax20,
  237: _rarity5,
  262: const ItemCompanion(iconColor: Value('PINK')),
  322: _iconTypeMonster,
  326: _carryMax99,
  334: _rarity4,
  340: _carryMax99,
  341: _carryMax50,
  350: _carryMax50,
  355: _iconTypeMonster,
  359: const ItemCompanion(rarity: Value(3)),
  446: const ItemCompanion(iconColor: Value('YELLOW')),
  459: const ItemCompanion(iconType: Value('PELT')),
  502: _rarity4,
  924: _carryMax1,
  925: _carryMax1,
  1117: const ItemCompanion(rarity: Value(8)),
  1120: _rarity7,
  1121: _rarity7,
  1129: const ItemCompanion(rarity: Value(6)),
  1136: _rarity5,
  for (var id = 1137; id <= 1156; id++) id: _carryMax10,
  1157: const ItemCompanion(carryMax: Value(10), iconType: Value('MONSTER')),
  1158: _carryMax20,
  1159: _carryMax20,
  1160: _carryMax20,
  1161: _carryMax10IconColorSky,
  for (var id = 1162; id <= 1167; id++) id: _carryMax10,
  1168: _carryMax10IconColorSky,
  1169: _carryMax10IconColorSky,
  for (var id = 1170; id <= 1181; id++) id: _carryMax10,
  for (var id = 1182; id <= 1184; id++) id: _carryMax20,
  1185: const ItemCompanion(carryMax: Value(20), iconColor: Value('PURPLE')),
  1186: _carryMax20,
  1188: _carryMax20,
  1189: _carryMax20,
  1190: const ItemCompanion(carryMax: Value(20), iconColor: Value('ORANGE')),
  for (var id = 1191; id <= 1212; id++) id: _carryMax20,
  1213: const ItemCompanion(rarity: Value(5), carryMax: Value(20)),
  for (var id = 1214; id <= 1221; id++) id: _carryMax20,
  1222: _carryMax5,
  1223: _carryMax5,
  1224: _carryMax5,
  for (var id = 1225; id <= 1244; id++) id: _carryMax20,
  for (var id = 1246; id <= 1256; id++) id: _carryMax20,
  1257: _carryMax1,
};

QueryExecutor _openConnection() {
  return driftDatabase(
    name: 'data',
    native: DriftNativeOptions(
      databasePath: () async {
        final file = await resolveDatabaseFile();
        return file.path;
      },
      setup: (database) {
        database.createCollation(
          name: 'LOCALIZED',
          function: compareLocalized,
        );
      },
    ),
    // There is no way to register a custom SQL collation for the web
    // backend, so accented names sort in raw byte order there instead of
    // next to their base letter.
    web: DriftWebOptions(
      sqlite3Wasm: Uri.parse('sqlite3.wasm'),
      driftWorker: Uri.parse('drift_worker.js'),
      initializeDatabase: () async {
        final blob = await rootBundle.load('assets/database/data.db');
        return blob.buffer.asUint8List(blob.offsetInBytes, blob.lengthInBytes);
      },
    ),
  );
}
