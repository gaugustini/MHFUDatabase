import 'package:flutter/material.dart';

import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/settings/app_start_page.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../l10n/app_localizations.dart';
import '../widgets/settings_options_dialog.dart';

class const SettingsView({
  required final VoidCallback navigateBack,
  required final VoidCallback openSearch,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final settings = AppSettingsController.instance;
    final isDarkTheme = Theme.of(context).brightness == Brightness.dark;

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.screenSettings,
        navigation: AppTopBarNavigation.back,
        onNavigationTap: navigateBack,
        onSearchTap: openSearch,
      ),
      body: ListenableBuilder(
        listenable: settings,
        builder: (context, _) => ListView(
          padding: context.scrollPadding(
            const EdgeInsets.fromLTRB(
              AppPadding.medium,
              0,
              AppPadding.medium,
              AppPadding.small,
            ),
          ),
          children: [
            Card(
              margin: EdgeInsets.zero,
              child: Column(
                children: [
                  ListTile(
                    leading: Image.asset(
                      isDarkTheme
                          ? 'assets/images/ic_ui_theme_white.webp'
                          : 'assets/images/ic_ui_theme_black.webp',
                      width: AppSize.extraSmall,
                      height: AppSize.extraSmall,
                    ),
                    title: Text(l10n.settingsTheme),
                    subtitle: Text(_themeModeLabel(l10n, settings.themeMode)),
                    onTap: () => showSettingsOptionsDialog<ThemeMode>(
                      context: context,
                      title: l10n.settingsThemeOptionsTitle,
                      selected: settings.themeMode,
                      options: ThemeMode.values,
                      labelOf: (mode) => _themeModeLabel(l10n, mode),
                      onConfirm: settings.setThemeMode,
                    ),
                  ),
                  const Divider(height: 1),
                  ListTile(
                    leading: Image.asset(
                      isDarkTheme
                          ? 'assets/images/ic_ui_language_white.webp'
                          : 'assets/images/ic_ui_language_black.webp',
                      width: AppSize.extraSmall,
                      height: AppSize.extraSmall,
                    ),
                    title: Text(l10n.settingsLanguage),
                    subtitle: Text(_localeLabel(l10n, settings.locale)),
                    onTap: () => showSettingsOptionsDialog<Locale>(
                      context: context,
                      title: l10n.settingsLanguageOptionsTitle,
                      selected: settings.locale,
                      options: AppLocalizations.supportedLocales,
                      labelOf: (locale) => _localeLabel(l10n, locale),
                      onConfirm: settings.setLocale,
                    ),
                  ),
                  const Divider(height: 1),
                  ListTile(
                    leading: const Icon(
                      Icons.home_outlined,
                      size: AppSize.extraSmall,
                    ),
                    title: Text(l10n.settingsStartPage),
                    subtitle: Text(settings.startPage.label(l10n)),
                    onTap: () => showSettingsOptionsDialog<AppStartPage>(
                      context: context,
                      title: l10n.settingsStartPageOptionsTitle,
                      selected: settings.startPage,
                      options: AppStartPage.values,
                      labelOf: (page) => page.label(l10n),
                      onConfirm: settings.setStartPage,
                    ),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  String _themeModeLabel(AppLocalizations l10n, ThemeMode mode) =>
      switch (mode) {
        ThemeMode.system => l10n.settingsThemeSystem,
        ThemeMode.light => l10n.settingsThemeLight,
        ThemeMode.dark => l10n.settingsThemeDark,
      };

  String _localeLabel(AppLocalizations l10n, Locale locale) =>
      switch (locale.languageCode) {
        'es' => l10n.settingsLanguageSpanish,
        _ => l10n.settingsLanguageEnglish,
      };
}
