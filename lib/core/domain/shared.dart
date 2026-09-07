import '../../features/item/domain/item.dart';
import '../../features/skill/domain/skill.dart';

class const SkillPoint({
  required final SkillTree skillTree,
  required final int points,
});

class const ItemQuantity({
  required final Item item,
  required final int quantity,
});

class const Usage<T>({required final T craftable, required final int quantity});
