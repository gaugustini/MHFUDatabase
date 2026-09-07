import 'package:flutter/widgets.dart';

extension AppScreenPadding on BuildContext {
  EdgeInsets scrollPadding(EdgeInsets padding) =>
      padding + EdgeInsets.only(bottom: MediaQuery.paddingOf(this).bottom);
}
