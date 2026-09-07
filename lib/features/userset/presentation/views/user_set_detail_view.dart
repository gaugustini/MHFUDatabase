import 'package:flutter/material.dart' hide Decoration;
import 'package:go_router/go_router.dart';

import '../../../../core/domain/enums.dart';
import '../../../../core/router/app_routes.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/equipment_recipe.dart';
import '../../../../core/widgets/equipment_stats.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/mhfu_colors.dart';
import '../../../../core/widgets/section_card.dart';
import '../../../../core/widgets/skill_points.dart';
import '../../../../l10n/app_localizations.dart';
import '../../../armor/domain/armor.dart';
import '../../../decoration/domain/decoration.dart';
import '../../../weapon/domain/weapon.dart';
import '../../data/user_equipment_set_repository.dart';
import '../../domain/user_equipment_set.dart';
import '../widgets/equipment_slot_item.dart';
import 'armor_selection_view.dart';
import 'decoration_selection_view.dart';
import 'weapon_selection_view.dart';

const _armorSlots = [
  EquipmentType.armorHead,
  EquipmentType.armorChest,
  EquipmentType.armorArms,
  EquipmentType.armorWaist,
  EquipmentType.armorLegs,
];

class const UserSetDetailView({
  required final int setId,
  required final String hunterType,
  required final String gender,
  required final VoidCallback navigateBack,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<UserSetDetailView> createState() => _UserSetDetailViewState();
}

class _UserSetDetailViewState extends State<UserSetDetailView> {
  final _repository = UserEquipmentSetRepository();
  UserEquipmentSet? _set;

  bool _statsExpanded = true;
  bool _activeSkillsExpanded = true;
  bool _skillsExpanded = true;
  bool _armorPiecesExpanded = true;
  bool _decorationsExpanded = true;
  bool _recipeExpanded = true;

  String get _language => AppSettingsController.instance.locale.languageCode;
  HunterType get _hunterType => HunterType.fromDb(widget.hunterType);
  Gender get _gender => Gender.fromDb(widget.gender);

  @override
  void initState() {
    super.initState();
    _load();
  }

  Future<void> _load() async {
    final set = await _repository.getUserEquipmentSet(widget.setId, _language);
    if (mounted) setState(() => _set = set);
  }

  Future<void> _rename() async {
    final l10n = AppLocalizations.of(context)!;
    final controller = TextEditingController(text: _set?.name);
    final name = await showDialog<String>(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(l10n.userSetRename),
        content: TextField(
          controller: controller,
          decoration: InputDecoration(labelText: l10n.userSetName),
        ),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(),
            child: Text(l10n.dialogCancel),
          ),
          TextButton(
            onPressed: () => Navigator.of(context).pop(controller.text),
            child: Text(l10n.dialogConfirm),
          ),
        ],
      ),
    );
    if (name == null || name.isEmpty) return;
    await _repository.renameUserEquipmentSet(widget.setId, name);
    _load();
  }

  Future<void> _delete() async {
    final l10n = AppLocalizations.of(context)!;
    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(l10n.userSetDelete),
        content: Text(l10n.userSetDeleteConfirm),
        actions: [
          TextButton(
            onPressed: () => Navigator.of(context).pop(false),
            child: Text(l10n.dialogCancel),
          ),
          TextButton(
            onPressed: () => Navigator.of(context).pop(true),
            child: Text(l10n.dialogConfirm),
          ),
        ],
      ),
    );
    if (confirmed != true) return;
    await _repository.deleteUserEquipmentSet(widget.setId);
    if (mounted) widget.navigateBack();
  }

  Future<void> _pickWeapon() async {
    final weapon = await Navigator.of(context).push<Weapon>(
      MaterialPageRoute(
        builder: (_) => WeaponSelectionView(hunterType: _hunterType),
      ),
    );
    if (weapon == null) return;
    await _repository.setWeapon(widget.setId, weapon.id);
    _load();
  }

  Future<void> _pickArmor(EquipmentType type) async {
    final armor = await Navigator.of(context).push<Armor>(
      MaterialPageRoute(
        builder: (_) => ArmorSelectionView(
          type: type,
          hunterType: _hunterType,
          gender: _gender,
        ),
      ),
    );
    if (armor == null) return;
    await _repository.addArmor(widget.setId, armor.id, type);
    _load();
  }

  Future<void> _addDecoration(EquipmentType type, int availableSlots) async {
    final decoration = await Navigator.of(context).push<Decoration>(
      MaterialPageRoute(
        builder: (_) =>
            DecorationSelectionView(maxAvailableSlots: availableSlots),
      ),
    );
    if (decoration == null) return;
    await _repository.addDecoration(widget.setId, decoration.id, type);
    _load();
  }

  Future<void> _removeDecoration(EquipmentType type, int decorationId) async {
    await _repository.removeDecoration(widget.setId, decorationId, type);
    _load();
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final set = _set;

    return Scaffold(
      appBar: AppTopBar(
        title: (set?.name.isEmpty ?? true) ? l10n.userSetCreate : set!.name,
        navigation: AppTopBarNavigation.back,
        onNavigationTap: widget.navigateBack,
        onSearchTap: widget.openSearch,
        actions: [
          IconButton(onPressed: _rename, icon: const Icon(Icons.edit)),
          IconButton(onPressed: _delete, icon: const Icon(Icons.delete)),
        ],
      ),
      body: set == null
          ? const Center(child: CircularProgressIndicator())
          : _buildContent(context, l10n, set),
    );
  }

  Widget _buildContent(
    BuildContext context,
    AppLocalizations l10n,
    UserEquipmentSet set,
  ) {
    final armors = set.armors ?? const <Armor>[];
    final decorations = set.decorations ?? const <EquipmentDecoration>[];
    final activeSkills = set.activeSkills ?? const [];
    final skills = set.skills ?? const [];
    final recipe = set.recipe ?? const [];

    final defense =
        armors.fold<int>(0, (sum, armor) => sum + armor.defense) +
        (set.weapon?.defense ?? 0);
    final maxDefense =
        armors.fold<int>(0, (sum, armor) => sum + armor.maxDefense) +
        (set.weapon?.defense ?? 0);

    final aggregatedDecorations = <int, EquipmentDecoration>{};
    for (final decoration in decorations) {
      final existing = aggregatedDecorations[decoration.decoration.id];
      aggregatedDecorations[decoration.decoration.id] = EquipmentDecoration(
        equipmentType: decoration.equipmentType,
        decoration: decoration.decoration,
        quantity: (existing?.quantity ?? 0) + decoration.quantity,
      );
    }
    final decorationSummary = aggregatedDecorations.values.toList()
      ..sort((a, b) => b.quantity.compareTo(a.quantity));

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
        SectionCard(
          title: l10n.listEquipmentStats,
          expanded: _statsExpanded,
          onTap: () => setState(() => _statsExpanded = !_statsExpanded),
          child: EquipmentStats(
            defense: defense,
            maxDefense: maxDefense,
            fire: set.fire,
            water: set.water,
            thunder: set.thunder,
            ice: set.ice,
            dragon: set.dragon,
          ),
        ),
        const SizedBox(height: AppSpacing.medium),
        SectionCard(
          title: l10n.listActiveSkills,
          expanded: _activeSkillsExpanded,
          onTap: () =>
              setState(() => _activeSkillsExpanded = !_activeSkillsExpanded),
          child: activeSkills.isEmpty
              ? _NoneText(text: l10n.userSetNone)
              : Column(
                  children: [
                    for (final (index, skill) in activeSkills.indexed) ...[
                      if (index > 0) const AppHDivider(),
                      ListItemLayout(
                        headline: Text(skill.name),
                        onTap: () => context.push(
                          AppRoutes.skillTreeDetail(skill.skillTreeId),
                        ),
                      ),
                    ],
                  ],
                ),
        ),
        const SizedBox(height: AppSpacing.medium),
        SectionCard(
          title: l10n.listSkills,
          expanded: _skillsExpanded,
          onTap: () => setState(() => _skillsExpanded = !_skillsExpanded),
          child: skills.isEmpty
              ? _NoneText(text: l10n.userSetNone)
              : SkillPoints(skills: skills),
        ),
        const SizedBox(height: AppSpacing.medium),
        SectionCard(
          title: l10n.listArmors,
          expanded: _armorPiecesExpanded,
          onTap: () =>
              setState(() => _armorPiecesExpanded = !_armorPiecesExpanded),
          child: Column(
            children: [
              EquipmentSlotItem(
                icon: WeaponEntityIcon(
                  type: set.weapon?.type ?? WeaponType.greatSword,
                  rarity: set.weapon?.rarity ?? 1,
                  size: AppSize.large,
                ),
                name: set.weapon?.name,
                numberOfSlots: set.weapon?.numberOfSlots ?? 0,
                decorations: decorations
                    .where((d) => d.equipmentType == EquipmentType.weapon)
                    .toList(),
                onTap: _pickWeapon,
                onAddDecoration: (slots) =>
                    _addDecoration(EquipmentType.weapon, slots),
                onRemoveDecoration: (id) =>
                    _removeDecoration(EquipmentType.weapon, id),
              ),
              for (final type in _armorSlots) ...[
                const AppHDivider(),
                Builder(
                  builder: (context) {
                    final armor = armors
                        .where((armor) => armor.type == type)
                        .firstOrNull;
                    return EquipmentSlotItem(
                      icon: EntityIcon(
                        asset: equipmentTypeIconAsset(type),
                        size: AppSize.large,
                        tint: rarityColor(armor?.rarity ?? 1),
                      ),
                      name: armor?.name,
                      numberOfSlots: armor?.numberOfSlots ?? 0,
                      decorations: decorations
                          .where((d) => d.equipmentType == type)
                          .toList(),
                      onTap: () => _pickArmor(type),
                      onAddDecoration: (slots) => _addDecoration(type, slots),
                      onRemoveDecoration: (id) => _removeDecoration(type, id),
                    );
                  },
                ),
              ],
            ],
          ),
        ),
        const SizedBox(height: AppSpacing.medium),
        SectionCard(
          title: l10n.listDecorations,
          expanded: _decorationsExpanded,
          onTap: () =>
              setState(() => _decorationsExpanded = !_decorationsExpanded),
          child: decorationSummary.isEmpty
              ? _NoneText(text: l10n.userSetNone)
              : Column(
                  children: [
                    for (final (index, decoration)
                        in decorationSummary.indexed) ...[
                      if (index > 0) const AppHDivider(),
                      ListItemLayout(
                        leading: EntityIcon(
                          asset: 'ic_item_jewel',
                          size: AppSize.medium,
                          tint: itemIconColorValue(decoration.decoration.color),
                        ),
                        headline: Text(decoration.decoration.name),
                        trailing: Text('${decoration.quantity}'),
                        onTap: () => context.push(
                          AppRoutes.decorationDetail(decoration.decoration.id),
                        ),
                      ),
                    ],
                  ],
                ),
        ),
        const SizedBox(height: AppSpacing.medium),
        SectionCard(
          title: l10n.listRecipe,
          expanded: _recipeExpanded,
          onTap: () => setState(() => _recipeExpanded = !_recipeExpanded),
          child: recipe.isEmpty
              ? _NoneText(text: l10n.userSetNone)
              : EquipmentRecipe(recipe: recipe),
        ),
      ],
    );
  }
}

class const _NoneText({required final String text}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(
        horizontal: AppSpacing.large,
        vertical: AppSpacing.medium,
      ),
      child: Text(text, style: Theme.of(context).textTheme.bodyMedium),
    );
  }
}
