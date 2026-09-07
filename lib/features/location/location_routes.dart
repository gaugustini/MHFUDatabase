import 'package:go_router/go_router.dart';

import '../../core/router/app_routes.dart';
import '../../core/router/app_shell.dart';
import '../../core/router/navigation_extensions.dart';
import '../../core/router/slide_transition_page.dart';
import '../../core/settings/app_settings_controller.dart';
import '../search/search_routes.dart';
import 'presentation/views/location_detail_view.dart';
import 'presentation/views/location_list_view.dart';

abstract final class LocationRoutes {
  static List<GoRoute> get routes => [
    GoRoute(
      name: AppRoutes.locationListName,
      path: AppRoutes.locationListPath,
      pageBuilder: (context, state) {
        AppSettingsController.instance.recordVisit(AppRoutes.locationListPath);
        return buildSlidePage(
          state: state,
          child: LocationListView(
            openDrawer: () => rootScaffoldKey.currentState?.openDrawer(),
            openSearch: () => context.pushNamed(SearchRoutes.name),
          ),
        );
      },
    ),
    GoRoute(
      name: AppRoutes.locationDetailName,
      path: AppRoutes.locationDetailPath,
      pageBuilder: (context, state) => buildSlidePage(
        state: state,
        child: LocationDetailView(
          locationId: int.parse(state.pathParameters['locationId']!),
          navigateBack: () => context.goBackOrHome(),
          openSearch: () => context.pushNamed(SearchRoutes.name),
        ),
      ),
    ),
  ];
}
