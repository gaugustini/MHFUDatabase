import 'dart:io';

import 'package:drift/native.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mhfudatabase/core/database/app_database.dart' show AppDatabase;
import 'package:mhfudatabase/core/database/localized_collation.dart';
import 'package:mhfudatabase/core/domain/enums.dart';
import 'package:mhfudatabase/features/decoration/data/decoration_repository.dart';
import 'package:mhfudatabase/features/decoration/domain/decoration_filter.dart';
import 'package:mhfudatabase/features/skill/domain/skill.dart' show SkillTree;

void main() {
  late AppDatabase database;
  late DecorationRepository repository;

  setUpAll(() {
    database = AppDatabase(
      NativeDatabase(
        File('assets/database/data.db'),
        setup: (db) {
          db.createCollation(name: 'LOCALIZED', function: compareLocalized);
        },
      ),
    );
    repository = DecorationRepository(database);
  });

  tearDownAll(() => database.close());

  test('getDecoration returns the decoration with skills and recipe', () async {
    final decoration = await repository.getDecoration(945, 'en');

    expect(decoration.id, 945);
    expect(decoration.name, isNotEmpty);
    expect(decoration.skills, isNotEmpty);
    expect(decoration.requiredSlots, greaterThan(0));
  });

  test('getDecorationList returns decorations without a filter', () async {
    final decorations = await repository.getDecorationList('en');
    expect(decorations, isNotEmpty);
  });

  test('getDecorationList filters by name', () async {
    final all = await repository.getDecorationList('en');
    final target = all.first;

    final filtered = await repository.getDecorationList(
      'en',
      filter: DecorationFilter(name: target.name),
    );

    expect(filtered.map((decoration) => decoration.id), contains(target.id));
  });

  test('getDecorationList filters by skill', () async {
    const skillTree = SkillTree(
      id: 2,
      name: '',
      category: SkillCategory.resistance,
    );

    final filtered = await repository.getDecorationList(
      'en',
      filter: DecorationFilter(skills: [skillTree]),
    );

    expect(filtered, isNotEmpty);
    for (final decoration in filtered) {
      final refetched = await repository.getDecoration(decoration.id, 'en');
      expect(
        refetched.skills!.any((point) => point.skillTree.id == 2),
        isTrue,
      );
    }
  });
}
