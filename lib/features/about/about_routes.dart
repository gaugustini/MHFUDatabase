import 'package:go_router/go_router.dart';

import '../../core/router/app_routes.dart';
import '../../core/router/navigation_extensions.dart';
import '../../core/router/slide_transition_page.dart';
import '../search/search_routes.dart';
import 'presentation/views/about_view.dart';

abstract final class AboutRoutes {
  static GoRoute get route => GoRoute(
    name: AppRoutes.aboutName,
    path: AppRoutes.aboutPath,
    pageBuilder: (context, state) => buildSlidePage(
      state: state,
      child: AboutView(
        navigateBack: () => context.goBackOrHome(),
        openSearch: () => context.pushNamed(SearchRoutes.name),
      ),
    ),
  );
}
