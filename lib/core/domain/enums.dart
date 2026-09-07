enum Language {
  english('en'),
  spanish('es');

  const Language(this.code);

  final String code;

  static Language fromCode(String code) {
    final base = code.toLowerCase().split('-').first;
    return Language.values.firstWhere(
      (value) => value.code == base,
      orElse: () => Language.english,
    );
  }
}

enum Gender {
  both,
  male,
  female;

  String get dbValue => switch (this) {
    Gender.both => 'BOTH',
    Gender.male => 'MALE',
    Gender.female => 'FEMALE',
  };

  static Gender fromDb(String value) => switch (value) {
    'BOTH' => Gender.both,
    'MALE' => Gender.male,
    'FEMALE' => Gender.female,
    _ => throw ArgumentError('Invalid gender value: $value'),
  };
}

enum HunterType {
  both,
  blade,
  gunner;

  String get dbValue => switch (this) {
    HunterType.both => 'BOTH',
    HunterType.blade => 'BLADE',
    HunterType.gunner => 'GUNNER',
  };

  static HunterType fromDb(String value) => switch (value) {
    'BOTH' => HunterType.both,
    'BLADE' => HunterType.blade,
    'GUNNER' => HunterType.gunner,
    _ => throw ArgumentError('Invalid hunter type value: $value'),
  };
}

enum EquipmentType {
  armorHead,
  armorChest,
  armorArms,
  armorWaist,
  armorLegs,
  decoration,
  weapon;

  String get dbValue => switch (this) {
    EquipmentType.armorHead => 'HEAD',
    EquipmentType.armorChest => 'CHEST',
    EquipmentType.armorArms => 'ARMS',
    EquipmentType.armorWaist => 'WAIST',
    EquipmentType.armorLegs => 'LEGS',
    EquipmentType.decoration => 'DECORATION',
    EquipmentType.weapon => 'WEAPON',
  };

  static EquipmentType fromDb(String value) => switch (value) {
    'HEAD' => EquipmentType.armorHead,
    'CHEST' => EquipmentType.armorChest,
    'ARMS' => EquipmentType.armorArms,
    'WAIST' => EquipmentType.armorWaist,
    'LEGS' => EquipmentType.armorLegs,
    'DECORATION' => EquipmentType.decoration,
    'WEAPON' => EquipmentType.weapon,
    _ => throw ArgumentError('Invalid equipment type value: $value'),
  };
}

enum Rank {
  unranked,
  low,
  high,
  g,
  treasure,
  training;

  String get dbValue => switch (this) {
    Rank.unranked => 'UNRANKED',
    Rank.low => 'LOW',
    Rank.high => 'HIGH',
    Rank.g => 'G',
    Rank.treasure => 'TREASURE',
    Rank.training => 'TRAINING',
  };

  static Rank fromDb(String value) => switch (value) {
    'UNRANKED' => Rank.unranked,
    'LOW' => Rank.low,
    'HIGH' => Rank.high,
    'G' => Rank.g,
    'TREASURE' => Rank.treasure,
    'TRAINING' => Rank.training,
    _ => throw ArgumentError('Invalid rank value: $value'),
  };
}

enum WeaponType {
  greatSword,
  longSword,
  swordAndShield,
  dualBlades,
  hammer,
  huntingHorn,
  lance,
  gunlance,
  lightBowgun,
  heavyBowgun,
  bow;

  String get dbValue => switch (this) {
    WeaponType.greatSword => 'GREAT_SWORD',
    WeaponType.longSword => 'LONG_SWORD',
    WeaponType.swordAndShield => 'SWORD_AND_SHIELD',
    WeaponType.dualBlades => 'DUAL_BLADES',
    WeaponType.hammer => 'HAMMER',
    WeaponType.huntingHorn => 'HUNTING_HORN',
    WeaponType.lance => 'LANCE',
    WeaponType.gunlance => 'GUNLANCE',
    WeaponType.lightBowgun => 'LIGHT_BOWGUN',
    WeaponType.heavyBowgun => 'HEAVY_BOWGUN',
    WeaponType.bow => 'BOW',
  };

