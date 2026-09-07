import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../../../core/domain/enums.dart';
import '../../../../core/router/app_routes.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/item_chip.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../l10n/app_localizations.dart';
import '../../../item/domain/item.dart';
import '../../domain/item_combination.dart';

class const CombinationRow({
  required final ItemCombination combination,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final textTheme = Theme.of(context).textTheme;
    final quantity = combination.quantityMin == combination.quantityMax
        ? 'x ${combination.quantityMin}'
        : 'x ${combination.quantityMin}~${combination.quantityMax}';

    return ListItemLayout(
      leading: ItemEntityIcon(
        type: combination.itemCreated.iconType,
        color: combination.itemCreated.iconColor,
        size: AppSize.medium,
      ),
      headline: Text(combination.itemCreated.name, style: textTheme.bodyMedium),
      supporting: Row(
        children: [
          Text('${combination.percentage}%', style: textTheme.bodyMedium),
          const SizedBox(width: AppSpacing.medium),
          Text(quantity, style: textTheme.bodyMedium),
          if (combination.type != ItemCombinationType.normal) ...[
            const SizedBox(width: AppSpacing.medium),
            _CombinationTag(
              label: combination.type == ItemCombinationType.treasure
                  ? l10n.combinationTreasure
                  : l10n.combinationAlchemy,
            ),
          ],
        ],
      ),
      trailing: Column(
        crossAxisAlignment: CrossAxisAlignment.end,
        mainAxisSize: MainAxisSize.min,
        children: [
          _CombinationMaterial(item: combination.itemA),
          const SizedBox(height: AppSpacing.small),
          _CombinationMaterial(item: combination.itemB),
        ],
      ),
      onTap: () =>
          context.push(AppRoutes.itemDetail(combination.itemCreated.id)),
    );
  }
}

class const _CombinationMaterial({required final Item item})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ItemChip(
      itemId: item.id,
      children: [
        Text(item.name, style: Theme.of(context).textTheme.bodySmall),
        const SizedBox(width: AppSpacing.small),
        ItemEntityIcon(
          type: item.iconType,
          color: item.iconColor,
          size: AppSize.small,
        ),
      ],
    );
  }
}

class const _CombinationTag({required final String label})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).colorScheme;

    return Container(
      padding: const EdgeInsets.all(AppPadding.small),
      decoration: BoxDecoration(
        color: colors.tertiaryContainer,
        borderRadius: BorderRadius.circular(AppRadius.small),
      ),
      child: Text(
        label,
        maxLines: 1,
        overflow: TextOverflow.ellipsis,
        style: Theme.of(
          context,
        ).textTheme.labelSmall?.copyWith(color: colors.onTertiaryContainer),
      ),
    );
  }
}
