import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../domain/shared.dart';
import '../router/app_routes.dart';
import 'app_h_divider.dart';
import 'equipment_stats.dart' show signedNumber;
import 'list_item_layout.dart';

class const SkillPoints({required final List<SkillPoint> skills, super.key})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        for (final (index, skill) in skills.indexed) ...[
          if (index > 0) const AppHDivider(),
          ListItemLayout(
            headline: Text(skill.skillTree.name),
            trailing: Text(signedNumber(skill.points)),
            onTap: () =>
                context.push(AppRoutes.skillTreeDetail(skill.skillTree.id)),
          ),
        ],
      ],
    );
  }
}
