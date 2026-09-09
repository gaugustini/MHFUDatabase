import 'dart:async';

import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../../../core/domain/enums.dart';
import '../../../../core/router/app_routes.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/mhfu_colors.dart';
import '../../../../core/widgets/pill_list_item.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/search_repository.dart';
import '../../domain/search_results.dart';

class const SearchView({
  required final VoidCallback navigateBack,
  super.key,
}) extends StatefulWidget {
  @override
  State<SearchView> createState() => _SearchViewState();
}

class _SearchViewState extends State<SearchView> {
  final _repository = SearchRepository();
  final _controller = TextEditingController();
  final _focusNode = FocusNode();
  Timer? _debounce;
  SearchResults? _results;

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addPostFrameCallback(
      (_) => _focusNode.requestFocus(),
    );
  }

  @override
  void dispose() {
    _debounce?.cancel();
    _controller.dispose();
    _focusNode.dispose();
    super.dispose();
  }

  void _onQueryChanged(String query) {
    _debounce?.cancel();
    if (query.isEmpty) {
      setState(() => _results = null);
      return;
    }
    _debounce = Timer(const Duration(milliseconds: 300), () async {
      final results = await _repository.search(
        query,
        AppSettingsController.instance.locale.languageCode,
      );
      if (mounted) setState(() => _results = results);
    });
  }

  void _onClear() {
    _controller.clear();
    _focusNode.requestFocus();
    setState(() => _results = null);
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final colors = Theme.of(context).colorScheme;
    final isCompact = Theme.of(context).visualDensity.horizontal < 0;
    final barPadding = isCompact ? AppPadding.medium : AppPadding.small;

    return Scaffold(
      appBar: AppBar(
        scrolledUnderElevation: 0,
        titleSpacing: 0,
        automaticallyImplyLeading: false,
        title: Padding(
          padding: EdgeInsets.all(barPadding),
          child: TextField(
            controller: _controller,
            focusNode: _focusNode,
            style: Theme.of(context).textTheme.titleLarge,
            decoration: InputDecoration(
              filled: true,
              fillColor: colors.surface,
              border: OutlineInputBorder(
                borderRadius: BorderRadius.circular(AppRadius.medium),
                borderSide: BorderSide.none,
              ),
              contentPadding: const EdgeInsets.symmetric(
                horizontal: AppSpacing.medium,
              ),
              prefixIcon: IconButton(
                icon: const Icon(Icons.arrow_back),
                onPressed: widget.navigateBack,
              ),
              hint: Row(
                mainAxisSize: MainAxisSize.min,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Icon(Icons.search, size: AppSize.extraSmall),
                  const SizedBox(width: AppSpacing.medium),
                  Text(
                    l10n.searchHint,
                    style: Theme.of(context).textTheme.titleLarge,
                  ),
                ],
              ),
              suffixIcon: ValueListenableBuilder(
                valueListenable: _controller,
                builder: (context, value, _) => value.text.isEmpty
                    ? const SizedBox.shrink()
                    : IconButton(
                        icon: const Icon(Icons.clear),
                        onPressed: _onClear,
                      ),
              ),
            ),
            textInputAction: TextInputAction.search,
            onChanged: _onQueryChanged,
          ),
        ),
      ),
      body: _buildResults(l10n),
    );
  }

  Widget _buildResults(AppLocalizations l10n) {
    final results = _results;
    if (results == null) return const SizedBox.shrink();
    if (results.isEmpty) return Center(child: Text(l10n.searchEmpty));

    final rows = [
      for (final location in results.locations)
        _SearchRow(
          icon: EntityIcon(
            asset: locationIconAsset(location.id),
            size: AppSize.small,
          ),
          name: location.name,
          trailing: l10n.searchLocation,
          onTap: () => context.push(AppRoutes.locationDetail(location.id)),
        ),
      for (final monster in results.monsters)
        _SearchRow(
          icon: EntityIcon(
            asset: monsterIconAsset(monster.id),
            size: AppSize.small,
          ),
          name: monster.name,
          trailing: l10n.searchMonster,
          onTap: () => context.push(AppRoutes.monsterDetail(monster.id)),
        ),
      for (final skillTree in results.skillTrees)
        _SearchRow(
          icon: const ItemEntityIcon(
            type: ItemIconType.armorStone,
            color: ItemIconColor.white,
            size: AppSize.small,
          ),
          name: skillTree.name,
          trailing: l10n.searchSkillTree,
          onTap: () => context.push(AppRoutes.skillTreeDetail(skillTree.id)),
        ),
      for (final skill in results.skills)
        _SearchRow(
          icon: const ItemEntityIcon(
            type: ItemIconType.armorStone,
            color: ItemIconColor.white,
            size: AppSize.small,
          ),
          name: skill.name,
          trailing: l10n.searchSkill,
          onTap: () =>
              context.push(AppRoutes.skillTreeDetail(skill.skillTreeId)),
        ),
      for (final quest in results.quests)
        _SearchRow(
          icon: QuestGoalIcon(goal: quest.goalType, size: AppSize.small),
          name: quest.name,
          trailing: _searchQuestLabel(l10n, quest.group),
          onTap: () => context.push(AppRoutes.questDetail(quest.id)),
        ),
      for (final item in results.items)
        _SearchRow(
          icon: ItemEntityIcon(
            type: item.iconType,
            color: item.iconColor,
            size: AppSize.small,
          ),
          name: item.name,
          trailing: l10n.searchItem,
          onTap: () => context.push(AppRoutes.itemDetail(item.id)),
        ),
      for (final decoration in results.decorations)
        _SearchRow(
          icon: EntityIcon(
            asset: 'ic_ui_decoration',
            tint: itemIconColorValue(decoration.color),
            size: AppSize.small,
          ),
          name: decoration.name,
          trailing: l10n.searchDecoration,
          onTap: () => context.push(AppRoutes.decorationDetail(decoration.id)),
        ),
      for (final armor in results.armors)
        _SearchRow(
          icon: EntityIcon(
            asset: equipmentTypeIconAsset(armor.type),
            tint: rarityColor(armor.rarity),
            size: AppSize.small,
          ),
          name: armor.name,
          trailing: switch (armor.hunterType) {
            HunterType.both => l10n.searchArmorBoth,
            HunterType.blade => l10n.searchArmorBlade,
            HunterType.gunner => l10n.searchArmorGunner,
          },
          onTap: () => context.push(AppRoutes.armorDetail(armor.id)),
        ),
      for (final weapon in results.weapons)
        _SearchRow(
          icon: WeaponEntityIcon(
            type: weapon.type,
            rarity: weapon.rarity,
            size: AppSize.small,
          ),
          name: weapon.name,
          trailing: l10n.searchWeapon,
          onTap: () => context.push(AppRoutes.weaponDetail(weapon.id)),
        ),
    ];

    return ListView.separated(
      padding: context.scrollPadding(
        const EdgeInsets.fromLTRB(
          AppPadding.medium,
          0,
          AppPadding.medium,
          AppPadding.small,
        ),
      ),
      itemCount: rows.length,
      separatorBuilder: (context, index) => const AppHDivider(),
      itemBuilder: (context, index) {
        final row = rows[index];
        return PillListItem(
          child: ListItemLayout(
            leading: row.icon,
            headline: Text(
              row.name,
              maxLines: 1,
              overflow: TextOverflow.ellipsis,
            ),
            trailing: Text(
              row.trailing,
              style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                color: Theme.of(context).colorScheme.onSurfaceVariant,
              ),
            ),
            onTap: row.onTap,
          ),
        );
      },
    );
  }
}

