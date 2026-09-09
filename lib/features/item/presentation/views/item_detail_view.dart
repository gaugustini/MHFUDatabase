import 'package:flutter/material.dart' hide Decoration;
import 'package:go_router/go_router.dart';

import '../../../../core/domain/enums.dart';
import '../../../../core/domain/shared.dart';
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
import '../../../../core/widgets/mhfu_colors.dart';
import '../../../../core/widgets/quest_group_label.dart';
import '../../../../core/widgets/section_card.dart';
import '../../../../core/widgets/surface_card.dart';
import '../../../../l10n/app_localizations.dart';
import '../../../armor/domain/armor.dart';
import '../../../decoration/domain/decoration.dart';
import '../../../itemcombination/presentation/widgets/combination_row.dart';
import '../../../veggie/presentation/widgets/veggie_trade_row.dart';
import '../../../weapon/domain/weapon.dart';
import '../../data/item_repository.dart';
import '../../domain/item.dart';

enum _ItemPage { summary, usages, sources }

class const ItemDetailView({
  required final int itemId,
  required final VoidCallback navigateBack,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<ItemDetailView> createState() => _ItemDetailViewState();
}

class _ItemDetailViewState extends State<ItemDetailView> {
  late final Future<Item> _future = ItemRepository().getItem(
    widget.itemId,
    AppSettingsController.instance.locale.languageCode,
  );
  _ItemPage _page = _ItemPage.summary;

  void _goToPage(_ItemPage page) => setState(() => _page = page);

  void _handleBack() {
    if (_page == _ItemPage.summary) {
      widget.navigateBack();
    } else {
      _goToPage(_ItemPage.summary);
    }
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return FutureBuilder<Item>(
      future: _future,
      builder: (context, snapshot) {
        final item = snapshot.data;

        return PopScope(
          canPop: _page == _ItemPage.summary,
          onPopInvokedWithResult: (didPop, result) {
            if (!didPop) _goToPage(_ItemPage.summary);
          },
          child: Scaffold(
            appBar: AppTopBar(
              title: item?.name ?? l10n.screenItemDetail,
              navigation: AppTopBarNavigation.back,
              onNavigationTap: _handleBack,
              onSearchTap: widget.openSearch,
            ),
            body: item == null
                ? const Center(child: CircularProgressIndicator())
                : AnimatedPageContent<_ItemPage>(
                    value: _page,
                    index: (page) => page.index,
                    builder: (context, page) => switch (page) {
                      _ItemPage.summary => _SummaryPage(
                        item: item,
                        onChangePage: _goToPage,
                      ),
                      _ItemPage.usages => _UsagesPage(
                        usages: item.usages ?? _emptyUsages(),
                      ),
                      _ItemPage.sources => _SourcesPage(
                        sources: item.sources ?? _emptySources(),
                      ),
                    },
                  ),
          ),
        );
      },
    );
  }
}

ItemSources _emptySources() => ItemSources(
  combinations: const [],
  locations: const [],
  monsterRewards: const [],
  questRewards: const [],
  veggieTrades: const [],
);

ItemUsages _emptyUsages() => ItemUsages(
  combinations: const [],
  veggieTrades: const [],
  armors: const [],
  decorations: const [],
  weapons: const [],
);

class const _SummaryPage({
  required final Item item,
  required final ValueChanged<_ItemPage> onChangePage,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final hasUsages = item.usages != null && !item.usages!.isEmpty;
    final hasSources = item.sources != null && !item.sources!.isEmpty;

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
                icon: ItemEntityIcon(
                  type: item.iconType,
                  color: item.iconColor,
                ),
                title: item.name,
                subtitle: l10n.itemRarity(item.rarity),
                description: item.description,
              ),
              const AppHDivider(),
              _ItemStats(item: item),
            ],
          ),
        ),
        if (hasUsages) ...[
          const SizedBox(height: AppSpacing.medium),
          ButtonPage(
            title: l10n.itemUsages,
            onTap: () => onChangePage(_ItemPage.usages),
          ),
        ],
        if (hasSources) ...[
          const SizedBox(height: AppSpacing.medium),
          ButtonPage(
            title: l10n.itemSources,
            onTap: () => onChangePage(_ItemPage.sources),
          ),
        ],
      ],
    );
  }
}

