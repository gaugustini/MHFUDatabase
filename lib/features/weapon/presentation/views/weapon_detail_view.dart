import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../../../core/domain/enums.dart';
import '../../../../core/router/app_routes.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/animated_page_content.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/button_page.dart';
import '../../../../core/widgets/detail_header.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/equipment_recipe.dart';
import '../../../../core/widgets/equipment_stats.dart'
    show SlotsIndicator, signedNumber;
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/mhfu_colors.dart';
import '../../../../core/widgets/section_card.dart';
import '../../../../core/widgets/surface_card.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/weapon_repository.dart';
import '../../domain/weapon.dart';

enum _WeaponPage { summary, paths }

const _bowgunTypes = {WeaponType.lightBowgun, WeaponType.heavyBowgun};

class const WeaponDetailView({
  required final int weaponId,
  required final VoidCallback navigateBack,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<WeaponDetailView> createState() => _WeaponDetailViewState();
}

class _WeaponDetailViewState extends State<WeaponDetailView> {
  late final Future<Weapon> _future = WeaponRepository().getWeapon(
    widget.weaponId,
    AppSettingsController.instance.locale.languageCode,
  );
  _WeaponPage _page = _WeaponPage.summary;

  void _goToPage(_WeaponPage page) => setState(() => _page = page);

  void _handleBack() {
    if (_page == _WeaponPage.summary) {
      widget.navigateBack();
    } else {
      _goToPage(_WeaponPage.summary);
    }
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return FutureBuilder<Weapon>(
      future: _future,
      builder: (context, snapshot) {
        final weapon = snapshot.data;

        return PopScope(
          canPop: _page == _WeaponPage.summary,
          onPopInvokedWithResult: (didPop, result) {
            if (!didPop) _goToPage(_WeaponPage.summary);
          },
          child: Scaffold(
            appBar: AppTopBar(
              title: weapon?.name ?? l10n.screenWeaponDetail,
              navigation: AppTopBarNavigation.back,
              onNavigationTap: _handleBack,
              onSearchTap: widget.openSearch,
            ),
            body: weapon == null
                ? const Center(child: CircularProgressIndicator())
                : AnimatedPageContent<_WeaponPage>(
                    value: _page,
                    index: (page) => page.index,
                    builder: (context, page) => switch (page) {
                      _WeaponPage.summary => _SummaryPage(
                        weapon: weapon,
                        onChangePage: _goToPage,
                      ),
                      _WeaponPage.paths => _PathsPage(weapon: weapon),
                    },
                  ),
          ),
        );
      },
    );
  }
}

class const _SummaryPage({
  required final Weapon weapon,
  required final ValueChanged<_WeaponPage> onChangePage,
}) extends StatefulWidget {
  @override
  State<_SummaryPage> createState() => _SummaryPageState();
}

class _SummaryPageState extends State<_SummaryPage> {
  bool _statsExpanded = true;
  bool _ammoExpanded = true;
  bool _recipeCreateAExpanded = true;
  bool _recipeCreateBExpanded = true;
  bool _recipeUpgradeExpanded = true;

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final weapon = widget.weapon;
    final recipesCreate = weapon.recipesCreate ?? const [];
    final recipeUpgrade = weapon.recipeUpgrade ?? const [];

    return ListView(
      padding: context.scrollPadding(
        const EdgeInsets.fromLTRB(
          AppPadding.medium,
          0,
          AppPadding.medium,
          AppPadding.small,
        ),
      ),
      children: [
        SurfaceCard(
          child: DetailHeader(
            icon: WeaponEntityIcon(type: weapon.type, rarity: weapon.rarity),
            title: weapon.name,
            subtitle: l10n.weaponRarity(weapon.rarity),
            description: weapon.description,
          ),
        ),
        if (!_bowgunTypes.contains(weapon.type)) ...[
          const SizedBox(height: AppSpacing.medium),
          ButtonPage(
            title: l10n.weaponPaths,
            onTap: () => widget.onChangePage(_WeaponPage.paths),
          ),
        ],
        const SizedBox(height: AppSpacing.medium),
        SectionCard(
          title: l10n.listEquipmentStats,
          expanded: _statsExpanded,
          onTap: () => setState(() => _statsExpanded = !_statsExpanded),
          child: _WeaponStats(weapon: weapon),
        ),
        if (weapon.ammoBow != null) ...[
          const SizedBox(height: AppSpacing.medium),
          SectionCard(
            title: l10n.weaponBowCoating,
            expanded: _ammoExpanded,
            onTap: () => setState(() => _ammoExpanded = !_ammoExpanded),
            child: _AmmoBowSummary(ammo: weapon.ammoBow!),
          ),
        ],
        if (weapon.ammoBowgun != null) ...[
          const SizedBox(height: AppSpacing.medium),
          SectionCard(
            title: l10n.weaponAmmoBowgun,
            expanded: _ammoExpanded,
            onTap: () => setState(() => _ammoExpanded = !_ammoExpanded),
            child: _AmmoBowgunSummary(ammo: weapon.ammoBowgun!),
          ),
        ],
        if (recipesCreate.isNotEmpty) ...[
          const SizedBox(height: AppSpacing.medium),
          SectionCard(
            title: recipesCreate.length == 1
                ? l10n.listRecipeCreate
                : l10n.listRecipeCreateA,
            expanded: _recipeCreateAExpanded,
            onTap: () => setState(
              () => _recipeCreateAExpanded = !_recipeCreateAExpanded,
            ),
            child: EquipmentRecipe(recipe: recipesCreate[0]),
          ),
          if (recipesCreate.length > 1) ...[
            const SizedBox(height: AppSpacing.medium),
            SectionCard(
              title: l10n.listRecipeCreateB,
              expanded: _recipeCreateBExpanded,
              onTap: () => setState(
                () => _recipeCreateBExpanded = !_recipeCreateBExpanded,
              ),
              child: EquipmentRecipe(recipe: recipesCreate[1]),
            ),
          ],
        ],
        if (recipeUpgrade.isNotEmpty) ...[
          const SizedBox(height: AppSpacing.medium),
          SectionCard(
            title: l10n.listRecipeUpgrade,
            expanded: _recipeUpgradeExpanded,
            onTap: () => setState(
              () => _recipeUpgradeExpanded = !_recipeUpgradeExpanded,
            ),
            child: EquipmentRecipe(recipe: recipeUpgrade),
          ),
        ],
      ],
    );
  }
}

