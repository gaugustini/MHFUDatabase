import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../../../core/router/app_routes.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/button_page.dart';
import '../../../../core/widgets/detail_header.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/equipment_recipe.dart';
import '../../../../core/widgets/equipment_stats.dart';
import '../../../../core/widgets/mhfu_colors.dart';
import '../../../../core/widgets/section_card.dart';
import '../../../../core/widgets/skill_points.dart';
import '../../../../core/widgets/surface_card.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/armor_repository.dart';
import '../../domain/armor.dart';

class const ArmorDetailView({
  required final int armorId,
  required final VoidCallback navigateBack,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<ArmorDetailView> createState() => _ArmorDetailViewState();
}

class _ArmorDetailViewState extends State<ArmorDetailView> {
  late final Future<Armor> _future = ArmorRepository().getArmor(
    widget.armorId,
    AppSettingsController.instance.locale.languageCode,
  );

  bool _statsExpanded = true;
  bool _skillsExpanded = true;
  bool _recipeAExpanded = true;
  bool _recipeBExpanded = true;

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.screenArmorDetail,
        navigation: AppTopBarNavigation.back,
        onNavigationTap: widget.navigateBack,
        onSearchTap: widget.openSearch,
      ),
      body: FutureBuilder<Armor>(
        future: _future,
        builder: (context, snapshot) {
          final armor = snapshot.data;
          if (armor == null) {
            return const Center(child: CircularProgressIndicator());
          }
          final skills = armor.skills ?? const [];
          final recipes = armor.recipes ?? const [];

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
                    asset: equipmentTypeIconAsset(armor.type),
                    tint: rarityColor(armor.rarity),
                  ),
                  title: armor.name,
                  subtitle: l10n.armorRarity(armor.rarity),
                  description: armor.description,
                ),
              ),
              const SizedBox(height: AppSpacing.medium),
              ButtonPage(
                title: l10n.armorSetDetails,
                onTap: () =>
                    context.push(AppRoutes.armorSetDetail(armor.armorSetId)),
              ),
              const SizedBox(height: AppSpacing.medium),
              SectionCard(
                title: l10n.listEquipmentStats,
                expanded: _statsExpanded,
                onTap: () => setState(() => _statsExpanded = !_statsExpanded),
                child: EquipmentStats(
                  numberOfSlots: armor.numberOfSlots,
                  defense: armor.defense,
                  maxDefense: armor.maxDefense,
                  fire: armor.fire,
                  water: armor.water,
                  thunder: armor.thunder,
                  ice: armor.ice,
                  dragon: armor.dragon,
                ),
              ),
              if (skills.isNotEmpty) ...[
                const SizedBox(height: AppSpacing.medium),
                SectionCard(
                  title: l10n.listSkills,
                  expanded: _skillsExpanded,
                  onTap: () =>
                      setState(() => _skillsExpanded = !_skillsExpanded),
                  child: SkillPoints(skills: skills),
                ),
              ],
              if (recipes.isNotEmpty) ...[
                const SizedBox(height: AppSpacing.medium),
                SectionCard(
                  title: recipes.length == 1
                      ? l10n.listRecipe
                      : l10n.listRecipeA,
                  expanded: _recipeAExpanded,
                  onTap: () =>
                      setState(() => _recipeAExpanded = !_recipeAExpanded),
                  child: EquipmentRecipe(recipe: recipes[0]),
                ),
              ],
              if (recipes.length > 1) ...[
                const SizedBox(height: AppSpacing.medium),
                SectionCard(
                  title: l10n.listRecipeB,
                  expanded: _recipeBExpanded,
                  onTap: () =>
                      setState(() => _recipeBExpanded = !_recipeBExpanded),
                  child: EquipmentRecipe(recipe: recipes[1]),
                ),
              ],
            ],
          );
        },
      ),
    );
  }
}
