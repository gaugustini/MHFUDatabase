import 'package:go_router/go_router.dart';

import '../../core/router/app_routes.dart';
import '../../core/router/app_shell.dart';
import '../../core/router/navigation_extensions.dart';
import '../../core/router/slide_transition_page.dart';
import '../../core/settings/app_settings_controller.dart';
import '../search/search_routes.dart';
import 'presentation/views/quest_detail_view.dart';
import 'presentation/views/quest_list_view.dart';

abstract final class QuestRoutes {
  static List<GoRoute> get routes => [
    GoRoute(
      name: AppRoutes.questListName,
      path: AppRoutes.questListPath,
      pageBuilder: (context, state) {
        AppSettingsController.instance.recordVisit(AppRoutes.questListPath);
        return buildSlidePage(
          state: state,
          child: QuestListView(
            openDrawer: () => rootScaffoldKey.currentState?.openDrawer(),
            openSearch: () => context.pushNamed(SearchRoutes.name),
          ),
        );
      },
    ),
    GoRoute(
      name: AppRoutes.questDetailName,
      path: AppRoutes.questDetailPath,
      pageBuilder: (context, state) => buildSlidePage(
        state: state,
        child: QuestDetailView(
          questId: int.parse(state.pathParameters['questId']!),
          navigateBack: () => context.goBackOrHome(),
          openSearch: () => context.pushNamed(SearchRoutes.name),
        ),
      ),
    ),
  ];
}
