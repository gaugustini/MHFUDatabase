import 'package:flutter/material.dart';

import '../domain/enums.dart';
import '../theme/app_dimensions.dart';
import 'entity_icon_ids.dart';
import 'mhfu_colors.dart';

String? itemIconAsset(ItemIconType type) => switch (type) {
  ItemIconType.armorStone => 'ic_item_armor_stone',
  ItemIconType.bag => 'ic_item_bag',
  ItemIconType.bait => 'ic_item_bait',
  ItemIconType.ball => 'ic_item_ball',
  ItemIconType.barrel => 'ic_item_barrel',
  ItemIconType.bbqSplit => 'ic_item_bbq_split',
  ItemIconType.binoculars => 'ic_item_binoculars',
  ItemIconType.bomb => 'ic_item_bomb',
  ItemIconType.bone => 'ic_item_bone',
  ItemIconType.book => 'ic_item_book',
  ItemIconType.boomerang => 'ic_item_boomerang',
  ItemIconType.bottle => 'ic_item_bottle',
  ItemIconType.bugnet => 'ic_item_bugnet',
  ItemIconType.carapaceonShell => 'ic_item_carapaceon_shell',
  ItemIconType.coin => 'ic_item_coin',
  ItemIconType.dung => 'ic_item_dung',
  ItemIconType.egg => 'ic_item_egg',
  ItemIconType.emptyBottle => 'ic_item_empty_bottle',
  ItemIconType.fang => 'ic_item_fang',
  ItemIconType.fish => 'ic_item_fish',
  ItemIconType.flute => 'ic_item_flute',
  ItemIconType.herb => 'ic_item_herb',
  ItemIconType.honey => 'ic_item_honey',
  ItemIconType.husk => 'ic_item_husk',
  ItemIconType.heavenlyScale => 'ic_item_heavenly_scale',
  ItemIconType.insect => 'ic_item_insect',
  ItemIconType.jewel => 'ic_item_jewel',
  ItemIconType.knife => 'ic_item_knife',
  ItemIconType.liquid => 'ic_item_liquid',
  ItemIconType.map => 'ic_item_map',
  ItemIconType.meat => 'ic_item_meat',
  ItemIconType.monster => 'ic_item_monster',
  ItemIconType.mushroom => 'ic_item_mushroom',
  ItemIconType.ore => 'ic_item_ore',
  ItemIconType.pelt => 'ic_item_pelt',
  ItemIconType.pickaxe => 'ic_item_pickaxe',
  ItemIconType.scale => 'ic_item_scale',
  ItemIconType.seed => 'ic_item_seed',
  ItemIconType.shell => 'ic_item_shell',
  ItemIconType.smoke => 'ic_item_smoke',
  ItemIconType.ticket => 'ic_item_ticket',
  ItemIconType.tool => 'ic_item_tool',
  ItemIconType.trap => 'ic_item_trap',
  ItemIconType.unknown => 'ic_item_unknown',
  ItemIconType.web => 'ic_item_web',
  ItemIconType.whetstone => 'ic_item_whetstone',
};

String? weaponTypeIconAsset(WeaponType type) => switch (type) {
  WeaponType.greatSword => 'ic_weapon_great_sword',
  WeaponType.longSword => 'ic_weapon_long_sword',
  WeaponType.swordAndShield => 'ic_weapon_sword_and_shield',
  WeaponType.dualBlades => 'ic_weapon_dual_blades',
  WeaponType.hammer => 'ic_weapon_hammer',
  WeaponType.huntingHorn => 'ic_weapon_hunting_horn',
  WeaponType.lance => 'ic_weapon_lance',
  WeaponType.gunlance => 'ic_weapon_gunlance',
  WeaponType.lightBowgun => 'ic_weapon_light_bowgun',
  WeaponType.heavyBowgun => 'ic_weapon_heavy_bowgun',
  WeaponType.bow => 'ic_weapon_bow',
};

String? elementIconAsset(WeaponElement element) => switch (element) {
  WeaponElement.fire => 'ic_element_fire',
  WeaponElement.water => 'ic_element_water',
  WeaponElement.thunder => 'ic_element_thunder',
  WeaponElement.ice => 'ic_element_ice',
  WeaponElement.dragon => 'ic_element_dragon',
  WeaponElement.poison => 'ic_status_poison',
  WeaponElement.paralysis => 'ic_status_paralysis',
  WeaponElement.sleep => 'ic_status_sleep',
};

String? ailmentIconAsset(MonsterAilment ailment) => switch (ailment) {
  MonsterAilment.knockout => 'ic_status_stun',
  MonsterAilment.paralysis => 'ic_status_paralysis',
  MonsterAilment.poison => 'ic_status_poison',
  MonsterAilment.sleep => 'ic_status_sleep',
};

String? equipmentTypeIconAsset(EquipmentType type) => switch (type) {
  EquipmentType.armorHead => 'ic_armor_head',
  EquipmentType.armorChest => 'ic_armor_chest',
  EquipmentType.armorArms => 'ic_armor_arms',
  EquipmentType.armorWaist => 'ic_armor_waist',
  EquipmentType.armorLegs => 'ic_armor_legs',
  EquipmentType.decoration => null,
  EquipmentType.weapon => null,
};

String? monsterIconAsset(int monsterId) => monsterIconIds[monsterId];

String? locationIconAsset(int locationId) => locationIconIds[locationId];

String? locationMapAsset(int locationId) => locationMapIds[locationId];

String? locationMapWithNodesAsset(int locationId) =>
    locationMapWithNodesIds[locationId];

class const EntityIcon({
  required final String? asset,
  final double size = AppSize.extraLarge,
  final Color? tint,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).colorScheme;

    return Container(
      width: size,
      height: size,
      decoration: BoxDecoration(
        gradient: LinearGradient(
          begin: Alignment.topCenter,
          end: Alignment.bottomCenter,
          colors: [colors.surfaceContainerLow, colors.surfaceContainerHighest],
        ),
        border: Border.all(color: colors.outlineVariant),
        borderRadius: BorderRadius.circular(AppRadius.small),
      ),
      child: asset == null
          ? Icon(Icons.help_outline, color: colors.onSurfaceVariant)
          : FractionallySizedBox(
              widthFactor: 0.8,
              heightFactor: 0.8,
              child: Image.asset(
                'assets/images/$asset.webp',
                fit: BoxFit.contain,
                color: tint,
                colorBlendMode: tint == null ? null : BlendMode.modulate,
              ),
            ),
    );
  }
}

class const ItemEntityIcon({
  required final ItemIconType type,
  required final ItemIconColor color,
  final double size = AppSize.extraLarge,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return EntityIcon(
      asset: itemIconAsset(type),
      size: size,
      tint: itemIconColorValue(color),
    );
  }
}

class const WeaponEntityIcon({
  required final WeaponType type,
  required final int rarity,
  final double size = AppSize.medium,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return EntityIcon(
      asset: weaponTypeIconAsset(type),
      size: size,
      tint: rarityColor(rarity),
    );
  }
}

class const QuestGoalIcon({
  required final QuestGoal goal,
  final double size = AppSize.small,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Image.asset(
      'assets/images/ic_quest.webp',
      width: size,
      height: size,
      color: questGoalColor(goal),
      colorBlendMode: BlendMode.modulate,
    );
  }
}
