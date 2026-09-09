import 'package:flutter/material.dart';

import '../theme/app_dimensions.dart';

class const FilterPill({
  required final Widget child,
  required final bool selected,
  required final VoidCallback onTap,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).colorScheme;

    return Material(
      color: selected ? colors.secondaryContainer : Colors.transparent,
      borderRadius: BorderRadius.circular(AppRadius.medium),
      child: InkWell(
        onTap: onTap,
        borderRadius: BorderRadius.circular(AppRadius.medium),
        child: Container(
          padding: const EdgeInsets.symmetric(
            horizontal: AppPadding.medium,
            vertical: AppPadding.small,
          ),
          decoration: BoxDecoration(
            border: Border.all(
              color: selected ? colors.secondary : colors.outline,
            ),
            borderRadius: BorderRadius.circular(AppRadius.medium),
          ),
          child: DefaultTextStyle.merge(
            style: TextStyle(
              color: selected ? colors.onSecondaryContainer : colors.onSurfaceVariant,
            ),
            child: child,
          ),
        ),
      ),
    );
  }
}
