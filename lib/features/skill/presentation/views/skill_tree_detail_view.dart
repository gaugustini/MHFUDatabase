import 'package:flutter/material.dart' hide Decoration;
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
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/equipment_stats.dart' show signedNumber;
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/mhfu_colors.dart';
import '../../../../core/widgets/section_card.dart';
import '../../../../core/widgets/surface_card.dart';
import '../../../../l10n/app_localizations.dart';
import '../../../armor/domain/armor.dart';
import '../../../decoration/domain/decoration.dart';
import '../../data/skill_repository.dart';
import '../../domain/skill.dart';

enum _SkillTreePage { summary, equipment }

class const SkillTreeDetailView({
  required final int skillTreeId,
  required final VoidCallback navigateBack,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<SkillTreeDetailView> createState() => _SkillTreeDetailViewState();
}

class _SkillTreeDetailViewState extends State<SkillTreeDetailView> {
  late final Future<SkillTree> _future = SkillRepository().getSkillTree(
    widget.skillTreeId,
    AppSettingsController.instance.locale.languageCode,
  );
  late final Future<List<Armor>> _armorsFuture = SkillRepository()
      .getArmorListWithSkill(
        widget.skillTreeId,
        AppSettingsController.instance.locale.languageCode,
      );
  late final Future<List<Decoration>> _decorationsFuture = SkillRepository()
      .getDecorationListWithSkill(
        widget.skillTreeId,
        AppSettingsController.instance.locale.languageCode,
      );

  _SkillTreePage _page = _SkillTreePage.summary;

  void _goToPage(_SkillTreePage page) => setState(() => _page = page);

  void _handleBack() {
    if (_page == _SkillTreePage.summary) {
      widget.navigateBack();
    } else {
      _goToPage(_SkillTreePage.summary);
    }
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return FutureBuilder<SkillTree>(
      future: _future,
      builder: (context, snapshot) {
        final skillTree = snapshot.data;

        return PopScope(
          canPop: _page == _SkillTreePage.summary,
          onPopInvokedWithResult: (didPop, result) {
            if (!didPop) _goToPage(_SkillTreePage.summary);
          },
          child: Scaffold(
            appBar: AppTopBar(
              title: skillTree?.name ?? l10n.screenSkillTreeDetail,
              navigation: AppTopBarNavigation.back,
              onNavigationTap: _handleBack,
              onSearchTap: widget.openSearch,
            ),
            body: skillTree == null
                ? const Center(child: CircularProgressIndicator())
                : AnimatedPageContent<_SkillTreePage>(
                    value: _page,
                    index: (page) => page.index,
                    builder: (context, page) => switch (page) {
                      _SkillTreePage.summary => _SummaryPage(
                        skillTree: skillTree,
                        onChangePage: _goToPage,
                      ),
                      _SkillTreePage.equipment => _EquipmentPage(
                        armorsFuture: _armorsFuture,
                        decorationsFuture: _decorationsFuture,
                      ),
                    },
                  ),
          ),
        );
      },
    );
  }
}

class const _SummaryPage({
  required final SkillTree skillTree,
  required final ValueChanged<_SkillTreePage> onChangePage,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final skills = skillTree.skills ?? const <Skill>[];

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
              for (final (index, skill) in skills.indexed) ...[
                if (index > 0) const AppHDivider(),
                ListItemLayout(
                  headline: Text(skill.name),
                  supporting: Text(skill.description),
                  trailing: Text(signedNumber(skill.requiredPoints)),
                ),
              ],
            ],
          ),
        ),
        const SizedBox(height: AppSpacing.medium),
        ButtonPage(
          title: l10n.skillTreeEquipment,
          onTap: () => onChangePage(_SkillTreePage.equipment),
        ),
      ],
    );
  }
}

class const _EquipmentPage({
  required final Future<List<Armor>> armorsFuture,
  required final Future<List<Decoration>> decorationsFuture,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

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
        FutureBuilder<List<Decoration>>(
          future: decorationsFuture,
          builder: (context, snapshot) {
            final decorations = snapshot.data;
            return AnimatedSwitcher(
              duration: const Duration(milliseconds: 200),
              child: decorations == null
                  ? const Center(child: CircularProgressIndicator())
                  : _EquipmentSection(
                      key: const ValueKey('decorations'),
                      title: l10n.skillDecorationList,
                      isEmpty: decorations.isEmpty,
                      children: [
                        for (final decoration in decorations)
                          _DecorationRow(decoration: decoration),
                      ],
                    ),
            );
          },
        ),
        const SizedBox(height: AppSpacing.medium),
        FutureBuilder<List<Armor>>(
          future: armorsFuture,
          builder: (context, snapshot) {
            final armors = snapshot.data;
            return AnimatedSwitcher(
              duration: const Duration(milliseconds: 200),
              child: armors == null
                  ? const Center(child: CircularProgressIndicator())
                  : _EquipmentSection(
                      key: const ValueKey('armors'),
                      title: l10n.skillArmorList,
                      isEmpty: armors.isEmpty,
                      children: [
                        for (final armor in armors) _ArmorRow(armor: armor),
                      ],
                    ),
            );
          },
        ),
      ],
    );
  }
}

class const _EquipmentSection({
  required final String title,
  required final bool isEmpty,
  required final List<Widget> children,
  super.key,
}) extends StatefulWidget {
  @override
  State<_EquipmentSection> createState() => _EquipmentSectionState();
}

class _EquipmentSectionState extends State<_EquipmentSection> {
  bool _expanded = true;

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return SectionCard(
      title: widget.title,
      expanded: _expanded,
      onTap: () => setState(() => _expanded = !_expanded),
      margin: EdgeInsets.zero,
      child: widget.isEmpty
          ? Padding(
              padding: const EdgeInsets.all(AppPadding.large),
              child: Text(l10n.skillEmptyList),
            )
          : Column(
              children: [
                for (final (index, child) in widget.children.indexed) ...[
                  if (index > 0) const AppHDivider(),
                  child,
                ],
              ],
            ),
    );
  }
}

class const _ArmorRow({required final Armor armor}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListItemLayout(
      leading: EntityIcon(
        asset: equipmentTypeIconAsset(armor.type),
        size: AppSize.medium,
        tint: rarityColor(armor.rarity),
      ),
      headline: Text(armor.name),
      trailing: Text(signedNumber(armor.skills!.first.points)),
      onTap: () => context.push(AppRoutes.armorDetail(armor.id)),
    );
  }
}

class const _DecorationRow({required final Decoration decoration})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListItemLayout(
      leading: ItemEntityIcon(
        type: ItemIconType.jewel,
        color: decoration.color,
        size: AppSize.medium,
      ),
      headline: Text(decoration.name),
      trailing: Text(signedNumber(decoration.skills!.first.points)),
      onTap: () => context.push(AppRoutes.decorationDetail(decoration.id)),
    );
  }
}