  static WeaponType fromDb(String value) => switch (value) {
    'GREAT_SWORD' => WeaponType.greatSword,
    'LONG_SWORD' => WeaponType.longSword,
    'SWORD_AND_SHIELD' => WeaponType.swordAndShield,
    'DUAL_BLADES' => WeaponType.dualBlades,
    'HAMMER' => WeaponType.hammer,
    'HUNTING_HORN' => WeaponType.huntingHorn,
    'LANCE' => WeaponType.lance,
    'GUNLANCE' => WeaponType.gunlance,
    'LIGHT_BOWGUN' => WeaponType.lightBowgun,
    'HEAVY_BOWGUN' => WeaponType.heavyBowgun,
    'BOW' => WeaponType.bow,
    _ => throw ArgumentError('Invalid weapon type value: $value'),
  };

  /// Weapon types sharing the same weapon tree with this one.
  List<WeaponType> get relatedTypes => switch (this) {
    WeaponType.greatSword || WeaponType.longSword => [
      WeaponType.greatSword,
      WeaponType.longSword,
    ],
    WeaponType.swordAndShield || WeaponType.dualBlades => [
      WeaponType.swordAndShield,
      WeaponType.dualBlades,
    ],
    WeaponType.hammer || WeaponType.huntingHorn => [
      WeaponType.hammer,
      WeaponType.huntingHorn,
    ],
    WeaponType.lance || WeaponType.gunlance => [
      WeaponType.lance,
      WeaponType.gunlance,
    ],
    WeaponType.lightBowgun => [WeaponType.lightBowgun],
    WeaponType.heavyBowgun => [WeaponType.heavyBowgun],
    WeaponType.bow => [WeaponType.bow],
  };

  static List<WeaponType> forHunterType(HunterType hunterType) =>
      switch (hunterType) {
        HunterType.both => WeaponType.values,
        HunterType.blade => [
          WeaponType.greatSword,
          WeaponType.longSword,
          WeaponType.swordAndShield,
          WeaponType.dualBlades,
          WeaponType.hammer,
          WeaponType.huntingHorn,
          WeaponType.lance,
          WeaponType.gunlance,
        ],
        HunterType.gunner => [
          WeaponType.lightBowgun,
          WeaponType.heavyBowgun,
          WeaponType.bow,
        ],
      };
}

enum WeaponElement {
  fire,
  water,
  thunder,
  ice,
  dragon,
  poison,
  paralysis,
  sleep;

  String get dbValue => switch (this) {
    WeaponElement.fire => 'FIRE',
    WeaponElement.water => 'WATER',
    WeaponElement.thunder => 'THUNDER',
    WeaponElement.ice => 'ICE',
    WeaponElement.dragon => 'DRAGON',
    WeaponElement.poison => 'POISON',
    WeaponElement.paralysis => 'PARALYSIS',
    WeaponElement.sleep => 'SLEEP',
  };

  static WeaponElement fromDb(String value) => switch (value) {
    'FIRE' => WeaponElement.fire,
    'WATER' => WeaponElement.water,
    'THUNDER' => WeaponElement.thunder,
    'ICE' => WeaponElement.ice,
    'DRAGON' => WeaponElement.dragon,
    'POISON' => WeaponElement.poison,
    'PARALYSIS' => WeaponElement.paralysis,
    'SLEEP' => WeaponElement.sleep,
    _ => throw ArgumentError('Invalid weapon element value: $value'),
  };
}

enum WeaponShelling {
  normal,
  long,
  spread;

  String get dbValue => switch (this) {
    WeaponShelling.normal => 'NORMAL',
    WeaponShelling.long => 'LONG',
    WeaponShelling.spread => 'SPREAD',
  };

  static WeaponShelling fromDb(String value) => switch (value) {
    'NORMAL' => WeaponShelling.normal,
    'LONG' => WeaponShelling.long,
    'SPREAD' => WeaponShelling.spread,
    _ => throw ArgumentError('Invalid weapon shelling value: $value'),
  };
}

enum WeaponRecoil {
  veryWeak,
  weak,
  light,
  moderate;

  String get dbValue => switch (this) {
    WeaponRecoil.veryWeak => 'VERY_WEAK',
    WeaponRecoil.weak => 'WEAK',
    WeaponRecoil.light => 'LIGHT',
    WeaponRecoil.moderate => 'MODERATE',
  };

