import 'package:go_router/go_router.dart';

import '../../core/router/app_routes.dart';
import '../../core/router/app_shell.dart';
import '../../core/router/navigation_extensions.dart';
import '../../core/router/slide_transition_page.dart';
import '../../core/settings/app_settings_controller.dart';
import '../search/search_routes.dart';
import 'presentation/views/item_detail_view.dart';
import 'presentation/views/item_list_view.dart';

abstract final class ItemRoutes {
  static List<GoRoute> get routes => [
    GoRoute(
      name: AppRoutes.itemListName,
      path: AppRoutes.itemListPath,
      pageBuilder: (context, state) {
        AppSettingsController.instance.recordVisit(AppRoutes.itemListPath);
        return buildSlidePage(
          state: state,
          child: ItemListView(
            openDrawer: () => rootScaffoldKey.currentState?.openDrawer(),
            openSearch: () => context.pushNamed(SearchRoutes.name),
          ),
        );
      },
    ),
    GoRoute(
      name: AppRoutes.itemDetailName,
      path: AppRoutes.itemDetailPath,
      pageBuilder: (context, state) => buildSlidePage(
        state: state,
        child: ItemDetailView(
          itemId: int.parse(state.pathParameters['itemId']!),
          navigateBack: () => context.goBackOrHome(),
          openSearch: () => context.pushNamed(SearchRoutes.name),
        ),
      ),
    ),
  ];
}
