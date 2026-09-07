import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../../../core/domain/enums.dart';
import '../../../../core/router/app_routes.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/pill_list_item.dart';
import '../../../../l10n/app_localizations.dart';

String weaponTypeLabel(AppLocalizations l10n, WeaponType type) =>
    switch (type) {
      WeaponType.greatSword => l10n.screenWeaponGreatSword,
      WeaponType.longSword => l10n.screenWeaponLongSword,
      WeaponType.swordAndShield => l10n.screenWeaponSwordAndShield,
      WeaponType.dualBlades => l10n.screenWeaponDualBlades,
      WeaponType.hammer => l10n.screenWeaponHammer,
      WeaponType.huntingHorn => l10n.screenWeaponHuntingHorn,
      WeaponType.lance => l10n.screenWeaponLance,
      WeaponType.gunlance => l10n.screenWeaponGunlance,
      WeaponType.lightBowgun => l10n.screenWeaponLightBowgun,
      WeaponType.heavyBowgun => l10n.screenWeaponHeavyBowgun,
      WeaponType.bow => l10n.screenWeaponBow,
    };

class const WeaponTypeListView({
  required final VoidCallback openDrawer,
  required final VoidCallback openSearch,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.screenWeaponTypeList,
        navigation: AppTopBarNavigation.menu,
        onNavigationTap: openDrawer,
        onSearchTap: openSearch,
      ),
      body: ListView.separated(
        padding: context.scrollPadding(
          const EdgeInsets.fromLTRB(
            AppPadding.medium,
            0,
            AppPadding.medium,
            AppPadding.small,
          ),
        ),
        itemCount: WeaponType.values.length,
        separatorBuilder: (context, index) => const AppHDivider(),
        itemBuilder: (context, index) {
          final type = WeaponType.values[index];
          return PillListItem(
            child: ListItemLayout(
              leading: WeaponEntityIcon(
                type: type,
                rarity: 1,
                size: AppSize.extraLarge,
              ),
              headline: Text(weaponTypeLabel(l10n, type)),
              onTap: () => context.push(AppRoutes.weaponTree(type.dbValue)),
            ),
          );
        },
      ),
    );
  }
}
