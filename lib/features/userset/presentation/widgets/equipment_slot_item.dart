import 'package:flutter/material.dart' hide Decoration;

import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/mhfu_colors.dart';
import '../../../../l10n/app_localizations.dart';
import '../../domain/user_equipment_set.dart';

class const EquipmentSlotItem({
  required final Widget icon,
  required final String? name,
  required final int numberOfSlots,
  required final List<EquipmentDecoration> decorations,
  required final VoidCallback onTap,
  required final ValueChanged<int> onAddDecoration,
  required final ValueChanged<int> onRemoveDecoration,
  super.key,
}) extends StatefulWidget {
  @override
  State<EquipmentSlotItem> createState() => _EquipmentSlotItemState();
}

class _EquipmentSlotItemState extends State<EquipmentSlotItem> {
  bool _expanded = false;

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final totalDecorationSlots = widget.decorations.fold<int>(
      0,
      (sum, decoration) => sum + decoration.decoration.requiredSlots * decoration.quantity,
    );
    final availableSlots = widget.numberOfSlots - totalDecorationSlots;

    return Column(
      children: [
        ListItemLayout(
          leading: widget.icon,
          headline: Text(widget.name ?? l10n.userSetNothingEquipped),
          trailing: widget.name == null
              ? null
              : _SlotPips(
                  numberOfSlots: widget.numberOfSlots,
                  filledColors: [
                    for (final decoration in widget.decorations)
                      for (var i = 0; i < decoration.decoration.requiredSlots * decoration.quantity; i++)
                        itemIconColorValue(decoration.decoration.color),
                  ],
                ),
          onTap: widget.onTap,
        ),
        if (widget.numberOfSlots > 0)
          InkWell(
            onTap: totalDecorationSlots == 0
                ? () => widget.onAddDecoration(availableSlots)
                : () => setState(() => _expanded = !_expanded),
            child: SizedBox(
              width: double.infinity,
              child: Center(
                child: Padding(
                  padding: const EdgeInsets.symmetric(vertical: AppPadding.small),
                  child: Icon(
                    totalDecorationSlots == 0
                        ? Icons.add
                        : (_expanded ? Icons.keyboard_arrow_up : Icons.keyboard_arrow_down),
                    color: Theme.of(context).colorScheme.onSurfaceVariant,
                    size: AppSize.extraSmall,
                  ),
                ),
              ),
            ),
          ),
        if (_expanded && totalDecorationSlots > 0)
          _DecorationList(
            decorations: widget.decorations,
            availableSlots: availableSlots,
            onAddDecoration: () => widget.onAddDecoration(availableSlots),
            onRemoveDecoration: widget.onRemoveDecoration,
          ),
      ],
    );
  }
}

class const _SlotPips({
  required final int numberOfSlots,
  required final List<Color> filledColors,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final color = Theme.of(context).colorScheme.onSurface;
    final availableSlots = numberOfSlots - filledColors.length;

    return SizedBox(
      width: AppSize.tiny * 3,
      height: AppSize.tiny,
      child: Row(
        children: [
          for (final filledColor in filledColors)
            Expanded(
              child: Center(
                child: Container(
                  width: AppSize.tiny * 0.6,
                  height: AppSize.tiny * 0.6,
                  decoration: BoxDecoration(shape: BoxShape.circle, color: filledColor),
                ),
              ),
            ),
          for (var i = 0; i < availableSlots; i++)
            Expanded(
              child: Center(
                child: Container(
                  width: AppSize.tiny * 0.6,
                  height: AppSize.tiny * 0.6,
                  decoration: BoxDecoration(
                    shape: BoxShape.circle,
                    border: Border.all(color: color),
                  ),
                ),
              ),
            ),
          for (var i = 0; i < 3 - numberOfSlots; i++)
            Expanded(
              child: Center(
                child: Container(
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

class const _DecorationList({
  required final List<EquipmentDecoration> decorations,
  required final int availableSlots,
  required final VoidCallback onAddDecoration,
  required final ValueChanged<int> onRemoveDecoration,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        for (final decoration in decorations)
          for (var i = 0; i < decoration.quantity; i++) ...[
            const AppHDivider(),
            ListItemLayout(
              contentPadding: const EdgeInsets.symmetric(
                horizontal: AppPadding.large,
                vertical: AppPadding.medium,
              ),
              leading: EntityIcon(
                asset: 'ic_item_jewel',
                size: AppSize.small,
                tint: itemIconColorValue(decoration.decoration.color),
              ),
              headline: Text(decoration.decoration.name),
              trailing: IconButton(
                icon: const Icon(Icons.close),
                onPressed: () => onRemoveDecoration(decoration.decoration.id),
              ),
            ),
          ],
        if (availableSlots > 0)
          InkWell(
            onTap: onAddDecoration,
            child: SizedBox(
              width: double.infinity,
              child: Center(
                child: Padding(
                  padding: const EdgeInsets.symmetric(vertical: AppPadding.small),
                  child: Icon(
                    Icons.add,
                    color: Theme.of(context).colorScheme.onSurfaceVariant,
                    size: AppSize.extraSmall,
                  ),
                ),
              ),
            ),
          ),
      ],
    );
  }
}
