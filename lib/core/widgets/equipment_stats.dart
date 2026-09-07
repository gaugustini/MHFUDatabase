import 'package:flutter/material.dart';

import '../domain/enums.dart';
import '../theme/app_dimensions.dart';
import '../../l10n/app_localizations.dart';
import 'app_h_divider.dart';
import 'list_item_layout.dart';
import 'mhfu_colors.dart';

String signedNumber(int value) => value < 0 ? '−${-value}' : '$value';

class const EquipmentStats({
  final int? numberOfSlots,
  required final int defense,
  required final int maxDefense,
  required final int fire,
  required final int water,
  required final int thunder,
  required final int ice,
  required final int dragon,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final stats = [
      (
        label: l10n.armorDefenseMax,
        value: '$defense ($maxDefense)',
        asset: 'ic_ui_defense',
      ),
      (
        label: l10n.armorFireResistance,
        value: signedNumber(fire),
        asset: 'ic_element_fire',
      ),
      (
        label: l10n.armorWaterResistance,
        value: signedNumber(water),
        asset: 'ic_element_water',
      ),
      (
        label: l10n.armorThunderResistance,
        value: signedNumber(thunder),
        asset: 'ic_element_thunder',
      ),
      (
        label: l10n.armorIceResistance,
        value: signedNumber(ice),
        asset: 'ic_element_ice',
      ),
      (
        label: l10n.armorDragonResistance,
        value: signedNumber(dragon),
        asset: 'ic_element_dragon',
      ),
    ];

    return Column(
      children: [
        if (numberOfSlots != null) ...[
          ListItemLayout(
            leading: Image.asset(
              'assets/images/ic_ui_slots.webp',
              width: AppSize.extraSmall,
              height: AppSize.extraSmall,
              color: itemIconColorValue(ItemIconColor.blue),
              colorBlendMode: BlendMode.modulate,
            ),
            headline: Text(l10n.armorNumberOfSlots),
            trailing: SlotsIndicator(numberOfSlots: numberOfSlots!),
          ),
          const AppHDivider(),
        ],
        for (final (index, stat) in stats.indexed) ...[
          if (index > 0) const AppHDivider(),
          ListItemLayout(
            leading: Image.asset(
              'assets/images/${stat.asset}.webp',
              width: AppSize.extraSmall,
              height: AppSize.extraSmall,
            ),
            headline: Text(stat.label),
            trailing: Text(stat.value),
          ),
        ],
      ],
    );
  }
}

class const SlotsIndicator({
  required final int numberOfSlots,
  final double dotSize = AppSize.tiny,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final color = Theme.of(context).colorScheme.onSurface;

    return SizedBox(
      width: dotSize * 3,
      height: dotSize,
      child: Row(
        children: [
          for (var i = 0; i < 3; i++)
            Expanded(
              child: Center(
                child: i < numberOfSlots
                    ? Container(
                        width: dotSize * 0.6,
                        height: dotSize * 0.6,
                        decoration: BoxDecoration(
                          shape: BoxShape.circle,
                          border: Border.all(color: color),
                        ),
                      )
                    : Container(
                        height: 1,
                        margin: const EdgeInsets.symmetric(horizontal: 2),
                        color: color,
                      ),
              ),
            ),
        ],
      ),
    );
  }
}
