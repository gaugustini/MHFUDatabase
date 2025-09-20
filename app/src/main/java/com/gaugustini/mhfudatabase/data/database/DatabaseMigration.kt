package com.gaugustini.mhfudatabase.data.database

import android.content.Context
import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigration {

    private fun fromAsset(
        context: Context,
        start: Int,
        end: Int
    ): Migration {
        return object : Migration(start, end) {
            override fun migrate(db: SupportSQLiteDatabase) {
                val fileName = "database/migrations/${start}_${end}.sql"

                try {
                    context.assets.open(fileName).bufferedReader().useLines { lines ->
                        lines.filter { it.isNotBlank() && !it.startsWith("--") }.forEach { sql ->
                            db.execSQL(sql)
                        }
                    }
                } catch (e: Exception) {
                    Log.e("DatabaseMigration", "Error running migration from $start -> $end", e)
                }
            }
        }
    }

    fun allMigrations(context: Context): Array<Migration> = arrayOf(
        // Add migrations here
    )

}
