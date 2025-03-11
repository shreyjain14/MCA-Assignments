package me.shreyjain.ete1.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserAuth.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USERS = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_USERS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USERNAME TEXT UNIQUE, " +
                "$COLUMN_PASSWORD TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addUser(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USERNAME, username)
        values.put(COLUMN_PASSWORD, password)
        
        val result = db.insert(TABLE_USERS, null, values)
        return result != -1L
    }

    fun checkUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_ID),
            "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(username, password),
            null,
            null,
            null
        )
        
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun updateCredentials(currentUsername: String, currentPassword: String, newUsername: String?, newPassword: String?): Boolean {
        if (newUsername == null && newPassword == null) return false
        if (!checkUser(currentUsername, currentPassword)) return false

        val db = this.writableDatabase
        val values = ContentValues()
        
        if (!newUsername.isNullOrBlank()) {
            values.put(COLUMN_USERNAME, newUsername)
        }
        if (!newPassword.isNullOrBlank()) {
            values.put(COLUMN_PASSWORD, newPassword)
        }

        val rowsAffected = db.update(
            TABLE_USERS,
            values,
            "$COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(currentUsername, currentPassword)
        )

        return rowsAffected > 0
    }

    fun getCurrentUsername(): String? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(COLUMN_USERNAME),
            null,
            null,
            null,
            null,
            null
        )

        var username: String? = null
        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME))
        }
        cursor.close()
        return username
    }
} 