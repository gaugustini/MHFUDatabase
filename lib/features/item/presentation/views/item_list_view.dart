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
import '../../../../core/widgets/filter_pill.dart';
import '../../../../core/widgets/filter_sheet.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/mhfu_colors.dart';
import '../../../../core/widgets/pill_list_item.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/item_repository.dart';
import '../../domain/item_filter.dart';
import '../../domain/item.dart';

const _itemRarities = [1, 2, 3, 4, 5, 6, 7, 8];

class const ItemListView({
  required final VoidCallback openDrawer,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<ItemListView> createState() => _ItemListViewState();
}

class _ItemListViewState extends State<ItemListView> {
  ItemFilter _filter = const ItemFilter();

  void _setFilter(ItemFilter filter) => setState(() => _filter = filter);

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.screenItemList,
        navigation: AppTopBarNavigation.menu,
        onNavigationTap: widget.openDrawer,
        onSearchTap: widget.openSearch,
      ),
      body: Column(
        children: [
          _ItemFilterBar(filter: _filter, onFilterChange: _setFilter),
          Expanded(
            child: FutureBuilder<List<Item>>(
              future: ItemRepository().getItemList(
                AppSettingsController.instance.locale.languageCode,
                filter: _filter,
              ),
              builder: (context, snapshot) {
                final items = snapshot.data;
                if (items == null) {
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
                  itemCount: items.length,
                  separatorBuilder: (context, index) => const AppHDivider(),
                  itemBuilder: (context, index) =>
                      PillListItem(child: _ItemRow(item: items[index])),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}

class const _ItemFilterBar({
  required final ItemFilter filter,
  required final ValueChanged<ItemFilter> onFilterChange,
}) extends StatefulWidget {
  @override
  State<_ItemFilterBar> createState() => _ItemFilterBarState();
}

class _ItemFilterBarState extends State<_ItemFilterBar> {
  bool _rarityOpen = false;
  bool _iconOpen = false;
  bool _colorOpen = false;

  Future<void> _openRaritySheet(AppLocalizations l10n) async {
    setState(() => _rarityOpen = true);
    await showFilterSheet<int>(
      context: context,
      title: l10n.itemFilterRarity,
      items: _itemRarities,
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

  Future<void> _openIconSheet(AppLocalizations l10n) async {
    setState(() => _iconOpen = true);
    await showFilterSheet<ItemIconType>(
      context: context,
      title: l10n.itemFilterIcon,
      items: ItemIconType.values,
      selectedItems: widget.filter.icons ?? const [],
      onItemsSelected: (icons) =>
          widget.onFilterChange(widget.filter.copyWith(icons: icons)),
      itemBuilder: (icon, isSelected, onTap) => FilterPill(
        selected: isSelected,
        onTap: onTap,
        child: Image.asset(
          'assets/images/${itemIconAsset(icon)}.webp',
          width: AppSize.extraSmall,
          height: AppSize.extraSmall,
        ),
      ),
    );
    if (mounted) setState(() => _iconOpen = false);
  }

  Future<void> _openColorSheet(AppLocalizations l10n) async {
    setState(() => _colorOpen = true);
    await showFilterSheet<ItemIconColor>(
      context: context,
      title: l10n.itemFilterColor,
      items: ItemIconColor.values,
      selectedItems: widget.filter.iconColors ?? const [],
      onItemsSelected: (colors) =>
          widget.onFilterChange(widget.filter.copyWith(iconColors: colors)),
      itemBuilder: (color, isSelected, onTap) => FilterPill(
        selected: isSelected,
        onTap: onTap,
        child: Container(
          width: AppSize.extraSmall,
          height: AppSize.extraSmall,
          decoration: BoxDecoration(
            color: itemIconColorValue(color),
            shape: BoxShape.circle,
          ),
        ),
      ),
    );
    if (mounted) setState(() => _colorOpen = false);
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Align(
      alignment: Alignment.centerLeft,
      child: SingleChildScrollView(
        scrollDirection: Axis.horizontal,
        padding: const EdgeInsets.all(AppPadding.medium),
        child: Row(
          children: [
            FilterPill(
              selected: _rarityOpen,
              onTap: () => _openRaritySheet(l10n),
              child: Text(l10n.itemFilterRarity),
            ),
            const SizedBox(width: AppSpacing.medium),
            FilterPill(
              selected: _iconOpen,
              onTap: () => _openIconSheet(l10n),
              child: Text(l10n.itemFilterIcon),
            ),
            const SizedBox(width: AppSpacing.medium),
            FilterPill(
              selected: _colorOpen,
              onTap: () => _openColorSheet(l10n),
              child: Text(l10n.itemFilterColor),
            ),
          ],
        ),
      ),
    );
  }
}

class const _ItemRow({required final Item item}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListItemLayout(
      leading: ItemEntityIcon(
        type: item.iconType,
        color: item.iconColor,
        size: AppSize.medium,
      ),
      headline: Text(
        item.name,
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      onTap: () => context.push(AppRoutes.itemDetail(item.id)),
    );
  }
}

extension on ItemFilter {
  ItemFilter copyWith({
    List<int>? rarity,
    List<ItemIconType>? icons,
    List<ItemIconColor>? iconColors,
  }) {
    return ItemFilter(
      name: name,
      rarity: (rarity ?? this.rarity)?.isEmpty ?? true
          ? null
          : rarity ?? this.rarity,
      icons: (icons ?? this.icons)?.isEmpty ?? true
          ? null
          : icons ?? this.icons,
      iconColors: (iconColors ?? this.iconColors)?.isEmpty ?? true
          ? null
          : iconColors ?? this.iconColors,
    );
  }
}
