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
import '../../data/location_repository.dart';
import '../../domain/location.dart';

class const LocationListView({
  required final VoidCallback openDrawer,
  required final VoidCallback openSearch,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.screenLocationList,
        navigation: AppTopBarNavigation.menu,
        onNavigationTap: openDrawer,
        onSearchTap: openSearch,
      ),
      body: FutureBuilder<List<Location>>(
        future: LocationRepository().getLocationList(
          AppSettingsController.instance.locale.languageCode,
        ),
        builder: (context, snapshot) {
          final locations = snapshot.data;
          if (locations == null) {
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
            itemCount: locations.length,
            separatorBuilder: (context, index) => const AppHDivider(),
            itemBuilder: (context, index) {
              final location = locations[index];
              return PillListItem(
                child: ListItemLayout(
                  leading: EntityIcon(asset: locationIconAsset(location.id)),
                  headline: Text(
                    location.name,
                    style: Theme.of(context).textTheme.bodyLarge,
                  ),
                  onTap: () =>
                      context.push(AppRoutes.locationDetail(location.id)),
                ),
              );
            },
          );
        },
      ),
    );
  }
}
