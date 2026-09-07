import 'package:flutter/material.dart';

import '../theme/app_dimensions.dart';

class const SurfaceCard({
  required final Widget child,
  final EdgeInsetsGeometry margin = const EdgeInsets.symmetric(
    horizontal: AppPadding.medium,
  ),
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: margin,
      child: Material(
        color: Theme.of(context).colorScheme.surface,
        borderRadius: BorderRadius.circular(AppRadius.medium),
        clipBehavior: Clip.antiAlias,
        child: child,
      ),
    );
  }
}
