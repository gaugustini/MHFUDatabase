package com.gaugustini.mhfudatabase.data.di

import android.content.Context
import androidx.room.Room
import com.gaugustini.mhfudatabase.data.database.AppDatabase
import com.gaugustini.mhfudatabase.data.database.DatabaseMigration
import com.gaugustini.mhfudatabase.data.database.dao.ArmorDao
import com.gaugustini.mhfudatabase.data.database.dao.ArmorSetDao
import com.gaugustini.mhfudatabase.data.database.dao.DecorationDao
import com.gaugustini.mhfudatabase.data.database.dao.ItemDao
import com.gaugustini.mhfudatabase.data.database.dao.LocationDao
import com.gaugustini.mhfudatabase.data.database.dao.MonsterDao
import com.gaugustini.mhfudatabase.data.database.dao.QuestDao
import com.gaugustini.mhfudatabase.data.database.dao.SkillDao
import com.gaugustini.mhfudatabase.data.database.dao.UserEquipmentSetDao
import com.gaugustini.mhfudatabase.data.database.dao.WeaponDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, "data.db"
        )
            .createFromAsset("database/data.db")
            .addMigrations(*DatabaseMigration.allMigrations(context))
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideArmorDao(appDatabase: AppDatabase): ArmorDao {
        return appDatabase.armorDao()
    }

    @Provides
    @Singleton
    fun provideArmorSetDao(appDatabase: AppDatabase): ArmorSetDao {
        return appDatabase.armorSetDao()
    }

    @Provides
    @Singleton
    fun provideSkillDao(appDatabase: AppDatabase): SkillDao {
        return appDatabase.skillDao()
    }

    @Provides
    @Singleton
    fun provideItemDao(appDatabase: AppDatabase): ItemDao {
        return appDatabase.itemDao()
    }

    @Provides
    @Singleton
    fun provideDecorationDao(appDatabase: AppDatabase): DecorationDao {
        return appDatabase.decorationDao()
    }

    @Provides
    @Singleton
    fun provideMonsterDao(appDatabase: AppDatabase): MonsterDao {
        return appDatabase.monsterDao()
    }

    @Provides
    @Singleton
    fun provideLocationDao(appDatabase: AppDatabase): LocationDao {
        return appDatabase.locationDao()
    }

    @Provides
    @Singleton
    fun provideQuestDao(appDatabase: AppDatabase): QuestDao {
        return appDatabase.questDao()
    }

    @Provides
    @Singleton
    fun provideUserEquipmentSetDao(appDatabase: AppDatabase): UserEquipmentSetDao {
        return appDatabase.userEquipmentSetDao()
    }

    @Provides
    @Singleton
    fun provideWeaponDao(appDatabase: AppDatabase): WeaponDao {
        return appDatabase.weaponDao()
    }

}
