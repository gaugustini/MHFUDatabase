import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

import '../../../../core/domain/enums.dart';
import '../../../../core/router/app_routes.dart';
import '../../../../core/settings/app_settings_controller.dart';
import '../../../../core/theme/app_dimensions.dart';
import '../../../../core/theme/screen_padding.dart';
import '../../../../core/widgets/animated_page_content.dart';
import '../../../../core/widgets/app_h_divider.dart';
import '../../../../core/widgets/app_top_bar.dart';
import '../../../../core/widgets/button_page.dart';
import '../../../../core/widgets/entity_icon.dart';
import '../../../../core/widgets/filter_dropdown.dart';
import '../../../../core/widgets/list_item_layout.dart';
import '../../../../core/widgets/quest_group_label.dart';
import '../../../../core/widgets/section_card.dart';
import '../../../../core/widgets/surface_card.dart';
import '../../../../l10n/app_localizations.dart';
import '../../../quest/domain/quest.dart';
import '../../data/location_repository.dart';
import '../../domain/location.dart';

enum _LocationPage { summary, gathering, quest }

class const LocationDetailView({
  required final int locationId,
  required final VoidCallback navigateBack,
  required final VoidCallback openSearch,
  super.key,
}) extends StatefulWidget {
  @override
  State<LocationDetailView> createState() => _LocationDetailViewState();
}

class _LocationDetailViewState extends State<LocationDetailView> {
  late final Future<Location> _future = LocationRepository().getLocation(
    widget.locationId,
    AppSettingsController.instance.locale.languageCode,
  );
  _LocationPage _page = _LocationPage.summary;
  Rank? _rank;
  int? _area;

  void _goToPage(_LocationPage page) => setState(() => _page = page);

  void _handleBack() {
    if (_page == _LocationPage.summary) {
      widget.navigateBack();
    } else {
      _goToPage(_LocationPage.summary);
    }
  }

  Rank? _effectiveRank(Location location) {
    final points = location.gatheringPoints ?? const {};
    if (_rank != null && points.containsKey(_rank)) return _rank;
    final ranks = points.keys.toList();
    return ranks.isEmpty ? null : ranks.first;
  }

  int? _effectiveArea(Location location, Rank? rank) {
    final points = rank == null
        ? const <GatheringPoint>[]
        : (location.gatheringPoints?[rank] ?? const []);
    final areas = points.map((point) => point.area).toSet().toList()..sort();
    if (_area != null && areas.contains(_area)) return _area;
    return areas.isEmpty ? null : areas.first;
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;

    return FutureBuilder<Location>(
      future: _future,
      builder: (context, snapshot) {
        final location = snapshot.data;
        final rank = location == null ? null : _effectiveRank(location);
        final area = location == null ? null : _effectiveArea(location, rank);

        return PopScope(
          canPop: _page == _LocationPage.summary,
          onPopInvokedWithResult: (didPop, result) {
            if (!didPop) _goToPage(_LocationPage.summary);
          },
          child: Scaffold(
            appBar: AppTopBar(
              title: location?.name ?? l10n.screenLocationDetail,
              navigation: AppTopBarNavigation.back,
              onNavigationTap: _handleBack,
              onSearchTap: widget.openSearch,
              actions: _page != _LocationPage.gathering || location == null
                  ? const []
                  : [
                      IconButton(
                        icon: const Icon(Icons.map_outlined),
                        onPressed: () => _showMapDialog(
                          context,
                          location.id,
                          initialShowNodes: true,
                        ),
                      ),
                      IconButton(
                        icon: const Icon(Icons.help_outline),
                        onPressed: () => _showGatheringInfoDialog(context),
                      ),
                    ],
            ),
            body: location == null
                ? const Center(child: CircularProgressIndicator())
                : AnimatedPageContent<_LocationPage>(
                    value: _page,
                    index: (page) => page.index,
                    builder: (context, page) => switch (page) {
                      _LocationPage.summary => _SummaryPage(
                        location: location,
                        onChangePage: _goToPage,
                      ),
                      _LocationPage.gathering => _GatheringPage(
                        location: location,
                        rank: rank,
                        area: area,
                        onChangeRank: (value) => setState(() => _rank = value),
                        onChangeArea: (value) => setState(() => _area = value),
                      ),
                      _LocationPage.quest => _QuestPage(location: location),
                    },
                  ),
          ),
        );
      },
    );
  }
}

void _showMapDialog(
  BuildContext context,
  int locationId, {
  required bool initialShowNodes,
}) {
  showDialog<void>(
    context: context,
    barrierColor: Theme.of(context).colorScheme.scrim.withValues(alpha: 0.9),
    builder: (context) => _LocationMapDialog(
      locationId: locationId,
      initialShowNodes: initialShowNodes,
    ),
  );
}

