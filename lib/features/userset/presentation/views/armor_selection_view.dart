import 'package:flutter/material.dart';

import '../../../../core/domain/enums.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/mhfu_colors.dart';
import '../../../../core/widgets/pill_list_item.dart';
import '../../../../l10n/app_localizations.dart';
import '../../../armor/data/armor_repository.dart';
import '../../../armor/domain/armor_filter.dart';
import '../../../armor/domain/armor.dart';
import '../../../skill/domain/skill.dart';
import '../widgets/selection_pill.dart';
import '../widgets/selection_search_bar.dart';
import 'skill_selection_view.dart';

const _rarities = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
const _slotOptions = [0, 1, 2, 3];

class const ArmorSelectionView({
  required final EquipmentType type,
  required final HunterType hunterType,
  required final Gender gender,
  super.key,
}) extends StatefulWidget {
  @override
  State<ArmorSelectionView> createState() => _ArmorSelectionViewState();
}

class _ArmorSelectionViewState extends State<ArmorSelectionView> {
  late ArmorFilter _filter = ArmorFilter(
    type: widget.type,
    hunterType: widget.hunterType,
    gender: widget.gender,
  );

  Future<void> _openFilterSheet() async {
    final updated = await showModalBottomSheet<ArmorFilter>(
      context: context,
      isScrollControlled: true,
      builder: (context) => _ArmorFilterSheet(filter: _filter),
    );
    if (!mounted || updated == null) return;
    setState(() => _filter = updated);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: SelectionSearchBar(
        onQueryChange: (name) => setState(
          () => _filter = ArmorFilter(
            name: name.isEmpty ? null : name,
            type: _filter.type,
            hunterType: _filter.hunterType,
            gender: _filter.gender,
            rarity: _filter.rarity,
            numberOfSlots: _filter.numberOfSlots,
            skills: _filter.skills,
          ),
        ),
        onFilterTap: _openFilterSheet,
      ),
      body: FutureBuilder<List<Armor>>(
        future: ArmorRepository().getArmorList(
          AppSettingsController.instance.locale.languageCode,
          filter: _filter,
        ),
        builder: (context, snapshot) {
          final armors = snapshot.data;
          if (armors == null) {
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
            itemCount: armors.length,
            separatorBuilder: (context, index) => const AppHDivider(),
            itemBuilder: (context, index) {
              final armor = armors[index];
              return PillListItem(
                child: ListItemLayout(
                  leading: EntityIcon(
                    asset: equipmentTypeIconAsset(armor.type),
                    size: AppSize.medium,
                    tint: rarityColor(armor.rarity),
                  ),
                  headline: Text(armor.name),
                  onTap: () => Navigator.of(context).pop(armor),
                ),
              );
            },
          );
        },
      ),
    );
  }
}

class const _ArmorFilterSheet({required final ArmorFilter filter})
    extends StatefulWidget {
  @override
  State<_ArmorFilterSheet> createState() => _ArmorFilterSheetState();
}

class _ArmorFilterSheetState extends State<_ArmorFilterSheet> {
  late ArmorFilter _filter = widget.filter;

  void _apply(ArmorFilter filter) {
    setState(() => _filter = filter);
    Navigator.of(context).pop(filter);
  }

  Future<void> _addSkillFilter() async {
    final skillTree = await Navigator.of(
      context,
    ).push<SkillTree>(MaterialPageRoute(builder: (_) => const SkillSelectionView()));
    if (skillTree == null || !mounted) return;
    final skills = _filter.skills ?? const <SkillTree>[];
    if (skills.any((skill) => skill.id == skillTree.id)) return;
    _apply(_filter.copyWith(skills: [...skills, skillTree]));
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final skills = _filter.skills ?? const <SkillTree>[];
    final rarities = _filter.rarity ?? const <int>[];
    final numberOfSlots = _filter.numberOfSlots ?? const <int>[];

    return SafeArea(
      child: SingleChildScrollView(
        padding: const EdgeInsets.all(AppPadding.large),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(l10n.userSetFilterSkill, style: Theme.of(context).textTheme.titleMedium),
            const SizedBox(height: AppSpacing.medium),
            Wrap(
              spacing: AppSpacing.small,
              runSpacing: AppSpacing.small,
              children: [
                for (final skill in skills)
                  SelectionPill(
                    selected: true,
                    onTap: () => _apply(
                      _filter.copyWith(
                        skills: skills.where((s) => s.id != skill.id).toList(),
                      ),
                    ),
                    child: Text(skill.name),
                  ),
                SelectionPill(
                  selected: false,
                  onTap: _addSkillFilter,
                  child: const Icon(Icons.add),
                ),
              ],
            ),
            const SizedBox(height: AppSpacing.large),
            Text(l10n.userSetFilterRarity, style: Theme.of(context).textTheme.titleMedium),
            const SizedBox(height: AppSpacing.medium),
            Wrap(
              spacing: AppSpacing.small,
              runSpacing: AppSpacing.small,
              children: [
                for (final rarity in _rarities)
                  SelectionPill(
                    selected: rarities.contains(rarity),
                    onTap: () => _apply(
                      _filter.copyWith(
                        rarity: rarities.contains(rarity)
                            ? (rarities.toList()..remove(rarity))
                            : (rarities.toList()..add(rarity)),
                      ),
                    ),
                    child: Text('$rarity'),
                  ),
              ],
            ),
            const SizedBox(height: AppSpacing.large),
            Text(
              l10n.userSetFilterNumberOfSlots,
              style: Theme.of(context).textTheme.titleMedium,
            ),
            const SizedBox(height: AppSpacing.medium),
            Wrap(
              spacing: AppSpacing.small,
              runSpacing: AppSpacing.small,
              children: [
                for (final slots in _slotOptions)
                  SelectionPill(
                    selected: numberOfSlots.contains(slots),
                    onTap: () => _apply(
                      _filter.copyWith(
                        numberOfSlots: numberOfSlots.contains(slots)
                            ? (numberOfSlots.toList()..remove(slots))
                            : (numberOfSlots.toList()..add(slots)),
                      ),
                    ),
                    child: Text('$slots'),
                  ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}

extension on ArmorFilter {
  ArmorFilter copyWith({
    List<int>? rarity,
    List<int>? numberOfSlots,
    List<SkillTree>? skills,
  }) {
    return ArmorFilter(
      name: name,
      type: type,
      hunterType: hunterType,
      gender: gender,
      rarity: (rarity ?? this.rarity)?.isEmpty ?? true ? null : rarity ?? this.rarity,
      numberOfSlots: (numberOfSlots ?? this.numberOfSlots)?.isEmpty ?? true
          ? null
          : numberOfSlots ?? this.numberOfSlots,
      skills: (skills ?? this.skills)?.isEmpty ?? true ? null : skills ?? this.skills,
    );
  }
}