class const _ItemStats({required final Item item}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final textTheme = Theme.of(context).textTheme;

    return Padding(
      padding: const EdgeInsets.all(AppPadding.large),
      child: Column(
        children: [
          Row(
            children: [
              Expanded(
                child: Text(
                  item.carryMax > 0
                      ? '${item.carryMax}'
                      : l10n.itemCarryMaxUnlimited,
                  textAlign: TextAlign.center,
                  style: textTheme.bodyMedium,
                ),
              ),
              Expanded(
                child: Text(
                  item.buyPrice != null ? '${item.buyPrice}z' : '-',
                  textAlign: TextAlign.center,
                  style: textTheme.bodyMedium,
                ),
              ),
              Expanded(
                child: Text(
                  '${item.sellPrice}z',
                  textAlign: TextAlign.center,
                  style: textTheme.bodyMedium,
                ),
              ),
            ],
          ),
          const SizedBox(height: AppSpacing.small),
          Row(
            children: [
              Expanded(
                child: Text(
                  l10n.itemCarryMax,
                  textAlign: TextAlign.center,
                  style: textTheme.bodySmall,
                ),
              ),
              Expanded(
                child: Text(
                  l10n.itemBuyPrice,
                  textAlign: TextAlign.center,
                  style: textTheme.bodySmall,
                ),
              ),
              Expanded(
                child: Text(
                  l10n.itemSellPrice,
                  textAlign: TextAlign.center,
                  style: textTheme.bodySmall,
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}

class const _SourcesPage({required final ItemSources sources})
    extends StatefulWidget {
  @override
  State<_SourcesPage> createState() => _SourcesPageState();
}

class _SourcesPageState extends State<_SourcesPage> {
  bool _combinationsExpanded = true;
  bool _locationsExpanded = true;
  bool _monsterRewardsExpanded = true;
  bool _questRewardsExpanded = true;
  bool _veggieExpanded = true;

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final sources = widget.sources;

    final locationGroups = <String, Map<Rank, List<GatheringSource>>>{};
    for (final source in sources.locations) {
      final byRank = locationGroups.putIfAbsent(
        source.location.name,
        () => {},
      );
      (byRank[source.rank] ??= []).add(source);
    }

    final questGroups = <HubType, List<QuestSource>>{};
    for (final source in sources.questRewards) {
      (questGroups[source.quest.hubType] ??= []).add(source);
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
        if (sources.combinations.isNotEmpty) ...[
          SectionCard(
            title: l10n.itemCrafting,
            expanded: _combinationsExpanded,
            onTap: () => setState(
              () => _combinationsExpanded = !_combinationsExpanded,
            ),
            margin: EdgeInsets.zero,
            child: Column(
              children: _withDividers([
                for (final combination in sources.combinations)
                  CombinationRow(combination: combination),
              ]),
            ),
          ),
          const SizedBox(height: AppSpacing.medium),
        ],
        if (sources.locations.isNotEmpty) ...[
          SectionCard(
            title: l10n.itemLocation,
            expanded: _locationsExpanded,
            onTap: () =>
                setState(() => _locationsExpanded = !_locationsExpanded),
            margin: EdgeInsets.zero,
            child: Column(
              children: _withDividers([
                for (final locationEntry in locationGroups.entries)
                  for (final rankEntry in locationEntry.value.entries)
                    _SubGroup(
                      title:
                          '${locationEntry.key} (${_rankLabel(l10n, rankEntry.key)})',
                      rows: [
                        for (final source in rankEntry.value)
                          _GatheringRow(source: source),
                      ],
                    ),
              ]),
            ),
          ),
          const SizedBox(height: AppSpacing.medium),
        ],
        if (sources.monsterRewards.isNotEmpty) ...[
          SectionCard(
            title: l10n.itemMonster,
            expanded: _monsterRewardsExpanded,
            onTap: () => setState(
              () => _monsterRewardsExpanded = !_monsterRewardsExpanded,
            ),
            margin: EdgeInsets.zero,
            child: Column(
              children: _withDividers([
                for (final source in sources.monsterRewards)
                  _MonsterSourceRow(source: source),
              ]),
            ),
          ),
          const SizedBox(height: AppSpacing.medium),
        ],
        if (sources.questRewards.isNotEmpty) ...[
          SectionCard(
            title: l10n.itemQuest,
            expanded: _questRewardsExpanded,
            onTap: () => setState(
              () => _questRewardsExpanded = !_questRewardsExpanded,
            ),
            margin: EdgeInsets.zero,
            child: Column(
              children: _withDividers([
                for (final hubEntry in questGroups.entries)
                  _SubGroup(
                    title: _hubLabel(l10n, hubEntry.key),
                    rows: [
                      for (final source in hubEntry.value)
                        _QuestSourceRow(source: source),
                    ],
                  ),
              ]),
            ),
          ),
          const SizedBox(height: AppSpacing.medium),
        ],
        if (sources.veggieTrades.isNotEmpty)
          SectionCard(
            title: l10n.itemTradeVeggie,
            expanded: _veggieExpanded,
            onTap: () => setState(() => _veggieExpanded = !_veggieExpanded),
            margin: EdgeInsets.zero,
            child: Column(
              children: _withDividers([
                for (final source in sources.veggieTrades)
                  VeggieTradeRow(
                    locationName: source.location.name,
                    trade: source.trade,
                  ),
              ]),
            ),
          ),
      ],
    );
  }
}

class const _UsagesPage({required final ItemUsages usages})
    extends StatefulWidget {
  @override
  State<_UsagesPage> createState() => _UsagesPageState();
}

class _UsagesPageState extends State<_UsagesPage> {
  bool _combinationsExpanded = true;
  bool _veggieExpanded = true;
  bool _armorExpanded = true;
  bool _decorationExpanded = true;
  bool _weaponExpanded = true;

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final usages = widget.usages;

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
        if (usages.combinations.isNotEmpty) ...[
          SectionCard(
            title: l10n.itemCrafting,
            expanded: _combinationsExpanded,
            onTap: () => setState(
              () => _combinationsExpanded = !_combinationsExpanded,
            ),
            margin: EdgeInsets.zero,
            child: Column(
              children: _withDividers([
                for (final combination in usages.combinations)
                  CombinationRow(combination: combination),
              ]),
            ),
          ),
          const SizedBox(height: AppSpacing.medium),
        ],
        if (usages.veggieTrades.isNotEmpty) ...[
          SectionCard(
            title: l10n.itemTradeVeggie,
            expanded: _veggieExpanded,
            onTap: () => setState(() => _veggieExpanded = !_veggieExpanded),
            margin: EdgeInsets.zero,
            child: Column(
              children: _withDividers([
                for (final usage in usages.veggieTrades)
                  VeggieTradeRow(
                    locationName: usage.location.name,
                    trade: usage.trade,
                  ),
              ]),
            ),
          ),
          const SizedBox(height: AppSpacing.medium),
        ],
        if (usages.armors.isNotEmpty) ...[
          SectionCard(
            title: l10n.itemArmor,
            expanded: _armorExpanded,
            onTap: () => setState(() => _armorExpanded = !_armorExpanded),
            margin: EdgeInsets.zero,
            child: Column(
              children: _withDividers([
                for (final usage in usages.armors) _ArmorUsageRow(usage: usage),
              ]),
            ),
          ),
          const SizedBox(height: AppSpacing.medium),
        ],
        if (usages.decorations.isNotEmpty) ...[
          SectionCard(
            title: l10n.itemDecoration,
            expanded: _decorationExpanded,
            onTap: () =>
                setState(() => _decorationExpanded = !_decorationExpanded),
            margin: EdgeInsets.zero,
            child: Column(
              children: _withDividers([
                for (final usage in usages.decorations)
                  _DecorationUsageRow(usage: usage),
              ]),
            ),
          ),
          const SizedBox(height: AppSpacing.medium),
        ],
        if (usages.weapons.isNotEmpty)
          SectionCard(
            title: l10n.itemWeapon,
            expanded: _weaponExpanded,
            onTap: () => setState(() => _weaponExpanded = !_weaponExpanded),
            margin: EdgeInsets.zero,
            child: Column(
              children: _withDividers([
                for (final usage in usages.weapons)
                  _WeaponUsageRow(usage: usage),
              ]),
            ),
          ),
      ],
    );
  }
}

class const _SubHeader({required final String title}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      color: Theme.of(context).colorScheme.surface,
      padding: const EdgeInsets.all(AppPadding.large),
      child: Text(title, style: Theme.of(context).textTheme.titleSmall),
    );
  }
}

