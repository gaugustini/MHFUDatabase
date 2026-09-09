import 'package:flutter/material.dart';

class const AppHDivider({
  final double thickness = 2,
  final Color? color,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Divider(
      thickness: thickness,
      height: thickness,
      color: color ?? Theme.of(context).scaffoldBackgroundColor,
    );
  }
}
