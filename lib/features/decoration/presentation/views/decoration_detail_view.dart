import 'package:flutter/material.dart' hide Decoration;

import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/detail_header.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/equipment_recipe.dart';
import '../../../../core/widgets/mhfu_colors.dart';
import '../../../../core/widgets/section_card.dart';
import '../../../../core/widgets/skill_points.dart';
import '../../../../core/widgets/surface_card.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/decoration_repository.dart';
import '../../domain/decoration.dart';

class const DecorationDetailView({
  required final int decorationId,
  required final VoidCallback navigateBack,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<DecorationDetailView> createState() => _DecorationDetailViewState();
}

class _DecorationDetailViewState extends State<DecorationDetailView> {
  late final Future<Decoration> _future = DecorationRepository().getDecoration(
    widget.decorationId,
    AppSettingsController.instance.locale.languageCode,
  );

  bool _skillsExpanded = true;
  bool _recipeAExpanded = true;
  bool _recipeBExpanded = true;

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.screenDecorationDetail,
        navigation: AppTopBarNavigation.back,
        onNavigationTap: widget.navigateBack,
        onSearchTap: widget.openSearch,
      ),
      body: FutureBuilder<Decoration>(
        future: _future,
        builder: (context, snapshot) {
          final decoration = snapshot.data;
          if (decoration == null) {
            return const Center(child: CircularProgressIndicator());
          }
          final skills = decoration.skills ?? const [];
          final recipeA = decoration.recipeA ?? const [];
          final recipeB = decoration.recipeB ?? const [];

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
                child: Column(
                  children: [
                    DetailHeader(
                      icon: EntityIcon(
                        asset: 'ic_ui_decoration',
                        tint: itemIconColorValue(decoration.color),
                      ),
                      title: decoration.name,
                      subtitle: l10n.armorRarity(decoration.rarity),
                      description: decoration.description,
                    ),
                    const AppHDivider(),
                    _DecorationSummary(decoration: decoration),
                  ],
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
              if (recipeA.isNotEmpty) ...[
                const SizedBox(height: AppSpacing.medium),
                SectionCard(
                  title: recipeB.isNotEmpty
                      ? l10n.listRecipeA
                      : l10n.listRecipe,
                  expanded: _recipeAExpanded,
                  onTap: () =>
                      setState(() => _recipeAExpanded = !_recipeAExpanded),
                  child: EquipmentRecipe(recipe: recipeA),
                ),
              ],
              if (recipeB.isNotEmpty) ...[
                const SizedBox(height: AppSpacing.medium),
                SectionCard(
                  title: l10n.listRecipeB,
                  expanded: _recipeBExpanded,
                  onTap: () =>
                      setState(() => _recipeBExpanded = !_recipeBExpanded),
                  child: EquipmentRecipe(recipe: recipeB),
                ),
              ],
            ],
          );
        },
      ),
    );
  }
}

class const _DecorationSummary({required final Decoration decoration})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final textTheme = Theme.of(context).textTheme;
    final color = itemIconColorValue(decoration.color);

    return Padding(
      padding: const EdgeInsets.all(AppPadding.large),
      child: Column(
        children: [
          Row(
            children: [
              Expanded(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    for (var i = 0; i < decoration.requiredSlots; i++)
                      Padding(
                        padding: const EdgeInsets.symmetric(
                          horizontal: AppPadding.small / 2,
                        ),
                        child: Container(
                          width: AppSize.tiny,
                          height: AppSize.tiny,
                          decoration: BoxDecoration(
                            shape: BoxShape.circle,
                            color: color,
                          ),
                        ),
                      ),
                  ],
                ),
              ),
              Expanded(
                child: Text(
                  '${decoration.buyPrice}z',
                  textAlign: TextAlign.center,
                  style: textTheme.bodyMedium,
                ),
              ),
              Expanded(
                child: Text(
                  '${decoration.sellPrice}z',
                  textAlign: TextAlign.center,
                  style: textTheme.bodyMedium,
                ),
              ),
            ],
          ),
          const SizedBox(height: AppSpacing.small),
          Row(
            children: [
              Expanded(
                child: Text(
                  l10n.decorationRequiredSlots,
                  textAlign: TextAlign.center,
                  style: textTheme.bodySmall,
                ),
              ),
              Expanded(
                child: Text(
                  l10n.decorationBuyPrice,
                  textAlign: TextAlign.center,
                  style: textTheme.bodySmall,
                ),
              ),
              Expanded(
                child: Text(
                  l10n.decorationSellPrice,
                  textAlign: TextAlign.center,
                  style: textTheme.bodySmall,
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
