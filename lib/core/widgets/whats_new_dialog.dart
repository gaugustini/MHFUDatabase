import 'package:flutter/material.dart';

import '../../l10n/app_localizations.dart';

class const WhatsNewDialog({
  required final String version,
  required final VoidCallback onConfirm,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return AlertDialog(
      title: Text(l10n.whatsNewTitle),
      content: SingleChildScrollView(child: Text(l10n.whatsNewText(version))),
      actions: [
        TextButton(onPressed: onConfirm, child: Text(l10n.dialogConfirm)),
      ],
    );
  }
}
