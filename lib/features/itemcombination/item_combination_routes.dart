import 'package:go_router/go_router.dart';

import '../../core/router/app_routes.dart';
import '../../core/router/app_shell.dart';
import '../../core/router/slide_transition_page.dart';
import '../../core/settings/app_settings_controller.dart';
import '../search/search_routes.dart';
import 'presentation/views/item_combination_list_view.dart';

abstract final class ItemCombinationRoutes {
  static GoRoute get route => GoRoute(
    name: AppRoutes.itemCombinationListName,
    path: AppRoutes.itemCombinationListPath,
    pageBuilder: (context, state) {
      AppSettingsController.instance.recordVisit(
        AppRoutes.itemCombinationListPath,
      );
      return buildSlidePage(
        state: state,
        child: ItemCombinationListView(
          openDrawer: () => rootScaffoldKey.currentState?.openDrawer(),
          openSearch: () => context.pushNamed(SearchRoutes.name),
        ),
      );
    },
  );
}