class const _WeaponStats({required final Weapon weapon})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Column(
      children: [
        ListItemLayout(
          leading: Image.asset(
            'assets/images/ic_ui_attack.webp',
            width: AppSize.extraSmall,
            height: AppSize.extraSmall,
          ),
          headline: Text(
            weapon.maxAttack != null ? l10n.weaponMaxAttack : l10n.weaponAttack,
          ),
          trailing: Text(
            weapon.maxAttack != null
                ? '${weapon.attack} (${weapon.maxAttack})'
                : '${weapon.attack}',
          ),
        ),
        const AppHDivider(),
        ListItemLayout(
          leading: Image.asset(
            'assets/images/ic_ui_affinity.webp',
            width: AppSize.extraSmall,
            height: AppSize.extraSmall,
          ),
          headline: Text(l10n.weaponAffinity),
          trailing: Text('${signedNumber(weapon.affinity)}%'),
        ),
        const AppHDivider(),
        ListItemLayout(
          leading: Image.asset(
            'assets/images/ic_ui_slots.webp',
            width: AppSize.extraSmall,
            height: AppSize.extraSmall,
            color: itemIconColorValue(ItemIconColor.blue),
            colorBlendMode: BlendMode.modulate,
          ),
          headline: Text(l10n.weaponSlots),
          trailing: SlotsIndicator(numberOfSlots: weapon.numberOfSlots),
        ),
        const AppHDivider(),
        ListItemLayout(
          leading: Image.asset(
            'assets/images/ic_ui_defense.webp',
            width: AppSize.extraSmall,
            height: AppSize.extraSmall,
          ),
          headline: Text(l10n.weaponDefense),
          trailing: Text('${weapon.defense}'),
        ),
        if (weapon.element1 != null) ...[
          const AppHDivider(),
          ListItemLayout(
            leading: Image.asset(
              'assets/images/ic_ui_element.webp',
              width: AppSize.extraSmall,
              height: AppSize.extraSmall,
            ),
            headline: Text(l10n.weaponElement),
            trailing: Row(
              mainAxisSize: MainAxisSize.min,
              children: [
                Image.asset(
                  'assets/images/${elementIconAsset(weapon.element1!)}.webp',
                  width: AppSize.extraSmall,
                  height: AppSize.extraSmall,
                ),
                Text('${weapon.element1Value}'),
                if (weapon.element2 != null) ...[
                  const SizedBox(width: AppSpacing.large),
                  Image.asset(
                    'assets/images/${elementIconAsset(weapon.element2!)}.webp',
                    width: AppSize.extraSmall,
                    height: AppSize.extraSmall,
                  ),
                  Text('${weapon.element2Value}'),
                ],
              ],
            ),
          ),
        ],
        if (weapon.sharpness != null && weapon.sharpnessPlus != null) ...[
          const AppHDivider(),
          ListItemLayout(
            leading: Image.asset(
              'assets/images/ic_ui_sharpness.webp',
              width: AppSize.extraSmall,
              height: AppSize.extraSmall,
              color: itemIconColorValue(ItemIconColor.yellow),
              colorBlendMode: BlendMode.modulate,
            ),
            headline: Text(l10n.weaponSharpness),
            trailing: Column(
              crossAxisAlignment: CrossAxisAlignment.end,
              mainAxisSize: MainAxisSize.min,
              children: [
                SharpnessBar(
                  sharpness: weapon.sharpness!,
                  height: 11,
                  width: 132,
                ),
                const SizedBox(height: AppSpacing.small),
                Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    const Text('+1'),
                    const SizedBox(width: AppSpacing.small),
                    SharpnessBar(
                      sharpness: weapon.sharpnessPlus!,
                      height: 11,
                      width: 132,
                    ),
                  ],
                ),
              ],
            ),
          ),
        ],
        if (weapon.songNotes != null) ...[
          const AppHDivider(),
          ListItemLayout(
            leading: Image.asset(
              'assets/images/ic_ui_song_notes.webp',
              width: AppSize.extraSmall,
              height: AppSize.extraSmall,
            ),
            headline: Text(l10n.weaponSongNotes),
            trailing: SongNotesBar(
              songNotes: weapon.songNotes!,
              height: AppSize.extraSmall,
              width: AppSize.extraSmall * 3,
            ),
          ),
        ],
        if (weapon.shellingType != null && weapon.shellingLevel != null) ...[
          const AppHDivider(),
          ListItemLayout(
            leading: Image.asset(
              'assets/images/ic_ui_shelling.webp',
              width: AppSize.extraSmall,
              height: AppSize.extraSmall,
            ),
            headline: Text(l10n.weaponShelling),
            trailing: Text(
              _shellingLabel(l10n, weapon.shellingType!, weapon.shellingLevel!),
            ),
          ),
        ],
        if (weapon.reloadSpeed != null) ...[
          const AppHDivider(),
          ListItemLayout(
            leading: Image.asset(
              'assets/images/ic_ui_reload_speed.webp',
              width: AppSize.extraSmall,
              height: AppSize.extraSmall,
            ),
            headline: Text(l10n.weaponReloadSpeed),
            trailing: Text(_reloadSpeedLabel(l10n, weapon.reloadSpeed!)),
          ),
        ],
        if (weapon.recoil != null) ...[
          const AppHDivider(),
          ListItemLayout(
            leading: Image.asset(
              'assets/images/ic_ui_recoil.webp',
              width: AppSize.extraSmall,
              height: AppSize.extraSmall,
            ),
            headline: Text(l10n.weaponRecoil),
            trailing: Text(_recoilLabel(l10n, weapon.recoil!)),
          ),
        ],
      ],
    );
  }
}

