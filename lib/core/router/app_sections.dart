import '../../l10n/app_localizations.dart';
import 'app_routes.dart';

class const AppSection({
  required final String icon,
  required final String path,
  required final String Function(AppLocalizations) label,
});

final appSections = <AppSection>[
  AppSection(
    icon: 'assets/images/ic_ui_monster.webp',
    path: AppRoutes.monsterListPath,
    label: (l10n) => l10n.screenMonsterList,
  ),
  AppSection(
    icon: 'assets/images/ic_ui_weapon.webp',
    path: AppRoutes.weaponTypeListPath,
    label: (l10n) => l10n.screenWeaponTypeList,
  ),
  AppSection(
    icon: 'assets/images/ic_armor_chest.webp',
    path: AppRoutes.armorSetListPath,
    label: (l10n) => l10n.screenArmorSetList,
  ),
  AppSection(
    icon: 'assets/images/ic_ui_quest.webp',
    path: AppRoutes.questListPath,
    label: (l10n) => l10n.screenQuestList,
  ),
  AppSection(
    icon: 'assets/images/ic_ui_item.webp',
    path: AppRoutes.itemListPath,
    label: (l10n) => l10n.screenItemList,
  ),
  AppSection(
    icon: 'assets/images/ic_ui_item_combination.webp',
    path: AppRoutes.itemCombinationListPath,
    label: (l10n) => l10n.screenItemCombinationList,
  ),
  AppSection(
    icon: 'assets/images/ic_ui_location.webp',
    path: AppRoutes.locationListPath,
    label: (l10n) => l10n.screenLocationList,
  ),
  AppSection(
    icon: 'assets/images/ic_ui_skill.webp',
    path: AppRoutes.skillTreeListPath,
    label: (l10n) => l10n.screenSkillTreeList,
  ),
  AppSection(
    icon: 'assets/images/ic_ui_decoration.webp',
    path: AppRoutes.decorationListPath,
    label: (l10n) => l10n.screenDecorationList,
  ),
  AppSection(
    icon: 'assets/images/ic_item_coin.webp',
    path: AppRoutes.veggieListPath,
    label: (l10n) => l10n.screenVeggieList,
  ),
  AppSection(
    icon: 'assets/images/ic_armor_set.webp',
    path: AppRoutes.userEquipmentSetListPath,
    label: (l10n) => l10n.screenUserEquipmentSetList,
  ),
];