class const _SubGroup({
  required final String title,
  required final List<Widget> rows,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: _withDividers([_SubHeader(title: title), ...rows]),
    );
  }
}

class const _GatheringRow({required final GatheringSource source})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final area = _areaLabel(l10n, source.area);
    final gatherType = _gatherTypeLabel(l10n, source.type);
    final minMax = _minMaxLabel(source.min, source.max);

    return ListItemLayout(
      leading: EntityIcon(
        asset: locationIconAsset(source.location.id),
        size: AppSize.medium,
      ),
      headline: Text(
        '$area: $gatherType ($minMax)',
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      trailing: Text(
        '${source.percentage}%',
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      onTap: () => context.push(AppRoutes.locationDetail(source.location.id)),
    );
  }
}

class const _MonsterSourceRow({required final MonsterSource source})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final colors = Theme.of(context).colorScheme;

    return ListItemLayout(
      leading: EntityIcon(
        asset: monsterIconAsset(source.monster.id),
        size: AppSize.medium,
      ),
      headline: Text(
        source.monster.name,
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      supporting: Text(
        '${source.condition} (${_rankLabel(l10n, source.rank)})',
        style: Theme.of(context).textTheme.bodySmall,
      ),
      trailing: Text(
        '${source.percentage}%',
        style: Theme.of(
          context,
        ).textTheme.bodyMedium?.copyWith(color: colors.onSurfaceVariant),
      ),
      onTap: () => context.push(AppRoutes.monsterDetail(source.monster.id)),
    );
  }
}

