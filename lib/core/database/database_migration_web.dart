/// Stub matching `database_migration.dart`'s API on the web, where none of
/// it applies: `DriftWebOptions.initializeDatabase` seeds the database from
/// the same bundled asset directly, and there is no local file whose schema
/// version could need migrating.
Future<Never> resolveDatabaseFile() =>
    throw UnsupportedError('resolveDatabaseFile is native-only');
