import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../l10n/app_localizations.dart';
import '../router/app_routes.dart';
import '../router/app_sections.dart';
import '../theme/app_colors.dart';
import '../theme/app_dimensions.dart';

class const AppDrawer({
  required final String currentLocation,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final isDarkTheme = Theme.of(context).brightness == Brightness.dark;

    return Drawer(
      child: Column(
        children: [
          Container(
            color: primaryLight,
            padding: EdgeInsets.only(top: MediaQuery.of(context).padding.top),
            child: Image.asset(
              'assets/images/header.webp',
              width: double.infinity,
              fit: BoxFit.cover,
            ),
          ),
          Expanded(
            child: SafeArea(
              top: false,
              child: ListView(
                padding: EdgeInsets.zero,
                children: [
                  const SizedBox(height: AppPadding.medium),
                  _buildLogoTile(
                    context,
                    label: l10n.screenHome,
                    path: AppRoutes.homePath,
                  ),
                  for (final section in appSections)
                    _buildAssetTile(
                      context,
                      icon: section.icon,
                      label: section.label(l10n),
                      path: section.path,
                    ),
                  const Divider(height: AppPadding.large * 2),
                  _buildAssetTile(
                    context,
                    icon: isDarkTheme
                        ? 'assets/images/ic_ui_settings_white.webp'
                        : 'assets/images/ic_ui_settings_black.webp',
                    label: l10n.screenSettings,
                    path: AppRoutes.settingsPath,
                  ),
                  _buildAssetTile(
                    context,
                    icon: 'assets/images/ic_item_ticket.webp',
                    label: l10n.screenAbout,
                    path: AppRoutes.aboutPath,
                  ),
                  const SizedBox(height: AppSpacing.large),
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildAssetTile(
    BuildContext context, {
    required String icon,
    required String label,
    required String path,
  }) {
    final isSelected = currentLocation == path;
    final colorScheme = Theme.of(context).colorScheme;

    return _tile(
      context,
      leading: Image.asset(
        icon,
        width: AppSize.extraSmall,
        height: AppSize.extraSmall,
        color: isSelected ? colorScheme.primaryContainer : null,
        colorBlendMode: isSelected ? BlendMode.modulate : null,
      ),
      label: label,
      path: path,
      isSelected: isSelected,
    );
  }

  Widget _buildLogoTile(
    BuildContext context, {
    required String label,
    required String path,
  }) {
    return _tile(
      context,
      leading: ClipRRect(
        borderRadius: BorderRadius.circular(AppRadius.small),
        child: Image.asset(
          'assets/images/ic_launcher.webp',
          width: AppSize.extraSmall,
          height: AppSize.extraSmall,
        ),
      ),
      label: label,
      path: path,
      isSelected: currentLocation == path,
    );
  }

  Widget _tile(
    BuildContext context, {
    required Widget leading,
    required String label,
    required String path,
    required bool isSelected,
  }) {
    final colorScheme = Theme.of(context).colorScheme;

    return Padding(
      padding: const EdgeInsets.symmetric(
        horizontal: AppPadding.medium,
        vertical: AppPadding.small,
      ),
      child: Material(
        color: isSelected ? colorScheme.secondaryContainer : Colors.transparent,
        borderRadius: BorderRadius.circular(AppRadius.large),
        child: InkWell(
          borderRadius: BorderRadius.circular(AppRadius.large),
          onTap: () {
            Scaffold.of(context).closeDrawer();
            if (currentLocation != path) {
              context.push(path);
            }
          },
          child: Padding(
            padding: const EdgeInsets.symmetric(
              horizontal: AppPadding.large,
              vertical: AppPadding.large,
            ),
            child: Row(
              children: [
                leading,
                const SizedBox(width: AppSpacing.large),
                Text(
                  label,
                  style: TextStyle(
                    color: isSelected ? colorScheme.onSecondaryContainer : null,
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
