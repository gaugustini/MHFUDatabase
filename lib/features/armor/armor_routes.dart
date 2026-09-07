import 'package:go_router/go_router.dart';

import '../../core/router/app_routes.dart';
import '../../core/router/app_shell.dart';
import '../../core/router/navigation_extensions.dart';
import '../../core/router/slide_transition_page.dart';
import '../../core/settings/app_settings_controller.dart';
import '../search/search_routes.dart';
import 'presentation/views/armor_detail_view.dart';
import 'presentation/views/armor_set_detail_view.dart';
import 'presentation/views/armor_set_list_view.dart';

abstract final class ArmorRoutes {
  static List<GoRoute> get routes => [
    GoRoute(
      name: AppRoutes.armorSetListName,
      path: AppRoutes.armorSetListPath,
      pageBuilder: (context, state) {
        AppSettingsController.instance.recordVisit(AppRoutes.armorSetListPath);
        return buildSlidePage(
          state: state,
          child: ArmorSetListView(
            openDrawer: () => rootScaffoldKey.currentState?.openDrawer(),
            openSearch: () => context.pushNamed(SearchRoutes.name),
          ),
        );
      },
    ),
    GoRoute(
      name: AppRoutes.armorDetailName,
      path: AppRoutes.armorDetailPath,
      pageBuilder: (context, state) => buildSlidePage(
        state: state,
        child: ArmorDetailView(
          armorId: int.parse(state.pathParameters['armorId']!),
          navigateBack: () => context.goBackOrHome(),
          openSearch: () => context.pushNamed(SearchRoutes.name),
        ),
      ),
    ),
    GoRoute(
      name: AppRoutes.armorSetDetailName,
      path: AppRoutes.armorSetDetailPath,
      pageBuilder: (context, state) => buildSlidePage(
        state: state,
        child: ArmorSetDetailView(
          armorSetId: int.parse(state.pathParameters['armorSetId']!),
          navigateBack: () => context.goBackOrHome(),
          openSearch: () => context.pushNamed(SearchRoutes.name),
        ),
      ),
    ),
  ];
}
