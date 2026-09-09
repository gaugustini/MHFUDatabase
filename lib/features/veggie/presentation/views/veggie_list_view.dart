import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../../../core/router/app_routes.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/pill_list_item.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/veggie_repository.dart';
import '../../domain/veggie.dart';

String _areaLabel(AppLocalizations l10n, int area) =>
    area == 0 ? l10n.locationBaseCamp : l10n.locationArea(area);

class const VeggieListView({
  required final VoidCallback openDrawer,
  required final VoidCallback openSearch,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.screenVeggieList,
        navigation: AppTopBarNavigation.menu,
        onNavigationTap: openDrawer,
        onSearchTap: openSearch,
      ),
      body: FutureBuilder<List<VeggieLocation>>(
        future: VeggieRepository().getVeggieLocationList(
          AppSettingsController.instance.locale.languageCode,
        ),
        builder: (context, snapshot) {
          final veggieLocations = snapshot.data;
          if (veggieLocations == null) {
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
            itemCount: veggieLocations.length,
            separatorBuilder: (context, index) => const AppHDivider(),
            itemBuilder: (context, index) {
              final veggieLocation = veggieLocations[index];
              return PillListItem(
                child: ListItemLayout(
                  leading: EntityIcon(
                    asset: locationIconAsset(veggieLocation.location.id),
                  ),
                  headline: Text(
                    veggieLocation.location.name,
                    style: Theme.of(context).textTheme.bodyLarge,
                  ),
                  supporting: Text(
                    _areaLabel(l10n, veggieLocation.locationArea),
                    style: Theme.of(context).textTheme.bodySmall,
                  ),
                  onTap: () =>
                      context.push(AppRoutes.veggieDetail(veggieLocation.id)),
                ),
              );
            },
          );
        },
      ),
    );
  }
}
