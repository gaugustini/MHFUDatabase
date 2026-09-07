import 'package:go_router/go_router.dart';

import '../../core/router/app_routes.dart';
import '../../core/router/app_shell.dart';
import '../../core/router/navigation_extensions.dart';
import '../../core/router/slide_transition_page.dart';
import '../../core/settings/app_settings_controller.dart';
import '../search/search_routes.dart';
import 'presentation/views/decoration_detail_view.dart';
import 'presentation/views/decoration_list_view.dart';

abstract final class DecorationRoutes {
  static List<GoRoute> get routes => [
    GoRoute(
      name: AppRoutes.decorationListName,
      path: AppRoutes.decorationListPath,
      pageBuilder: (context, state) {
        AppSettingsController.instance.recordVisit(
          AppRoutes.decorationListPath,
        );
        return buildSlidePage(
          state: state,
          child: DecorationListView(
            openDrawer: () => rootScaffoldKey.currentState?.openDrawer(),
            openSearch: () => context.pushNamed(SearchRoutes.name),
          ),
        );
      },
    ),
    GoRoute(
      name: AppRoutes.decorationDetailName,
      path: AppRoutes.decorationDetailPath,
      pageBuilder: (context, state) => buildSlidePage(
        state: state,
        child: DecorationDetailView(
          decorationId: int.parse(state.pathParameters['decorationId']!),
          navigateBack: () => context.goBackOrHome(),
          openSearch: () => context.pushNamed(SearchRoutes.name),
        ),
      ),
    ),
  ];
}
