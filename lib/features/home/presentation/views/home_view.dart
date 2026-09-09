import 'package:flutter/material.dart';

import '../../../../core/router/app_sections.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../l10n/app_localizations.dart';

class const HomeView({
  required final VoidCallback openDrawer,
  required final VoidCallback openSearch,
  required final ValueChanged<String> onSectionTap,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.appName,
        navigation: AppTopBarNavigation.menu,
        onNavigationTap: openDrawer,
        onSearchTap: openSearch,
      ),
      body: GridView.builder(
        padding: context.scrollPadding(
          const EdgeInsets.fromLTRB(
            AppPadding.medium,
            0,
            AppPadding.medium,
            AppPadding.small,
          ),
        ),
        gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
          crossAxisCount: 3,
          mainAxisSpacing: AppSpacing.medium,
          crossAxisSpacing: AppSpacing.medium,
        ),
        itemCount: appSections.length,
        itemBuilder: (context, index) {
          final section = appSections[index];
          return _SectionTile(
            icon: section.icon,
            label: section.label(l10n),
            onTap: () => onSectionTap(section.path),
          );
        },
      ),
    );
  }
}

class const _SectionTile({
  required final String icon,
  required final String label,
  required final VoidCallback onTap,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Material(
      color: Theme.of(context).colorScheme.surface,
      borderRadius: BorderRadius.circular(AppRadius.medium),
      clipBehavior: Clip.antiAlias,
      child: InkWell(
        borderRadius: BorderRadius.circular(AppRadius.medium),
        onTap: onTap,
        child: Padding(
          padding: const EdgeInsets.all(8),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Image.asset(icon, width: 40, height: 40),
              const SizedBox(height: 8),
              Text(
                label,
                textAlign: TextAlign.center,
                maxLines: 2,
                overflow: TextOverflow.ellipsis,
                style: Theme.of(context).textTheme.bodySmall,
              ),
            ],
          ),
        ),
      ),
    );
  }
}
