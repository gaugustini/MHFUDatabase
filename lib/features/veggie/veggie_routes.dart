import 'package:go_router/go_router.dart';

import '../../core/router/app_routes.dart';
import '../../core/router/app_shell.dart';
import '../../core/router/navigation_extensions.dart';
import '../../core/router/slide_transition_page.dart';
import '../../core/settings/app_settings_controller.dart';
import '../search/search_routes.dart';
import 'presentation/views/veggie_detail_view.dart';
import 'presentation/views/veggie_list_view.dart';

abstract final class VeggieRoutes {
  static List<GoRoute> get routes => [
    GoRoute(
      name: AppRoutes.veggieListName,
      path: AppRoutes.veggieListPath,
      pageBuilder: (context, state) {
        AppSettingsController.instance.recordVisit(AppRoutes.veggieListPath);
        return buildSlidePage(
          state: state,
          child: VeggieListView(
            openDrawer: () => rootScaffoldKey.currentState?.openDrawer(),
            openSearch: () => context.pushNamed(SearchRoutes.name),
          ),
        );
      },
    ),
    GoRoute(
      name: AppRoutes.veggieDetailName,
      path: AppRoutes.veggieDetailPath,
      pageBuilder: (context, state) => buildSlidePage(
        state: state,
        child: VeggieDetailView(
          veggieId: int.parse(state.pathParameters['veggieId']!),
          navigateBack: () => context.goBackOrHome(),
          openSearch: () => context.pushNamed(SearchRoutes.name),
        ),
      ),
    ),
  ];
}
