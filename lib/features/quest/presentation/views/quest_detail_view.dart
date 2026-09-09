import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../../../core/domain/enums.dart';
import '../../../../core/router/app_routes.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/animated_page_content.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/button_page.dart';
import '../../../../core/widgets/detail_header.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/quest_group_label.dart';
import '../../../../core/widgets/section_card.dart';
import '../../../../core/widgets/surface_card.dart';
import '../../../../l10n/app_localizations.dart';
import '../../../location/domain/location.dart';
import '../../../monster/domain/monster.dart';
import '../../data/quest_repository.dart';
import '../../domain/quest.dart';

enum _QuestPage { summary, supply, reward }

class const QuestDetailView({
  required final int questId,
  required final VoidCallback navigateBack,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<QuestDetailView> createState() => _QuestDetailViewState();
}

class _QuestDetailViewState extends State<QuestDetailView> {
  late final Future<Quest> _future = QuestRepository().getQuest(
    widget.questId,
    AppSettingsController.instance.locale.languageCode,
  );
  _QuestPage _page = _QuestPage.summary;

  void _goToPage(_QuestPage page) => setState(() => _page = page);

  void _handleBack() {
    if (_page == _QuestPage.summary) {
      widget.navigateBack();
    } else {
      _goToPage(_QuestPage.summary);
    }
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return FutureBuilder<Quest>(
      future: _future,
      builder: (context, snapshot) {
        final quest = snapshot.data;

        return PopScope(
          canPop: _page == _QuestPage.summary,
          onPopInvokedWithResult: (didPop, result) {
            if (!didPop) _goToPage(_QuestPage.summary);
          },
          child: Scaffold(
            appBar: AppTopBar(
              title: quest?.name ?? l10n.screenQuestDetail,
              navigation: AppTopBarNavigation.back,
              onNavigationTap: _handleBack,
              onSearchTap: widget.openSearch,
            ),
            body: quest == null
                ? const Center(child: CircularProgressIndicator())
                : AnimatedPageContent<_QuestPage>(
                    value: _page,
                    index: (page) => page.index,
                    builder: (context, page) => switch (page) {
                      _QuestPage.summary => _SummaryPage(
                        quest: quest,
                        onChangePage: _goToPage,
                      ),
                      _QuestPage.supply => _SupplyPage(quest: quest),
                      _QuestPage.reward => _RewardPage(quest: quest),
                    },
                  ),
          ),
        );
      },
    );
  }
}

class const _SummaryPage({
  required final Quest quest,
  required final ValueChanged<_QuestPage> onChangePage,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final monsters = quest.monsters ?? const <Monster>[];

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
        SurfaceCard(
          child: Column(
            children: [
              DetailHeader(
                icon: QuestGoalIcon(goal: quest.goalType),
                title: quest.name,
                subtitle: questGroupDetailLabel(l10n, quest.group),
                description: quest.goal,
              ),
              const AppHDivider(),
              _QuestSummaryStats(quest: quest),
              const AppHDivider(),
              Padding(
                padding: const EdgeInsets.all(AppPadding.large),
                child: Text(
                  quest.description,
                  style: quest.group == QuestGroup.trainingG
                      ? Theme.of(context).textTheme.bodyMedium?.copyWith(
                          fontFamily: 'monospace',
                        )
                      : Theme.of(context).textTheme.bodyMedium,
                ),
              ),
              Padding(
                padding: const EdgeInsets.fromLTRB(
                  AppPadding.large,
                  0,
                  AppPadding.large,
                  AppPadding.large,
                ),
                child: Text(
                  l10n.questClient(quest.client),
                  style: Theme.of(context).textTheme.bodyMedium,
                ),
              ),
            ],
          ),
        ),
        if (quest.supplies != null && quest.supplies!.isNotEmpty) ...[
          const SizedBox(height: AppSpacing.medium),
          ButtonPage(
            title: l10n.questSupplyBox,
            onTap: () => onChangePage(_QuestPage.supply),
          ),
        ],
        if (quest.rewards != null && quest.rewards!.isNotEmpty) ...[
          const SizedBox(height: AppSpacing.medium),
          ButtonPage(
            title: l10n.questRewards,
            onTap: () => onChangePage(_QuestPage.reward),
          ),
        ],
        if (quest.location != null) ...[
          const SizedBox(height: AppSpacing.medium),
          SectionCard(
            title: l10n.questLocation,
            child: _LocationRow(
              location: quest.location!,
              daytime: quest.daytime,
            ),
          ),
        ],
        if (monsters.isNotEmpty) ...[
          const SizedBox(height: AppSpacing.medium),
          SectionCard(
            title: l10n.questMonsters,
            child: Column(
              children: [
                for (var i = 0; i < monsters.length; i++) ...[
                  _MonsterRow(monster: monsters[i]),
                  if (i != monsters.length - 1) const AppHDivider(),
                ],
              ],
            ),
          ),
        ],
      ],
    );
  }
}

