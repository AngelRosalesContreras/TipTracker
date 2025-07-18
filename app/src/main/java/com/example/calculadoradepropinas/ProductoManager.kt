package com.example.calculadoradepropinas

import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

object ProductoManager {

    private lateinit var adapter: PublicacionAdapter  // Variable global para el adaptador
    private lateinit var publicaciones: List<Publicacion>  // Variable global para las publicaciones
    private var totalGeneral = 0.0

    fun configurarPublicacionUsuarioActual(activity: AppCompatActivity, imageViewUser: ImageView, textViewUserName: TextView, textViewUserDescription: TextView, usuarioActual: Publicacion) {
        // Configurar los elementos de la vista con los datos del usuario actual
        imageViewUser.setImageResource(usuarioActual.imagen)
        textViewUserName.text = usuarioActual.nombre
        textViewUserDescription.text = usuarioActual.descripcion
    }


    // Configurar el ListView con las publicaciones de otros usuarios
    fun configurarLista(activity: AppCompatActivity, listViewOtherPosts: ListView) {
        val publicaciones = listOf(
            Publicacion(R.drawable.img2, "Hamburguesa", "Jugosa carne de res a la parrilla, servida en un pan brioche suave y dorado. Acompañada de lechuga fresca, tomate maduro, cebolla roja crujiente y pepinillos en vinagre.", 56.55, 0,00.00),
            Publicacion(R.drawable.img3, "Hotdog Clásico", "Salchicha de alta calidad envuelta en un panecillo suave y esponjoso. Complementado con mostaza amarilla, kétchup, cebolla picada fresca y una pizca de relish de pepinillos. ",27.50,0,00.00),
            Publicacion(R.drawable.img4, "Nachos Supremos", "Crujientes totopos de maíz cubiertos con una generosa capa de queso cheddar fundido. Acompañados de frijoles refritos cremosos, pico de gallo fresco con tomate, cebolla y cilantro, además de jalapeños picantes para un toque de calor.", 36.00,0,00.00),
            Publicacion(R.drawable.img5, "Malteadas Clásicas", "Deléitate con nuestras irresistibles malteadas, preparadas al estilo tradicional con helado cremoso de vainilla. Disponibles en una variedad de sabores clásicos como fresa, chocolate o vainilla. Mezcladas a la perfección con leche fresca, garantizando una textura suave y cremosa. Decoradas con una cima de nata montada y una cereza en la cima. ", 14.50,0,00.00),
            Publicacion(R.drawable.img6, "Donas Artesanales", "Elige entre una variedad tentadora de sabores, desde clásicos como glaseado de azúcar o chocolate hasta opciones más creativas como crema de vainilla, fresa con chispas de chocolate, o caramelo salado. Decoradas con toppings deliciosos como virutas de chocolate, nueces picadas o chispas de colores.", 16.00,0,00.00),
            // Agrega más publicaciones según necesites
        )

        //val adapter = PublicacionAdapter(activity, R.layout.item_productos, publicaciones)
        //listViewOtherPosts.adapter = adapter
        adapter = PublicacionAdapter(activity, R.layout.item_productos, publicaciones, object : OnCantidadChangedListener {
            override fun onCantidadIncreased(position: Int) {
                val currentPublicacion = publicaciones[position]
                currentPublicacion.cantidad++
                // Actualizar la vista del adaptador después de cambiar la cantidad
                adapter.notifyDataSetChanged()
            }

            override fun onCantidadDecreased(position: Int) {
                val currentPublicacion = publicaciones[position]
                if (currentPublicacion.cantidad > 0) {
                    currentPublicacion.cantidad--
                    // Actualizar la vista del adaptador después de cambiar la cantidad
                    adapter.notifyDataSetChanged()
                }
            }
        })

        listViewOtherPosts.adapter = adapter
    }


    fun getAdapter(): PublicacionAdapter {
        return adapter
    }

    fun actualizarTotalGeneral(nuevoTotal: Double) {
        totalGeneral = nuevoTotal
    }

    fun obtenerTotalGeneral(): Double {
        return totalGeneral
    }
}