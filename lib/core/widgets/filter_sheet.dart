import 'package:flutter/material.dart';

import '../theme/app_dimensions.dart';

Future<void> showFilterSheet<T>({
  required BuildContext context,
  required String title,
  required List<T> items,
  required List<T> selectedItems,
  required ValueChanged<List<T>> onItemsSelected,
  required Widget Function(T item, bool isSelected, VoidCallback onTap)
  itemBuilder,
}) {
  var selection = List<T>.from(selectedItems);

  return showModalBottomSheet<void>(
    context: context,
    isScrollControlled: true,
    builder: (sheetContext) => StatefulBuilder(
      builder: (sheetContext, setSheetState) => SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.all(AppPadding.large),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(title, style: Theme.of(sheetContext).textTheme.titleMedium),
              const SizedBox(height: AppSpacing.medium),
              Wrap(
                spacing: AppSpacing.small,
                runSpacing: AppSpacing.small,
                children: [
                  for (final item in items)
                    itemBuilder(item, selection.contains(item), () {
                      final updated = selection.contains(item)
                          ? (selection.toList()..remove(item))
                          : (selection.toList()..add(item));
                      setSheetState(() => selection = updated);
                      onItemsSelected(updated);
                    }),
                ],
              ),
            ],
          ),
        ),
      ),
    ),
  );
}