  static WeaponRecoil fromDb(String value) => switch (value) {
    'VERY_WEAK' => WeaponRecoil.veryWeak,
    'WEAK' => WeaponRecoil.weak,
    'LIGHT' => WeaponRecoil.light,
    'MODERATE' => WeaponRecoil.moderate,
    _ => throw ArgumentError('Invalid weapon recoil value: $value'),
  };
}

enum WeaponReloadSpeed {
  verySlow,
  slow,
  normal,
  fast,
  veryFast;

  String get dbValue => switch (this) {
    WeaponReloadSpeed.verySlow => 'VERY_SLOW',
    WeaponReloadSpeed.slow => 'SLOW',
    WeaponReloadSpeed.normal => 'NORMAL',
    WeaponReloadSpeed.fast => 'FAST',
    WeaponReloadSpeed.veryFast => 'VERY_FAST',
  };

  static WeaponReloadSpeed fromDb(String value) => switch (value) {
    'VERY_SLOW' => WeaponReloadSpeed.verySlow,
    'SLOW' => WeaponReloadSpeed.slow,
    'NORMAL' => WeaponReloadSpeed.normal,
    'FAST' => WeaponReloadSpeed.fast,
    'VERY_FAST' => WeaponReloadSpeed.veryFast,
    _ => throw ArgumentError('Invalid weapon reload speed value: $value'),
  };
}

enum WeaponAmmo {
  normalRapid,
  pierce,
  pelletScatter,
  power,
  closeRange,
  crag,
  clust,
  recovery,
  poison,
  paralysis,
  sleep,
  flame,
  water,
  thunder,
  freeze,
  dragon,
  tranquilizer,
  paint,
  demon,
  armor;

  String get dbValue => switch (this) {
    WeaponAmmo.normalRapid => 'NORMAL',
    WeaponAmmo.pierce => 'PIERCE',
    WeaponAmmo.pelletScatter => 'PELLET',
    WeaponAmmo.power => 'POWER',
    WeaponAmmo.closeRange => 'CLOSE',
    WeaponAmmo.crag => 'CRAG',
    WeaponAmmo.clust => 'CLUST',
    WeaponAmmo.recovery => 'RECOVERY',
    WeaponAmmo.poison => 'POISON',
    WeaponAmmo.paralysis => 'PARALYSIS',
    WeaponAmmo.sleep => 'SLEEP',
    WeaponAmmo.flame => 'FLAME',
    WeaponAmmo.water => 'WATER',
    WeaponAmmo.thunder => 'THUNDER',
    WeaponAmmo.freeze => 'FREEZE',
    WeaponAmmo.dragon => 'DRAGON',
    WeaponAmmo.tranquilizer => 'TRANQUILIZER',
    WeaponAmmo.paint => 'PAINT',
    WeaponAmmo.demon => 'DEMON',
    WeaponAmmo.armor => 'ARMOR',
  };

  static WeaponAmmo fromDb(String value) => switch (value) {
    'NORMAL' || 'RAPID' => WeaponAmmo.normalRapid,
    'PIERCE' => WeaponAmmo.pierce,
    'PELLET' || 'SCATTER' => WeaponAmmo.pelletScatter,
    'POWER' => WeaponAmmo.power,
    'CLOSE' => WeaponAmmo.closeRange,
    'CRAG' => WeaponAmmo.crag,
    'CLUST' => WeaponAmmo.clust,
    'RECOVERY' => WeaponAmmo.recovery,
    'POISON' => WeaponAmmo.poison,
    'PARALYSIS' => WeaponAmmo.paralysis,
    'SLEEP' => WeaponAmmo.sleep,
    'FLAME' => WeaponAmmo.flame,
    'WATER' => WeaponAmmo.water,
    'THUNDER' => WeaponAmmo.thunder,
    'FREEZE' => WeaponAmmo.freeze,
    'DRAGON' => WeaponAmmo.dragon,
    'TRANQUILIZER' => WeaponAmmo.tranquilizer,
    'PAINT' => WeaponAmmo.paint,
    'DEMON' => WeaponAmmo.demon,
    'ARMOR' => WeaponAmmo.armor,
    _ => throw ArgumentError('Invalid weapon ammo value: $value'),
  };
}

enum MonsterType {
  small,
  large;

  String get dbValue => switch (this) {
    MonsterType.small => 'SMALL',
    MonsterType.large => 'LARGE',
  };

