import '../../../core/domain/enums.dart';
import '../../item/data/item_repository.dart';
import '../domain/item_combination.dart';

class ItemCombinationRepository {
  ItemCombinationRepository([ItemRepository? itemRepository])
    : _itemRepository = itemRepository ?? ItemRepository();

  final ItemRepository _itemRepository;

  Future<List<ItemCombination>> getItemCombinationList(
    String language, {
    ItemCombinationType? type,
  }) {
    return _itemRepository.getItemCombinationList(language, type: type);
  }
}
