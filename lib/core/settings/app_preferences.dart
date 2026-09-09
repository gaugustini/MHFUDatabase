import 'package:shared_preferences/shared_preferences.dart';

class AppPreferences {
  AppPreferences._();

  static final instance = AppPreferences._();

  static const _lastVersionKey = 'last_version';

  late SharedPreferences _prefs;

  Future<void> load() async {
    _prefs = await SharedPreferences.getInstance();
  }

  int get lastAppVersion => _prefs.getInt(_lastVersionKey) ?? -1;

  Future<void> setLastAppVersion(int version) =>
      _prefs.setInt(_lastVersionKey, version);
}
