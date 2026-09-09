import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../../../core/router/app_routes.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/pill_list_item.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/skill_repository.dart';
import '../../domain/skill.dart';

class const SkillTreeListView({
  required final VoidCallback openDrawer,
  required final VoidCallback openSearch,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.screenSkillTreeList,
        navigation: AppTopBarNavigation.menu,
        onNavigationTap: openDrawer,
        onSearchTap: openSearch,
      ),
      body: FutureBuilder<List<SkillTree>>(
        future: SkillRepository().getSkillTreeList(
          AppSettingsController.instance.locale.languageCode,
        ),
        builder: (context, snapshot) {
          final skillTrees = snapshot.data;
          if (skillTrees == null) {
            return const Center(child: CircularProgressIndicator());
          }
          return ListView.separated(
            padding: context.scrollPadding(
              const EdgeInsets.fromLTRB(
                AppPadding.medium,
                0,
                AppPadding.medium,
                AppPadding.small,
              ),
            ),
            itemCount: skillTrees.length,
            separatorBuilder: (context, index) => const AppHDivider(),
            itemBuilder: (context, index) {
              final skillTree = skillTrees[index];
              return PillListItem(
                child: ListItemLayout(
                  headline: Text(skillTree.name),
                  onTap: () =>
                      context.push(AppRoutes.skillTreeDetail(skillTree.id)),
                ),
              );
            },
          );
        },
      ),
    );
  }
}
