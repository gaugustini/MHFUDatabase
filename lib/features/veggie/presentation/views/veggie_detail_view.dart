import 'package:flutter/material.dart';

import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/surface_card.dart';
import '../../../../l10n/app_localizations.dart';
import '../../data/veggie_repository.dart';
import '../../domain/veggie.dart';
import '../widgets/veggie_trade_row.dart';

class const VeggieDetailView({
  required final int veggieId,
  required final VoidCallback navigateBack,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<VeggieDetailView> createState() => _VeggieDetailViewState();
}

class _VeggieDetailViewState extends State<VeggieDetailView> {
  late final Future<VeggieLocation> _future = VeggieRepository()
      .getVeggieLocation(
        widget.veggieId,
        AppSettingsController.instance.locale.languageCode,
      );

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return FutureBuilder<VeggieLocation>(
      future: _future,
      builder: (context, snapshot) {
        final veggieLocation = snapshot.data;
        final trades = veggieLocation?.trades ?? const [];

        return Scaffold(
          appBar: AppTopBar(
            title: veggieLocation?.location.name ?? l10n.screenVeggieDetail,
            navigation: AppTopBarNavigation.back,
            onNavigationTap: widget.navigateBack,
            onSearchTap: widget.openSearch,
          ),
          body: veggieLocation == null
              ? const Center(child: CircularProgressIndicator())
              : ListView(
                  padding: context.scrollPadding(
                    const EdgeInsets.fromLTRB(
                      AppPadding.medium,
                      0,
                      AppPadding.medium,
                      AppPadding.small,
                    ),
                  ),
                  children: [
                    SurfaceCard(
                      margin: EdgeInsets.zero,
                      child: Column(
                        children: [
                          for (final (index, trade) in trades.indexed) ...[
                            if (index > 0) const AppHDivider(),
                            VeggieTradeRow(trade: trade),
                          ],
                        ],
                      ),
                    ),
                  ],
                ),
        );
      },
    );
  }
}
