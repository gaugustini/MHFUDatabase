import 'package:flutter/material.dart';

import '../theme/app_dimensions.dart';

class const ListItemLayout({
  required final Widget headline,
  final Widget? supporting,
  final Widget? leading,
  final Widget? trailing,
  final EdgeInsetsGeometry contentPadding = const EdgeInsets.symmetric(
    horizontal: AppPadding.large,
    vertical: AppPadding.medium,
  ),
  final Color? backgroundColor,
  final VoidCallback? onTap,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Material(
      color: backgroundColor ?? Colors.transparent,
      child: InkWell(
        onTap: onTap,
        child: Padding(
          padding: contentPadding,
          child: ConstrainedBox(
            constraints: const BoxConstraints(minHeight: AppSize.medium),
            child: Row(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                if (leading != null) ...[
                  leading!,
                  const SizedBox(width: AppSpacing.large),
                ],
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      headline,
                      if (supporting != null) ...[
                        const SizedBox(height: AppSpacing.small),
                        supporting!,
                      ],
                    ],
                  ),
                ),
                if (trailing != null) ...[
                  const SizedBox(width: AppSpacing.large),
                  trailing!,
                ],
              ],
            ),
          ),
        ),
      ),
    );
  }
}
