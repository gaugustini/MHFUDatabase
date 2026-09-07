import '../../item/domain/item.dart';
import '../../location/domain/location.dart';

class const VeggieLocation({
  required final int id,
  required final Location location,
  required final int locationArea,
  final List<VeggieTrade>? trades,
});

class const VeggieTrade({
  required final Item itemTraded,
  required final Item itemCommon,
  required final Item itemRare,
});

class const VeggieUsage({
  required final Location location,
  required final int area,
  required final VeggieTrade trade,
});
