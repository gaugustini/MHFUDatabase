import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../../../core/domain/enums.dart';
import '../../../../core/router/app_routes.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/animated_page_content.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/button_page.dart';
import '../../../../core/widgets/detail_header.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/filter_dropdown.dart';
import '../../../../core/widgets/info_dialog.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/mhfu_colors.dart';
import '../../../../core/widgets/quest_group_label.dart';
import '../../../../core/widgets/section_card.dart';
import '../../../../core/widgets/surface_card.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/monster_repository.dart';
import '../../domain/monster.dart';

enum _MonsterPage { summary, damage, reward, quest }

class const MonsterDetailView({
  required final int monsterId,
  required final VoidCallback navigateBack,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<MonsterDetailView> createState() => _MonsterDetailViewState();
}

class _MonsterDetailViewState extends State<MonsterDetailView> {
  late final Future<Monster> _future = MonsterRepository().getMonster(
    widget.monsterId,
    AppSettingsController.instance.locale.languageCode,
  );
  _MonsterPage _page = _MonsterPage.summary;
  Rank? _rewardRank;

  void _goToPage(_MonsterPage page) => setState(() => _page = page);

  void _handleBack() {
    if (_page == _MonsterPage.summary) {
      widget.navigateBack();
    } else {
      _goToPage(_MonsterPage.summary);
    }
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return FutureBuilder<Monster>(
      future: _future,
      builder: (context, snapshot) {
        final monster = snapshot.data;

        return PopScope(
          canPop: _page == _MonsterPage.summary,
          onPopInvokedWithResult: (didPop, result) {
            if (!didPop) _goToPage(_MonsterPage.summary);
          },
          child: Scaffold(
            appBar: AppTopBar(
              title: monster?.name ?? l10n.screenMonsterDetail,
              navigation: AppTopBarNavigation.back,
              onNavigationTap: _handleBack,
              onSearchTap: widget.openSearch,
              actions: [
                if (_page == _MonsterPage.reward)
                  IconButton(
                    icon: const Icon(Icons.help_outline),
                    onPressed: () => _showRewardInfoDialog(context),
                  ),
              ],
            ),
            body: monster == null
                ? const Center(child: CircularProgressIndicator())
                : AnimatedPageContent<_MonsterPage>(
                    value: _page,
                    index: (page) => page.index,
                    builder: (context, page) => switch (page) {
                      _MonsterPage.summary => _SummaryPage(
                        monster: monster,
                        onChangePage: _goToPage,
                      ),
                      _MonsterPage.damage => _DamagePage(monster: monster),
                      _MonsterPage.reward => _RewardPage(
                        monster: monster,
                        rank: _rewardRank,
                        onChangeRank: (rank) =>
                            setState(() => _rewardRank = rank),
                      ),
                      _MonsterPage.quest => _QuestPage(monster: monster),
                    },
                  ),
          ),
        );
      },
    );
  }
}

void _showRewardInfoDialog(BuildContext context) {
  final l10n = AppLocalizations.of(context)!;
  showInfoDialog(
    context,
    title: l10n.monsterRewardInfoTitle,
    children: [
      InfoSectionHeader(text: l10n.monsterRewardInfoRankCategoriesHeader),
      InfoBullet(
        label: l10n.monsterRewardInfoRankBeginnerLabel,
        description: l10n.monsterRewardInfoRankBeginnerDescription,
      ),
      InfoBullet(
        label: l10n.monsterRewardInfoRankLowLabel,
        description: l10n.monsterRewardInfoRankLowDescription,
      ),
      InfoBullet(
        label: l10n.monsterRewardInfoRankHighLabel,
        description: l10n.monsterRewardInfoRankHighDescription,
      ),
      InfoBullet(
        label: l10n.monsterRewardInfoRankGLabel,
        description: l10n.monsterRewardInfoRankGDescription,
      ),
      InfoBullet(
        label: l10n.monsterRewardInfoRankTreasureLabel,
        description: l10n.monsterRewardInfoRankTreasureDescription,
      ),
      InfoBullet(
        label: l10n.monsterRewardInfoRankTrainingLabel,
        description: l10n.monsterRewardInfoRankTrainingDescription,
      ),
      const SizedBox(height: AppSpacing.medium),
      InfoSectionHeader(text: l10n.monsterRewardInfoRewardsHeader),
      InfoBullet(
        label: l10n.monsterRewardInfoShinyDropLabel,
        description: l10n.monsterRewardInfoShinyDropDescription,
      ),
      InfoBullet(
        label: l10n.monsterRewardInfoCarvingLabel,
        description: l10n.monsterRewardInfoCarvingDescription,
      ),
      InfoBullet(
        label: l10n.monsterRewardInfoCaptureLabel,
        description: l10n.monsterRewardInfoCaptureDescription,
      ),
      InfoBullet(
        label: l10n.monsterRewardInfoBreakablesLabel,
        description: l10n.monsterRewardInfoBreakablesDescription,
      ),
      InfoBullet(
        label: l10n.monsterRewardInfoDropRatesLabel,
        description: l10n.monsterRewardInfoDropRatesDescription,
      ),
      const SizedBox(height: AppSpacing.medium),
      InfoSectionHeader(text: l10n.monsterRewardInfoNoteHeader),
      Text(
        l10n.monsterRewardInfoNoteDescription,
        style: Theme.of(context).textTheme.bodyMedium,
      ),
    ],
  );
}

class const _SummaryPage({
  required final Monster monster,
  required final ValueChanged<_MonsterPage> onChangePage,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

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
          child: DetailHeader(
            icon: EntityIcon(asset: monsterIconAsset(monster.id)),
            title: monster.name,
            subtitle: monster.ecology,
            description: monster.description,
          ),
        ),
        if (monster.damageStats != null && monster.damageStats!.isNotEmpty) ...[
          const SizedBox(height: AppSpacing.medium),
          ButtonPage(
            title: l10n.monsterDamageStats,
            onTap: () => onChangePage(_MonsterPage.damage),
          ),
        ],
        if (monster.rewards != null && monster.rewards!.isNotEmpty) ...[
          const SizedBox(height: AppSpacing.medium),
          ButtonPage(
            title: l10n.monsterRewards,
            onTap: () => onChangePage(_MonsterPage.reward),
          ),
        ],
        if (monster.quests != null && monster.quests!.isNotEmpty) ...[
          const SizedBox(height: AppSpacing.medium),
          ButtonPage(
            title: l10n.monsterQuests,
            onTap: () => onChangePage(_MonsterPage.quest),
          ),
        ],
        if (monster.itemEffectiveness != null) ...[
          const SizedBox(height: AppSpacing.medium),
          SurfaceCard(
            child: _ItemEffectiveness(
              effectiveness: monster.itemEffectiveness!,
            ),
          ),
        ],
      ],
    );
  }
}

