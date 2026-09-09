import 'package:go_router/go_router.dart';

import '../../core/router/app_routes.dart';
import '../../core/router/app_shell.dart';
import '../../core/router/navigation_extensions.dart';
import '../../core/router/slide_transition_page.dart';
import '../../core/settings/app_settings_controller.dart';
import '../search/search_routes.dart';
import 'presentation/views/weapon_detail_view.dart';
import 'presentation/views/weapon_tree_view.dart';
import 'presentation/views/weapon_type_list_view.dart';

abstract final class WeaponRoutes {
  static List<GoRoute> get routes => [
    GoRoute(
      name: AppRoutes.weaponTypeListName,
      path: AppRoutes.weaponTypeListPath,
      pageBuilder: (context, state) {
        AppSettingsController.instance.recordVisit(
          AppRoutes.weaponTypeListPath,
        );
        return buildSlidePage(
          state: state,
          child: WeaponTypeListView(
            openDrawer: () => rootScaffoldKey.currentState?.openDrawer(),
            openSearch: () => context.pushNamed(SearchRoutes.name),
          ),
        );
      },
    ),
    GoRoute(
      name: AppRoutes.weaponTreeName,
      path: AppRoutes.weaponTreePath,
      pageBuilder: (context, state) => buildSlidePage(
        state: state,
        child: WeaponTreeView(
          weaponType: state.pathParameters['weaponType']!,
          navigateBack: () => context.goBackOrHome(),
          openSearch: () => context.pushNamed(SearchRoutes.name),
        ),
      ),
    ),
    GoRoute(
      name: AppRoutes.weaponDetailName,
      path: AppRoutes.weaponDetailPath,
      pageBuilder: (context, state) => buildSlidePage(
        state: state,
        child: WeaponDetailView(
          weaponId: int.parse(state.pathParameters['weaponId']!),
          navigateBack: () => context.goBackOrHome(),
          openSearch: () => context.pushNamed(SearchRoutes.name),
        ),
      ),
    ),
  ];
}
