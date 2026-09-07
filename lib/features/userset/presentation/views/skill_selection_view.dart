import 'package:flutter/material.dart';

import '../../../../core/domain/enums.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/pill_list_item.dart';
import '../../../../l10n/app_localizations.dart';
import '../../../skill/data/skill_repository.dart';
import '../../../skill/domain/skill_tree_filter.dart';
import '../../../skill/domain/skill.dart';
import '../widgets/selection_pill.dart';
import '../widgets/selection_search_bar.dart';

class const SkillSelectionView({super.key}) extends StatefulWidget {
  @override
  State<SkillSelectionView> createState() => _SkillSelectionViewState();
}

class _SkillSelectionViewState extends State<SkillSelectionView> {
  SkillTreeFilter _filter = const SkillTreeFilter();

  Future<void> _openFilterSheet() async {
    final category = await showModalBottomSheet<SkillCategory?>(
      context: context,
      isScrollControlled: true,
      builder: (context) => _SkillCategorySheet(selected: _filter.category),
    );
    if (!mounted) return;
    setState(() => _filter = SkillTreeFilter(name: _filter.name, category: category));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SelectionSearchBar(
        onQueryChange: (name) => setState(
          () => _filter = SkillTreeFilter(
            name: name.isEmpty ? null : name,
            category: _filter.category,
          ),
        ),
        onFilterTap: _openFilterSheet,
      ),
      body: FutureBuilder<List<SkillTree>>(
        future: SkillRepository().getSkillTreeList(
          AppSettingsController.instance.locale.languageCode,
          filter: _filter,
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
                  onTap: () => Navigator.of(context).pop(skillTree),
                ),
              );
            },
          );
        },
      ),
    );
  }
}

class const _SkillCategorySheet({required final SkillCategory? selected})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    String labelFor(SkillCategory category) => switch (category) {
      SkillCategory.blade => l10n.userSetFilterSkillBlade,
      SkillCategory.combat => l10n.userSetFilterSkillCombat,
      SkillCategory.felyne => l10n.userSetFilterSkillFelyne,
      SkillCategory.gather => l10n.userSetFilterSkillGather,
      SkillCategory.gunner => l10n.userSetFilterSkillGunner,
      SkillCategory.item => l10n.userSetFilterSkillItem,
      SkillCategory.resistance => l10n.userSetFilterSkillResistance,
      SkillCategory.status => l10n.userSetFilterSkillStatus,
    };

    return SafeArea(
      child: SingleChildScrollView(
        padding: const EdgeInsets.all(AppPadding.large),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              l10n.userSetFilterSkillCategory,
              style: Theme.of(context).textTheme.titleMedium,
            ),
            const SizedBox(height: AppSpacing.medium),
            Wrap(
              spacing: AppSpacing.small,
              runSpacing: AppSpacing.small,
              children: [
                for (final category in SkillCategory.values)
                  SelectionPill(
                    selected: category == selected,
                    onTap: () => Navigator.of(context).pop(
                      category == selected ? null : category,
                    ),
                    child: Text(labelFor(category)),
                  ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
