import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../../../core/domain/enums.dart';
import '../../../../core/router/app_routes.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/mhfu_colors.dart';
import '../../../../core/widgets/pill_list_item.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/user_equipment_set_repository.dart';
import '../../domain/user_equipment_set.dart';

class const UserSetListView({
  required final VoidCallback openDrawer,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<UserSetListView> createState() => _UserSetListViewState();
}

class _UserSetListViewState extends State<UserSetListView> {
  final _repository = UserEquipmentSetRepository();
  late Future<List<UserEquipmentSet>> _future;

  @override
  void initState() {
    super.initState();
    _load();
  }

  void _load() {
    _future = _repository.getUserEquipmentSetList(
      AppSettingsController.instance.locale.languageCode,
    );
  }

  Future<void> _create() async {
    final l10n = AppLocalizations.of(context)!;
    var hunterType = HunterType.blade;
    var gender = Gender.male;

    final confirmed = await showDialog<bool>(
      context: context,
      builder: (context) => StatefulBuilder(
        builder: (context, setDialogState) => AlertDialog(
          title: Text(l10n.userSetDialogNewSetTitle),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                l10n.userSetDialogHunterType,
                style: Theme.of(context).textTheme.titleSmall,
              ),
              const SizedBox(height: AppSpacing.small),
              SegmentedButton<HunterType>(
                expandedInsets: EdgeInsets.zero,
                segments: [
                  ButtonSegment(
                    value: HunterType.blade,
                    label: Text(l10n.userSetDialogHunterTypeBlade),
                  ),
                  ButtonSegment(
                    value: HunterType.gunner,
                    label: Text(l10n.userSetDialogHunterTypeGunner),
                  ),
                ],
                selected: {hunterType},
                onSelectionChanged: (selection) =>
                    setDialogState(() => hunterType = selection.first),
              ),
              const SizedBox(height: AppSpacing.large),
              Text(
                l10n.userSetDialogGender,
                style: Theme.of(context).textTheme.titleSmall,
              ),
              const SizedBox(height: AppSpacing.small),
              SegmentedButton<Gender>(
                expandedInsets: EdgeInsets.zero,
                segments: [
                  ButtonSegment(
                    value: Gender.male,
                    label: Text(l10n.userSetDialogGenderMale),
                  ),
                  ButtonSegment(
                    value: Gender.female,
                    label: Text(l10n.userSetDialogGenderFemale),
                  ),
                ],
                selected: {gender},
                onSelectionChanged: (selection) =>
                    setDialogState(() => gender = selection.first),
              ),
            ],
          ),
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
      ),
    );

    if (confirmed != true || !mounted) return;

    final id = await _repository.createUserEquipmentSet(
      l10n.userSetNewName,
      hunterType,
      gender,
    );
    if (!mounted) return;
    context.push(
      AppRoutes.userEquipmentSetDetail(id, hunterType.dbValue, gender.dbValue),
    );
    setState(_load);
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.screenUserEquipmentSetList,
        navigation: AppTopBarNavigation.menu,
        onNavigationTap: widget.openDrawer,
        onSearchTap: widget.openSearch,
      ),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: _create,
        icon: const Icon(Icons.add),
        label: Text(l10n.userSetCreate),
      ),
      body: FutureBuilder<List<UserEquipmentSet>>(
        future: _future,
        builder: (context, snapshot) {
          final sets = snapshot.data;
          if (sets == null) {
            return const Center(child: CircularProgressIndicator());
          }
          if (sets.isEmpty) {
            return Center(child: Text(l10n.userSetEmpty));
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
            itemCount: sets.length,
            separatorBuilder: (context, index) => const AppHDivider(),
            itemBuilder: (context, index) {
              final set = sets[index];
              return PillListItem(
                child: ListItemLayout(
                  leading: EntityIcon(
                    asset: 'ic_armor_set',
                    tint: rarityColor(0),
                  ),
                  headline: Text(
                    set.name.isEmpty ? l10n.userSetCreate : set.name,
                  ),
                  onTap: () => context.push(
                    AppRoutes.userEquipmentSetDetail(
                      set.id,
                      set.hunterType.dbValue,
                      set.gender.dbValue,
                    ),
                  ),
                ),
              );
            },
          );
        },
      ),
    );
  }
}
