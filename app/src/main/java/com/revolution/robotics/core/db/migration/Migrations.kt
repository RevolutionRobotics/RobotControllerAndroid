package com.revolution.robotics.core.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migrations  {
    companion object {
        val MIGRATION_20_21 = object : Migration(20, 21) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE 'CompletedChallenge' ('challengeId' TEXT NOT NULL, " +
                            "PRIMARY KEY('challengeId'))"
                )
            }
        }
    }
}