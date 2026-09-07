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
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/pill_list_item.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/monster_repository.dart';
import '../../domain/monster_filter.dart';
import '../../domain/monster.dart';

class const MonsterListView({
  required final VoidCallback openDrawer,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<MonsterListView> createState() => _MonsterListViewState();
}

class _MonsterListViewState extends State<MonsterListView> {
  MonsterFilter _filter = const MonsterFilter();

  void _setFilter(MonsterFilter filter) => setState(() => _filter = filter);

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.screenMonsterList,
        navigation: AppTopBarNavigation.menu,
        onNavigationTap: widget.openDrawer,
        onSearchTap: widget.openSearch,
      ),
      body: Column(
        children: [
          _TypeFilter(filter: _filter, onFilterChange: _setFilter),
          Expanded(
            child: FutureBuilder<List<Monster>>(
              future: MonsterRepository().getMonsterList(
                AppSettingsController.instance.locale.languageCode,
                filter: _filter,
              ),
              builder: (context, snapshot) {
                final monsters = snapshot.data;
                if (monsters == null) {
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
                  itemCount: monsters.length,
                  separatorBuilder: (context, index) => const AppHDivider(),
                  itemBuilder: (context, index) {
                    final monster = monsters[index];
                    return PillListItem(
                      child: ListItemLayout(
                        leading: EntityIcon(
                          asset: monsterIconAsset(monster.id),
                        ),
                        headline: Text(monster.name),
                        supporting: Text(monster.ecology),
                        onTap: () =>
                            context.push(AppRoutes.monsterDetail(monster.id)),
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

class const _TypeFilter({
  required final MonsterFilter filter,
  required final ValueChanged<MonsterFilter> onFilterChange,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    String labelFor(MonsterType? type) => switch (type) {
      MonsterType.small => l10n.monsterFilterSizeSmall,
      MonsterType.large => l10n.monsterFilterSizeLarge,
      null => l10n.monsterFilterSizeAll,
    };

    return Padding(
      padding: const EdgeInsets.all(AppPadding.medium),
      child: Align(
        alignment: Alignment.centerLeft,
        child: FilterDropdown<MonsterType?>(
          value: filter.type,
          items: [null, ...MonsterType.values],
          labelBuilder: labelFor,
          onChanged: (type) => onFilterChange(MonsterFilter(type: type)),
        ),
      ),
    );
  }
}