  static MonsterType fromDb(String value) => switch (value) {
    'SMALL' => MonsterType.small,
    'LARGE' => MonsterType.large,
    _ => throw ArgumentError('Invalid monster type value: $value'),
  };
}

enum MonsterAilment {
  knockout,
  paralysis,
  poison,
  sleep;

  String get dbValue => switch (this) {
    MonsterAilment.knockout => 'KNOCKOUT',
    MonsterAilment.paralysis => 'PARALYSIS',
    MonsterAilment.poison => 'POISON',
    MonsterAilment.sleep => 'SLEEP',
  };

  static MonsterAilment fromDb(String value) => switch (value) {
    'KNOCKOUT' => MonsterAilment.knockout,
    'PARALYSIS' => MonsterAilment.paralysis,
    'POISON' => MonsterAilment.poison,
    'SLEEP' => MonsterAilment.sleep,
    _ => throw ArgumentError('Invalid monster ailment value: $value'),
  };
}

enum QuestType {
  normal,
  key,
  urgent,
  special,
  treasure,
  training,
  event,
  challenge;

  String get dbValue => switch (this) {
    QuestType.normal => 'NORMAL',
    QuestType.key => 'KEY',
    QuestType.urgent => 'URGENT',
    QuestType.special => 'SPECIAL',
    QuestType.treasure => 'TREASURE',
    QuestType.training => 'TRAINING',
    QuestType.event => 'EVENT',
    QuestType.challenge => 'CHALLENGE',
  };

  static QuestType fromDb(String value) => switch (value) {
    'NORMAL' => QuestType.normal,
    'KEY' => QuestType.key,
    'URGENT' => QuestType.urgent,
    'SPECIAL' => QuestType.special,
    'TREASURE' => QuestType.treasure,
    'TRAINING' => QuestType.training,
    'EVENT' => QuestType.event,
    'CHALLENGE' => QuestType.challenge,
    _ => throw ArgumentError('Invalid quest type value: $value'),
  };
}

enum QuestGroup {
  village1,
  village2,
  village3,
  village4,
  village5,
  village6,
  village7,
  village8,
  village9,
  hr1a,
  hr1b,
  hr1c,
  hr2,
  hr3,
  hr4,
  hr5,
  hr6,
  hr7,
  hr8,
  hr9,
  treasure,
  event,
  beginnerBasic,
  beginnerWeapon,
  trainingBattle,
  trainingSpecial,
  trainingG,
  groupPractice,
  groupChallenge;

  String get dbValue => switch (this) {
    QuestGroup.village1 => 'VILLAGE_1',
    QuestGroup.village2 => 'VILLAGE_2',
    QuestGroup.village3 => 'VILLAGE_3',
    QuestGroup.village4 => 'VILLAGE_4',
    QuestGroup.village5 => 'VILLAGE_5',
    QuestGroup.village6 => 'VILLAGE_6',
    QuestGroup.village7 => 'VILLAGE_7',
    QuestGroup.village8 => 'VILLAGE_8',
    QuestGroup.village9 => 'VILLAGE_9',
    QuestGroup.hr1a => 'HR_1_1',
    QuestGroup.hr1b => 'HR_1_2',
    QuestGroup.hr1c => 'HR_1_3',
    QuestGroup.hr2 => 'HR_2',
    QuestGroup.hr3 => 'HR_3',
    QuestGroup.hr4 => 'HR_4',
    QuestGroup.hr5 => 'HR_5',
    QuestGroup.hr6 => 'HR_6',
    QuestGroup.hr7 => 'HR_7',
    QuestGroup.hr8 => 'HR_8',
    QuestGroup.hr9 => 'HR_9',
    QuestGroup.treasure => 'TREASURE',
    QuestGroup.event => 'EVENT',
    QuestGroup.beginnerBasic => 'BEGINNER_BASIC',
    QuestGroup.beginnerWeapon => 'BEGINNER_WEAPON',
    QuestGroup.trainingBattle => 'TRAINING_BATTLE',
    QuestGroup.trainingSpecial => 'TRAINING_SPECIAL',
    QuestGroup.trainingG => 'TRAINING_G',
    QuestGroup.groupPractice => 'GROUP_PRACTICE',
    QuestGroup.groupChallenge => 'GROUP_CHALLENGE',
  };

