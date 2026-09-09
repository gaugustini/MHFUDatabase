import 'package:flutter/material.dart';

import '../theme/app_dimensions.dart';

class const SectionCard({
  required final String title,
  required final Widget child,
  final bool expanded = true,
  final VoidCallback? onTap,
  final EdgeInsetsGeometry margin = const EdgeInsets.symmetric(
    horizontal: AppPadding.medium,
  ),
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).colorScheme;
    final borderRadius = BorderRadius.only(
      topLeft: const Radius.circular(AppRadius.medium),
      topRight: const Radius.circular(AppRadius.medium),
      bottomLeft: expanded
          ? Radius.zero
          : const Radius.circular(AppRadius.medium),
      bottomRight: expanded
          ? Radius.zero
          : const Radius.circular(AppRadius.medium),
    );

    return Container(
      margin: margin,
      child: Column(
        children: [
          Material(
            color: colors.secondaryContainer,
            borderRadius: borderRadius,
            clipBehavior: Clip.antiAlias,
            child: InkWell(
              onTap: onTap,
              borderRadius: borderRadius,
              child: Container(
                width: double.infinity,
                padding: const EdgeInsets.all(AppPadding.large),
                child: Row(
                  children: [
                    Expanded(
                      child: Text(
                        title,
                        style: Theme.of(
                          context,
                        ).textTheme.titleSmall?.copyWith(
                          color: colors.onSecondaryContainer,
                        ),
                      ),
                    ),
                    if (onTap != null)
                      Icon(
                        expanded
                            ? Icons.keyboard_arrow_up
                            : Icons.keyboard_arrow_down,
                        color: colors.onSecondaryContainer,
                        size: AppSize.extraSmall,
                      ),
                  ],
                ),
              ),
            ),
          ),
          if (expanded)
            Material(
              color: colors.surface,
              borderRadius: const BorderRadius.only(
                bottomLeft: Radius.circular(AppRadius.medium),
                bottomRight: Radius.circular(AppRadius.medium),
              ),
              clipBehavior: Clip.antiAlias,
              child: child,
            ),
        ],
      ),
    );
  }
}