String _shellingLabel(AppLocalizations l10n, WeaponShelling type, int level) {
  final typeLabel = switch (type) {
    WeaponShelling.normal => l10n.weaponShellingNormal,
    WeaponShelling.long => l10n.weaponShellingLong,
    WeaponShelling.spread => l10n.weaponShellingSpread,
  };
  return '$typeLabel Lv$level';
}

String _reloadSpeedLabel(AppLocalizations l10n, WeaponReloadSpeed speed) =>
    switch (speed) {
      WeaponReloadSpeed.verySlow => l10n.weaponReloadSpeedVerySlow,
      WeaponReloadSpeed.slow => l10n.weaponReloadSpeedSlow,
      WeaponReloadSpeed.normal => l10n.weaponReloadSpeedNormal,
      WeaponReloadSpeed.fast => l10n.weaponReloadSpeedFast,
      WeaponReloadSpeed.veryFast => l10n.weaponReloadSpeedVeryFast,
    };

String _recoilLabel(AppLocalizations l10n, WeaponRecoil recoil) =>
    switch (recoil) {
      WeaponRecoil.veryWeak => l10n.weaponRecoilVeryWeak,
      WeaponRecoil.weak => l10n.weaponRecoilWeak,
      WeaponRecoil.light => l10n.weaponRecoilLight,
      WeaponRecoil.moderate => l10n.weaponRecoilModerate,
    };

