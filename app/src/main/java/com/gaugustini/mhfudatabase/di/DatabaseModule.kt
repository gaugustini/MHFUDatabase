package com.gaugustini.mhfudatabase.di

import android.content.Context
import androidx.room.Room
import com.gaugustini.mhfudatabase.data.database.AppDatabase
import com.gaugustini.mhfudatabase.data.database.dao.ArmorDao
import com.gaugustini.mhfudatabase.data.database.dao.DecorationDao
import com.gaugustini.mhfudatabase.data.database.dao.ItemDao
import com.gaugustini.mhfudatabase.data.database.dao.LocationDao
import com.gaugustini.mhfudatabase.data.database.dao.MonsterDao
import com.gaugustini.mhfudatabase.data.database.dao.QuestDao
import com.gaugustini.mhfudatabase.data.database.dao.SearchDao
import com.gaugustini.mhfudatabase.data.database.dao.SkillDao
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
            .build()
    }

    @Provides
    fun provideSearchDao(appDatabase: AppDatabase): SearchDao {
        return appDatabase.searchDao()
    }

    @Provides
    fun provideArmorDao(appDatabase: AppDatabase): ArmorDao {
        return appDatabase.armorDao()
    }

    @Provides
    fun provideSkillDao(appDatabase: AppDatabase): SkillDao {
        return appDatabase.skillDao()
    }

    @Provides
    fun provideItemDao(appDatabase: AppDatabase): ItemDao {
        return appDatabase.itemDao()
    }

    @Provides
    fun provideDecorationDao(appDatabase: AppDatabase): DecorationDao {
        return appDatabase.decorationDao()
    }

    @Provides
    fun provideMonsterDao(appDatabase: AppDatabase): MonsterDao {
        return appDatabase.monsterDao()
    }

    @Provides
    fun provideLocationDao(appDatabase: AppDatabase): LocationDao {
        return appDatabase.locationDao()
    }

    @Provides
    fun provideQuestDao(appDatabase: AppDatabase): QuestDao {
        return appDatabase.questDao()
    }

    @Provides
    fun provideWeaponDao(appDatabase: AppDatabase): WeaponDao {
        return appDatabase.weaponDao()
    }

}
