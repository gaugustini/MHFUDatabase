abstract final class AppRoutes {
  // Main Routes

  static const homeName = 'home';
  static const homePath = '/';

  static const searchName = 'search';
  static const searchPath = '/search';

  static const armorSetListName = 'armor-set-list';
  static const armorSetListPath = '/armor-set-list';

  static const decorationListName = 'decoration-list';
  static const decorationListPath = '/decoration-list';

  static const itemListName = 'item-list';
  static const itemListPath = '/item-list';

  static const itemCombinationListName = 'item-combination-list';
  static const itemCombinationListPath = '/item-combination-list';

  static const locationListName = 'location-list';
  static const locationListPath = '/location-list';

  static const monsterListName = 'monster-list';
  static const monsterListPath = '/monster-list';

  static const questListName = 'quest-list';
  static const questListPath = '/quest-list';

  static const skillTreeListName = 'skill-tree-list';
  static const skillTreeListPath = '/skill-tree-list';

  static const veggieListName = 'veggie-list';
  static const veggieListPath = '/veggie-list';

  static const weaponTypeListName = 'weapon-type-list';
  static const weaponTypeListPath = '/weapon-type-list';

  static const userEquipmentSetListName = 'user-equipment-set-list';
  static const userEquipmentSetListPath = '/user-equipment-set-list';

  static const settingsName = 'settings';
  static const settingsPath = '/settings';

  static const aboutName = 'about';
  static const aboutPath = '/about';

  // Detail Routes

  static const armorDetailName = 'armor-detail';
  static const armorDetailPath = '/armor-detail/:armorId';

  static const armorSetDetailName = 'armor-set-detail';
  static const armorSetDetailPath = '/armor-set-detail/:armorSetId';

  static const decorationDetailName = 'decoration-detail';
  static const decorationDetailPath = '/decoration-detail/:decorationId';

  static const itemDetailName = 'item-detail';
  static const itemDetailPath = '/item-detail/:itemId';

  static const locationDetailName = 'location-detail';
  static const locationDetailPath = '/location-detail/:locationId';

  static const monsterDetailName = 'monster-detail';
  static const monsterDetailPath = '/monster-detail/:monsterId';

  static const questDetailName = 'quest-detail';
  static const questDetailPath = '/quest-detail/:questId';

  static const skillTreeDetailName = 'skill-tree-detail';
  static const skillTreeDetailPath = '/skill-tree-detail/:skillTreeId';

  static const veggieDetailName = 'veggie-detail';
  static const veggieDetailPath = '/veggie-detail/:veggieId';

  static const weaponTreeName = 'weapon-tree';
  static const weaponTreePath = '/weapon-tree/:weaponType';

  static const weaponDetailName = 'weapon-detail';
  static const weaponDetailPath = '/weapon-detail/:weaponId';

  static const userEquipmentSetDetailName = 'user-equipment-set-detail';
  static const userEquipmentSetDetailPath =
      '/user-equipment-set-detail/:setId/:hunterType/:gender';

  static String armorDetail(int armorId) => '/armor-detail/$armorId';
  static String armorSetDetail(int armorSetId) =>
      '/armor-set-detail/$armorSetId';
  static String decorationDetail(int decorationId) =>
      '/decoration-detail/$decorationId';
  static String itemDetail(int itemId) => '/item-detail/$itemId';
  static String locationDetail(int locationId) =>
      '/location-detail/$locationId';
  static String monsterDetail(int monsterId) => '/monster-detail/$monsterId';
  static String questDetail(int questId) => '/quest-detail/$questId';
  static String skillTreeDetail(int skillTreeId) =>
      '/skill-tree-detail/$skillTreeId';
  static String veggieDetail(int veggieId) => '/veggie-detail/$veggieId';
  static String weaponTree(String weaponType) => '/weapon-tree/$weaponType';
  static String weaponDetail(int weaponId) => '/weapon-detail/$weaponId';
  static String userEquipmentSetDetail(
    int setId,
    String hunterType,
    String gender,
  ) => '/user-equipment-set-detail/$setId/$hunterType/$gender';
}