class const _DamagePage({required final Monster monster})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final damage = monster.damageStats ?? const <MonsterDamageStats>[];
    final ailments = monster.ailmentStats ?? const <MonsterAilmentStats>[];

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
        SurfaceCard(child: _PhysicalDamageTable(damage: damage)),
        const SizedBox(height: AppSpacing.medium),
        SurfaceCard(child: _ElementalDamageTable(damage: damage)),
        if (ailments.isNotEmpty) ...[
          const SizedBox(height: AppSpacing.medium),
          SurfaceCard(child: _AilmentsTable(ailments: ailments)),
        ],
      ],
    );
  }
}

class const _PhysicalDamageTable({
  required final List<MonsterDamageStats> damage,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Column(
      children: [
        _TableHeader(
          title: l10n.monsterPhysicalDamage,
          columns: [
            _TableHeaderIcon(
              tooltip: l10n.monsterCutDamage,
              asset: weaponTypeIconAsset(WeaponType.greatSword),
            ),
            _TableHeaderIcon(
              tooltip: l10n.monsterImpactDamage,
              asset: weaponTypeIconAsset(WeaponType.hammer),
            ),
            _TableHeaderIcon(
              tooltip: l10n.monsterShotDamage,
              asset: itemIconAsset(ItemIconType.shell),
            ),
          ],
        ),
        for (final stat in damage)
          _TableRow(
            label: stat.name,
            values: ['${stat.cut}', '${stat.impact}', '${stat.shot}'],
          ),
      ],
    );
  }
}

