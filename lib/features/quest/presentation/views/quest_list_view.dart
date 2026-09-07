import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../../../core/domain/enums.dart';
import '../../../../core/router/app_routes.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/filter_dropdown.dart';
import '../../../../core/widgets/filter_pill.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/quest_repository.dart';
import '../../domain/quest_filter.dart';
import '../../domain/quest.dart';

const _starExcludedGroups = {
  QuestGroup.treasure,
  QuestGroup.event,
  QuestGroup.beginnerBasic,
  QuestGroup.beginnerWeapon,
  QuestGroup.trainingBattle,
  QuestGroup.trainingSpecial,
  QuestGroup.trainingG,
  QuestGroup.groupPractice,
  QuestGroup.groupChallenge,
};

const _gRankGroups = {QuestGroup.hr7, QuestGroup.hr8, QuestGroup.hr9};

String _questSectionGroupLabel(AppLocalizations l10n, QuestGroup group) =>
    switch (group) {
      QuestGroup.village1 => l10n.questSectionGroupVillage1,
      QuestGroup.village2 => l10n.questSectionGroupVillage2,
      QuestGroup.village3 => l10n.questSectionGroupVillage3,
      QuestGroup.village4 => l10n.questSectionGroupVillage4,
      QuestGroup.village5 => l10n.questSectionGroupVillage5,
      QuestGroup.village6 => l10n.questSectionGroupVillage6,
      QuestGroup.village7 => l10n.questSectionGroupVillage7,
      QuestGroup.village8 => l10n.questSectionGroupVillage8,
      QuestGroup.village9 => l10n.questSectionGroupVillage9,
      QuestGroup.hr1a => l10n.questSectionGroupHr1a,
      QuestGroup.hr1b => l10n.questSectionGroupHr1b,
      QuestGroup.hr1c => l10n.questSectionGroupHr1c,
      QuestGroup.hr2 => l10n.questSectionGroupHr2,
      QuestGroup.hr3 => l10n.questSectionGroupHr3,
      QuestGroup.hr4 => l10n.questSectionGroupHr4,
      QuestGroup.hr5 => l10n.questSectionGroupHr5,
      QuestGroup.hr6 => l10n.questSectionGroupHr6,
      QuestGroup.hr7 => l10n.questSectionGroupHr7,
      QuestGroup.hr8 => l10n.questSectionGroupHr8,
      QuestGroup.hr9 => l10n.questSectionGroupHr9,
      QuestGroup.treasure => l10n.questSectionGroupTreasure,
      QuestGroup.event => l10n.questSectionGroupEvent,
      QuestGroup.beginnerBasic => l10n.questSectionGroupBeginnerBasic,
      QuestGroup.beginnerWeapon => l10n.questSectionGroupBeginnerWeapon,
      QuestGroup.trainingBattle => l10n.questSectionGroupTrainingBattle,
      QuestGroup.trainingSpecial => l10n.questSectionGroupTrainingSpecial,
      QuestGroup.trainingG => l10n.questSectionGroupTrainingG,
      QuestGroup.groupPractice => l10n.questSectionGroupGroupPractice,
      QuestGroup.groupChallenge => l10n.questSectionGroupGroupChallenge,
    };

