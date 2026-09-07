import 'package:flutter/material.dart' hide Decoration;
import 'package:go_router/go_router.dart';

import '../../../../core/router/app_routes.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/mhfu_colors.dart';
import '../../../../core/widgets/pill_list_item.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/decoration_repository.dart';
import '../../domain/decoration.dart';

class const DecorationListView({
  required final VoidCallback openDrawer,
  required final VoidCallback openSearch,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.screenDecorationList,
        navigation: AppTopBarNavigation.menu,
        onNavigationTap: openDrawer,
        onSearchTap: openSearch,
      ),
      body: FutureBuilder<List<Decoration>>(
        future: DecorationRepository().getDecorationList(
          AppSettingsController.instance.locale.languageCode,
        ),
        builder: (context, snapshot) {
          final decorations = snapshot.data;
          if (decorations == null) {
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
            itemCount: decorations.length,
            separatorBuilder: (context, index) => const AppHDivider(),
            itemBuilder: (context, index) {
              final decoration = decorations[index];
              return PillListItem(
                child: ListItemLayout(
                  leading: EntityIcon(
                    asset: 'ic_ui_decoration',
                    tint: itemIconColorValue(decoration.color),
                    size: AppSize.medium,
                  ),
                  headline: Text(decoration.name),
                  onTap: () =>
                      context.push(AppRoutes.decorationDetail(decoration.id)),
                ),
              );
            },
          );
        },
      ),
    );
  }
}
