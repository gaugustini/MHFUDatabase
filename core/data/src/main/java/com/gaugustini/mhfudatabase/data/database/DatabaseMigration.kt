package com.gaugustini.mhfudatabase.data.database

import android.content.Context
import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigration {

    private fun fromAsset(
        context: Context,
        startVersion: Int,
        endVersion: Int
    ): Migration {
        return object : Migration(startVersion, endVersion) {
            override fun migrate(db: SupportSQLiteDatabase) {
                val fileName = "database/migrations/${startVersion}_${endVersion}.sql"

                try {
                    context.assets.open(fileName).bufferedReader().useLines { lines ->
                        lines.filter { it.isNotBlank() && !it.startsWith("--") }.forEach { sql ->
                            db.execSQL(sql)
                        }
                    }
                } catch (error: Exception) {
                    Log.e(
                        "DatabaseMigration",
                        "Error running migration from $startVersion -> $endVersion",
                        error
                    )
                }
            }
        }
    }

    fun allMigrations(context: Context): Array<Migration> = arrayOf(
        // Add migrations here
    )

}