class const _ElementalDamageTable({
  required final List<MonsterDamageStats> damage,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Column(
      children: [
        _TableHeader(
          title: l10n.monsterElementalDamage,
          columns: [
            _TableHeaderIcon(
              tooltip: l10n.monsterFireDamage,
              asset: elementIconAsset(WeaponElement.fire),
            ),
            _TableHeaderIcon(
              tooltip: l10n.monsterWaterDamage,
              asset: elementIconAsset(WeaponElement.water),
            ),
            _TableHeaderIcon(
              tooltip: l10n.monsterThunderDamage,
              asset: elementIconAsset(WeaponElement.thunder),
            ),
            _TableHeaderIcon(
              tooltip: l10n.monsterIceDamage,
              asset: elementIconAsset(WeaponElement.ice),
            ),
            _TableHeaderIcon(
              tooltip: l10n.monsterDragonDamage,
              asset: elementIconAsset(WeaponElement.dragon),
            ),
          ],
        ),
        for (final stat in damage)
          _TableRow(
            label: stat.name,
            values: [
              stat.fire == 0 ? '-' : '${stat.fire}',
              stat.water == 0 ? '-' : '${stat.water}',
              stat.thunder == 0 ? '-' : '${stat.thunder}',
              stat.ice == 0 ? '-' : '${stat.ice}',
              stat.dragon == 0 ? '-' : '${stat.dragon}',
            ],
          ),
      ],
    );
  }
}

class const _AilmentsTable({required final List<MonsterAilmentStats> ailments})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Column(
      children: [
        _TableHeader(
          title: null,
          leading: Expanded(
            child: Center(
              child: Text(
                l10n.monsterAilmentStatus,
                style: Theme.of(context).textTheme.titleSmall?.copyWith(
                  color: Theme.of(context).colorScheme.onSecondaryContainer,
                ),
              ),
            ),
          ),
          columns: [
            _TableHeaderText(label: l10n.monsterAilmentInitial),
            _TableHeaderText(label: l10n.monsterAilmentIncrease),
            _TableHeaderText(label: l10n.monsterAilmentMax),
            _TableHeaderText(label: l10n.monsterAilmentDuration),
            _TableHeaderText(label: l10n.monsterAilmentDamage),
          ],
        ),
        for (final ailment in ailments)
          _TableRow(
            leading: Expanded(
              child: Center(
                child: EntityIcon(
                  asset: ailmentIconAsset(ailment.type),
                  size: AppSize.extraSmall,
                ),
              ),
            ),
            values: [
              '${ailment.initial}',
              '${ailment.increase}',
              '${ailment.max}',
              '${ailment.duration}s',
              ailment.damage == 0 ? '-' : '${ailment.damage}',
            ],
          ),
      ],
    );
  }
}

class const _TableHeader({
  final String? title,
  final Widget? leading,
  required final List<Widget> columns,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).colorScheme;

    return Container(
      width: double.infinity,
      color: colors.secondaryContainer,
      padding: const EdgeInsets.all(AppPadding.large),
      child: Row(
        children: [
          if (leading != null)
            leading!
          else if (title != null)
            Expanded(
              flex: 5,
              child: Text(
                title!,
                style: Theme.of(context).textTheme.titleSmall?.copyWith(
                  color: colors.onSecondaryContainer,
                ),
              ),
            ),
          for (final column in columns) Expanded(child: column),
        ],
      ),
    );
  }
}

class const _TableHeaderIcon({
  required final String tooltip,
  required final String? asset,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Tooltip(
        message: tooltip,
        child: asset == null
            ? const SizedBox.shrink()
            : Image.asset(
                'assets/images/$asset.webp',
                width: AppSize.extraSmall,
                height: AppSize.extraSmall,
              ),
      ),
    );
  }
}

