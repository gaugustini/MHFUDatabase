import '../../l10n/app_localizations.dart';
import '../router/app_routes.dart';

enum AppStartPage {
  home,
  monsterList,
  weaponTypeList,
  armorSetList,
  questList,
  itemList,
  itemCombinationList,
  locationList,
  skillTreeList,
  decorationList,
  veggieList,
  userEquipmentSetList,
  resumeLast,
}

extension AppStartPageRoute on AppStartPage {
  String? get path => switch (this) {
    AppStartPage.home => AppRoutes.homePath,
    AppStartPage.monsterList => AppRoutes.monsterListPath,
    AppStartPage.weaponTypeList => AppRoutes.weaponTypeListPath,
    AppStartPage.armorSetList => AppRoutes.armorSetListPath,
    AppStartPage.questList => AppRoutes.questListPath,
    AppStartPage.itemList => AppRoutes.itemListPath,
    AppStartPage.itemCombinationList => AppRoutes.itemCombinationListPath,
    AppStartPage.locationList => AppRoutes.locationListPath,
    AppStartPage.skillTreeList => AppRoutes.skillTreeListPath,
    AppStartPage.decorationList => AppRoutes.decorationListPath,
    AppStartPage.veggieList => AppRoutes.veggieListPath,
    AppStartPage.userEquipmentSetList => AppRoutes.userEquipmentSetListPath,
    AppStartPage.resumeLast => null,
  };
}

extension AppStartPageLabel on AppStartPage {
  String label(AppLocalizations l10n) => switch (this) {
    AppStartPage.home => l10n.screenHome,
    AppStartPage.monsterList => l10n.screenMonsterList,
    AppStartPage.weaponTypeList => l10n.screenWeaponTypeList,
    AppStartPage.armorSetList => l10n.screenArmorSetList,
    AppStartPage.questList => l10n.screenQuestList,
    AppStartPage.itemList => l10n.screenItemList,
    AppStartPage.itemCombinationList => l10n.screenItemCombinationList,
    AppStartPage.locationList => l10n.screenLocationList,
    AppStartPage.skillTreeList => l10n.screenSkillTreeList,
    AppStartPage.decorationList => l10n.screenDecorationList,
    AppStartPage.veggieList => l10n.screenVeggieList,
    AppStartPage.userEquipmentSetList => l10n.screenUserEquipmentSetList,
    AppStartPage.resumeLast => l10n.settingsStartPageResumeLast,
  };
}