class const _AmmoBowSummary({required final AmmoBow ammo})
    extends StatelessWidget {
  String _chargeLabel(
    AppLocalizations l10n,
    int position,
    WeaponAmmo type,
    int level,
  ) {
    final typeLabel = switch (type) {
      WeaponAmmo.normalRapid => l10n.weaponAmmoRapid,
      WeaponAmmo.pierce => l10n.weaponAmmoPierce,
      WeaponAmmo.pelletScatter => l10n.weaponAmmoScatter,
      _ => type.name,
    };
    return l10n.weaponBowCharge(position, typeLabel, level);
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final coatings = <(bool, String, ItemIconColor)>[
      (ammo.power, l10n.weaponBowCoatingPower, ItemIconColor.red),
      (ammo.close, l10n.weaponBowCoatingClose, ItemIconColor.white),
      (ammo.paint, l10n.weaponBowCoatingPaint, ItemIconColor.pink),
      (ammo.poison, l10n.weaponBowCoatingPoison, ItemIconColor.purple),
      (ammo.paralysis, l10n.weaponBowCoatingParalysis, ItemIconColor.yellow),
      (ammo.sleep, l10n.weaponBowCoatingSleep, ItemIconColor.sky),
    ];
    final charges = <(WeaponAmmo?, int?)>[
      (ammo.charge1Type, ammo.charge1Level),
      (ammo.charge2Type, ammo.charge2Level),
      (ammo.charge3Type, ammo.charge3Level),
      (ammo.charge4Type, ammo.charge4Level),
    ];

    return Column(
      children: [
        ListItemLayout(
          leading: Image.asset(
            'assets/images/ic_item_bottle.webp',
            width: AppSize.extraSmall,
            height: AppSize.extraSmall,
            color: itemIconColorValue(ItemIconColor.orange),
            colorBlendMode: BlendMode.modulate,
          ),
          headline: Text(
            l10n.weaponBowCoating,
            maxLines: 1,
            overflow: TextOverflow.ellipsis,
          ),
          trailing: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              for (final (hasCoating, label, color) in coatings)
                Padding(
                  padding: const EdgeInsets.only(left: AppSpacing.small),
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: [
                      Image.asset(
                        'assets/images/${hasCoating ? 'ic_item_bottle' : 'ic_ui_none'}.webp',
                        width: AppSize.extraSmall,
                        height: AppSize.extraSmall,
                        color: hasCoating ? itemIconColorValue(color) : null,
                        colorBlendMode: hasCoating ? BlendMode.modulate : null,
                      ),
                      Text(
                        label,
                        style: Theme.of(context).textTheme.labelSmall,
                      ),
                    ],
                  ),
                ),
            ],
          ),
        ),
        const AppHDivider(),
        ListItemLayout(
          leading: Image.asset(
            'assets/images/ic_weapon_bow.webp',
            width: AppSize.extraSmall,
            height: AppSize.extraSmall,
            color: itemIconColorValue(ItemIconColor.yellow),
            colorBlendMode: BlendMode.modulate,
          ),
          headline: Text(l10n.weaponBowCharges),
          trailing: Column(
            crossAxisAlignment: CrossAxisAlignment.end,
            mainAxisSize: MainAxisSize.min,
            children: [
              for (final (index, charge) in charges.indexed)
                if (charge.$1 != null && charge.$2 != null)
                  Text(_chargeLabel(l10n, index + 1, charge.$1!, charge.$2!)),
            ],
          ),
        ),
      ],
    );
  }
}

