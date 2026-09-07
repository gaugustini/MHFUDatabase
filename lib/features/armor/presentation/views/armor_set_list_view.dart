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
import '../../../../core/widgets/filter_sheet.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/mhfu_colors.dart';
import '../../../../core/widgets/pill_list_item.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/armor_repository.dart';
import '../../domain/armor_filter.dart';
import '../../domain/armor.dart';

const _armorSetRarities = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

class const ArmorSetListView({
  required final VoidCallback openDrawer,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<ArmorSetListView> createState() => _ArmorSetListViewState();
}

class _ArmorSetListViewState extends State<ArmorSetListView> {
  ArmorSetFilter _filter = const ArmorSetFilter();

  void _setFilter(ArmorSetFilter filter) => setState(() => _filter = filter);

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.screenArmorSetList,
        navigation: AppTopBarNavigation.menu,
        onNavigationTap: widget.openDrawer,
        onSearchTap: widget.openSearch,
      ),
      body: Column(
        children: [
          _ArmorSetFilterBar(filter: _filter, onFilterChange: _setFilter),
          Expanded(
            child: FutureBuilder<List<ArmorSet>>(
              future: ArmorRepository().getArmorSetList(
                AppSettingsController.instance.locale.languageCode,
                filter: _filter,
              ),
              builder: (context, snapshot) {
                final armorSets = snapshot.data;
                if (armorSets == null) {
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
                  itemCount: armorSets.length,
                  separatorBuilder: (context, index) => const AppHDivider(),
                  itemBuilder: (context, index) {
                    final armorSet = armorSets[index];
                    return PillListItem(
                      child: ListItemLayout(
                        leading: EntityIcon(
                          asset: 'ic_armor_set',
                          tint: rarityColor(armorSet.rarity),
                        ),
                        headline: Text(
                          armorSet.name,
                          style: Theme.of(context).textTheme.bodyLarge,
                        ),
                        onTap: () => context.push(
                          AppRoutes.armorSetDetail(armorSet.id),
                        ),
                      ),
                    );
                  },
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}

class const _ArmorSetFilterBar({
  required final ArmorSetFilter filter,
  required final ValueChanged<ArmorSetFilter> onFilterChange,
}) extends StatefulWidget {
  @override
  State<_ArmorSetFilterBar> createState() => _ArmorSetFilterBarState();
}

class _ArmorSetFilterBarState extends State<_ArmorSetFilterBar> {
  bool _rarityOpen = false;

  Future<void> _openRaritySheet(AppLocalizations l10n) async {
    setState(() => _rarityOpen = true);
    await showFilterSheet<int>(
      context: context,
      title: l10n.armorSetFilterRarity,
      items: _armorSetRarities,
      selectedItems: widget.filter.rarity ?? const [],
      onItemsSelected: (rarity) =>
          widget.onFilterChange(widget.filter.copyWith(rarity: rarity)),
      itemBuilder: (rarity, isSelected, onTap) => FilterPill(
        selected: isSelected,
        onTap: onTap,
        child: Text(rarity.toString()),
      ),
    );
    if (mounted) setState(() => _rarityOpen = false);
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final filter = widget.filter;

    String labelForHunter(HunterType? hunterType) => switch (hunterType) {
      HunterType.blade => l10n.armorSetFilterHunterBlade,
      HunterType.gunner => l10n.armorSetFilterHunterGunner,
      HunterType.both || null => l10n.armorSetFilterHunterAll,
    };

    String labelForGender(Gender? gender) => switch (gender) {
      Gender.male => l10n.armorSetFilterGenderMale,
      Gender.female => l10n.armorSetFilterGenderFemale,
      Gender.both || null => l10n.armorSetFilterGenderAll,
    };

    return Align(
      alignment: Alignment.centerLeft,
      child: SingleChildScrollView(
        scrollDirection: Axis.horizontal,
        padding: const EdgeInsets.all(AppPadding.medium),
        child: Row(
          children: [
            FilterDropdown<HunterType?>(
              value: filter.hunterType,
              items: const [null, HunterType.blade, HunterType.gunner],
              labelBuilder: labelForHunter,
              onChanged: (hunterType) => widget.onFilterChange(
                filter.copyWith(hunterType: hunterType),
              ),
            ),
            const SizedBox(width: AppSpacing.medium),
            FilterDropdown<Gender?>(
              value: filter.gender,
              items: const [null, Gender.male, Gender.female],
              labelBuilder: labelForGender,
              onChanged: (gender) =>
                  widget.onFilterChange(filter.copyWith(gender: gender)),
            ),
            const SizedBox(width: AppSpacing.medium),
            FilterPill(
              selected: _rarityOpen,
              onTap: () => _openRaritySheet(l10n),
              child: Text(l10n.armorSetFilterRarity),
            ),
          ],
        ),
      ),
    );
  }
}

extension on ArmorSetFilter {
  ArmorSetFilter copyWith({
    HunterType? hunterType,
    Gender? gender,
    List<int>? rarity,
  }) {
    return ArmorSetFilter(
      name: name,
      rank: rank,
      hunterType: hunterType ?? this.hunterType,
      gender: gender ?? this.gender,
      skills: skills,
      rarity: (rarity ?? this.rarity)?.isEmpty ?? true
          ? null
          : rarity ?? this.rarity,
    );
  }
}
