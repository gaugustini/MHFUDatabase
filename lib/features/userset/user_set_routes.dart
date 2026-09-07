import 'package:go_router/go_router.dart';

import '../../core/router/app_routes.dart';
import '../../core/router/app_shell.dart';
import '../../core/router/navigation_extensions.dart';
import '../../core/router/slide_transition_page.dart';
import '../../core/settings/app_settings_controller.dart';
import '../search/search_routes.dart';
import 'presentation/views/user_set_detail_view.dart';
import 'presentation/views/user_set_list_view.dart';

abstract final class UserSetRoutes {
  static List<GoRoute> get routes => [
    GoRoute(
      name: AppRoutes.userEquipmentSetListName,
      path: AppRoutes.userEquipmentSetListPath,
      pageBuilder: (context, state) {
        AppSettingsController.instance.recordVisit(
          AppRoutes.userEquipmentSetListPath,
        );
        return buildSlidePage(
          state: state,
          child: UserSetListView(
            openDrawer: () => rootScaffoldKey.currentState?.openDrawer(),
            openSearch: () => context.pushNamed(SearchRoutes.name),
          ),
        );
      },
    ),
    GoRoute(
      name: AppRoutes.userEquipmentSetDetailName,
      path: AppRoutes.userEquipmentSetDetailPath,
      pageBuilder: (context, state) => buildSlidePage(
        state: state,
        child: UserSetDetailView(
          setId: int.parse(state.pathParameters['setId']!),
          hunterType: state.pathParameters['hunterType']!,
          gender: state.pathParameters['gender']!,
          navigateBack: () => context.goBackOrHome(),
          openSearch: () => context.pushNamed(SearchRoutes.name),
        ),
      ),
    ),
  ];
}