class const _AmmoBowgunSummary({required final AmmoBowgun ammo})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final shots = <(String, String, ItemIconColor)>[
      (ammo.normal, l10n.weaponAmmoBowgunNormal, ItemIconColor.white),
      (ammo.pierce, l10n.weaponAmmoBowgunPierce, ItemIconColor.white),
      (ammo.pellet, l10n.weaponAmmoBowgunPellet, ItemIconColor.white),
      (ammo.crag, l10n.weaponAmmoBowgunCrag, ItemIconColor.white),
      (ammo.clust, l10n.weaponAmmoBowgunClust, ItemIconColor.white),
      (ammo.recovery, l10n.weaponAmmoBowgunRecovery, ItemIconColor.green),
      (ammo.poison, l10n.weaponAmmoBowgunPoison, ItemIconColor.purple),
      (ammo.paralysis, l10n.weaponAmmoBowgunParalysis, ItemIconColor.yellow),
      (ammo.sleep, l10n.weaponAmmoBowgunSleep, ItemIconColor.sky),
      (ammo.flame, l10n.weaponAmmoBowgunFlame, ItemIconColor.red),
      (ammo.water, l10n.weaponAmmoBowgunWater, ItemIconColor.sky),
      (ammo.thunder, l10n.weaponAmmoBowgunThunder, ItemIconColor.yellow),
      (ammo.freeze, l10n.weaponAmmoBowgunFreeze, ItemIconColor.blue),
      (ammo.dragon, l10n.weaponAmmoBowgunDragon, ItemIconColor.red),
      (ammo.tranq, l10n.weaponAmmoBowgunTranq, ItemIconColor.red),
      (ammo.paint, l10n.weaponAmmoBowgunPaint, ItemIconColor.pink),
      (ammo.demon, l10n.weaponAmmoBowgunDemon, ItemIconColor.red),
      (ammo.armor, l10n.weaponAmmoBowgunArmor, ItemIconColor.orange),
    ];

    return Column(
      children: [
        for (final (index, shot) in shots.indexed) ...[
          if (index > 0) const AppHDivider(),
          ListItemLayout(
            leading: Image.asset(
              'assets/images/ic_item_shell.webp',
              width: AppSize.extraSmall,
              height: AppSize.extraSmall,
              color: itemIconColorValue(shot.$3),
              colorBlendMode: BlendMode.modulate,
            ),
            headline: Text(shot.$2),
            trailing: Text(shot.$1.replaceAll('-', ' - ')),
          ),
        ],
        if (ammo.rapidFire != null) ...[
          const AppHDivider(),
          ListItemLayout(
            leading: SizedBox(
              width: AppSize.extraSmall,
              height: AppSize.extraSmall,
            ),
            headline: Text(l10n.weaponAmmoBowgunRapidFire),
            trailing: Column(
              crossAxisAlignment: CrossAxisAlignment.end,
              mainAxisSize: MainAxisSize.min,
              children: [
                for (final shot in ammo.rapidFire!.split('|')) Text(shot),
              ],
            ),
          ),
        ],
      ],
    );
  }
}

class const _PathsPage({required final Weapon weapon}) extends StatefulWidget {
  @override
  State<_PathsPage> createState() => _PathsPageState();
}

class _PathsPageState extends State<_PathsPage> {
  late Set<int> _pathsExpanded = {
    for (var i = 0; i < (widget.weapon.paths ?? const []).length; i++) i,
  };
  bool _upgradesExpanded = true;
  bool _finalsExpanded = true;

