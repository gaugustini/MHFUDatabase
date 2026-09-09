import 'package:go_router/go_router.dart';

import '../../features/about/about_routes.dart';
import '../../features/armor/armor_routes.dart';
import '../../features/decoration/decoration_routes.dart';
import '../../features/home/home_routes.dart';
import '../../features/item/item_routes.dart';
import '../../features/itemcombination/item_combination_routes.dart';
import '../../features/location/location_routes.dart';
import '../../features/monster/monster_routes.dart';
import '../../features/quest/quest_routes.dart';
import '../../features/search/search_routes.dart';
import '../../features/settings/settings_routes.dart';
import '../../features/skill/skill_routes.dart';
import '../../features/userset/user_set_routes.dart';
import '../../features/veggie/veggie_routes.dart';
import '../../features/weapon/weapon_routes.dart';
import 'app_shell.dart';

GoRouter buildAppRouter({required String initialLocation}) {
  return GoRouter(
    initialLocation: initialLocation,
    routes: [
      ShellRoute(
        builder: (context, state, child) =>
            AppShell(location: state.uri.path, child: child),
        routes: [
          HomeRoutes.route,
          SearchRoutes.route,
          SettingsRoutes.route,
          AboutRoutes.route,
          ItemCombinationRoutes.route,
          ...MonsterRoutes.routes,
          ...ArmorRoutes.routes,
          ...DecorationRoutes.routes,
          ...ItemRoutes.routes,
          ...LocationRoutes.routes,
          ...QuestRoutes.routes,
          ...SkillRoutes.routes,
          ...VeggieRoutes.routes,
          ...WeaponRoutes.routes,
          ...UserSetRoutes.routes,
        ],
      ),
    ],
  );
}
