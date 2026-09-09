import '../../../core/domain/enums.dart';

class const SkillTree({
  required final int id,
  required final String name,
  required final SkillCategory category,
  final List<Skill>? skills,
});

class const Skill({
  required final int id,
  required final int skillTreeId,
  required final String name,
  required final String description,
  required final int requiredPoints,
});
