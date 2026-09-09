import 'package:drift/drift.dart';

/// Builds positional `?` placeholders for [AppDatabase.customSelect] inline,
/// so each placeholder and its bound value are written next to each other.
class SqlArgs {
  final _variables = <Variable>[];

  List<Variable> get variables => _variables;

  String integer(int? value) {
    _variables.add(Variable<int>(value));
    return '?';
  }

  String text(String? value) {
    _variables.add(Variable<String>(value));
    return '?';
  }

  String flag(bool value) {
    _variables.add(Variable<bool>(value));
    return '?';
  }

  String integers(List<int>? values) {
    final list = values ?? const <int>[];
    for (final value in list) {
      _variables.add(Variable<int>(value));
    }
    return '(${list.map((_) => '?').join(', ')})';
  }

  String texts(List<String>? values) {
    final list = values ?? const <String>[];
    for (final value in list) {
      _variables.add(Variable<String>(value));
    }
    return '(${list.map((_) => '?').join(', ')})';
  }
}

/// Orders a `rank` column by `Rank`'s own declaration order rather than the
/// alphabetical order of its stored string.
String rankOrderCase(String column) => '''
    CASE $column
      WHEN 'UNRANKED' THEN 0
      WHEN 'LOW' THEN 1
      WHEN 'HIGH' THEN 2
      WHEN 'G' THEN 3
      WHEN 'TREASURE' THEN 4
      WHEN 'TRAINING' THEN 5
    END''';