class const _TableHeaderText({required final String label})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
      child: Text(
        label,
        textAlign: TextAlign.center,
        maxLines: 1,
        overflow: TextOverflow.ellipsis,
        style: Theme.of(context).textTheme.titleSmall?.copyWith(
          color: Theme.of(context).colorScheme.onSecondaryContainer,
        ),
      ),
    );
  }
}

class const _TableRow({
  final String? label,
  final Widget? leading,
  required final List<String> values,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      color: Theme.of(context).colorScheme.surface,
      padding: const EdgeInsets.symmetric(
        horizontal: AppPadding.large,
        vertical: AppPadding.medium,
      ),
      child: Row(
        children: [
          if (leading != null)
            leading!
          else if (label != null)
            Expanded(
              flex: 5,
              child: Text(
                label!,
                style: Theme.of(context).textTheme.bodyMedium,
              ),
            ),
          for (final value in values)
            Expanded(
              child: Text(
                value,
                textAlign: TextAlign.center,
                style: Theme.of(context).textTheme.bodyMedium,
              ),
            ),
        ],
      ),
    );
  }
}

class const _ItemEffectiveness({
  required final MonsterItemEffectiveness effectiveness,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    String pairedTooltip(
      bool normal,
      bool enraged,
      String normalLabel,
      String enragedLabel,
    ) =>
        '${normal ? '✓' : '✗'} $normalLabel\n${enraged ? '✓' : '✗'} $enragedLabel';

    return Padding(
      padding: const EdgeInsets.all(AppPadding.large),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            l10n.monsterItemEffectiveness,
            style: Theme.of(context).textTheme.titleSmall,
          ),
          const SizedBox(height: AppSpacing.medium),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              _EffectivenessIcon(
                tooltip: l10n.monsterFlashBomb,
                asset: itemIconAsset(ItemIconType.ball),
                tint: itemIconColorValue(ItemIconColor.yellow),
                effective: effectiveness.flashBomb,
              ),
              _EffectivenessIcon(
                tooltip: pairedTooltip(
                  effectiveness.sonicBombNormal,
                  effectiveness.sonicBombEnraged,
                  l10n.monsterSonicBombNormal,
                  l10n.monsterSonicBombEnraged,
                ),
                asset: itemIconAsset(ItemIconType.ball),
                tint: itemIconColorValue(ItemIconColor.gray),
                effective:
                    effectiveness.sonicBombNormal ||
                    effectiveness.sonicBombEnraged,
              ),
              _EffectivenessIcon(
                tooltip: l10n.monsterShockTrap,
                asset: itemIconAsset(ItemIconType.trap),
                tint: itemIconColorValue(ItemIconColor.purple),
                effective: effectiveness.shockTrap,
              ),
              _EffectivenessIcon(
                tooltip: pairedTooltip(
                  effectiveness.pitfallTrapNormal,
                  effectiveness.pitfallTrapEnraged,
                  l10n.monsterPitfallTrapNormal,
                  l10n.monsterPitfallTrapEnraged,
                ),
                asset: itemIconAsset(ItemIconType.trap),
                tint: itemIconColorValue(ItemIconColor.green),
                effective:
                    effectiveness.pitfallTrapNormal ||
                    effectiveness.pitfallTrapEnraged,
              ),
              _EffectivenessIcon(
                tooltip: l10n.monsterCanUseMeat,
                asset: itemIconAsset(ItemIconType.meat),
                tint: itemIconColorValue(ItemIconColor.red),
                effective: effectiveness.canUseMeat,
              ),
              _EffectivenessIcon(
                tooltip: l10n.monsterCanUseDungBomb,
                asset: itemIconAsset(ItemIconType.dung),
                tint: itemIconColorValue(ItemIconColor.yellow),
                effective: effectiveness.canUseDungBomb,
              ),
            ],
          ),
        ],
      ),
    );
  }
}

