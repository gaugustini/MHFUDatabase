import 'package:go_router/go_router.dart';

import '../../core/router/app_routes.dart';
import '../../core/router/navigation_extensions.dart';
import '../../core/router/slide_transition_page.dart';
import '../search/search_routes.dart';
import 'presentation/views/settings_view.dart';

abstract final class SettingsRoutes {
  static GoRoute get route => GoRoute(
    name: AppRoutes.settingsName,
    path: AppRoutes.settingsPath,
    pageBuilder: (context, state) => buildSlidePage(
      state: state,
      child: SettingsView(
        navigateBack: () => context.goBackOrHome(),
        openSearch: () => context.pushNamed(SearchRoutes.name),
      ),
    ),
  );
}
