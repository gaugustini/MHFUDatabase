import 'package:go_router/go_router.dart';

import '../../core/router/app_routes.dart';
import '../../core/router/app_shell.dart';
import '../../core/router/navigation_extensions.dart';
import '../../core/router/slide_transition_page.dart';
import '../../core/settings/app_settings_controller.dart';
import '../search/search_routes.dart';
import 'presentation/views/skill_tree_detail_view.dart';
import 'presentation/views/skill_tree_list_view.dart';

abstract final class SkillRoutes {
  static List<GoRoute> get routes => [
    GoRoute(
      name: AppRoutes.skillTreeListName,
      path: AppRoutes.skillTreeListPath,
      pageBuilder: (context, state) {
        AppSettingsController.instance.recordVisit(AppRoutes.skillTreeListPath);
        return buildSlidePage(
          state: state,
          child: SkillTreeListView(
            openDrawer: () => rootScaffoldKey.currentState?.openDrawer(),
            openSearch: () => context.pushNamed(SearchRoutes.name),
          ),
        );
      },
    ),
    GoRoute(
      name: AppRoutes.skillTreeDetailName,
      path: AppRoutes.skillTreeDetailPath,
      pageBuilder: (context, state) => buildSlidePage(
        state: state,
        child: SkillTreeDetailView(
          skillTreeId: int.parse(state.pathParameters['skillTreeId']!),
          navigateBack: () => context.goBackOrHome(),
          openSearch: () => context.pushNamed(SearchRoutes.name),
        ),
      ),
    ),
  ];
}