void _showGatheringInfoDialog(BuildContext context) {
  final l10n = AppLocalizations.of(context)!;
  showDialog<void>(
    context: context,
    builder: (context) => AlertDialog(
      title: Text(l10n.locationGatheringInfoTitle),
      content: SingleChildScrollView(
        child: Text(l10n.locationGatheringInfoContent),
      ),
      actions: [
        TextButton(
          onPressed: () => Navigator.of(context).pop(),
          child: Text(MaterialLocalizations.of(context).okButtonLabel),
        ),
      ],
    ),
  );
}

class const _LocationMapDialog({
  required final int locationId,
  required final bool initialShowNodes,
}) extends StatefulWidget {
  @override
  State<_LocationMapDialog> createState() => _LocationMapDialogState();
}

class _LocationMapDialogState extends State<_LocationMapDialog> {
  late bool _showNodes = widget.initialShowNodes;

  // Arena has no gathering nodes.
  static const _arenaLocationId = 20;

  @override
  Widget build(BuildContext context) {
    final colors = Theme.of(context).colorScheme;
    final mapAsset = locationMapAsset(widget.locationId);
    final nodesAsset = widget.locationId == _arenaLocationId
        ? null
        : locationMapWithNodesAsset(widget.locationId);

    return Dialog(
      insetPadding: EdgeInsets.zero,
      backgroundColor: Colors.transparent,
      child: SizedBox.expand(
        child: Stack(
          children: [
            Positioned.fill(
              child: InteractiveViewer(
                minScale: 1,
                maxScale: 5,
                child: Stack(
                  fit: StackFit.expand,
                  children: [
                    if (mapAsset != null)
                      Image.asset(
                        'assets/images/$mapAsset.webp',
                        fit: BoxFit.contain,
                      ),
                    if (_showNodes && nodesAsset != null)
                      Image.asset(
                        'assets/images/$nodesAsset.webp',
                        fit: BoxFit.contain,
                      ),
                  ],
                ),
              ),
            ),
            Positioned(
              top: AppPadding.medium,
              right: AppPadding.medium,
              child: Row(
                children: [
                  if (nodesAsset != null) ...[
                    IconButton.filled(
                      style: IconButton.styleFrom(
                        backgroundColor: _showNodes
                            ? colors.primary
                            : colors.surface,
                        foregroundColor: _showNodes
                            ? colors.onPrimary
                            : colors.onSurface,
                      ),
                      onPressed: () => setState(() => _showNodes = !_showNodes),
                      icon: Icon(
                        _showNodes ? Icons.layers : Icons.layers_clear,
                      ),
                    ),
                    const SizedBox(width: AppSpacing.small),
                  ],
                  IconButton.filled(
                    style: IconButton.styleFrom(
                      backgroundColor: colors.surface,
                      foregroundColor: colors.onSurface,
                    ),
                    onPressed: () => Navigator.of(context).pop(),
                    icon: const Icon(Icons.close),
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class const _SummaryPage({
  required final Location location,
  required final ValueChanged<_LocationPage> onChangePage,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final gatheringPoints = location.gatheringPoints ?? const {};
    final quests = location.quests ?? const [];

    return ListView(
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
          child: InkWell(
            onTap: () => _showMapDialog(
              context,
              location.id,
              initialShowNodes: false,
            ),
            child: Padding(
              padding: const EdgeInsets.all(AppPadding.large),
              child: AspectRatio(
                aspectRatio: 1,
                child: locationMapAsset(location.id) == null
                    ? const Icon(Icons.map_outlined)
                    : Image.asset(
                        'assets/images/${locationMapAsset(location.id)}.webp',
                        fit: BoxFit.contain,
                      ),
              ),
            ),
          ),
        ),
        if (gatheringPoints.isNotEmpty) ...[
          const SizedBox(height: AppSpacing.medium),
          ButtonPage(
            title: l10n.locationGatheringSpots,
            onTap: () => onChangePage(_LocationPage.gathering),
          ),
        ],
        if (quests.isNotEmpty) ...[
          const SizedBox(height: AppSpacing.medium),
          ButtonPage(
            title: l10n.locationQuests,
            onTap: () => onChangePage(_LocationPage.quest),
          ),
        ],
      ],
    );
  }
}

String _rankLabel(AppLocalizations l10n, Rank rank) => switch (rank) {
  Rank.low => l10n.rankLow,
  Rank.high => l10n.rankHigh,
  Rank.g => l10n.rankG,
  Rank.treasure => l10n.rankTreasure,
  Rank.unranked => l10n.rankUnranked,
  Rank.training => l10n.rankTraining,
};

String _gatherTypeLabel(AppLocalizations l10n, GatherType type) =>
    switch (type) {
      GatherType.collect => l10n.locationGatherCollect,
      GatherType.mine => l10n.locationGatherMine,
      GatherType.bug => l10n.locationGatherBug,
      GatherType.fish => l10n.locationGatherFish,
    };

String _areaLabel(AppLocalizations l10n, int area) => switch (area) {
  -1 => l10n.locationSecretArea,
  0 => l10n.locationBaseCamp,
  _ => l10n.locationArea(area),
};

class const _GatheringPage({
  required final Location location,
  required final Rank? rank,
  required final int? area,
  required final ValueChanged<Rank> onChangeRank,
  required final ValueChanged<int> onChangeArea,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final allPoints = location.gatheringPoints ?? const {};
    final ranks = allPoints.keys.toList();
    final rankPoints = rank == null
        ? const <GatheringPoint>[]
        : (allPoints[rank] ?? const []);
    final areas = rankPoints.map((point) => point.area).toSet().toList()
      ..sort();
    final points = rankPoints.where((point) => point.area == area).toList();

    final pointsByNode = <int, List<GatheringPoint>>{};
    for (final point in points) {
      pointsByNode.putIfAbsent(point.node, () => []).add(point);
    }

    final selectedRank = rank;
    final selectedArea = area;

    return Column(
      children: [
        if (ranks.isNotEmpty && selectedRank != null)
          Padding(
            padding: const EdgeInsets.all(AppPadding.medium),
            child: Row(
              children: [
                FilterDropdown<Rank>(
                  value: selectedRank,
                  items: ranks,
                  labelBuilder: (value) => _rankLabel(l10n, value),
                  onChanged: onChangeRank,
                ),
                if (selectedArea != null) ...[
                  const SizedBox(width: AppSpacing.medium),
                  FilterDropdown<int>(
                    value: selectedArea,
                    items: areas,
                    labelBuilder: (value) => _areaLabel(l10n, value),
                    onChanged: onChangeArea,
                  ),
                ],
              ],
            ),
          ),
        Expanded(
          child: ListView(
            padding: context.scrollPadding(
              const EdgeInsets.fromLTRB(
                AppPadding.medium,
                0,
                AppPadding.medium,
                AppPadding.small,
              ),
            ),
            children: [
              for (final node in pointsByNode.keys) ...[
                _GatheringNodeGroup(
                  node: node,
                  points: pointsByNode[node]!,
                ),
                const SizedBox(height: AppSpacing.medium),
              ],
            ],
          ),
        ),
      ],
    );
  }
}

class const _GatheringNodeGroup({
  required final int node,
  required final List<GatheringPoint> points,
}) extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final first = points.first;
    final minMax = first.min == -1
        ? '∞'
        : first.min == first.max
        ? '${first.min}'
        : '${first.min}~${first.max}';

    return SectionCard(
      title: l10n.locationNode(
        node,
        _gatherTypeLabel(l10n, first.type),
        minMax,
      ),
      margin: EdgeInsets.zero,
      child: Column(
        children: [
          for (var index = 0; index < points.length; index++) ...[
            _GatheringPointRow(point: points[index]),
            if (index != points.length - 1) const AppHDivider(),
          ],
        ],
      ),
    );
  }
}

class const _GatheringPointRow({required final GatheringPoint point})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListItemLayout(
      leading: ItemEntityIcon(
        type: point.item.iconType,
        color: point.item.iconColor,
        size: AppSize.medium,
      ),
      headline: Text(
        point.item.name,
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      trailing: Text(
        '${point.percentage}%',
        style: Theme.of(context).textTheme.bodyMedium,
      ),
      onTap: point.item.id == 0
          ? null
          : () => context.push(AppRoutes.itemDetail(point.item.id)),
    );
  }
}

class const _QuestPage({required final Location location})
    extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final quests = location.quests ?? const <Quest>[];

    return ListView(
      padding: context.scrollPadding(
        const EdgeInsets.fromLTRB(
          AppPadding.medium,
          0,
          AppPadding.medium,
          AppPadding.small,
        ),
      ),
      children: [
        for (final quest in quests)
          SurfaceCard(
            margin: const EdgeInsets.only(bottom: AppSpacing.medium),
            child: ListItemLayout(
              leading: QuestGoalIcon(goal: quest.goalType),
              headline: Text(
                quest.name,
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
              ),
              trailing: Text(questGroupSummaryLabel(l10n, quest.group)),
              onTap: () => context.push(AppRoutes.questDetail(quest.id)),
            ),
          ),
      ],
    );
  }
}