class const QuestListView({
  required final VoidCallback openDrawer,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<QuestListView> createState() => _QuestListViewState();
}

class _QuestListViewState extends State<QuestListView> {
  QuestFilter _filter = const QuestFilter(hub: HubType.village);
  Set<QuestGroup> _expanded = {};

  void _setFilter(QuestFilter filter) {
    setState(() {
      _filter = filter;
      _expanded = {};
    });
  }

  void _setHub(HubType? hub) => _setFilter(QuestFilter(hub: hub));

  void _toggleKeyUrgentOnly() {
    _setFilter(
      QuestFilter(
        hub: _filter.hub,
        type: _filter.type == null
            ? const [QuestType.key, QuestType.urgent]
            : null,
      ),
    );
  }

  void _toggleExpand(QuestGroup group) {
    setState(() {
      _expanded = _expanded.contains(group)
          ? (_expanded.toSet()..remove(group))
          : (_expanded.toSet()..add(group));
    });
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.screenQuestList,
        navigation: AppTopBarNavigation.menu,
        onNavigationTap: widget.openDrawer,
        onSearchTap: widget.openSearch,
      ),
      body: Column(
        children: [
          _QuestFilterBar(
            filter: _filter,
            onHubChange: _setHub,
            onToggleKeyUrgentOnly: _toggleKeyUrgentOnly,
          ),
          Expanded(
            child: FutureBuilder<List<Quest>>(
              future: QuestRepository().getQuestList(
                AppSettingsController.instance.locale.languageCode,
                filter: _filter,
              ),
              builder: (context, snapshot) {
                final quests = snapshot.data;
                if (quests == null) {
                  return const Center(child: CircularProgressIndicator());
                }

                final grouped = <QuestGroup, List<Quest>>{};
                for (final quest in quests) {
                  (grouped[quest.group] ??= []).add(quest);
                }

                return ListView(
                  padding: context.scrollPadding(
                    const EdgeInsets.fromLTRB(
                      AppPadding.medium,
                      0,
                      AppPadding.medium,
                      AppPadding.small,
                    ),
                  ),
                  children: [
                    for (final entry in grouped.entries) ...[
                      _QuestGroupHeader(
                        group: entry.key,
                        stars: _starExcludedGroups.contains(entry.key)
                            ? 0
                            : entry.value.first.stars,
                        expanded: _expanded.contains(entry.key),
                        onTap: () => _toggleExpand(entry.key),
                      ),
                      if (_expanded.contains(entry.key)) ...[
                        Container(
                          decoration: BoxDecoration(
                            color: Theme.of(context).colorScheme.surface,
                            borderRadius: const BorderRadius.only(
                              bottomLeft: Radius.circular(AppRadius.medium),
                              bottomRight: Radius.circular(AppRadius.medium),
                            ),
                          ),
                          clipBehavior: Clip.antiAlias,
                          child: Column(
                            children: [
                              for (var i = 0; i < entry.value.length; i++) ...[
                                _QuestRow(quest: entry.value[i]),
                                if (i != entry.value.length - 1)
                                  const AppHDivider(),
                              ],
                            ],
                          ),
                        ),
                        const SizedBox(height: AppSpacing.small),
                      ] else
                        const SizedBox(height: AppSpacing.small),
                    ],
                  ],
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}

class const _QuestFilterBar({
  required final QuestFilter filter,
  required final ValueChanged<HubType?> onHubChange,
  required final VoidCallback onToggleKeyUrgentOnly,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    String labelFor(HubType? hub) => switch (hub) {
      HubType.village => l10n.questFilterHubVillage,
      HubType.guild => l10n.questFilterHubGuild,
      HubType.training => l10n.questFilterHubTraining,
      null => l10n.questFilterHubAll,
    };

    return Align(
      alignment: Alignment.centerLeft,
      child: SingleChildScrollView(
        scrollDirection: Axis.horizontal,
        padding: const EdgeInsets.all(AppPadding.medium),
        child: Row(
          children: [
            FilterDropdown<HubType?>(
              value: filter.hub,
              items: [null, ...HubType.values],
              labelBuilder: labelFor,
              onChanged: onHubChange,
            ),
            if (filter.hub != HubType.training) ...[
              const SizedBox(width: AppSpacing.medium),
              FilterPill(
                selected: filter.type != null,
                onTap: onToggleKeyUrgentOnly,
                child: Text(l10n.questFilterOnlyKeyUrgent),
              ),
            ],
          ],
        ),
      ),
    );
  }
}

class const _QuestGroupHeader({
  required final QuestGroup group,
  required final int stars,
  required final bool expanded,
  required final VoidCallback onTap,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final colors = Theme.of(context).colorScheme;
    final textColor = expanded ? colors.onSecondaryContainer : colors.onSurface;
    final isGRank = _gRankGroups.contains(group);
    final starCount = isGRank ? stars - 8 : stars;

    return Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.only(
          topLeft: const Radius.circular(AppRadius.medium),
          topRight: const Radius.circular(AppRadius.medium),
          bottomLeft: expanded
              ? Radius.zero
              : const Radius.circular(AppRadius.medium),
          bottomRight: expanded
              ? Radius.zero
              : const Radius.circular(AppRadius.medium),
        ),
      ),
      clipBehavior: Clip.antiAlias,
      child: ListItemLayout(
        backgroundColor: expanded ? colors.secondaryContainer : colors.surface,
        leading: Text(
          _questSectionGroupLabel(l10n, group),
          style: Theme.of(
            context,
          ).textTheme.bodyLarge?.copyWith(color: textColor),
        ),
        headline: stars <= 0
            ? const SizedBox.shrink()
            : Row(
                children: [
                  if (isGRank)
                    Text(
                      'G ',
                      style: Theme.of(
                        context,
                      ).textTheme.bodyLarge?.copyWith(color: textColor),
                    ),
                  for (var i = 0; i < starCount; i++)
                    Icon(
                      Icons.star,
                      color: colors.tertiary,
                      size: AppSize.extraSmall,
                    ),
                ],
              ),
        trailing: Icon(
          expanded ? Icons.keyboard_arrow_up : Icons.keyboard_arrow_down,
          color: textColor,
          size: AppSize.extraSmall,
        ),
        onTap: onTap,
      ),
    );
  }
}

class const _QuestRow({required final Quest quest}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final colors = Theme.of(context).colorScheme;

    return ListItemLayout(
      leading: QuestGoalIcon(goal: quest.goalType, size: AppSize.medium),
      headline: Text(
        quest.name,
        maxLines: 1,
        overflow: TextOverflow.ellipsis,
        style: Theme.of(context).textTheme.bodyLarge,
      ),
      supporting: Text(
        quest.goal,
        maxLines: 1,
        overflow: TextOverflow.ellipsis,
        style: Theme.of(context).textTheme.bodySmall,
      ),
      trailing: switch (quest.questType) {
        QuestType.key => _QuestTypeBadge(
          label: l10n.questTypeKey,
          background: colors.tertiaryContainer,
          foreground: colors.onTertiaryContainer,
        ),
        QuestType.urgent => _QuestTypeBadge(
          label: l10n.questTypeUrgent,
          background: colors.error,
          foreground: colors.onError,
        ),
        _ => null,
      },
      onTap: () => context.push(AppRoutes.questDetail(quest.id)),
    );
  }
}

class const _QuestTypeBadge({
  required final String label,
  required final Color background,
  required final Color foreground,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(AppPadding.small),
      decoration: BoxDecoration(
        color: background,
        borderRadius: BorderRadius.circular(AppRadius.small),
      ),
      child: Text(
        label,
        style: Theme.of(
          context,
        ).textTheme.labelSmall?.copyWith(color: foreground),
      ),
    );
  }
}
