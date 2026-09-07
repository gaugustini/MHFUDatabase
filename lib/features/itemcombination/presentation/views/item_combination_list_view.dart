import 'package:flutter/material.dart';

import '../../../../core/domain/enums.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/filter_dropdown.dart';
import '../../../../core/widgets/pill_list_item.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/item_combination_repository.dart';
import '../../domain/item_combination.dart';
import '../widgets/combination_row.dart';

class const ItemCombinationListView({
  required final VoidCallback openDrawer,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<ItemCombinationListView> createState() =>
      _ItemCombinationListViewState();
}

class _ItemCombinationListViewState extends State<ItemCombinationListView> {
  ItemCombinationType? _type;

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.screenItemCombinationList,
        navigation: AppTopBarNavigation.menu,
        onNavigationTap: widget.openDrawer,
        onSearchTap: widget.openSearch,
      ),
      body: Column(
        children: [
          _TypeFilter(
            type: _type,
            onFilterChange: (type) => setState(() => _type = type),
          ),
          Expanded(
            child: FutureBuilder<List<ItemCombination>>(
              future: ItemCombinationRepository().getItemCombinationList(
                AppSettingsController.instance.locale.languageCode,
                type: _type,
              ),
              builder: (context, snapshot) {
                final combinations = snapshot.data;
                if (combinations == null) {
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
                  itemCount: combinations.length,
                  separatorBuilder: (context, index) => const AppHDivider(),
                  itemBuilder: (context, index) => PillListItem(
                    child: CombinationRow(combination: combinations[index]),
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}

class const _TypeFilter({
  required final ItemCombinationType? type,
  required final ValueChanged<ItemCombinationType?> onFilterChange,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    String labelFor(ItemCombinationType? type) => switch (type) {
      ItemCombinationType.normal => l10n.combinationFilterTypeNormal,
      ItemCombinationType.treasure => l10n.combinationFilterTypeTreasure,
      ItemCombinationType.alchemy => l10n.combinationFilterTypeAlchemy,
      null => l10n.combinationFilterTypeAll,
    };

    return Padding(
      padding: const EdgeInsets.all(AppPadding.medium),
      child: Align(
        alignment: Alignment.centerLeft,
        child: FilterDropdown<ItemCombinationType?>(
          value: type,
          items: const [null, ...ItemCombinationType.values],
          labelBuilder: labelFor,
          onChanged: onFilterChange,
        ),
      ),
    );
  }
}
