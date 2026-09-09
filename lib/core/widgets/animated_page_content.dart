import 'package:flutter/material.dart';

class const AnimatedPageContent<T>({
  required final T value,
  required final int Function(T) index,
  required final Widget Function(BuildContext, T) builder,
  final Duration duration = const Duration(milliseconds: 300),
  super.key,
}) extends StatefulWidget {
  @override
  State<AnimatedPageContent<T>> createState() => _AnimatedPageContentState<T>();
}

class _AnimatedPageContentState<T> extends State<AnimatedPageContent<T>> {
  late int _previousIndex = widget.index(widget.value);
  bool _forward = true;

  @override
  void didUpdateWidget(covariant AnimatedPageContent<T> oldWidget) {
    super.didUpdateWidget(oldWidget);
    final newIndex = widget.index(widget.value);
    if (newIndex != _previousIndex) {
      _forward = newIndex > _previousIndex;
      _previousIndex = newIndex;
    }
  }

  @override
  Widget build(BuildContext context) {
    final currentIndex = widget.index(widget.value);
    final forward = _forward;

    return ClipRect(
      child: AnimatedSwitcher(
        duration: widget.duration,
        switchInCurve: Curves.easeOut,
        switchOutCurve: Curves.easeIn,
        layoutBuilder: (currentChild, previousChildren) {
          final children = [...previousChildren];
          if (currentChild != null) {
            if (forward) {
              children.add(currentChild);
            } else {
              children.insert(0, currentChild);
            }
          }
          return Stack(alignment: Alignment.topLeft, children: children);
        },
        transitionBuilder: (child, animation) {
          final isIncoming = child.key == ValueKey(currentIndex);
          final sign = isIncoming == forward ? 1.0 : -1.0;
          return SlideTransition(
            position: Tween<Offset>(
              begin: Offset(sign, 0),
              end: Offset.zero,
            ).animate(animation),
            child: child,
          );
        },
        child: KeyedSubtree(
          key: ValueKey(currentIndex),
          child: widget.builder(context, widget.value),
        ),
      ),
    );
  }
}
