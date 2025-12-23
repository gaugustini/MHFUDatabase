package com.gaugustini.mhfudatabase.data.database

import android.content.Context
import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Helper object that provides database migrations for the Room database.
 */
object DatabaseMigration {

    /**
     * Creates a [Migration] that executes SQL statements from a file in the `assets` folder.
     *
     * The SQL file is expected to be located in `database/migrations/` and named in the format
     * `${startVersion}_${endVersion}.sql`.
     */
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

    /**
     * Returns an array of all migrations for the database.
     */
    fun allMigrations(context: Context): Array<Migration> = arrayOf(
        // Add migrations here
    )

}
