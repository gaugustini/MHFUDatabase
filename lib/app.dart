import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import 'core/router/app_router.dart';
import 'core/settings/app_settings_controller.dart';
import 'core/theme/app_theme.dart';
import 'core/widgets/whats_new_dialog.dart';
import 'l10n/app_localizations.dart';

class const MyApp({final String? whatsNewVersion, super.key})
    extends StatefulWidget {
  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  late final GoRouter _router = buildAppRouter(
    initialLocation: AppSettingsController.instance.initialLocation,
  );

  @override
  void initState() {
    super.initState();
    final version = widget.whatsNewVersion;
    if (version != null) {
      WidgetsBinding.instance.addPostFrameCallback(
        (_) => _showWhatsNew(version),
      );
    }
  }

  void _showWhatsNew(String version) {
    final context = _router.routerDelegate.navigatorKey.currentContext;
    if (context == null) {
      return;
    }
    showDialog<void>(
      context: context,
      builder: (context) => WhatsNewDialog(
        version: version,
        onConfirm: () => Navigator.of(context).pop(),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return ListenableBuilder(
      listenable: AppSettingsController.instance,
      builder: (context, _) => MaterialApp.router(
        title: 'MHFU Database',
        debugShowCheckedModeBanner: false,
        theme: buildAppTheme(lightColorScheme),
        darkTheme: buildAppTheme(darkColorScheme),
        themeMode: AppSettingsController.instance.themeMode,
        locale: AppSettingsController.instance.locale,
        localizationsDelegates: AppLocalizations.localizationsDelegates,
        supportedLocales: AppLocalizations.supportedLocales,
        routerConfig: _router,
      ),
    );
  }
}
