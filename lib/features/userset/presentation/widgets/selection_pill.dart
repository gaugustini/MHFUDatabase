import 'package:flutter/material.dart';

import '../../../../core/theme/app_dimensions.dart';

class const SelectionPill({
  required final bool selected,
  required final VoidCallback onTap,
  required final Widget child,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).colorScheme;

    return Material(
      color: selected ? colors.primaryContainer : colors.surfaceContainer,
      borderRadius: BorderRadius.circular(AppRadius.small),
      child: InkWell(
        borderRadius: BorderRadius.circular(AppRadius.small),
        onTap: onTap,
        child: Container(
          constraints: const BoxConstraints(
            minWidth: AppSize.large,
            minHeight: AppSize.large,
          ),
          padding: const EdgeInsets.symmetric(horizontal: AppPadding.medium),
          child: Center(widthFactor: 1, heightFactor: 1, child: child),
        ),
      ),
    );
  }
}
