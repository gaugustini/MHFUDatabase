import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../../../core/router/app_routes.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/item_chip.dart';
import '../../../item/domain/item.dart';
import '../../domain/veggie.dart';

class const VeggieTradeRow({
  required final VeggieTrade trade,
  final String? locationName,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final sameCommonRare = trade.itemCommon.id == trade.itemRare.id;

    return InkWell(
      onTap: () => context.push(AppRoutes.itemDetail(trade.itemTraded.id)),
      child: Padding(
        padding: const EdgeInsets.symmetric(
          horizontal: AppPadding.large,
          vertical: AppPadding.medium,
        ),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Expanded(
              flex: 9,
              child: Row(
                children: [
                  ItemEntityIcon(
                    type: trade.itemTraded.iconType,
                    color: trade.itemTraded.iconColor,
                    size: AppSize.medium,
                  ),
                  const SizedBox(width: AppSpacing.large),
                  Expanded(
                    child: locationName == null
                        ? Text(
                            trade.itemTraded.name,
                            maxLines: 1,
                            overflow: TextOverflow.ellipsis,
                            style: Theme.of(context).textTheme.bodyMedium,
                          )
                        : Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text(
                                trade.itemTraded.name,
                                maxLines: 1,
                                overflow: TextOverflow.ellipsis,
                                style: Theme.of(context).textTheme.bodyMedium,
                              ),
                              Text(
                                locationName!,
                                style: Theme.of(context).textTheme.bodySmall,
                              ),
                            ],
                          ),
                  ),
                ],
              ),
            ),
            const SizedBox(width: AppSpacing.large),
            Expanded(
              flex: 7,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  _VeggieTradeItem(
                    item: trade.itemCommon,
                    percentage: sameCommonRare ? 100 : 80,
                  ),
                  if (!sameCommonRare) ...[
                    const SizedBox(height: AppSpacing.small),
                    _VeggieTradeItem(item: trade.itemRare, percentage: 20),
                  ],
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class const _VeggieTradeItem({
  required final Item item,
  required final int percentage,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).colorScheme;
    final style = Theme.of(
      context,
    ).textTheme.bodySmall?.copyWith(color: colors.onSurfaceVariant);

    return ItemChip(
      itemId: item.id,
      children: [
        ItemEntityIcon(
          type: item.iconType,
          color: item.iconColor,
          size: AppSize.small,
        ),
        const SizedBox(width: AppSpacing.medium),
        Flexible(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                item.name,
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
                style: style,
              ),
              Text('($percentage%)', style: style),
            ],
          ),
        ),
      ],
    );
  }
}
