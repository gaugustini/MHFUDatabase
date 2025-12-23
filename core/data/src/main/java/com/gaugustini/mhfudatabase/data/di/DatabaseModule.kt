package com.gaugustini.mhfudatabase.data.di

import android.content.Context
import androidx.room.Room
import com.gaugustini.mhfudatabase.data.database.AppDatabase
import com.gaugustini.mhfudatabase.data.database.DatabaseMigration
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

}
