import 'package:go_router/go_router.dart';

import '../../core/router/app_routes.dart';
import '../../core/router/app_shell.dart';
import '../../core/router/navigation_extensions.dart';
import '../../core/router/slide_transition_page.dart';
import '../../core/settings/app_settings_controller.dart';
import '../search/search_routes.dart';
import 'presentation/views/monster_detail_view.dart';
import 'presentation/views/monster_list_view.dart';

abstract final class MonsterRoutes {
  static List<GoRoute> get routes => [
    GoRoute(
      name: AppRoutes.monsterListName,
      path: AppRoutes.monsterListPath,
      pageBuilder: (context, state) {
        AppSettingsController.instance.recordVisit(AppRoutes.monsterListPath);
        return buildSlidePage(
          state: state,
          child: MonsterListView(
            openDrawer: () => rootScaffoldKey.currentState?.openDrawer(),
            openSearch: () => context.pushNamed(SearchRoutes.name),
          ),
        );
      },
    ),
    GoRoute(
      name: AppRoutes.monsterDetailName,
      path: AppRoutes.monsterDetailPath,
      pageBuilder: (context, state) => buildSlidePage(
        state: state,
        child: MonsterDetailView(
          monsterId: int.parse(state.pathParameters['monsterId']!),
          navigateBack: () => context.goBackOrHome(),
          openSearch: () => context.pushNamed(SearchRoutes.name),
        ),
      ),
    ),
  ];
}
