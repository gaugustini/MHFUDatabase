import '../../../core/domain/enums.dart';
import '../../item/domain/item.dart';

class const ItemCombination({
  required final Item itemCreated,
  required final Item itemA,
  required final Item itemB,
  required final ItemCombinationType type,
  required final int quantityMin,
  required final int quantityMax,
  required final int percentage,
});
