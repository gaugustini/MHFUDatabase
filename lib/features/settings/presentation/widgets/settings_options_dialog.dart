import 'package:flutter/material.dart';

import '../../../../l10n/app_localizations.dart';

Future<void> showSettingsOptionsDialog<T>({
  required BuildContext context,
  required String title,
  required T selected,
  required List<T> options,
  required String Function(T) labelOf,
  required ValueChanged<T> onConfirm,
}) {
  return showDialog<void>(
    context: context,
    builder: (context) => _SettingsOptionsDialog<T>(
      title: title,
      selected: selected,
      options: options,
      labelOf: labelOf,
      onConfirm: onConfirm,
    ),
  );
}

class const _SettingsOptionsDialog<T>({
  required final String title,
  required final T selected,
  required final List<T> options,
  required final String Function(T) labelOf,
  required final ValueChanged<T> onConfirm,
}) extends StatefulWidget {
  @override
  State<_SettingsOptionsDialog<T>> createState() =>
      _SettingsOptionsDialogState<T>();
}

class _SettingsOptionsDialogState<T> extends State<_SettingsOptionsDialog<T>> {
  late T _pending = widget.selected;

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return AlertDialog(
      title: Text(widget.title),
      content: SizedBox(
        width: double.maxFinite,
        child: RadioGroup<T>(
          groupValue: _pending,
          onChanged: (value) => setState(() => _pending = value as T),
          child: ListView(
            shrinkWrap: true,
            children: [
              for (final option in widget.options)
                RadioListTile<T>(
                  value: option,
                  title: Text(widget.labelOf(option)),
                ),
            ],
          ),
        ),
      ),
      actions: [
        TextButton(
          onPressed: () => Navigator.of(context).pop(),
          child: Text(l10n.settingsOptionCancel),
        ),
        TextButton(
          onPressed: () {
            widget.onConfirm(_pending);
            Navigator.of(context).pop();
          },
          child: Text(l10n.settingsOptionConfirm),
        ),
      ],
    );
  }
}
