import 'package:flutter/material.dart';

import '../domain/enums.dart';

Color rarityColor(int rarity) => switch (rarity) {
  1 || 2 || 3 => const Color(0xFFFFFFFF),
  4 => const Color(0xFF73CB8D),
  5 => const Color(0xFFED93A4),
  6 => const Color(0xFF96B5FD),
  7 => const Color(0xFFFF985D),
  8 => const Color(0xFFFF5D5D),
  9 => const Color(0xFFFFD35D),
  10 => const Color(0xFFAC5CC0),
  _ => const Color(0xFFFFFFFF),
};

Color itemIconColorValue(ItemIconColor color) => switch (color) {
  ItemIconColor.blue => const Color(0xFF96B5FD),
  ItemIconColor.gray => const Color(0xFFA0A0A0),
  ItemIconColor.green => const Color(0xFF73CB8D),
  ItemIconColor.orange => const Color(0xFFFF985D),
  ItemIconColor.pink => const Color(0xFFED93A4),
  ItemIconColor.purple => const Color(0xFFB895C6),
  ItemIconColor.red => const Color(0xFFFF5D5D),
  ItemIconColor.sky => const Color(0xFF9BDFF0),
  ItemIconColor.white => const Color(0xFFFFFFFF),
  ItemIconColor.yellow => const Color(0xFFFFD35D),
};

Color questGoalColor(QuestGoal goal) => switch (goal) {
  QuestGoal.gather => const Color(0xFF00FF00),
  QuestGoal.hunt => const Color(0xFFFFFFFF),
  QuestGoal.slay => const Color(0xFFFF0000),
  QuestGoal.special => const Color(0xFFFF00FF),
  QuestGoal.treasure => const Color(0xFFFFFF00),
};

Color sharpnessColor(String letter) => switch (letter) {
  'R' => const Color(0xFFC60839),
  'O' => const Color(0xFFEF5218),
  'Y' => const Color(0xFFF7CE31),
  'G' => const Color(0xFF5AD600),
  'B' => const Color(0xFF316BEF),
  'W' => const Color(0xFFF7F7F7),
  'P' => const Color(0xFFF700F7),
  _ => const Color(0xFF1A1A1A),
};

Color songNoteColor(String letter) => switch (letter) {
  'A' => const Color(0xFF6CC8FE),
  'B' => const Color(0xFF7175FD),
  'G' => const Color(0xFF7BE61F),
  'P' => const Color(0xFFE176EE),
  'R' => const Color(0xFFFD3B1F),
  'W' => const Color(0xFFFFFFFF),
  'Y' => const Color(0xFFFFEB2B),
  _ => const Color(0xFF1A1A1A),
};
