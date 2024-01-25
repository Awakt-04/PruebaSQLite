package com.example.pruebasqlite

import android.annotation.SuppressLint
import android.content.ContentValues

class Mochila(private val articulos :DataBaseHelper){

    fun addArticulo (a: Articulo){

        var obj = false
        when (a.getTipoArticulo()) {
            Articulo.TipoArticulo.ARMA -> {
                obj = when (a.getNombre()) {
                    Articulo.Nombre.BASTON, Articulo.Nombre.ESPADA, Articulo.Nombre.DAGA,
                    Articulo.Nombre.MARTILLO, Articulo.Nombre.GARRAS -> {
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
            Articulo.TipoArticulo.OBJETO -> {
                obj = when (a.getNombre()) {
                    Articulo.Nombre.POCION, Articulo.Nombre.IRA -> {
                        true
                    }

                    else -> false
                }
            }
            Articulo.TipoArticulo.PROTECCION -> {
                when (a.getNombre()) {
                    Articulo.Nombre.ESCUDO, Articulo.Nombre.ARMADURA -> {
                        obj = true
                    }
                    else -> obj = false
                }
            }
        }

        if (obj) {
            val db = articulos.readableDatabase
            val values = ContentValues().apply {
                put(DataBaseHelper.COLUMN_NOMBRE, a.getNombre().name)
                put(DataBaseHelper.COLUMN_TIPO_ARTICULO, a.getTipoArticulo().name)
                put(DataBaseHelper.COLUMN_PESO, a.getPeso())
                put(DataBaseHelper.COLUMN_PRECIO, a.getPrecio())
                put(DataBaseHelper.COLUMN_ATAQUE, a.getAumentoAtaque())
                put(DataBaseHelper.COLUMN_VIDA, a.getAumentoVida())
                put(DataBaseHelper.COLUMN_DEFENSA, a.getAumentoDefensa())
            }
            db.insert(DataBaseHelper.TABLA_MOCHILA, null, values)
            db.close()
        }
    }

    fun getNumeroArticulos() :Int{
        var num = 0
        val selectQuery = "SELECT COUNT(*) FROM ${DataBaseHelper.TABLA_MOCHILA}"
        val db = articulos.readableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if(cursor.moveToFirst()){
            do{
                num++
            }while(cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return num
    }

    @SuppressLint("Range")
    fun getContenido() :ArrayList<Articulo>{
        val listaArticulos = ArrayList<Articulo>()
        val selectQuery = "SELECT * FROM ${DataBaseHelper.TABLA_MOCHILA}"
        val db = articulos.readableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if(cursor.moveToFirst()){
            do{
                val id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.KEY_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_NOMBRE))
                val tipoArticulo = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_TIPO_ARTICULO))
                val peso = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_PESO))
                val precio = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_PRECIO))
                val ataque = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_ATAQUE))
                val vida = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_VIDA))
                val defensa = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_DEFENSA))

                listaArticulos.add(Articulo(Articulo.TipoArticulo.valueOf(tipoArticulo),
                    Articulo.Nombre.valueOf(nombre),peso,precio))
            }while(cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return listaArticulos
    }

    @SuppressLint("Range")
    fun findObjeto (nombreArt : Articulo.Nombre) :Articulo?{
        var articulo: Articulo? = null
        val selectQuery = "SELECT * FROM ${DataBaseHelper.TABLA_MOCHILA} WHERE nombre=${nombreArt.name} LIMIT 1"
        val db = articulos.readableDatabase
        val cursor = db.rawQuery(selectQuery,null)
        if(cursor.moveToFirst()){
            val id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.KEY_ID))
            val nombre = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_NOMBRE))
            val tipoArticulo = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_TIPO_ARTICULO))
            val peso = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_PESO))
            val precio = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_PRECIO))

            articulo = Articulo(Articulo.TipoArticulo.valueOf(tipoArticulo),
                Articulo.Nombre.valueOf(nombre),peso,precio)
        }

        cursor.close()
        db.close()

        return articulo
    }


//    fun addArticulo(articulo: Articulo) {
//        if (articulo.getPeso() <= pesoMochila) {
//            when (articulo.getTipoArticulo()) {
//                Articulo.TipoArticulo.ARMA -> {
//                    when (articulo.getNombre()) {
//                        Articulo.Nombre.BASTON, Articulo.Nombre.ESPADA, Articulo.Nombre.DAGA,
//                        Articulo.Nombre.MARTILLO, Articulo.Nombre.GARRAS -> {
//                            contenido.add(articulo)
//                            this.pesoMochila -= articulo.getPeso()
//                            println("${articulo.getNombre()} ha sido añadido a la mochila.")
//                        }
//                        else -> println("Nombre del artículo no válido para el tipo ARMA.")
//                    }
//                }
//                Articulo.TipoArticulo.OBJETO -> {
//                    when (articulo.getNombre()) {
//                        Articulo.Nombre.POCION, Articulo.Nombre.IRA -> {
//                            contenido.add(articulo)
//                            this.pesoMochila -= articulo.getPeso()
//                            println("${articulo.getNombre()} ha sido añadido a la mochila.")
//                        }
//                        else -> println("Nombre del artículo no válido para el tipo OBJETO.")
//                    }
//                }
//                Articulo.TipoArticulo.PROTECCION -> {
//                    when (articulo.getNombre()) {
//                        Articulo.Nombre.ESCUDO, Articulo.Nombre.ARMADURA -> {
//                            contenido.add(articulo)
//                            this.pesoMochila -= articulo.getPeso()
//                            println("${articulo.getNombre()} ha sido añadido a la mochila.")
//                        }
//                        else -> println("Nombre del artículo no válido para el tipo PROTECCION.")
//                    }
//                }
//            }
//        } else {
//            println("El peso del artículo excede el límite de la mochila.")
//        }

//    fun getContenido(): ArrayList<Articulo> {
//        return contenido
//    }
//    fun findObjeto(nombre: Articulo.Nombre): Int {
//        return contenido.indexOfFirst { it.getNombre() == nombre }
//    }
//    override fun toString(): String {
//        return if (contenido.isEmpty()) {
//            "Mochila vacía"
//        } else {
//            "Artículos en la mochila: ${contenido.joinToString("\n")}"
//        }
//}
}

