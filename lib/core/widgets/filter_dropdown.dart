import 'package:flutter/material.dart';

import '../theme/app_dimensions.dart';

class const FilterDropdown<T>({
  required final T value,
  required final List<T> items,
  required final String Function(T) labelBuilder,
  required final ValueChanged<T> onChanged,
  super.key,
}) extends StatefulWidget {
  @override
  State<FilterDropdown<T>> createState() => _FilterDropdownState<T>();
}

class _FilterDropdownState<T> extends State<FilterDropdown<T>> {
  bool _open = false;

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).colorScheme;

    return PopupMenuButton<T>(
      initialValue: widget.value,
      offset: const Offset(0, AppSize.medium),
      onOpened: () => setState(() => _open = true),
      onCanceled: () => setState(() => _open = false),
      onSelected: (value) {
        setState(() => _open = false);
        widget.onChanged(value);
      },
      itemBuilder: (context) => [
        for (final item in widget.items)
          PopupMenuItem(value: item, child: Text(widget.labelBuilder(item))),
      ],
      child: Container(
        padding: const EdgeInsets.symmetric(
          horizontal: AppPadding.medium,
          vertical: AppPadding.small,
        ),
        decoration: BoxDecoration(
          color: _open ? colors.secondaryContainer : Colors.transparent,
          border: Border.all(color: _open ? colors.secondary : colors.outline),
          borderRadius: BorderRadius.circular(AppRadius.medium),
        ),
        child: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            Text(
              widget.labelBuilder(widget.value),
              style: TextStyle(
                color: _open ? colors.onSecondaryContainer : colors.onSurfaceVariant,
              ),
            ),
            const SizedBox(width: AppSpacing.small),
            Icon(
              Icons.arrow_drop_down,
              color: _open ? colors.onSecondaryContainer : colors.onSurfaceVariant,
              size: AppSize.tiny,
            ),
          ],
        ),
      ),
    );
  }
}
