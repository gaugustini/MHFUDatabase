import 'package:go_router/go_router.dart';

import '../../core/router/app_routes.dart';
import '../../core/router/navigation_extensions.dart';
import '../../core/router/slide_transition_page.dart';
import 'presentation/views/search_view.dart';

abstract final class SearchRoutes {
  static const name = AppRoutes.searchName;
  static const path = AppRoutes.searchPath;

  static GoRoute get route => GoRoute(
    name: name,
    path: path,
    pageBuilder: (context, state) => buildSlidePage(
      state: state,
      child: SearchView(navigateBack: () => context.goBackOrHome()),
    ),
  );
}
