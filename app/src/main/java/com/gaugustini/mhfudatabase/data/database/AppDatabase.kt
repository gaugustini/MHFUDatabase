package com.gaugustini.mhfudatabase.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gaugustini.mhfudatabase.data.database.dao.ArmorDao
import com.gaugustini.mhfudatabase.data.database.dao.DecorationDao
import com.gaugustini.mhfudatabase.data.database.dao.ItemDao
import com.gaugustini.mhfudatabase.data.database.dao.LocationDao
import com.gaugustini.mhfudatabase.data.database.dao.MonsterDao
import com.gaugustini.mhfudatabase.data.database.dao.QuestDao
import com.gaugustini.mhfudatabase.data.database.dao.SearchDao
import com.gaugustini.mhfudatabase.data.database.dao.SkillDao
import com.gaugustini.mhfudatabase.data.database.dao.WeaponDao
import com.gaugustini.mhfudatabase.data.database.entity.ArmorEntity
import com.gaugustini.mhfudatabase.data.database.entity.ArmorRecipeEntity
import com.gaugustini.mhfudatabase.data.database.entity.ArmorSetEntity
import com.gaugustini.mhfudatabase.data.database.entity.ArmorSetTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.ArmorSkillEntity
import com.gaugustini.mhfudatabase.data.database.entity.ArmorTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.DecorationEntity
import com.gaugustini.mhfudatabase.data.database.entity.DecorationRecipeEntity
import com.gaugustini.mhfudatabase.data.database.entity.DecorationSkillEntity
import com.gaugustini.mhfudatabase.data.database.entity.HitzoneEntity
import com.gaugustini.mhfudatabase.data.database.entity.HitzoneTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.ItemCombinationEntity
import com.gaugustini.mhfudatabase.data.database.entity.ItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.ItemTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.LocationEntity
import com.gaugustini.mhfudatabase.data.database.entity.LocationItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.LocationTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.MonsterEntity
import com.gaugustini.mhfudatabase.data.database.entity.MonsterHitzoneEntity
import com.gaugustini.mhfudatabase.data.database.entity.MonsterItemEntity
import com.gaugustini.mhfudatabase.data.database.entity.MonsterRewardEntity
import com.gaugustini.mhfudatabase.data.database.entity.MonsterStatusEntity
import com.gaugustini.mhfudatabase.data.database.entity.MonsterTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.QuestEntity
import com.gaugustini.mhfudatabase.data.database.entity.QuestMonsterEntity
import com.gaugustini.mhfudatabase.data.database.entity.QuestTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.RewardConditionEntity
import com.gaugustini.mhfudatabase.data.database.entity.RewardConditionTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.SkillEntity
import com.gaugustini.mhfudatabase.data.database.entity.SkillTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.SkillTreeEntity
import com.gaugustini.mhfudatabase.data.database.entity.SkillTreeTextEntity
import com.gaugustini.mhfudatabase.data.database.entity.WeaponAmmoBowEntity
import com.gaugustini.mhfudatabase.data.database.entity.WeaponAmmoBowgunEntity
import com.gaugustini.mhfudatabase.data.database.entity.WeaponEntity
import com.gaugustini.mhfudatabase.data.database.entity.WeaponParentEntity
import com.gaugustini.mhfudatabase.data.database.entity.WeaponRecipeEntity
import com.gaugustini.mhfudatabase.data.database.entity.WeaponTextEntity

@Database(
    entities = [
        ArmorEntity::class, ArmorTextEntity::class,
        ArmorSkillEntity::class, ArmorRecipeEntity::class,
        ArmorSetEntity::class, ArmorSetTextEntity::class,
        DecorationEntity::class, DecorationSkillEntity::class, DecorationRecipeEntity::class,
        ItemEntity::class, ItemTextEntity::class, ItemCombinationEntity::class,
        LocationEntity::class, LocationTextEntity::class, LocationItemEntity::class,
        MonsterEntity::class, MonsterTextEntity::class,
        HitzoneEntity::class, HitzoneTextEntity::class, MonsterHitzoneEntity::class,
        MonsterStatusEntity::class, MonsterItemEntity::class,
        RewardConditionEntity::class, RewardConditionTextEntity::class, MonsterRewardEntity::class,
        QuestEntity::class, QuestTextEntity::class, QuestMonsterEntity::class,
        SkillTreeEntity::class, SkillTreeTextEntity::class,
        SkillEntity::class, SkillTextEntity::class,
        WeaponEntity::class, WeaponTextEntity::class, WeaponParentEntity::class,
        WeaponAmmoBowEntity::class, WeaponAmmoBowgunEntity::class,
        WeaponRecipeEntity::class,
    ],
    version = 2,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun searchDao(): SearchDao

    abstract fun armorDao(): ArmorDao

    abstract fun skillDao(): SkillDao

    abstract fun itemDao(): ItemDao

    abstract fun decorationDao(): DecorationDao

    abstract fun monsterDao(): MonsterDao

    abstract fun locationDao(): LocationDao

    abstract fun questDao(): QuestDao

    abstract fun weaponDao(): WeaponDao

}
