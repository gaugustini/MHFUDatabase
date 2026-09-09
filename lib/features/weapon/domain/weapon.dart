import '../../../core/domain/enums.dart';
import '../../../core/domain/shared.dart';

class const Weapon({
  required final int id,
  required final String name,
  required final String description,
  required final WeaponType type,
  required final int rarity,
  required final int affinity,
  required final int defense,
  required final int numberOfSlots,
  required final int attack,
  final int? maxAttack,
  required final int price,
  final WeaponElement? element1,
  final int? element1Value,
  final WeaponElement? element2,
  final int? element2Value,
  final String? sharpness,
  final String? sharpnessPlus,
  final WeaponShelling? shellingType,
  final int? shellingLevel,
  final String? songNotes,
  final WeaponReloadSpeed? reloadSpeed,
  final WeaponRecoil? recoil,
  required final bool buildable,
  final AmmoBow? ammoBow,
  final AmmoBowgun? ammoBowgun,
  final List<List<ItemQuantity>>? recipesCreate,
  final List<ItemQuantity>? recipeUpgrade,
  final List<List<Weapon>>? paths,
  final List<Weapon>? upgrades,
  final List<Weapon>? finals,
});

class const AmmoBow({
  required final WeaponAmmo charge1Type,
  required final int charge1Level,
  required final WeaponAmmo charge2Type,
  required final int charge2Level,
  required final WeaponAmmo charge3Type,
  required final int charge3Level,
  final WeaponAmmo? charge4Type,
  final int? charge4Level,
  required final bool power,
  required final bool close,
  required final bool paint,
  required final bool poison,
  required final bool paralysis,
  required final bool sleep,
});

class const AmmoBowgun({
  required final String normal,
  required final String pierce,
  required final String pellet,
  required final String crag,
  required final String clust,
  required final String recovery,
  required final String poison,
  required final String paralysis,
  required final String sleep,
  required final String flame,
  required final String water,
  required final String thunder,
  required final String freeze,
  required final String dragon,
  required final String tranq,
  required final String paint,
  required final String demon,
  required final String armor,
  final String? rapidFire,
});

class WeaponNode {
  WeaponNode({
    required this.weapon,
    List<WeaponNode>? parents,
    List<WeaponNode>? children,
  }) : parents = parents ?? [],
       children = children ?? [];

  final Weapon weapon;
  final List<WeaponNode> parents;
  final List<WeaponNode> children;
}

class const FlattenedWeaponNode({
  required final int uniqueId,
  required final Weapon weapon,
  required final int depth,
  required final bool hasChildren,
  required final bool isLastInGroup,
});