class const _QuestSummaryStats({required final Quest quest})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final valueStyle = Theme.of(context).textTheme.bodyMedium;
    final labelStyle = Theme.of(context).textTheme.bodySmall;

    return Padding(
      padding: const EdgeInsets.all(AppPadding.large),
      child: Column(
        children: [
          Row(
            children: [
              Expanded(
                child: Text(
                  '${quest.reward}z',
                  textAlign: TextAlign.center,
                  style: valueStyle,
                ),
              ),
              Expanded(
                child: Text(
                  '${quest.fee}z',
                  textAlign: TextAlign.center,
                  style: valueStyle,
                ),
              ),
              Expanded(
                child: Text(
                  '${quest.timeLimit}m',
                  textAlign: TextAlign.center,
                  style: valueStyle,
                ),
              ),
            ],
          ),
          const SizedBox(height: AppSpacing.small),
          Row(
            children: [
              Expanded(
                child: Text(
                  l10n.questReward,
                  textAlign: TextAlign.center,
                  style: labelStyle,
                ),
              ),
              Expanded(
                child: Text(
                  l10n.questFee,
                  textAlign: TextAlign.center,
                  style: labelStyle,
                ),
              ),
              Expanded(
                child: Text(
                  l10n.questTimeLimit,
                  textAlign: TextAlign.center,
                  style: labelStyle,
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}

class const _LocationRow({
  required final Location location,
  required final LocationDaytime? daytime,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return ListItemLayout(
      leading: EntityIcon(
        asset: locationIconAsset(location.id),
        size: AppSize.extraLarge,
      ),
      headline: Text(
        location.name,
        style: Theme.of(context).textTheme.bodyLarge,
      ),
      supporting: daytime == null
          ? null
          : Text(
              switch (daytime!) {
                LocationDaytime.day => l10n.locationDaytimeDay,
                LocationDaytime.night => l10n.locationDaytimeNight,
              },
              style: Theme.of(context).textTheme.bodySmall,
            ),
      onTap: () => context.push(AppRoutes.locationDetail(location.id)),
    );
  }
}

class const _MonsterRow({required final Monster monster})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListItemLayout(
      leading: EntityIcon(
        asset: monsterIconAsset(monster.id),
        size: AppSize.extraLarge,
      ),
      headline: Text(
        monster.name,
        style: Theme.of(context).textTheme.bodyLarge,
      ),
      supporting: Text(
        monster.ecology,
        style: Theme.of(context).textTheme.bodySmall,
      ),
      onTap: () => context.push(AppRoutes.monsterDetail(monster.id)),
    );
  }
}

class const _SupplyPage({required final Quest quest}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final supplies = quest.supplies ?? const <QuestSupply>[];

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
        for (final supply in supplies)
          Container(
            margin: const EdgeInsets.only(bottom: AppSpacing.small),
            decoration: BoxDecoration(
              color: Theme.of(context).colorScheme.surface,
              borderRadius: BorderRadius.circular(AppRadius.small),
            ),
            clipBehavior: Clip.antiAlias,
            child: _SupplyRow(supply: supply),
          ),
      ],
    );
  }
}

class const _SupplyRow({required final QuestSupply supply})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListItemLayout(
      leading: ItemEntityIcon(
        type: supply.item.iconType,
        color: supply.item.iconColor,
        size: AppSize.medium,
      ),
      headline: Text(
        supply.item.name,
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      trailing: Text(
        'x ${supply.quantity}',
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      onTap: () => context.push(AppRoutes.itemDetail(supply.item.id)),
    );
  }
}

class const _RewardPage({required final Quest quest}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final rewards = quest.rewards ?? const <QuestReward>[];
    final rewardsByCondition = <String, List<QuestReward>>{};
    for (final reward in rewards) {
      (rewardsByCondition[reward.condition] ??= []).add(reward);
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
        const SizedBox(height: AppSpacing.medium),
        for (final entry in rewardsByCondition.entries) ...[
          SectionCard(
            title: entry.key,
            margin: EdgeInsets.zero,
            child: Column(
              children: [
                for (var i = 0; i < entry.value.length; i++) ...[
                  _RewardRow(reward: entry.value[i]),
                  if (i != entry.value.length - 1) const AppHDivider(),
                ],
              ],
            ),
          ),
          const SizedBox(height: AppSpacing.medium),
        ],
      ],
    );
  }
}

class const _RewardRow({required final QuestReward reward})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListItemLayout(
      leading: ItemEntityIcon(
        type: reward.item.iconType,
        color: reward.item.iconColor,
        size: AppSize.medium,
      ),
      headline: Row(
        children: [
          Flexible(
            child: Text(
              reward.item.name,
              style: Theme.of(context).textTheme.bodyMedium,
            ),
          ),
          const SizedBox(width: AppSpacing.medium),
          Text(
            'x ${reward.quantity}',
            style: Theme.of(context).textTheme.bodyMedium?.copyWith(
              color: Theme.of(context).colorScheme.onSurfaceVariant,
            ),
          ),
        ],
      ),
      trailing: Text(
        '${reward.percentage}%',
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      onTap: () => context.push(AppRoutes.itemDetail(reward.item.id)),
    );
  }
}
