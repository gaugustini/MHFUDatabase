import 'dart:io';

import 'package:drift/native.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mhfudatabase/core/database/app_database.dart' show AppDatabase;
import 'package:mhfudatabase/core/database/localized_collation.dart';
import 'package:mhfudatabase/core/domain/enums.dart';
import 'package:mhfudatabase/features/userset/data/user_equipment_set_repository.dart';
import 'package:path/path.dart' as p;

void main() {
  late Directory tempDir;
  late AppDatabase database;
  late UserEquipmentSetRepository repository;

  setUp(() async {
    tempDir = await Directory.systemTemp.createTemp('user_set_test');
    final file = File(p.join(tempDir.path, 'data.db'));
    await File('assets/database/data.db').copy(file.path);

    database = AppDatabase(
      NativeDatabase(
        file,
        setup: (db) {
          db.createCollation(name: 'LOCALIZED', function: compareLocalized);
        },
      ),
    );
    repository = UserEquipmentSetRepository(database);
  });

  tearDown(() async {
    await database.close();
    await tempDir.delete(recursive: true);
  });

  test('creates, lists, and deletes an equipment set', () async {
    final id = await repository.createUserEquipmentSet(
      'My Hunter',
      HunterType.both,
      Gender.both,
    );

    final list = await repository.getUserEquipmentSetList('en');
    expect(list.map((set) => set.id), contains(id));

    await repository.deleteUserEquipmentSet(id);
    final afterDelete = await repository.getUserEquipmentSetList('en');
    expect(afterDelete.map((set) => set.id), isNot(contains(id)));
  });

  test('renames a set', () async {
    final id = await repository.createUserEquipmentSet(
      'Old Name',
      HunterType.both,
      Gender.both,
    );
    await repository.renameUserEquipmentSet(id, 'New Name');

    final set = await repository.getUserEquipmentSet(id, 'en');
    expect(set.name, 'New Name');
  });

  test('adds a weapon, armor, and decoration and aggregates stats', () async {
    final id = await repository.createUserEquipmentSet(
      'Build',
      HunterType.both,
      Gender.both,
    );

    await repository.setWeapon(id, 1);
    await repository.addArmor(id, 426, EquipmentType.armorChest);
    await repository.addDecoration(id, 945, EquipmentType.armorChest);

    final set = await repository.getUserEquipmentSet(id, 'en');

    expect(set.weapon?.id, 1);
    expect(set.armors, hasLength(1));
    expect(set.armors!.first.id, 426);
    expect(set.decorations, hasLength(1));
    expect(set.decorations!.first.decoration.id, 945);
    expect(set.defense, set.armors!.first.defense);
  });

  test(
    'replacing an armor in the same slot keeps only the new piece',
    () async {
      final id = await repository.createUserEquipmentSet(
        'Build',
        HunterType.both,
        Gender.both,
      );

      await repository.addArmor(id, 426, EquipmentType.armorChest);
      await repository.addArmor(id, 427, EquipmentType.armorChest);

      final set = await repository.getUserEquipmentSet(id, 'en');
      expect(set.armors, hasLength(1));
      expect(set.armors!.first.id, 427);
    },
  );

  test('adding the same decoration twice increases its quantity', () async {
    final id = await repository.createUserEquipmentSet(
      'Build',
      HunterType.both,
      Gender.both,
    );

    await repository.addDecoration(id, 945, EquipmentType.armorChest);
    await repository.addDecoration(id, 945, EquipmentType.armorChest);

    final set = await repository.getUserEquipmentSet(id, 'en');
    expect(set.decorations, hasLength(1));
    expect(set.decorations!.first.quantity, 2);

    await repository.removeDecoration(id, 945, EquipmentType.armorChest);
    final afterRemove = await repository.getUserEquipmentSet(id, 'en');
    expect(afterRemove.decorations!.first.quantity, 1);
  });

  test('removing an armor slot clears it', () async {
    final id = await repository.createUserEquipmentSet(
      'Build',
      HunterType.both,
      Gender.both,
    );

    await repository.addArmor(id, 426, EquipmentType.armorChest);
    await repository.removeArmor(id, EquipmentType.armorChest);

    final set = await repository.getUserEquipmentSet(id, 'en');
    expect(set.armors, isEmpty);
  });
}
