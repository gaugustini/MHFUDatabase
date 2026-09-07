import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../domain/shared.dart';
import '../router/app_routes.dart';
import '../theme/app_dimensions.dart';
import 'app_h_divider.dart';
import 'entity_icon.dart';
import 'list_item_layout.dart';

class const EquipmentRecipe({
  required final List<ItemQuantity> recipe,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        for (final (index, entry) in recipe.indexed) ...[
          if (index > 0) const AppHDivider(),
          ListItemLayout(
            leading: ItemEntityIcon(
              type: entry.item.iconType,
              color: entry.item.iconColor,
              size: AppSize.medium,
            ),
            headline: Text(entry.item.name),
            trailing: Text('${entry.quantity}'),
            onTap: () => context.push(AppRoutes.itemDetail(entry.item.id)),
          ),
        ],
      ],
    );
  }
}
