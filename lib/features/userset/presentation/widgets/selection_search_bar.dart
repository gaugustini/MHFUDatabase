import 'package:flutter/material.dart';

import '../../../../core/theme/app_dimensions.dart';
import '../../../../l10n/app_localizations.dart';

class const SelectionSearchBar({
  required final ValueChanged<String> onQueryChange,
  required final VoidCallback onFilterTap,
  super.key,
}) extends StatefulWidget implements PreferredSizeWidget {
  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);

  @override
  State<SelectionSearchBar> createState() => _SelectionSearchBarState();
}

class _SelectionSearchBarState extends State<SelectionSearchBar> {
  final _controller = TextEditingController();

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context)!;
    final colors = Theme.of(context).colorScheme;
    final isCompact = Theme.of(context).visualDensity.horizontal < 0;
    final barPadding = isCompact ? AppPadding.medium : AppPadding.small;

    return AppBar(
      scrolledUnderElevation: 0,
      titleSpacing: 0,
      automaticallyImplyLeading: false,
      title: Padding(
        padding: EdgeInsets.all(barPadding),
        child: TextField(
          controller: _controller,
          style: Theme.of(context).textTheme.titleMedium,
          decoration: InputDecoration(
            filled: true,
            fillColor: colors.surface,
            border: OutlineInputBorder(
              borderRadius: BorderRadius.circular(AppRadius.medium),
              borderSide: BorderSide.none,
            ),
            contentPadding: const EdgeInsets.symmetric(
              horizontal: AppSpacing.medium,
            ),
            prefixIcon: IconButton(
              icon: const Icon(Icons.arrow_back),
              onPressed: () => Navigator.of(context).pop(),
            ),
            hintText: l10n.userSetSelectionSearch,
            hintStyle: Theme.of(context).textTheme.titleMedium,
            suffixIcon: Row(
              mainAxisSize: MainAxisSize.min,
              children: [
                ValueListenableBuilder(
                  valueListenable: _controller,
                  builder: (context, value, _) => value.text.isEmpty
                      ? const SizedBox.shrink()
                      : IconButton(
                          icon: const Icon(Icons.clear),
                          onPressed: () {
                            _controller.clear();
                            widget.onQueryChange('');
                            setState(() {});
                          },
                        ),
                ),
                IconButton(
                  icon: const Icon(Icons.filter_list),
                  onPressed: widget.onFilterTap,
                ),
              ],
            ),
          ),
          textInputAction: TextInputAction.search,
          onChanged: widget.onQueryChange,
        ),
      ),
    );
  }
}
