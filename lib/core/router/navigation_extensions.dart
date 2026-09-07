import 'package:flutter/widgets.dart';
import 'package:go_router/go_router.dart';

import 'app_routes.dart';

extension AppNavigation on BuildContext {
  void goBackOrHome() {
    if (canPop()) {
      pop();
    } else {
      go(AppRoutes.homePath);
    }
  }
}
