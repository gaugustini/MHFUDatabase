import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../../../core/router/app_routes.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/detail_header.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/equipment_recipe.dart';
import '../../../../core/widgets/equipment_stats.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/mhfu_colors.dart';
import '../../../../core/widgets/section_card.dart';
import '../../../../core/widgets/skill_points.dart';
import '../../../../core/widgets/surface_card.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/armor_repository.dart';
import '../../domain/armor.dart';

class const ArmorSetDetailView({
  required final int armorSetId,
  required final VoidCallback navigateBack,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<ArmorSetDetailView> createState() => _ArmorSetDetailViewState();
}

class _ArmorSetDetailViewState extends State<ArmorSetDetailView> {
  late final Future<ArmorSet> _future = ArmorRepository().getArmorSet(
    widget.armorSetId,
    AppSettingsController.instance.locale.languageCode,
  );

  bool _statsExpanded = true;
  bool _armorsExpanded = true;
  bool _skillsExpanded = true;
  bool _recipeExpanded = true;

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return FutureBuilder<ArmorSet>(
      future: _future,
      builder: (context, snapshot) {
        final armorSet = snapshot.data;

        return Scaffold(
          appBar: AppTopBar(
            title: armorSet?.name ?? l10n.screenArmorSetDetail,
            navigation: AppTopBarNavigation.back,
            onNavigationTap: widget.navigateBack,
            onSearchTap: widget.openSearch,
          ),
          body: armorSet == null
              ? const Center(child: CircularProgressIndicator())
              : _buildContent(context, l10n, armorSet),
        );
      },
    );
  }

  Widget _buildContent(
    BuildContext context,
    AppLocalizations l10n,
    ArmorSet armorSet,
  ) {
    final armors = armorSet.armors ?? const <Armor>[];
    final skills = armorSet.skills ?? const [];
    final recipe = armorSet.recipe ?? const [];

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
            icon: EntityIcon(
              asset: 'ic_armor_set',
              tint: rarityColor(armorSet.rarity),
            ),
            title: armorSet.name,
            subtitle: l10n.armorRarity(armorSet.rarity),
          ),
        ),
        const SizedBox(height: AppSpacing.medium),
        SectionCard(
          title: l10n.listEquipmentStats,
          expanded: _statsExpanded,
          onTap: () => setState(() => _statsExpanded = !_statsExpanded),
          child: EquipmentStats(
            defense: armorSet.defense,
            maxDefense: armorSet.maxDefense,
            fire: armorSet.fire,
            water: armorSet.water,
            thunder: armorSet.thunder,
            ice: armorSet.ice,
            dragon: armorSet.dragon,
          ),
        ),
        if (armors.isNotEmpty) ...[
          const SizedBox(height: AppSpacing.medium),
          SectionCard(
            title: l10n.listArmors,
            expanded: _armorsExpanded,
            onTap: () => setState(() => _armorsExpanded = !_armorsExpanded),
            child: Column(
              children: [
                for (final (index, armor) in armors.indexed) ...[
                  if (index > 0) const AppHDivider(),
                  _ArmorRow(armor: armor),
                ],
              ],
            ),
          ),
        ],
        if (skills.isNotEmpty) ...[
          const SizedBox(height: AppSpacing.medium),
          SectionCard(
            title: l10n.listSkills,
            expanded: _skillsExpanded,
            onTap: () => setState(() => _skillsExpanded = !_skillsExpanded),
            child: SkillPoints(skills: skills),
          ),
        ],
        if (recipe.isNotEmpty) ...[
          const SizedBox(height: AppSpacing.medium),
          SectionCard(
            title: l10n.listRecipe,
            expanded: _recipeExpanded,
            onTap: () => setState(() => _recipeExpanded = !_recipeExpanded),
            child: EquipmentRecipe(recipe: recipe),
          ),
        ],
      ],
    );
  }
}

class const _ArmorRow({required final Armor armor}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return ListItemLayout(
      leading: EntityIcon(
        asset: equipmentTypeIconAsset(armor.type),
        size: AppSize.medium,
        tint: rarityColor(armor.rarity),
      ),
      headline: Text(armor.name),
      supporting: Text(l10n.armorRarity(armor.rarity)),
      trailing: SlotsIndicator(numberOfSlots: armor.numberOfSlots),
      onTap: () => context.push(AppRoutes.armorDetail(armor.id)),
    );
  }
}