  static QuestGroup fromDb(String value) => switch (value) {
    'VILLAGE_1' => QuestGroup.village1,
    'VILLAGE_2' => QuestGroup.village2,
    'VILLAGE_3' => QuestGroup.village3,
    'VILLAGE_4' => QuestGroup.village4,
    'VILLAGE_5' => QuestGroup.village5,
    'VILLAGE_6' => QuestGroup.village6,
    'VILLAGE_7' => QuestGroup.village7,
    'VILLAGE_8' => QuestGroup.village8,
    'VILLAGE_9' => QuestGroup.village9,
    'HR_1_1' => QuestGroup.hr1a,
    'HR_1_2' => QuestGroup.hr1b,
    'HR_1_3' => QuestGroup.hr1c,
    'HR_2' => QuestGroup.hr2,
    'HR_3' => QuestGroup.hr3,
    'HR_4' => QuestGroup.hr4,
    'HR_5' => QuestGroup.hr5,
    'HR_6' => QuestGroup.hr6,
    'HR_7' => QuestGroup.hr7,
    'HR_8' => QuestGroup.hr8,
    'HR_9' => QuestGroup.hr9,
    'TREASURE' => QuestGroup.treasure,
    'EVENT' => QuestGroup.event,
    'BEGINNER_BASIC' => QuestGroup.beginnerBasic,
    'BEGINNER_WEAPON' => QuestGroup.beginnerWeapon,
    'TRAINING_BATTLE' => QuestGroup.trainingBattle,
    'TRAINING_SPECIAL' => QuestGroup.trainingSpecial,
    'TRAINING_G' => QuestGroup.trainingG,
    'GROUP_PRACTICE' => QuestGroup.groupPractice,
    'GROUP_CHALLENGE' => QuestGroup.groupChallenge,
    _ => throw ArgumentError('Invalid quest group value: $value'),
  };
}

enum QuestGoal {
  gather,
  hunt,
  slay,
  special,
  treasure;

  String get dbValue => switch (this) {
    QuestGoal.gather => 'GATHER',
    QuestGoal.hunt => 'HUNT',
    QuestGoal.slay => 'SLAY',
    QuestGoal.special => 'SPECIAL',
    QuestGoal.treasure => 'TREASURE',
  };

  static QuestGoal fromDb(String value) => switch (value) {
    'GATHER' => QuestGoal.gather,
    'HUNT' => QuestGoal.hunt,
    'SLAY' => QuestGoal.slay,
    'SPECIAL' => QuestGoal.special,
    'TREASURE' => QuestGoal.treasure,
    _ => throw ArgumentError('Invalid quest goal value: $value'),
  };
}

enum HubType {
  village,
  guild,
  training;

  String get dbValue => switch (this) {
    HubType.village => 'VILLAGE',
    HubType.guild => 'GUILD',
    HubType.training => 'TRAINING',
  };

  static HubType fromDb(String value) => switch (value) {
    'VILLAGE' => HubType.village,
    'GUILD' => HubType.guild,
    'TRAINING' => HubType.training,
    _ => throw ArgumentError('Invalid hub type value: $value'),
  };
}

enum LocationDaytime {
  day,
  night;

  String get dbValue => switch (this) {
    LocationDaytime.day => 'DAY',
    LocationDaytime.night => 'NIGHT',
  };

  static LocationDaytime fromDb(String value) => switch (value) {
    'DAY' => LocationDaytime.day,
    'NIGHT' => LocationDaytime.night,
    _ => throw ArgumentError('Invalid location daytime value: $value'),
  };
}

enum GatherType {
  collect,
  mine,
  bug,
  fish;

  String get dbValue => switch (this) {
    GatherType.collect => 'COLLECT',
    GatherType.mine => 'MINE',
    GatherType.bug => 'BUG',
    GatherType.fish => 'FISH',
  };

  static GatherType fromDb(String value) => switch (value) {
    'COLLECT' => GatherType.collect,
    'MINE' => GatherType.mine,
    'BUG' => GatherType.bug,
    'FISH' => GatherType.fish,
    _ => throw ArgumentError('Invalid gather type value: $value'),
  };
}

enum ItemCombinationType {
  normal,
  treasure,
  alchemy;

  String get dbValue => switch (this) {
    ItemCombinationType.normal => 'NORMAL',
    ItemCombinationType.treasure => 'TREASURE',
    ItemCombinationType.alchemy => 'ALCHEMY',
  };