  void _togglePath(int index) => setState(() {
    _pathsExpanded = _pathsExpanded.contains(index)
        ? ({..._pathsExpanded}..remove(index))
        : ({..._pathsExpanded}..add(index));
  });

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final paths = widget.weapon.paths ?? const [];
    final upgrades = widget.weapon.upgrades ?? const [];
    final finals = widget.weapon.finals ?? const [];

    return ListView(
      padding: context.scrollPadding(
        const EdgeInsets.fromLTRB(
          AppPadding.medium,
          0,
          AppPadding.medium,
          AppPadding.small,
        ),
      ),
      children: [
        for (final (pathIndex, path) in paths.indexed) ...[
          SectionCard(
            title: paths.length == 1
                ? l10n.weaponPath
                : l10n.weaponPathDetails(pathIndex + 1),
            expanded: _pathsExpanded.contains(pathIndex),
            onTap: () => _togglePath(pathIndex),
            margin: EdgeInsets.zero,
            child: _WeaponRowList(weapons: path),
          ),
          const SizedBox(height: AppSpacing.medium),
        ],
        if (upgrades.isNotEmpty) ...[
          SectionCard(
            title: l10n.weaponUpgrades,
            expanded: _upgradesExpanded,
            onTap: () => setState(() => _upgradesExpanded = !_upgradesExpanded),
            margin: EdgeInsets.zero,
            child: _WeaponRowList(weapons: upgrades),
          ),
          const SizedBox(height: AppSpacing.medium),
        ],
        SectionCard(
          title: l10n.weaponFinals,
          expanded: _finalsExpanded,
          onTap: () => setState(() => _finalsExpanded = !_finalsExpanded),
          margin: EdgeInsets.zero,
          child: _WeaponRowList(weapons: finals),
        ),
      ],
    );
  }
}

class const _WeaponRowList({required final List<Weapon> weapons})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        for (final (index, weapon) in weapons.indexed) ...[
          if (index > 0) const AppHDivider(),
          _WeaponRow(weapon: weapon),
        ],
      ],
    );
  }
}

