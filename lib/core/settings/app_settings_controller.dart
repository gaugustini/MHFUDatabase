import 'dart:async';
import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../router/app_routes.dart';
import 'app_start_page.dart';

class AppSettingsController extends ChangeNotifier {
  AppSettingsController._();

  static final instance = AppSettingsController._();

  static const _themeModeKey = 'theme_mode';
  static const _languageKey = 'app_language';
  static const _startPageKey = 'start_page';
  static const _lastVisitedPathKey = 'last_visited_path';

  late SharedPreferences _prefs;

  ThemeMode _themeMode = ThemeMode.system;
  Locale _locale = const Locale('en');
  AppStartPage _startPage = AppStartPage.home;
  String? _lastVisitedPath;

  ThemeMode get themeMode => _themeMode;
  Locale get locale => _locale;
  AppStartPage get startPage => _startPage;
  String? get lastVisitedPath => _lastVisitedPath;

  String get initialLocation =>
      startPage.path ?? _lastVisitedPath ?? AppRoutes.homePath;

  Future<void> load() async {
    _prefs = await SharedPreferences.getInstance();

    final themeIndex = _prefs.getInt(_themeModeKey);
    if (themeIndex != null &&
        themeIndex >= 0 &&
        themeIndex < ThemeMode.values.length) {
      _themeMode = ThemeMode.values[themeIndex];
    }

    final languageCode =
        _prefs.getString(_languageKey) ?? _deviceLanguageCode();
    _locale = Locale(languageCode);

    final startPageName = _prefs.getString(_startPageKey);
    _startPage = AppStartPage.values.firstWhere(
      (value) => value.name == startPageName,
      orElse: () => AppStartPage.home,
    );

    _lastVisitedPath = _prefs.getString(_lastVisitedPathKey);
  }

  String _deviceLanguageCode() {
    final code = PlatformDispatcher.instance.locale.languageCode.toLowerCase();
    return code == 'es' ? 'es' : 'en';
  }

  Future<void> setThemeMode(ThemeMode mode) async {
    _themeMode = mode;
    notifyListeners();
    await _prefs.setInt(_themeModeKey, mode.index);
  }

  Future<void> setLocale(Locale locale) async {
    _locale = locale;
    notifyListeners();
    await _prefs.setString(_languageKey, locale.languageCode);
  }

  Future<void> setStartPage(AppStartPage startPage) async {
    _startPage = startPage;
    notifyListeners();
    await _prefs.setString(_startPageKey, startPage.name);
  }

  void recordVisit(String path) {
    if (_lastVisitedPath == path) {
      return;
    }
    _lastVisitedPath = path;
    unawaited(_prefs.setString(_lastVisitedPathKey, path));
  }
}
