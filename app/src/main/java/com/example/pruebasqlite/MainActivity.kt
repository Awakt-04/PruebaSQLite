package com.example.pruebasqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mochila = Mochila(DataBaseHelper(this))

        mochila.addArticulo(Articulo(Articulo.TipoArticulo.OBJETO,Articulo.Nombre.POCION,0,20))
        mochila.addArticulo(Articulo(Articulo.TipoArticulo.ARMA,Articulo.Nombre.ESPADA,0,20))
        mochila.addArticulo(Articulo(Articulo.TipoArticulo.PROTECCION,Articulo.Nombre.ESCUDO,0,20))


        mochila.getNumeroArticulos()


        val listaArticulos = mochila.getContenido()

        val objeto = mochila.findObjeto(Articulo.Nombre.ESPADA)

    }


}