class const _EffectivenessIcon({
  required final String tooltip,
  required final String? asset,
  required final Color tint,
  required final bool effective,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).colorScheme;

    return Tooltip(
      message: tooltip,
      child: SizedBox(
        width: AppSize.large,
        height: AppSize.large,
        child: Stack(
          clipBehavior: Clip.none,
          children: [
            Opacity(
              opacity: effective ? 1 : 0.3,
              child: asset == null
                  ? const SizedBox.shrink()
                  : Image.asset(
                      'assets/images/$asset.webp',
                      fit: BoxFit.contain,
                      color: tint,
                      colorBlendMode: BlendMode.modulate,
                    ),
            ),
            Positioned(
              bottom: -2,
              right: -2,
              child: Container(
                padding: const EdgeInsets.all(1),
                decoration: BoxDecoration(
                  color: colors.surface,
                  shape: BoxShape.circle,
                ),
                child: Icon(
                  effective ? Icons.check_circle : Icons.cancel,
                  size: AppSize.tiny,
                  color: effective
                      ? const Color(0xFF30C030)
                      : const Color(0xFFC03030),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

String _rankLabel(AppLocalizations l10n, Rank rank) => switch (rank) {
  Rank.low => l10n.rankLow,
  Rank.high => l10n.rankHigh,
  Rank.g => l10n.rankG,
  Rank.treasure => l10n.rankTreasure,
  Rank.unranked => l10n.rankUnranked,
  Rank.training => l10n.rankTraining,
};

class const _RewardPage({
  required final Monster monster,
  required final Rank? rank,
  required final ValueChanged<Rank> onChangeRank,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final rewardsByRank =
        monster.rewards ?? const <Rank, List<MonsterReward>>{};
    final ranks = rewardsByRank.keys.toList();
    final selectedRank = rank ?? (ranks.isEmpty ? null : ranks.first);
    final rewards = selectedRank == null
        ? const <MonsterReward>[]
        : rewardsByRank[selectedRank] ?? const [];
    final rewardsByCondition = <String, List<MonsterReward>>{};
    for (final reward in rewards) {
      (rewardsByCondition[reward.condition] ??= []).add(reward);
    }

    return Column(
      children: [
        if (ranks.length > 1)
          Padding(
            padding: const EdgeInsets.all(AppPadding.medium),
            child: Align(
              alignment: Alignment.centerLeft,
              child: FilterDropdown<Rank>(
                value: selectedRank!,
                items: ranks,
                labelBuilder: (value) => _rankLabel(l10n, value),
                onChanged: onChangeRank,
              ),
            ),
          ),
        Expanded(
          child: ListView(
            padding: context.scrollPadding(
              const EdgeInsets.fromLTRB(
                AppPadding.medium,
                0,
                AppPadding.medium,
                AppPadding.small,
              ),
            ),
            children: [
              for (final entry in rewardsByCondition.entries) ...[
                SectionCard(
                  title: entry.key,
                  margin: EdgeInsets.zero,
                  child: Column(
                    children: [
                      for (final reward in entry.value)
                        _RewardListItem(reward: reward),
                    ],
                  ),
                ),
                const SizedBox(height: AppSpacing.medium),
              ],
            ],
          ),
        ),
      ],
    );
  }
}

class const _RewardListItem({required final MonsterReward reward})
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
          Flexible(child: Text(reward.item.name)),
          const SizedBox(width: AppSpacing.medium),
          Text(
            'x${reward.quantity}',
            style: Theme.of(context).textTheme.bodyMedium?.copyWith(
              color: Theme.of(context).colorScheme.onSurfaceVariant,
            ),
          ),
        ],
      ),
      trailing: reward.percentage == null
          ? null
          : Text('${reward.percentage}%'),
      onTap: () => context.push(AppRoutes.itemDetail(reward.item.id)),
    );
  }
}

class const _QuestPage({required final Monster monster})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final quests = monster.quests ?? const [];

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
        for (final quest in quests)
          SurfaceCard(
            margin: const EdgeInsets.only(bottom: AppSpacing.medium),
            child: ListItemLayout(
              leading: QuestGoalIcon(goal: quest.goalType),
              headline: Text(
                quest.name,
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
              ),
              trailing: Text(questGroupSummaryLabel(l10n, quest.group)),
              onTap: () => context.push(AppRoutes.questDetail(quest.id)),
            ),
          ),
      ],
    );
  }
}