  static ItemCombinationType fromDb(String value) => switch (value) {
    'NORMAL' => ItemCombinationType.normal,
    'TREASURE' => ItemCombinationType.treasure,
    'ALCHEMY' => ItemCombinationType.alchemy,
    _ => throw ArgumentError('Invalid item combination type value: $value'),
  };
}

enum ItemIconType {
  armorStone,
  bag,
  bait,
  ball,
  barrel,
  bbqSplit,
  binoculars,
  bomb,
  bone,
  book,
  boomerang,
  bottle,
  bugnet,
  carapaceonShell,
  coin,
  dung,
  egg,
  emptyBottle,
  fang,
  fish,
  flute,
  herb,
  honey,
  husk,
  heavenlyScale,
  insect,
  jewel,
  knife,
  liquid,
  map,
  meat,
  monster,
  mushroom,
  ore,
  pelt,
  pickaxe,
  scale,
  seed,
  shell,
  smoke,
  ticket,
  tool,
  trap,
  unknown,
  web,
  whetstone;

  String get dbValue => switch (this) {
    ItemIconType.armorStone => 'ARMOR_STONE',
    ItemIconType.bag => 'BAG',
    ItemIconType.bait => 'BAIT',
    ItemIconType.ball => 'BALL',
    ItemIconType.barrel => 'BARREL',
    ItemIconType.bbqSplit => 'BBQ_SPLIT',
    ItemIconType.binoculars => 'BINOCULARS',
    ItemIconType.bomb => 'BOMB',
    ItemIconType.bone => 'BONE',
    ItemIconType.book => 'BOOK',
    ItemIconType.boomerang => 'BOOMERANG',
    ItemIconType.bottle => 'BOTTLE',
    ItemIconType.bugnet => 'BUGNET',
    ItemIconType.carapaceonShell => 'CARAPACEON_SHELL',
    ItemIconType.coin => 'COIN',
    ItemIconType.dung => 'DUNG',
    ItemIconType.egg => 'EGG',
    ItemIconType.emptyBottle => 'EMPTY_BOTTLE',
    ItemIconType.fang => 'FANG',
    ItemIconType.fish => 'FISH',
    ItemIconType.flute => 'FLUTE',
    ItemIconType.herb => 'HERB',
    ItemIconType.honey => 'HONEY',
    ItemIconType.husk => 'HUSK',
    ItemIconType.heavenlyScale => 'HEAVENLY_SCALE',
    ItemIconType.insect => 'INSECT',
    ItemIconType.jewel => 'JEWEL',
    ItemIconType.knife => 'KNIFE',
    ItemIconType.liquid => 'LIQUID',
    ItemIconType.map => 'MAP',
    ItemIconType.meat => 'MEAT',
    ItemIconType.monster => 'MONSTER',
    ItemIconType.mushroom => 'MUSHROOM',
    ItemIconType.ore => 'ORE',
    ItemIconType.pelt => 'PELT',
    ItemIconType.pickaxe => 'PICKAXE',
    ItemIconType.scale => 'SCALE',
    ItemIconType.seed => 'SEED',
    ItemIconType.shell => 'SHELL',
    ItemIconType.smoke => 'SMOKE',
    ItemIconType.ticket => 'TICKET',
    ItemIconType.tool => 'TOOL',
    ItemIconType.trap => 'TRAP',
    ItemIconType.unknown => 'UNKNOWN',
    ItemIconType.web => 'WEB',
    ItemIconType.whetstone => 'WHETSTONE',
  };

