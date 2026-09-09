import 'package:flutter/material.dart';

import '../widgets/app_drawer.dart';

final rootScaffoldKey = GlobalKey<ScaffoldState>();

class const AppShell({
  required final String location,
  required final Widget child,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      key: rootScaffoldKey,
      drawer: AppDrawer(currentLocation: location),
      body: child,
    );
  }
}
