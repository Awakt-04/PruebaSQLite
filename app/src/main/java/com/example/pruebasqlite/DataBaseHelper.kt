package com.example.pruebasqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context,DATABASE,null,DATABASE_VERSION){
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE = "Mochila.db"
        internal const val TABLA_MOCHILA = "articulos"
        internal const val KEY_ID = "_id"
        internal const val COLUMN_TIPO_ARTICULO = "tipoarticulo"
        internal const val COLUMN_NOMBRE = "nombre"
        internal const val COLUMN_PESO = "peso"
        internal const val COLUMN_PRECIO = "precio"
        internal const val COLUMN_VIDA = "vida"
        internal const val COLUMN_ATAQUE = "ataque"
        internal const val COLUMN_DEFENSA = "defensa"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLA_MOCHILA ($KEY_ID INTEGER PRIMARY KEY," +
                "$COLUMN_NOMBRE TEXT, $COLUMN_TIPO_ARTICULO TEXT," +
                "$COLUMN_PESO INTEGER, $COLUMN_PRECIO INTEGER," +
                "$COLUMN_ATAQUE INTEGER, $COLUMN_VIDA INTEGER, $COLUMN_DEFENSA INTEGER)"

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldV: Int, newV: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_MOCHILA")
        onCreate(db)
    }
}