package com.gaugustini.mhfudatabase.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gaugustini.mhfudatabase.data.database.dao.ArmorDao
import com.gaugustini.mhfudatabase.data.database.dao.ArmorSetDao
import com.gaugustini.mhfudatabase.data.database.dao.DecorationDao
import com.gaugustini.mhfudatabase.data.database.dao.ItemDao
import com.gaugustini.mhfudatabase.data.database.dao.LocationDao
import com.gaugustini.mhfudatabase.data.database.dao.MonsterDao
import com.gaugustini.mhfudatabase.data.database.dao.QuestDao
import com.gaugustini.mhfudatabase.data.database.dao.SearchDao
import com.gaugustini.mhfudatabase.data.database.dao.SkillDao
import com.gaugustini.mhfudatabase.data.database.dao.UserEquipmentSetDao
import com.gaugustini.mhfudatabase.data.database.dao.WeaponDao
import com.gaugustini.mhfudatabase.data.database.entity.armor.ArmorEntity
import com.gaugustini.mhfudatabase.data.database.entity.armor.ArmorRecipeEntity
import com.gaugustini.mhfudatabase.data.database.entity.armor.ArmorSkillEntity
import com.gaugustini.mhfudatabase.data.database.entity.armor.ArmorTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.armorset.ArmorSetEntity
import com.gaugustini.mhfudatabase.data.database.entity.armorset.ArmorSetTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.decoration.DecorationEntity
import com.gaugustini.mhfudatabase.data.database.entity.decoration.DecorationRecipeEntity
import com.gaugustini.mhfudatabase.data.database.entity.decoration.DecorationSkillEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemCombinationEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.item.ItemTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationEntity
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.location.LocationTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.HitzoneEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.HitzoneTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterHitzoneEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterRewardEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterStatusEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.MonsterTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.RewardConditionEntity
import com.gaugustini.mhfudatabase.data.database.entity.monster.RewardConditionTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.quest.QuestEntity
import com.gaugustini.mhfudatabase.data.database.entity.quest.QuestMonsterEntity
import com.gaugustini.mhfudatabase.data.database.entity.quest.QuestTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillEntity
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillTreeEntity
import com.gaugustini.mhfudatabase.data.database.entity.skill.SkillTreeTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.userset.UserEquipmentSetArmorEntity
import com.gaugustini.mhfudatabase.data.database.entity.userset.UserEquipmentSetDecorationEntity
import com.gaugustini.mhfudatabase.data.database.entity.userset.UserEquipmentSetEntity
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponAmmoBowEntity
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponAmmoBowgunEntity
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponEntity
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponParentEntity
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponRecipeEntity
import com.gaugustini.mhfudatabase.data.database.entity.weapon.WeaponTextEntity

/**
 * The [RoomDatabase] used by the application.
 */
@Database(
    entities = [
        // Armor
        ArmorEntity::class, ArmorTextEntity::class,
        ArmorSkillEntity::class, ArmorRecipeEntity::class,
        // Armor Set
        ArmorSetEntity::class, ArmorSetTextEntity::class,
        // Decoration
        DecorationEntity::class, DecorationSkillEntity::class, DecorationRecipeEntity::class,
        // Item
        ItemEntity::class, ItemTextEntity::class, ItemCombinationEntity::class,
        // Location
        LocationEntity::class, LocationTextEntity::class, LocationItemEntity::class,
        // Monster
        MonsterEntity::class, MonsterTextEntity::class,
        HitzoneEntity::class, HitzoneTextEntity::class, MonsterHitzoneEntity::class,
        MonsterStatusEntity::class, MonsterItemEntity::class,
        RewardConditionEntity::class, RewardConditionTextEntity::class, MonsterRewardEntity::class,
        // Quest
        QuestEntity::class, QuestTextEntity::class, QuestMonsterEntity::class,
        // Skill
        SkillTreeEntity::class, SkillTreeTextEntity::class,
        SkillEntity::class, SkillTextEntity::class,
        // User Equipment Set
        UserEquipmentSetEntity::class, UserEquipmentSetArmorEntity::class,
        UserEquipmentSetDecorationEntity::class,
        // Weapon
        WeaponEntity::class, WeaponTextEntity::class, WeaponParentEntity::class,
        WeaponAmmoBowEntity::class, WeaponAmmoBowgunEntity::class,
        WeaponRecipeEntity::class,
    ],
    version = 5,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun armorDao(): ArmorDao

    abstract fun armorSetDao(): ArmorSetDao

    abstract fun decorationDao(): DecorationDao

    abstract fun itemDao(): ItemDao

    abstract fun locationDao(): LocationDao

    abstract fun monsterDao(): MonsterDao

    abstract fun questDao(): QuestDao

    abstract fun searchDao(): SearchDao

    abstract fun skillDao(): SkillDao

    abstract fun userEquipmentSetDao(): UserEquipmentSetDao

    abstract fun weaponDao(): WeaponDao

}
