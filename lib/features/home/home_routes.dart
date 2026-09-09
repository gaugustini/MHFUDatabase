import 'package:go_router/go_router.dart';

import '../../core/router/app_routes.dart';
import '../../core/router/app_shell.dart';
import '../../core/router/slide_transition_page.dart';
import '../../core/settings/app_settings_controller.dart';
import '../search/search_routes.dart';
import 'presentation/views/home_view.dart';

abstract final class HomeRoutes {
  static GoRoute get route => GoRoute(
    name: AppRoutes.homeName,
    path: AppRoutes.homePath,
    pageBuilder: (context, state) {
      AppSettingsController.instance.recordVisit(AppRoutes.homePath);
      return buildSlidePage(
        state: state,
        child: HomeView(
          openDrawer: () => rootScaffoldKey.currentState?.openDrawer(),
          openSearch: () => context.pushNamed(SearchRoutes.name),
          onSectionTap: (path) => context.push(path),
        ),
      );
    },
  );
}
