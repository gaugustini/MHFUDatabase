import 'package:flutter/material.dart';
import 'package:package_info_plus/package_info_plus.dart';

import 'app.dart';
import 'core/settings/app_preferences.dart';
import 'core/settings/app_settings_controller.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await AppSettingsController.instance.load();
  await AppPreferences.instance.load();

  final packageInfo = await PackageInfo.fromPlatform();
  final currentVersion = int.tryParse(packageInfo.buildNumber) ?? 0;
  final lastVersion = AppPreferences.instance.lastAppVersion;
  final showWhatsNew = lastVersion != -1 && currentVersion > lastVersion;
  await AppPreferences.instance.setLastAppVersion(currentVersion);

  runApp(MyApp(whatsNewVersion: showWhatsNew ? packageInfo.version : null));
}
