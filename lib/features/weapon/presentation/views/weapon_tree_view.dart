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
import '../../../../l10n/app_localizations.dart';
import '../../data/weapon_repository.dart';
import '../../domain/weapon.dart';
import 'weapon_type_list_view.dart' show weaponTypeLabel;

class const WeaponTreeView({
  required final String weaponType,
  required final VoidCallback navigateBack,
  required final VoidCallback openSearch,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final type = WeaponType.fromDb(weaponType);
    final title = weaponTypeLabel(l10n, type);

    return Scaffold(
      appBar: AppTopBar(
        title: title,
        navigation: AppTopBarNavigation.back,
        onNavigationTap: navigateBack,
        onSearchTap: openSearch,
      ),
      body: FutureBuilder<List<FlattenedWeaponNode>>(
        future: WeaponRepository().getWeaponTree(
          type,
          AppSettingsController.instance.locale.languageCode,
        ),
        builder: (context, snapshot) {
          final nodes = snapshot.data;
          if (nodes == null) {
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
            itemCount: nodes.length,
            separatorBuilder: (context, index) => const AppHDivider(),
            itemBuilder: (context, index) {
              final node = nodes[index];
              return Padding(
                padding: EdgeInsets.only(left: AppPadding.small * node.depth),
                child: _WeaponTreeTile(weapon: node.weapon),
              );
            },
          );
        },
      ),
    );
  }
}

class const _WeaponTreeTile({required final Weapon weapon})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Material(
      color: Theme.of(context).colorScheme.surface,
      borderRadius: BorderRadius.circular(AppRadius.small),
      clipBehavior: Clip.antiAlias,
      child: InkWell(
        onTap: () => context.push(AppRoutes.weaponDetail(weapon.id)),
        child: Padding(
          padding: const EdgeInsets.symmetric(
            horizontal: AppPadding.large,
            vertical: AppPadding.medium,
          ),
          child: Row(
            children: [
              WeaponEntityIcon(type: weapon.type, rarity: weapon.rarity),
              const SizedBox(width: AppSpacing.large),
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      weapon.name,
                      maxLines: 1,
                      overflow: TextOverflow.ellipsis,
                    ),
                    const SizedBox(height: AppSpacing.small),
                    Row(
                      children: [
                        Text('${weapon.attack}'),
                        if (weapon.element1 != null &&
                            weapon.element1Value != null) ...[
                          const SizedBox(width: AppSpacing.medium),
                          Image.asset(
                            'assets/images/${elementIconAsset(weapon.element1!)}.webp',
                            width: AppSize.tiny,
                            height: AppSize.tiny,
                          ),
                          Text('${weapon.element1Value}'),
                        ],
                        if (weapon.element2 != null &&
                            weapon.element2Value != null) ...[
                          const SizedBox(width: AppSpacing.medium),
                          Image.asset(
                            'assets/images/${elementIconAsset(weapon.element2!)}.webp',
                            width: AppSize.tiny,
                            height: AppSize.tiny,
                          ),
                          Text('${weapon.element2Value}'),
                        ],
                      ],
                    ),
                  ],
                ),
              ),
              Text(l10n.weaponRarity(weapon.rarity)),
            ],
          ),
        ),
      ),
    );
  }
}