String _searchQuestLabel(AppLocalizations l10n, QuestGroup group) =>
    switch (group) {
      QuestGroup.village1 ||
      QuestGroup.village2 ||
      QuestGroup.village3 ||
      QuestGroup.village4 ||
      QuestGroup.village5 ||
      QuestGroup.village6 ||
      QuestGroup.village7 ||
      QuestGroup.village8 ||
      QuestGroup.village9 => l10n.searchQuestVillage,
      QuestGroup.hr1a ||
      QuestGroup.hr1b ||
      QuestGroup.hr1c ||
      QuestGroup.hr2 ||
      QuestGroup.hr3 ||
      QuestGroup.hr4 ||
      QuestGroup.hr5 ||
      QuestGroup.hr6 ||
      QuestGroup.hr7 ||
      QuestGroup.hr8 ||
      QuestGroup.hr9 => l10n.searchQuestGuild,
      QuestGroup.treasure => l10n.searchQuestTreasure,
      QuestGroup.event => l10n.searchQuestEvent,
      QuestGroup.beginnerBasic ||
      QuestGroup.beginnerWeapon ||
      QuestGroup.trainingBattle ||
      QuestGroup.trainingSpecial ||
      QuestGroup.trainingG ||
      QuestGroup.groupPractice => l10n.searchQuestTraining,
      QuestGroup.groupChallenge => l10n.searchQuestChallenge,
    };

class const _SearchRow({
  required final Widget icon,
  required final String name,
  required final String trailing,
  required final VoidCallback onTap,
}) {}