class const _QuestSourceRow({required final QuestSource source})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final colors = Theme.of(context).colorScheme;

    return ListItemLayout(
      leading: QuestGoalIcon(
        goal: source.quest.goalType,
        size: AppSize.medium,
      ),
      headline: Text(
        '${questGroupLabel(l10n, source.quest.group)}: ${source.quest.name}',
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      supporting: Text(
        source.condition,
        style: Theme.of(context).textTheme.bodySmall,
      ),
      trailing: Text(
        '${source.percentage}%',
        style: Theme.of(
          context,
        ).textTheme.bodyMedium?.copyWith(color: colors.onSurfaceVariant),
      ),
      onTap: () => context.push(AppRoutes.questDetail(source.quest.id)),
    );
  }
}

class const _ArmorUsageRow({required final Usage<Armor> usage})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListItemLayout(
      leading: EntityIcon(
        asset: equipmentTypeIconAsset(usage.craftable.type),
        size: AppSize.medium,
        tint: rarityColor(usage.craftable.rarity),
      ),
      headline: Text(
        usage.craftable.name,
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      trailing: Text(
        '${usage.quantity}',
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      onTap: () => context.push(AppRoutes.armorDetail(usage.craftable.id)),
    );
  }
}

class const _DecorationUsageRow({required final Usage<Decoration> usage})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListItemLayout(
      leading: ItemEntityIcon(
        type: ItemIconType.jewel,
        color: usage.craftable.color,
        size: AppSize.medium,
      ),
      headline: Text(
        usage.craftable.name,
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      trailing: Text(
        '${usage.quantity}',
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      onTap: () => context.push(AppRoutes.decorationDetail(usage.craftable.id)),
    );
  }
}

class const _WeaponUsageRow({required final Usage<Weapon> usage})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListItemLayout(
      leading: WeaponEntityIcon(
        type: usage.craftable.type,
        rarity: usage.craftable.rarity,
        size: AppSize.medium,
      ),
      headline: Text(
        usage.craftable.name,
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      trailing: Text(
        '${usage.quantity}',
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      onTap: () => context.push(AppRoutes.weaponDetail(usage.craftable.id)),
    );
  }
}

String _rankLabel(AppLocalizations l10n, Rank rank) => switch (rank) {
  Rank.unranked => l10n.rankUnranked,
  Rank.low => l10n.rankLow,
  Rank.high => l10n.rankHigh,
  Rank.g => l10n.rankG,
  Rank.treasure => l10n.rankTreasure,
  Rank.training => l10n.rankTraining,
};

String _hubLabel(AppLocalizations l10n, HubType hub) => switch (hub) {
  HubType.village => l10n.questFilterHubVillage,
  HubType.guild => l10n.itemQuestHubGuild,
  HubType.training => l10n.itemQuestHubTraining,
};

String _gatherTypeLabel(AppLocalizations l10n, GatherType type) =>
    switch (type) {
      GatherType.collect => l10n.locationGatherCollect,
      GatherType.mine => l10n.locationGatherMine,
      GatherType.bug => l10n.locationGatherBug,
      GatherType.fish => l10n.locationGatherFish,
    };

String _areaLabel(AppLocalizations l10n, int area) => switch (area) {
  -1 => l10n.locationSecretArea,
  0 => l10n.locationBaseCamp,
  _ => l10n.locationArea(area),
};

String _minMaxLabel(int min, int max) {
  if (min == -1) return '∞';
  if (min == max) return '$min';
  return '$min~$max';
}

List<Widget> _withDividers(List<Widget> rows) {
  final result = <Widget>[];
  for (var i = 0; i < rows.length; i++) {
    result.add(rows[i]);
    if (i != rows.length - 1) result.add(const AppHDivider());
  }
  return result;
}