class const _WeaponRow({required final Weapon weapon}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return InkWell(
      onTap: () => context.push(AppRoutes.weaponDetail(weapon.id)),
      child: Column(
        children: [
          ListItemLayout(
            contentPadding: const EdgeInsets.only(
              left: AppPadding.large,
              top: AppPadding.medium,
              right: AppPadding.large,
            ),
            headline: Row(
              mainAxisSize: MainAxisSize.min,
              children: [
                Flexible(
                  child: Text(
                    weapon.name,
                    maxLines: 1,
                    overflow: TextOverflow.ellipsis,
                  ),
                ),
                if (weapon.buildable) ...[
                  const SizedBox(width: AppSpacing.medium),
                  Image.asset(
                    'assets/images/ic_ui_build.webp',
                    width: AppSize.tiny,
                    height: AppSize.tiny,
                  ),
                ],
              ],
            ),
            trailing: SlotsIndicator(numberOfSlots: weapon.numberOfSlots),
          ),
          ListItemLayout(
            leading: WeaponEntityIcon(
              type: weapon.type,
              rarity: weapon.rarity,
              size: AppSize.medium,
            ),
            headline: Row(
              mainAxisSize: MainAxisSize.min,
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
                  Text(
                    weapon.element1Value! > 0
                        ? '${weapon.element1Value}'
                        : l10n.weaponElementAdd,
                  ),
                ],
                if (weapon.element2 != null &&
                    weapon.element2Value != null) ...[
                  const SizedBox(width: AppSpacing.medium),
                  Image.asset(
                    'assets/images/${elementIconAsset(weapon.element2!)}.webp',
                    width: AppSize.tiny,
                    height: AppSize.tiny,
                  ),
                  Text(
                    weapon.element2Value! > 0
                        ? '${weapon.element2Value}'
                        : l10n.weaponElementAdd,
                  ),
                ],
              ],
            ),
            trailing: Column(
              crossAxisAlignment: CrossAxisAlignment.end,
              mainAxisSize: MainAxisSize.min,
              children: [
                if (weapon.sharpness != null &&
                    weapon.sharpnessPlus != null) ...[
                  SharpnessBar(
                    sharpness: weapon.sharpness!,
                    height: 7,
                    width: 84,
                  ),
                  const SizedBox(height: AppSpacing.small),
                  SharpnessBar(
                    sharpness: weapon.sharpnessPlus!,
                    height: 7,
                    width: 84,
                  ),
                  const SizedBox(height: AppSpacing.medium),
                ],
                if (weapon.songNotes != null) ...[
                  SongNotesBar(
                    songNotes: weapon.songNotes!,
                    height: AppSize.tiny,
                    width: AppSize.tiny * 3,
                  ),
                  const SizedBox(height: AppSpacing.medium),
                ],
                if (weapon.shellingType != null && weapon.shellingLevel != null)
                  Text(
                    _shellingLabel(
                      l10n,
                      weapon.shellingType!,
                      weapon.shellingLevel!,
                    ),
                    style: Theme.of(context).textTheme.bodySmall,
                  ),
                if (weapon.reloadSpeed != null)
                  Text(
                    l10n.weaponReloadDetail(
                      _reloadSpeedLabel(l10n, weapon.reloadSpeed!),
                    ),
                    style: Theme.of(context).textTheme.bodySmall,
                  ),
                if (weapon.recoil != null)
                  Text(
                    l10n.weaponRecoilDetail(_recoilLabel(l10n, weapon.recoil!)),
                    style: Theme.of(context).textTheme.bodySmall,
                  ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class const SharpnessBar({
  required final String sharpness,
  required final double height,
  required final double width,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final levels = sharpness
        .split('-')
        .map((value) => int.tryParse(value) ?? 0)
        .toList();
    int levelAt(int index) =>
        index < levels.length ? levels[index].clamp(0, 1 << 30) : 0;

    final red = levelAt(0);
    final orange = levelAt(1);
    final yellow = levelAt(2);
    final green = levelAt(3);
    final blue = levelAt(4);
    final white = levelAt(5);
    final purple = levelAt(6);
    final none = (45 - (red + orange + yellow + green + blue + white + purple))
        .clamp(0, 1 << 30);

    final segments = <(Color, int)>[
      (sharpnessColor('R'), red),
      (sharpnessColor('O'), orange),
      (sharpnessColor('Y'), yellow),
      (sharpnessColor('G'), green),
      (sharpnessColor('B'), blue),
      (sharpnessColor('W'), white),
      (sharpnessColor('P'), purple),
      (sharpnessColor('_'), none),
    ];

    return CustomPaint(
      size: Size(width, height),
      painter: _SharpnessBarPainter(segments: segments),
    );
  }
}

class _SharpnessBarPainter extends CustomPainter {
  _SharpnessBarPainter({required this.segments});

  final List<(Color, int)> segments;

  @override
  void paint(Canvas canvas, Size size) {
    final total = segments.fold<int>(0, (sum, segment) => sum + segment.$2);
    if (total == 0) return;

    var currentX = 0.0;
    for (final (color, value) in segments) {
      if (value <= 0) continue;
      final segmentWidth = size.width * (value / total);
      canvas.drawRect(
        Rect.fromLTWH(currentX, 0, segmentWidth, size.height),
        Paint()..color = color,
      );
      currentX += segmentWidth;
    }

    canvas.drawRect(
      Rect.fromLTWH(0, 0, size.width, size.height),
      Paint()
        ..color = Colors.black
        ..style = PaintingStyle.stroke
        ..strokeWidth = 1,
    );
  }

  @override
  bool shouldRepaint(covariant _SharpnessBarPainter oldDelegate) =>
      oldDelegate.segments != segments;
}

class const SongNotesBar({
  required final String songNotes,
  required final double height,
  required final double width,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final notes = songNotes.split('');

    return SizedBox(
      width: width,
      height: height,
      child: Row(
        children: [
          for (final note in notes)
            Expanded(
              child: Image.asset(
                'assets/images/ic_ui_song_note.webp',
                color: songNoteColor(note),
                colorBlendMode: BlendMode.modulate,
              ),
            ),
        ],
      ),
    );
  }
}
