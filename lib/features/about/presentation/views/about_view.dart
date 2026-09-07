import 'package:flutter/material.dart';
import 'package:package_info_plus/package_info_plus.dart';
import 'package:url_launcher/url_launcher.dart';

import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/section_card.dart';
import '../../../../l10n/app_localizations.dart';

class const _CreditItem({
  required final String icon,
  required final String title,
  required final String uri,
  final String? author,
});

class const AboutView({
  required final VoidCallback navigateBack,
  required final VoidCallback openSearch,
  super.key,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final isDarkTheme = Theme.of(context).brightness == Brightness.dark;
    final githubIcon = isDarkTheme
        ? 'assets/images/ic_github_white.webp'
        : 'assets/images/ic_github_black.webp';

    final credits = [
      _CreditItem(
        icon: githubIcon,
        title: 'Gathering Hall Studios',
        uri: 'https://github.com/gatheringhallstudios',
      ),
      _CreditItem(
        icon: githubIcon,
        title: 'MHFU-DB',
        uri: 'https://github.com/Kolyn090/mhfu-db',
        author: 'Kolyn090',
      ),
      const _CreditItem(
        icon: 'assets/images/ic_weapon_great_sword.webp',
        title: 'MHFU Blacksmith',
        uri: 'https://mhfu.vallode.com/',
        author: 'vallode',
      ),
      const _CreditItem(
        icon: 'assets/images/ic_item_book.webp',
        title: 'MHFU Wiki',
        uri: 'https://monsterhunter.fandom.com/wiki/Monster_Hunter_Freedom_Unite',
      ),
      const _CreditItem(
        icon: 'assets/images/ic_item_book.webp',
        title: 'MHP2G Wiki',
        uri: 'https://w.atwiki.jp/mhp2g/',
      ),
      const _CreditItem(
        icon: 'assets/images/ic_item_book.webp',
        title: 'GameFAQs',
        uri: 'https://gamefaqs.gamespot.com/psp/943356-monster-hunter-freedom-unite',
        author: 'ryin77, ZeoKnight, Boldrin',
      ),
      const _CreditItem(
        icon: 'assets/images/ic_item_book.webp',
        title: 'FUComplete',
        uri: 'https://fucomplete.github.io/',
      ),
      _CreditItem(
        icon: githubIcon,
        title: 'MHP2G Patterns',
        uri: 'https://github.com/IncognitoMan/mhp2g-patterns',
        author: 'IncognitoMan',
      ),
      const _CreditItem(
        icon: 'assets/images/ic_item_book.webp',
        title: 'Neoseeker',
        uri: 'https://monsterhunter.neoseeker.com/wiki/Monster_Hunter_Freedom_Unite_(PSP)',
      ),
      _CreditItem(
        icon: githubIcon,
        title: 'Monster Hunter DB',
        uri: 'https://github.com/CrimsonNynja/monster-hunter-DB',
        author: 'CrimsonNynja',
      ),
      _CreditItem(
        icon: githubIcon,
        title: 'Zeedif',
        uri: 'https://github.com/zeedif',
      ),
    ];

    return Scaffold(
      appBar: AppTopBar(
        title: l10n.screenAbout,
        navigation: AppTopBarNavigation.back,
        onNavigationTap: navigateBack,
        onSearchTap: openSearch,
      ),
      body: ListView(
        padding: context.scrollPadding(
          const EdgeInsets.fromLTRB(
            AppPadding.medium,
            0,
            AppPadding.medium,
            AppPadding.small,
          ),
        ),
        children: [
          Card(
            margin: EdgeInsets.zero,
            child: Padding(
              padding: const EdgeInsets.all(AppPadding.large),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    l10n.appName,
                    style: Theme.of(context).textTheme.titleLarge,
                  ),
                  const SizedBox(height: AppSpacing.medium),
                  FutureBuilder<PackageInfo>(
                    future: PackageInfo.fromPlatform(),
                    builder: (context, snapshot) {
                      final version = snapshot.data?.version ?? '';
                      return Text(l10n.aboutVersion(version));
                    },
                  ),
                  const SizedBox(height: AppSpacing.medium),
                  Text(l10n.aboutDescription),
                  const SizedBox(height: AppSpacing.medium),
                  Text(l10n.aboutVisitProject),
                  const SizedBox(height: AppSpacing.medium),
                  _CreditTile(
                    item: _CreditItem(
                      icon: githubIcon,
                      title: 'GitHub',
                      uri: 'https://github.com/gaugustini/MHFUDatabase',
                    ),
                  ),
                ],
              ),
            ),
          ),
          const SizedBox(height: AppSpacing.large),
          SectionCard(
            title: l10n.aboutCreditResources,
            margin: EdgeInsets.zero,
            child: Column(
              children: [
                for (final item in credits) ...[
                  const Divider(height: 1),
                  _CreditTile(item: item),
                ],
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class const _CreditTile({
  required final _CreditItem item,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListTile(
      leading: Image.asset(
        item.icon,
        width: AppSize.small,
        height: AppSize.small,
      ),
      title: Text(item.title),
      trailing: item.author != null ? Text(item.author!) : null,
      onTap: () =>
          launchUrl(Uri.parse(item.uri), mode: LaunchMode.externalApplication),
    );
  }
}
