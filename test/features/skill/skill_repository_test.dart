import 'dart:io';

import 'package:drift/native.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mhfudatabase/core/database/app_database.dart' show AppDatabase;
import 'package:mhfudatabase/core/database/localized_collation.dart';
import 'package:mhfudatabase/core/domain/enums.dart';
import 'package:mhfudatabase/features/skill/data/skill_repository.dart';
import 'package:mhfudatabase/features/skill/domain/skill_tree_filter.dart';

void main() {
  late AppDatabase database;
  late SkillRepository repository;

  setUpAll(() {
    database = AppDatabase(
      NativeDatabase(
        File('assets/database/data.db'),
        setup: (db) {
          db.createCollation(name: 'LOCALIZED', function: compareLocalized);
        },
      ),
    );
    repository = SkillRepository(database);
  });

  tearDownAll(() => database.close());

  test('getSkillTree returns the tree with its skills', () async {
    final skillTree = await repository.getSkillTree(1, 'en');

    expect(skillTree.id, 1);
    expect(skillTree.category, SkillCategory.status);
    expect(skillTree.name, isNotEmpty);
    expect(skillTree.skills, isNotEmpty);
    expect(skillTree.skills!.first.requiredPoints, 10);
  });

  test('getSkillTreeList returns trees without a filter', () async {
    final skillTrees = await repository.getSkillTreeList('en');
    expect(skillTrees, isNotEmpty);
  });

  test('getSkillTreeList filters by name', () async {
    final all = await repository.getSkillTreeList('en');
    final target = all.first;

    final filtered = await repository.getSkillTreeList(
      'en',
      filter: SkillTreeFilter(name: target.name),
    );

    expect(filtered.map((tree) => tree.id), contains(target.id));
  });

  test('getSkillTreeList filters by category', () async {
    final filtered = await repository.getSkillTreeList(
      'en',
      filter: const SkillTreeFilter(category: SkillCategory.resistance),
    );

    expect(filtered, isNotEmpty);
    expect(
      filtered.every((tree) => tree.category == SkillCategory.resistance),
      isTrue,
    );
  });
}