  static ItemIconType fromDb(String value) => switch (value) {
    'ARMOR_STONE' => ItemIconType.armorStone,
    'BAG' => ItemIconType.bag,
    'BAIT' => ItemIconType.bait,
    'BALL' => ItemIconType.ball,
    'BARREL' => ItemIconType.barrel,
    'BBQ_SPLIT' => ItemIconType.bbqSplit,
    'BINOCULARS' => ItemIconType.binoculars,
    'BOMB' => ItemIconType.bomb,
    'BONE' => ItemIconType.bone,
    'BOOK' => ItemIconType.book,
    'BOOMERANG' => ItemIconType.boomerang,
    'BOTTLE' => ItemIconType.bottle,
    'BUGNET' => ItemIconType.bugnet,
    'CARAPACEON_SHELL' => ItemIconType.carapaceonShell,
    'COIN' => ItemIconType.coin,
    'DUNG' => ItemIconType.dung,
    'EGG' => ItemIconType.egg,
    'EMPTY_BOTTLE' => ItemIconType.emptyBottle,
    'FANG' => ItemIconType.fang,
    'FISH' => ItemIconType.fish,
    'FLUTE' => ItemIconType.flute,
    'HERB' => ItemIconType.herb,
    'HONEY' => ItemIconType.honey,
    'HUSK' => ItemIconType.husk,
    'HEAVENLY_SCALE' => ItemIconType.heavenlyScale,
    'INSECT' => ItemIconType.insect,
    'JEWEL' => ItemIconType.jewel,
    'KNIFE' => ItemIconType.knife,
    'LIQUID' => ItemIconType.liquid,
    'MAP' => ItemIconType.map,
    'MEAT' => ItemIconType.meat,
    'MONSTER' => ItemIconType.monster,
    'MUSHROOM' => ItemIconType.mushroom,
    'ORE' => ItemIconType.ore,
    'PELT' => ItemIconType.pelt,
    'PICKAXE' => ItemIconType.pickaxe,
    'SCALE' => ItemIconType.scale,
    'SEED' => ItemIconType.seed,
    'SHELL' => ItemIconType.shell,
    'SMOKE' => ItemIconType.smoke,
    'TICKET' => ItemIconType.ticket,
    'TOOL' => ItemIconType.tool,
    'TRAP' => ItemIconType.trap,
    'UNKNOWN' => ItemIconType.unknown,
    'WEB' => ItemIconType.web,
    'WHETSTONE' => ItemIconType.whetstone,
    _ => throw ArgumentError('Invalid item icon type value: $value'),
  };
}

enum ItemIconColor {
  blue,
  gray,
  green,
  orange,
  pink,
  purple,
  red,
  sky,
  white,
  yellow;

  String get dbValue => switch (this) {
    ItemIconColor.blue => 'BLUE',
    ItemIconColor.gray => 'GRAY',
    ItemIconColor.green => 'GREEN',
    ItemIconColor.orange => 'ORANGE',
    ItemIconColor.pink => 'PINK',
    ItemIconColor.purple => 'PURPLE',
    ItemIconColor.red => 'RED',
    ItemIconColor.sky => 'SKY',
    ItemIconColor.white => 'WHITE',
    ItemIconColor.yellow => 'YELLOW',
  };

  static ItemIconColor fromDb(String value) => switch (value) {
    'BLUE' => ItemIconColor.blue,
    'GRAY' => ItemIconColor.gray,
    'GREEN' => ItemIconColor.green,
    'ORANGE' => ItemIconColor.orange,
    'PINK' => ItemIconColor.pink,
    'PURPLE' => ItemIconColor.purple,
    'RED' => ItemIconColor.red,
    'SKY' => ItemIconColor.sky,
    'WHITE' => ItemIconColor.white,
    'YELLOW' => ItemIconColor.yellow,
    _ => throw ArgumentError('Invalid item icon color value: $value'),
  };
}

enum SkillCategory {
  blade,
  combat,
  felyne,
  gather,
  gunner,
  item,
  resistance,
  status;

  String get dbValue => switch (this) {
    SkillCategory.blade => 'BLADE',
    SkillCategory.combat => 'COMBAT',
    SkillCategory.felyne => 'FELYNE',
    SkillCategory.gather => 'GATHER',
    SkillCategory.gunner => 'GUNNER',
    SkillCategory.item => 'ITEM',
    SkillCategory.resistance => 'RESISTANCE',
    SkillCategory.status => 'STATUS',
  };

  static SkillCategory fromDb(String value) => switch (value) {
    'BLADE' => SkillCategory.blade,
    'COMBAT' => SkillCategory.combat,
    'FELYNE' => SkillCategory.felyne,
    'GATHER' => SkillCategory.gather,
    'GUNNER' => SkillCategory.gunner,
    'ITEM' => SkillCategory.item,
    'RESISTANCE' => SkillCategory.resistance,
    'STATUS' => SkillCategory.status,
    _ => throw ArgumentError('Invalid skill category value: $value'),
  };
}
