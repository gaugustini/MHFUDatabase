import 'package:flutter/material.dart';

enum AppTopBarNavigation { menu, back }

class const AppTopBar({
  required final String title,
  required final AppTopBarNavigation navigation,
  final VoidCallback? onNavigationTap,
  final VoidCallback? onSearchTap,
  final List<Widget> actions = const [],
  super.key,
}) extends StatelessWidget implements PreferredSizeWidget {
  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);

  @override
  Widget build(BuildContext context) {
    return AppBar(
      scrolledUnderElevation: 0,
      title: Text(title, maxLines: 1, overflow: TextOverflow.ellipsis),
      leading: IconButton(
        icon: Icon(
          navigation == AppTopBarNavigation.menu
              ? Icons.menu
              : Icons.arrow_back,
        ),
        onPressed: onNavigationTap,
      ),
      actions: [
        ...actions,
        if (onSearchTap != null)
          IconButton(icon: const Icon(Icons.search), onPressed: onSearchTap),
      ],
    );
  }
}
